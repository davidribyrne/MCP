package mcp.modules;

import net.dacce.commons.cli.OptionContainer;


public abstract class Module
{
	public abstract void initialize();
	public abstract OptionContainer getOptions();

	
	// Template:
	//
	// private static List<ReconOption> options;
	//
	// public static Collection<ReconOption> getOptions()
	// {
	// if (options == null)
	// {
	// options = new ArrayList<ReconOption>();
	//
	// }
	// return options;
	// }
	//

}
