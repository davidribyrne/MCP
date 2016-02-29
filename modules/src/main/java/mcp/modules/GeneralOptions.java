package mcp.modules;

import java.io.File;
import mcp.commons.WorkingDirectories;
import net.dacce.commons.cli.Option;
import net.dacce.commons.cli.OptionContainer;
import net.dacce.commons.cli.OptionGroup;
import net.dacce.commons.general.FileUtils;
import net.dacce.commons.validators.NumericValidator;
import net.dacce.commons.validators.PathState;
import net.dacce.commons.validators.PathValidator;
import net.dacce.commons.validators.Requirement;


public class GeneralOptions extends Module
{

	private final static String SCAN_DATA_DIRECTORY = "scandata";
	private final static String RESUME_DIRECTORY = "resume";
	private final static GeneralOptions instance = new GeneralOptions();
	private static OptionGroup group;
	private int verbose;
	private Option verboseOption;
	private Option workingDirectoryOption;
//	private Option continueAfterErrorOption;
	private Option basicReconOption;
	private Option threadCount;

	private GeneralOptions()
	{
		verboseOption = new Option("v", "verbose", "Output verbosity (0-6).", true, false, "2", "n");
		workingDirectoryOption = new Option(null, "workingDirectory", "Working directory for Recon Master.", true, true, ".",
				"directory path");
//		continueAfterErrorOption = new Option(null, "continueAfterError", "Continue trying to run scans if something goes wrong.");
		basicReconOption = new Option("b", "basicRecon", "Run with basic recon options. Equivalent to: "
				+ "--hostnameDiscovery --icmpEchoScan --topTcpScan "
				+ "--udpPorts 53,67,68,69,111,123,135,137,138,139,161,162,445,500,514,520,631,1434,1604,4500,5353,10000");
		threadCount = new Option("t", "threads", "Working thread count.", true, true, "3", "n");
		
		verboseOption.addValidator(new NumericValidator(false, 0, 6));
		PathState workingDirState = new PathState();
		workingDirState.directory = Requirement.MUST;
		workingDirState.writeable = Requirement.MUST;
		workingDirState.readable = Requirement.MUST;
		workingDirectoryOption.addValidator(new PathValidator(workingDirState));

		group = new OptionGroup("General", "General options");
		group.addChild(verboseOption);
		group.addChild(workingDirectoryOption);
//		group.addChild(continueAfterErrorOption);
		group.addChild(basicReconOption);
		group.addChild(threadCount);

	}



	/**
	 * Initialize.
	 *
	 * @throws IllegalArgumentException
	 *             thrown if a target directory already exists as a file
	 */
	@Override
	public void initialize() throws IllegalArgumentException
	{
		
		File workingDirectory = new File(workingDirectoryOption.getValue());
		FileUtils.createDirectory(workingDirectory);
		WorkingDirectories.setWorkingDirectory(workingDirectory.toString() + File.separator);

		File scanDataDirectory = new File(workingDirectory, SCAN_DATA_DIRECTORY);
		FileUtils.createDirectory(scanDataDirectory);
		WorkingDirectories.setScanDataDirectory(scanDataDirectory.toString() + File.separator);

		File resumeDirectory = new File(workingDirectory, RESUME_DIRECTORY);
		FileUtils.createDirectory(resumeDirectory);
		WorkingDirectories.setResumeDirectory(resumeDirectory.toString() + File.separator);

		try
		{
			verbose = Integer.valueOf(verboseOption.getValue());
		}
		catch (NumberFormatException e)
		{
			throw new IllegalArgumentException("Verbose option requires an integer argument.", e);
		}
		if ((verbose > 6) || (verbose < 0))
		{
			throw new IllegalArgumentException("Verbose option out of range.");
		}
	}


	public int getVerbose()
	{
		return verbose;
	}


	public static GeneralOptions getInstance()
	{
		return instance;
	}



//	public Option getContinueAfterError()
//	{
//		return continueAfterErrorOption;
//	}


	public Option getBasicReconOption()
	{
		return basicReconOption;
	}


	@Override
	public OptionContainer getOptions()
	{
		return group;
	}



	public Option getThreadCount()
	{
		return threadCount;
	}

}
