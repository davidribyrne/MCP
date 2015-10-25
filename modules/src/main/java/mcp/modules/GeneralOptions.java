package mcp.modules;

import java.io.File;

import net.dacce.commons.cli.Group;
import net.dacce.commons.cli.Option;
import net.dacce.commons.cli.OptionContainer;
import net.dacce.commons.general.FileUtils;
import net.dacce.commons.validators.NumericValidator;
import net.dacce.commons.validators.PathState;
import net.dacce.commons.validators.PathValidator;
import net.dacce.commons.validators.Requirement;


public class GeneralOptions extends Module
{

	private final static String SCAN_DATA_DIRECTORY = "scandata";
	private final static GeneralOptions instance = new GeneralOptions();
	private static Group group;
	private int verbose;
	private Option verboseOption;
	private Option workingDirectoryOption;
	private Option continueAfterErrorOption;
	private Option basicReconOption;

	private GeneralOptions()
	{
		verboseOption = new Option("v", "verbose", "Output verbosity (0-6).", true, false, "2", "n");
		workingDirectoryOption = new Option(null, "workingDirectory", "Working directory for Recon Master.", true, true, ".",
				"directory path");
		continueAfterErrorOption = new Option(null, "continueAfterError", "Continue trying to run scans if something goes wrong.");
		basicReconOption = new Option("b", "basicRecon", "Run with basic recon options. Equivalent to: --icmpEchoScan --topTcpScan --udpPorts 53,67,68,69,111,123,135,137,138,139,161,162,445,500,514,520,631,1434,1604,4500,5353,10000");

		verboseOption.addValidator(new NumericValidator(false, 0, 6));
		PathState workingDirState = new PathState();
		workingDirState.directory = Requirement.MUST;
		workingDirState.writeable = Requirement.MUST;
		workingDirState.readable = Requirement.MUST;
		workingDirectoryOption.addValidator(new PathValidator(workingDirState));

		group = new Group("General", "General options");
		group.addChild(verboseOption);
		group.addChild(workingDirectoryOption);
		group.addChild(continueAfterErrorOption);
		group.addChild(basicReconOption);

	}

	private File workingDirectory;
	private File scanDataDirectory;




	/**
	 * Initialize.
	 *
	 * @throws IllegalArgumentException
	 *             thrown if a target directory already exists as a file
	 */
	@Override
	public void initialize() throws IllegalArgumentException
	{
		workingDirectory = new File(workingDirectoryOption.getValue());
		FileUtils.createDirectory(workingDirectory);

		scanDataDirectory = new File(workingDirectory, SCAN_DATA_DIRECTORY);
		FileUtils.createDirectory(scanDataDirectory);

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


	public String getScandataDirectory()
	{
		return scanDataDirectory.toString() + File.separator;
	}


	public String getWorkingDirectory()
	{
		return workingDirectory.toString() + File.separator;
	}


	public Option getContinueAfterError()
	{
		return continueAfterErrorOption;
	}


	public Option getBasicReconOption()
	{
		return basicReconOption;
	}


	@Override
	public OptionContainer getOptions()
	{
		return group;
	}

}
