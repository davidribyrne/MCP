package mcp.shell;

import org.crsh.plugin.Embedded;
import org.crsh.plugin.PluginContext;
import org.crsh.plugin.PluginDiscovery;
import org.crsh.plugin.ServiceLoaderDiscovery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;


public class MCPBootstrap2 extends Embedded
{
	private final static Logger logger = LoggerFactory.getLogger(MCPBootstrap2.class);
	private Map<String, Object> attributes;
	private PluginDiscovery discovery;
	private ClassLoader loader;
	private PluginContext context;

	public MCPBootstrap2()
	{
		attributes = new HashMap<String, Object>();
		loader = MCPBootstrap2.class.getClassLoader();
		discovery = new ServiceLoaderDiscovery(loader);
	}



	public void bootstrap()
	{
		context = super.start(attributes, discovery, loader);
	}


}
