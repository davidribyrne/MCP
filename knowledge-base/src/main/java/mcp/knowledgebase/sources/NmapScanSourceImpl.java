package mcp.knowledgebase.sources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class NmapScanSourceImpl implements NmapScanSource
{

	private final String outputPath;
	private final String description;
	private final String commandLine;
	
	public NmapScanSourceImpl(String outputPath, String description, String commandLine)
	{
		this.outputPath = outputPath;
		this.description = description;
		this.commandLine = commandLine;
	}

	/* (non-Javadoc)
	 * @see mcp.knowledgebase.sources.NmapScanSource#getOutputPath()
	 */
	@Override
	public String getOutputPath()
	{
		return outputPath;
	}

	/* (non-Javadoc)
	 * @see mcp.knowledgebase.sources.NmapScanSource#getDescription()
	 */
	@Override
	public String getDescription()
	{
		return description;
	}

	/* (non-Javadoc)
	 * @see mcp.knowledgebase.sources.NmapScanSource#getCommandLine()
	 */
	@Override
	public String getCommandLine()
	{
		return commandLine;
	}

}
