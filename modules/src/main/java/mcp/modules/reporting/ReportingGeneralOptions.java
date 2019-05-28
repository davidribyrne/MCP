package mcp.modules.reporting;


import mcp.modules.Module;
import mcp.options.MCPOptions;
import space.dcce.commons.cli.OptionGroup;


public class ReportingGeneralOptions extends Module
{

	private OptionGroup group;

	public ReportingGeneralOptions()
	{
		super("General reporting options");
	}


	@Override
	public void initialize()
	{
	}

	@Override
	protected void initializeOptions()
	{
		group = MCPOptions.instance.addOptionGroup("Reporting options", "");
		
	}

	public OptionGroup getOptions()
	{
		return group;
	}
}
