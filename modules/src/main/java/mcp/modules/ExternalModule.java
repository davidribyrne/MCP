package mcp.modules;

import space.dcce.commons.cli.ModuleOptions;
import space.dcce.commons.cli.OptionContainer;

public abstract class ExternalModule extends Module
{
	
	private ModuleOptions options;

	
	/**
	 * 
	 * @param name Name of the module
	 * @param optionName String that is used to accept options from the command line. Alphanumeric only.
	 * @param description Brief module description
	 */
	protected ExternalModule(String name, String optionName, String description)
	{
		super(name);
		options = new ModuleOptions(optionName, description, name);
	}

	@Override
	public ModuleOptions getOptions()
	{
		return options;
	}
	
	protected void addOption(OptionContainer option)
	{
		options.getSubOptions().addOptionContainer(option);
	}
}
