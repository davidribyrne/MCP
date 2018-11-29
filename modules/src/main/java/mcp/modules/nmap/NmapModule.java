package mcp.modules.nmap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.modules.Module;

public abstract class NmapModule extends Module
{

	private final static Logger logger = LoggerFactory.getLogger(NmapModule.class);
	
	protected NmapModule(String name)
	{
		super("Nmap scan - " + name);
	}


}
