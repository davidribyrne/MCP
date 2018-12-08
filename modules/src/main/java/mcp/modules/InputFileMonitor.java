package mcp.modules;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.commons.WorkingDirectories;
import mcp.modules.parsers.Parser;
import mcp.modules.parsers.Parsers;
import space.dcce.commons.cli.Option;
import space.dcce.commons.cli.OptionContainer;
import space.dcce.commons.cli.OptionGroup;
import space.dcce.commons.general.FileUtils;

//TODO Finish InputFileMonitor
public class InputFileMonitor extends Module
{
	private final static Logger logger = LoggerFactory.getLogger(InputFileMonitor.class);

	private static OptionGroup group;
	private static Option inputFilePathOption;
	private static Option forceReparseOption;
	
	static
	{
		group = new OptionGroup("Input File Monitor", "Monitors a directory for files to parse");
		inputFilePathOption = new Option(null, "inputFilePath", 
				"Path to directory of scan result files. Files placed here will be automatically parsed.", 
				true, true, "input-files", "directory");
		group.addChild(inputFilePathOption);
		forceReparseOption = new Option(null, "forceReparse", "Force a reparse of all input files.");
		group.addChild(forceReparseOption);
	}
	public InputFileMonitor()
	{
		super("Input file monitor");
		
	}
	
	

	@Override
	public void initialize()
	{
		WorkingDirectories.setInputFileDirectory(inputFilePathOption.getValue());
		for(Parser parser: Parsers.getInstance().getParsers())
		{
			String name = WorkingDirectories.getInputFileDirectory() + File.separator + parser.getDir();
			File dir = new File(name);
			try
			{
				FileUtils.createDirectory(dir);
			}
			catch (FileAlreadyExistsException e)
			{
				logger.error("Failed trying to create " + name + " directory. It already exists, but is a file.");
			}
		}
	}

	static public OptionContainer getOptions()
	{
		return group;
	}

}
