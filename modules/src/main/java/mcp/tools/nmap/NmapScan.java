package mcp.tools.nmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.commons.WorkingDirectories;
import mcp.jobmanager.executors.CommandLineExecutor;
import mcp.jobmanager.executors.ExecutionScheduler;
import mcp.jobmanager.executors.JobCompleteCallback;
import mcp.jobmanager.jobs.JobState;
import mcp.modules.Modules;
import mcp.modules.nmap.NmapGeneralOptions;
import mcp.tools.nmap.parser.NmapXmlParser;
import space.dcce.commons.general.FileUtils;
import space.dcce.commons.general.UnexpectedException;
import space.dcce.commons.netaddr.Addresses;


public class NmapScan implements JobCompleteCallback
{

	private boolean resume;
	private byte speed;
	private String outputFileName;
	private boolean resolve = false;
	private final List<FlagPair> flags;
	private final Addresses targets;
	private final static String TARGET_IP_FILE_SUFFIX = "--target-ips.txt";
	private final static String CONSOLE_OUT_FILE_SUFFIX = "--stdout";
	private final String jobName;
	private String nmapPath;
	final static Logger logger = LoggerFactory.getLogger(NmapScan.class);

	
	public NmapScan(String jobName, Addresses targets)
	{
		NmapGeneralOptions nopt = (NmapGeneralOptions) Modules.instance.getModuleInstance(NmapGeneralOptions.class);
		this.jobName = jobName;
		this.targets = targets;
		speed = nopt.getSpeed();
		resume = nopt.isResume();
		nmapPath = nopt.getNmapPath();
		flags = new ArrayList<FlagPair>();
		outputFileName = WorkingDirectories.getScanDataDirectory() + "nmap-" + jobName.replaceAll("[^a-zA-Z0-9\\-]", "-");
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
			content = FileUtils.readFileToString(outputFileName + ".xml");
		}
		catch (@SuppressWarnings("unused") FileNotFoundException | NoSuchFileException e)
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
			jobComplete(JobState.ALREADY_RUN);
			return;
		}

		String targetFile = outputFileName + TARGET_IP_FILE_SUFFIX;
		try
		{
			FileUtils.writeToFile(targetFile, targets.getCIDRList("\n"));
		}
		catch (FileNotFoundException e)
		{
			throw new UnexpectedException("Weird problem writing to nmap target file " + targetFile + ": " + e.getLocalizedMessage(), e);
		}
		catch (IOException e)
		{
			throw new UnexpectedException("Weird problem writing to nmap target file " + targetFile + ": " + e.getLocalizedMessage(), e);
		}
		List<String> arguments = generateCommandArguments(status);
		CommandLineExecutor executor = new CommandLineExecutor("Nmap", jobName, nmapPath, arguments, WorkingDirectories.getWorkingDirectory(),
				outputFileName + CONSOLE_OUT_FILE_SUFFIX, outputFileName + CONSOLE_OUT_FILE_SUFFIX, true, 0);
		executor.setCallback(this);
		logger.trace("Starting execution for " + executor.toString());

		ExecutionScheduler.getInstance().executeImmediately(executor);
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

	private void parseResults() throws FileNotFoundException
	{
		File f = new File(outputFileName + ".xml");
		if (!f.exists())
		{
			throw new FileNotFoundException("Nmap output file (" + f.getAbsolutePath() + ") not found for the " + jobName + " scan.");
		}
		NmapXmlParser.parse(f, jobName);
	}


	@Override
	public void jobComplete(JobState result)
	{
		if (result == JobState.FAILED)
		{
			logger.warn("Nmap job failed - " + jobName + ". Trying to parse result file anyway. The error could be in " + outputFileName + CONSOLE_OUT_FILE_SUFFIX);
		}
		try
		{
			parseResults();
		}
		catch (FileNotFoundException e)
		{
			logger.error(e.getLocalizedMessage(), e);
		}
	}


	public void setResolve(boolean resolve)
	{
		this.resolve = resolve;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this).append("resume", resume).append("speed", speed)
				.append("outputFileName", outputFileName).append("resolve", resolve)
				.append("flags", flags).append("targets", targets).append("jobName", jobName)
				.build();
	}
}
