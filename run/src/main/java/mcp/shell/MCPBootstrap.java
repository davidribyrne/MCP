package mcp.shell;

import org.crsh.plugin.PluginContext;
import org.crsh.plugin.ServiceLoaderDiscovery;
import org.crsh.standalone.Bootstrap;
import org.crsh.vfs.FS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;


public class MCPBootstrap extends Bootstrap
{
	private final static Logger logger = LoggerFactory.getLogger(MCPBootstrap.class);


	public MCPBootstrap(ClassLoader baseLoader, FS confFS, FS cmdFS) throws NullPointerException
	{
		super(baseLoader, confFS, cmdFS);
	}


	public MCPBootstrap(ClassLoader baseLoader) throws NullPointerException
	{
		super(baseLoader);
	}



}
