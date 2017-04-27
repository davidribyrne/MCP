package mcp.modules.nmap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.modules.Module;
import net.dacce.commons.cli.OptionContainer;
import net.dacce.commons.cli.OptionGroup;

public abstract class NmapModule extends Module
{

	private final static Logger logger = LoggerFactory.getLogger(NmapModule.class);
	
	protected NmapModule(String name)
	{
		super("Nmap scan - " + name);
		NmapGeneralOptions.getInstance().getOptions().addChild(getOptionGroup());
	}

	protected abstract OptionGroup getOptionGroup();
	

	@Override
	final public OptionContainer getOptions()
	{
		return null;
	}

}
