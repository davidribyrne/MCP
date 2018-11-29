package mcp.modules.hostnames;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.modules.Module;
import space.dcce.commons.cli.OptionContainer;

public abstract class HostnameDiscoveryModule extends Module
{
	private final static Logger logger = LoggerFactory.getLogger(HostnameDiscoveryModule.class);



	public HostnameDiscoveryModule(String name)
	{
		super("Hostname discovery - " + name);
//		HostnameDiscoveryGeneralOptions.getInstance().getOptions().addChild();
	}


	@Override
	public void initialize()
	{
		// TODO Auto-generated method stub

	}


	static public OptionContainer getOptions()
	{
		return null;
	}
}
