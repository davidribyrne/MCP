package mcp.modules.parsers;

import java.io.File;

import space.dcce.commons.cli.OptionContainer;

public interface Parser
{
	public String getDir();
	public String getName();
	public OptionContainer getOptions();
	public void parse(File file);
}
