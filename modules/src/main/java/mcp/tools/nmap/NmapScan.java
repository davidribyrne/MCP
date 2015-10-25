package mcp.tools.nmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

import mcp.jobmanager.executors.Callback;
import mcp.jobmanager.executors.CommandLineExecutor;
import mcp.jobmanager.executors.ExecutorManager;
import mcp.jobmanager.jobs.JobState;
import mcp.modules.GeneralOptions;
import mcp.modules.nmap.NmapGeneralOptions;
import mcp.tools.nmap.parser.OnePassParser;
import net.dacce.commons.general.FileUtils;
import net.dacce.commons.general.UnexpectedException;
import net.dacce.commons.netaddr.Addresses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NmapScan implements Callback
{

	private boolean resume;
	private byte speed;
	private String outputFileName;
	private boolean resolve = false;
	private final List<FlagPair> flags;
	private final Addresses targets;
	private final static String TARGET_IP_FILE_SUFFIX = "--target-ips.txt";
	private final static String CONSOLE_OUT_FILE_SUFFIX = "--out";
	private final String jobName;
	final static Logger logger = LoggerFactory.getLogger(NmapScan.class);


	public NmapScan(String jobName, Addresses targets)
	{
		this.jobName = jobName;
		this.targets = targets;
		speed = NmapGeneralOptions.getInstance().getSpeed();
		resume = NmapGeneralOptions.getInstance().isResume();
		flags = new ArrayList<FlagPair>();
		outputFileName = GeneralOptions.getInstance().getScandataDirectory() + "nmap-" + jobName.replaceAll("[^a-zA-Z0-9\\-]", "-");
	}

	private enum ScanStatus
	{
		UNSTARTED, COMPLETE, RESUMABLE, RESTART
	}


	private ScanStatus checkNmapScanStatus()
	{
		String content;
		try
		{
			content = FileUtils.slurp(outputFileName + ".xml");
		}
		catch (FileNotFoundException | NoSuchFileException e)
		{
			return ScanStatus.UNSTARTED;
		}
		catch (IOException e)
		{
			throw new UnexpectedException("Problem while testing nmap result file " + outputFileName + " for completion: " + e.getLocalizedMessage(), e);
		}

		if (content.contains("</nmaprun>"))
		{
			return ScanStatus.COMPLETE;
		}

		if (content.contains("<nmaprun"))
		{
			return ScanStatus.RESUMABLE;
		}

		return ScanStatus.RESTART;
	}


	public void execute()
	{
		ScanStatus status = checkNmapScanStatus();
		if (status == ScanStatus.COMPLETE)
		{
			parseResults();
			return;
		}

		String targetFile = outputFileName + TARGET_IP_FILE_SUFFIX;
		try
		{
			FileUtils.writeToFile(targetFile, targets.getNmapList());
		}
		catch (FileNotFoundException e)
		{
			throw new UnexpectedException("Weird problem writing to nmap target file " + targetFile + ": " + e.getLocalizedMessage(), e);
		}
		catch (IOException e)
		{
			throw new UnexpectedException("Weird problem writing to nmap target file " + targetFile + ": " + e.getLocalizedMessage(), e);
		}
		CommandLineExecutor executor = new CommandLineExecutor("Nmap", jobName, NmapGeneralOptions.getInstance()
				.getNmapPath(), generateCommandArguments(status), GeneralOptions.getInstance().getScandataDirectory(),
				outputFileName + CONSOLE_OUT_FILE_SUFFIX, outputFileName + CONSOLE_OUT_FILE_SUFFIX, true);
		ExecutorManager.getInstance().execute(executor);
	}


	private List<String> generateCommandArguments(ScanStatus status)
	{
		List<String> args = new ArrayList<String>();
		args.add("-iL");
		args.add(outputFileName + TARGET_IP_FILE_SUFFIX);

		for (FlagPair pair : flags)
		{
			args.add(pair.flag.getFlag());
			if (pair.value != null)
			{
				args.add(pair.value);
			}
		}

		String fullOutputFile = outputFileName;
		args.add("-oA");
		args.add(fullOutputFile);

		args.add("-T" + speed);
		args.add("-vv");
		if (resume && (status == ScanStatus.RESUMABLE))
		{
			args.add(NmapFlag.RESUME.toString());
			args.add(fullOutputFile + ".xml");
		}

		if (!resolve)
		{
			args.add(NmapFlag.NEVER_DO_DNS.toString());
		}

		return args;
	}


	private class FlagPair
	{
		public FlagPair(NmapFlag flag, String value)
		{
			this.flag = flag;
			this.value = value;
		}

		NmapFlag flag;
		String value;
	}


	public void addFlag(NmapFlag flag)
	{
		addFlag(flag, null);
	}


	public void addFlag(NmapFlag flag, String value)
	{
		if (flag == NmapFlag.ALWAYS_DO_DNS)
		{
			resolve = true;
		}
		flags.add(new FlagPair(flag, value));
	}


	public void setSpeed(byte speed)
	{
		if ((speed < 0) || (speed > 5))
		{
			throw new IllegalArgumentException("Nmap speed is 0-5");
		}
		this.speed = speed;
	}


	public String getOutputFileName()
	{
		return outputFileName;
	}

	private void parseResults()
	{
		File f = new File(outputFileName + ".xml");
		if (!f.exists())
		{
			throw new IllegalStateException("Output file does not exist");
		}
		OnePassParser opp = new OnePassParser();
		opp.parse(f);
	}


	@Override
	public void jobComplete(JobState result)
	{
		parseResults();
	}


	public void setResolve(boolean resolve)
	{
		this.resolve = resolve;
	}

}
