package mcp.modules;

public abstract class ExternalModule extends Module
{
	
	/**
	 * 
	 * @param name Name of the module
	 * @param optionName String that is used to accept options from the command line. Alphanumeric only.
	 * @param description Brief module description
	 */
	protected ExternalModule(String name, String optionName, String description)
	{
		super(name);
		
	}

//	public static ModuleOptions getOptions()
//	{
//		return options;
//	}
	
//	protected void addOption(OptionContainer option)
//	{
//		options.getSubOptions().addOptionContainer(option);
//	}
}
