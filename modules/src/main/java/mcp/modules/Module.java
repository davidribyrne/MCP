package mcp.modules;

import space.dcce.commons.cli.OptionContainer;


public abstract class Module
{
	public abstract void initialize();
	public abstract OptionContainer getOptions();
	private final String name;
	protected Module(String name)
	{
		this.name = name;
//		if (register)
//		{
//			Modules.getInstance().registerNormalModule(this);
//		}
	}
	
	public String getName()
	{
		return name;
	}
	
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
