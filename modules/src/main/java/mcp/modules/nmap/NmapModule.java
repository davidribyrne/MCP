package mcp.modules.nmap;

import mcp.modules.Module;

public abstract class NmapModule extends Module
{

	
	protected NmapModule(String name)
	{
		super("Nmap scan - " + name);
	}


}
