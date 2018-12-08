package mcp.modules;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.commons.WorkingDirectories;
import space.dcce.commons.cli.Option;
import space.dcce.commons.cli.OptionContainer;
import space.dcce.commons.cli.OptionGroup;
import space.dcce.commons.general.FileUtils;
import space.dcce.commons.validators.NumericValidator;
import space.dcce.commons.validators.PathState;
import space.dcce.commons.validators.PathValidator;
import space.dcce.commons.validators.Requirement;


public class GeneralOptions extends Module
{
	private final static Logger logger = LoggerFactory.getLogger(GeneralOptions.class);

	private final static String SCAN_DATA_DIRECTORY = "scandata";
	private final static String RESUME_DIRECTORY = "resume";
	private final static GeneralOptions instance = new GeneralOptions();
	private static OptionGroup group;
	private int verbose;
	private boolean interactiveConsole;



	private static Option verboseOption;
	private static Option workingDirectoryOption;
//	private Option continueAfterErrorOption;
	private static Option basicReconOption;
	private static Option trackAllData;

	private static Option threadCount;
	private static OptionGroup interactiveOptions;
	private static Option interactiveConsoleOption;
	private static Option interactiveTelnetOption;
	private static Option sudoPasswordOption;

	static
	{
		verboseOption = new Option("v", "verbose", "Output verbosity (0-6).", true, false, "3", "n");
		workingDirectoryOption = new Option(null, "workingDirectory", "Working directory for Recon Master.", true, true, ".",
				"directory path");
//		continueAfterErrorOption = new Option(null, "continueAfterError", "Continue trying to run scans if something goes wrong.");
		basicReconOption = new Option("b", "basicRecon", "Run with basic recon options. Equivalent to: "
				+ "--hostnameDiscovery --icmpEchoScan --topTcpScan "
				+ "--udpPorts 53,67,68,69,111,123,135,137,138,139,161,162,445,500,514,520,631,1434,1604,4500,5353,10000");
		threadCount = new Option("t", "threads", "Working thread count.", true, true, "3", "n");
		trackAllData = new Option("", "trackall", "Track all data, even negative results (e.g., non-open ports). This may significantly increase resource use");
		verboseOption.addValidator(new NumericValidator(false, 0, 6));
		sudoPasswordOption = new Option("", "sudoPassword", "Password to use with sudo for programs that need root", true, true, "", "password");
		PathState workingDirState = new PathState();
		workingDirState.directory = Requirement.MUST;
		workingDirState.writeable = Requirement.MUST;
		workingDirState.readable = Requirement.MUST;
		workingDirectoryOption.addValidator(new PathValidator(workingDirState));
		
		
		
		group = new OptionGroup("General Options", "");
		group.addChild(verboseOption);
		group.addChild(interactiveConsoleOption);
		group.addChild(workingDirectoryOption);
//		group.addChild(continueAfterErrorOption);
		group.addChild(basicReconOption);
		group.addChild(sudoPasswordOption);
		group.addChild(threadCount);
		group.addChild(trackAllData);

		interactiveOptions = new OptionGroup("Interactive options", "");
		interactiveConsoleOption = new Option("i", "interactive", "Run in interactive mode on the console.");
		interactiveTelnetOption = new Option(null, "telnet", "Run interactive telnet server.", true, false, "2300", "port");
		interactiveOptions.addChild(interactiveConsoleOption);
		interactiveOptions.addChild(interactiveTelnetOption);
		group.addChild(interactiveOptions);

	}
	
	private GeneralOptions()
	{
		super("General options");
	}



	/**
	 * Initialize.
	 *
	 * @throws IllegalArgumentException
	 *             thrown if a target directory already exists as a file
	 */
	@Override
	public void initialize()
	{
		
		try
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
		}
		catch (FileAlreadyExistsException e1)
		{
			logger.error("Failed to create working directory: " + e1.toString());
		}

		verbose = Integer.valueOf(verboseOption.getValue());
		
		interactiveConsole = interactiveConsoleOption.isEnabled();
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


	public static OptionContainer getOptions()
	{
		return group;
	}



	public Option getThreadCount()
	{
		return threadCount;
	}
	
	public boolean isInteractiveConsole()
	{
		return interactiveConsole;
	}

	public boolean isInteractive()
	{
		return interactiveConsole || interactiveTelnetOption.isEnabled();
	}


	public Option getTelnetOption()
	{
		return interactiveTelnetOption;
	}



	public Option getTrackAllData()
	{
		return trackAllData;
	}



	public static Option getWorkingDirectoryOption()
	{
		return workingDirectoryOption;
	}



	public static Option getSudoPasswordOption()
	{
		return sudoPasswordOption;
	}


}
