package mcp.knowledgebase.sources;

import org.apache.commons.lang3.builder.ToStringBuilder;

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

	@Override
	public String toString()
	{
		return new ToStringBuilder(this).append("outputPath", outputPath).append("description", description)
				.append("commandLine", commandLine).build();
	}
}