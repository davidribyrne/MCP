package mcp.modules.hostnames;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.modules.Module;
import mcp.modules.nmap.NmapGeneralOptions;
import net.dacce.commons.cli.OptionContainer;
import net.dacce.commons.cli.OptionGroup;

import java.util.*;

public abstract class HostnameDiscoveryModule extends Module
{
	private final static Logger logger = LoggerFactory.getLogger(HostnameDiscoveryModule.class);



	public HostnameDiscoveryModule(String name)
	{
		super("Hostname discovery - " + name);
		HostnameDiscoveryGeneralOptions.getInstance().getOptions().addChild(getOptionGroup());
	}


	@Override
	public void initialize()
	{
		// TODO Auto-generated method stub

	}

	protected abstract OptionGroup getOptionGroup();

	@Override
	final public OptionContainer getOptions()
	{
		return null;
	}
}
