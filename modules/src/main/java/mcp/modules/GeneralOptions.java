package mcp.modules;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.commons.WorkingDirectories;
import mcp.options.MCPOptions;
import space.dcce.commons.cli.Option;
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
	
	
	
	private OptionGroup group;
	private int verbose;
	private boolean interactiveConsole;


	private Option verboseOption;
	private Option workingDirectoryOption;
	private Option basicReconOption;
	private Option trackAllData;

	private Option threadCount;
	private OptionGroup interactiveOptions;
	private Option interactiveConsoleOption;
	private Option interactiveTelnetOption;
	private Option sudoPasswordOption;

	@Override
	protected void initializeOptions()
	{
		group = MCPOptions.instance.addOptionGroup("General Options", "");

		verboseOption = group.addOption("v", "verbose", "Output verbosity (0-6).", true, false, "3", "n");
		workingDirectoryOption = group.addOption(null, "workingDirectory", "Working directory for Recon Master.", true, true, ".",
				"directory path");
		// continueAfterErrorOption = new Option(null, "continueAfterError", "Continue trying to run scans if something
		// goes wrong.");
		basicReconOption = group.addOption("b", "basicRecon", "Run with basic recon options. Equivalent to: "
				+ "--hostnameDiscovery --icmpEchoScan --topTcpScan "
				+ "--udpPorts 53,67,68,69,111,123,135,137,138,139,161,162,445,500,514,520,631,1434,1604,4500,5353,10000");
		threadCount = group.addOption("t", "threads", "Working thread count.", true, true, "3", "n");
		trackAllData = group.addOption("", "trackall",
				"Track all data, even negative results (e.g., non-open ports). This may significantly increase resource use");
		verboseOption.addValidator(new NumericValidator(false, 0, 6));
		sudoPasswordOption = group.addOption("", "sudoPassword", "Password to use with sudo for programs that need root", true, true, "", "password");


		PathState workingDirState = new PathState();
		workingDirState.directory = Requirement.MUST;
		workingDirState.writeable = Requirement.MUST;
		workingDirState.readable = Requirement.MUST;
		workingDirectoryOption.addValidator(new PathValidator(workingDirState));



		interactiveOptions = MCPOptions.instance.addOptionGroup("Interactive options", "");
		interactiveConsoleOption = interactiveOptions.addOption("i", "interactive", "Run in interactive mode on the console.");
		interactiveTelnetOption = interactiveOptions.addOption(null, "telnet", "Run interactive telnet server.", true, false, "2300", "port");

	}


	public GeneralOptions()
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


	public Option getBasicReconOption()
	{
		return basicReconOption;
	}


	public OptionGroup getOptions()
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


	public Option getWorkingDirectoryOption()
	{
		return workingDirectoryOption;
	}


	public Option getSudoPasswordOption()
	{
		return sudoPasswordOption;
	}


}
