package mcp.modules.reporting;


import mcp.modules.Module;
import space.dcce.commons.cli.OptionGroup;


public class ReportingGeneralOptions extends Module
{


	private static OptionGroup group;

	static
	{
		group = new OptionGroup("Reporting options", "");

	}

	public ReportingGeneralOptions()
	{
		super("General reporting options");
	}


	public static OptionGroup getOptions()
	{
		return group;
	}


	@Override
	public void initialize()
	{
		
	}
}
