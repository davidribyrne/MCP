package mcp.modules;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mcp.commons.WorkingDirectories;
import mcp.modules.parsers.Parser;
import mcp.modules.parsers.Parsers;
import net.dacce.commons.cli.Option;
import net.dacce.commons.cli.OptionContainer;
import net.dacce.commons.cli.OptionGroup;
import net.dacce.commons.general.FileUtils;
import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

public class InputFileMonitor extends Module
{
	private final static Logger logger = LoggerFactory.getLogger(InputFileMonitor.class);

	private OptionGroup group;
	private Option inputFilePathOption;
	private Option forceReparseOption;
	
	
	public InputFileMonitor()
	{
		super("Input file monitor");
		inputFilePathOption = new Option(null, "inputFilePath", 
				"Path to directory of scan result files. Files placed here will be automatically parsed.", 
				true, true, "input-files", "directory");
		forceReparseOption = new Option(null, "forceReparse", "Force a reparse of all input files.");
		
	}
	
	
	@Override
	public String toString()
	{
		return new ToStringBuilder(this)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder()
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		return new EqualsBuilder()
				.isEquals();
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

	@Override
	public OptionContainer getOptions()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
