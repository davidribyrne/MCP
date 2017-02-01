package mcp.shell;

import org.crsh.plugin.Embedded;
import org.crsh.plugin.PluginContext;
import org.crsh.plugin.PluginDiscovery;
import org.crsh.plugin.ServiceLoaderDiscovery;
import org.crsh.vfs.FS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Level;


public class MCPBootstrap2 extends Embedded
{
	private final static Logger logger = LoggerFactory.getLogger(MCPBootstrap2.class);
	private Map<String, Object> attributes;
	private PluginDiscovery discovery;
	private ClassLoader loader;
	private PluginContext context;
	private ExecutorService executor;

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


	protected PluginContext create(Map<String, Object> attributes, PluginDiscovery discovery, ClassLoader loader)
	{

		//
		FS cmdFS;
		FS confFS;
		try
		{
			cmdFS = createCommandFS();
			confFS = createConfFS();
		}
		catch (IOException e)
		{
			log.log(Level.SEVERE, "Coult not initialize the file system", e);
			return null;
		}

		executor = Executors.newFixedThreadPool(20);
		ScheduledThreadPoolExecutor scheduledExecutor = new ScheduledThreadPoolExecutor(1);
		//
		return new PluginContext(executor, scheduledExecutor, discovery, attributes, cmdFS, confFS, loader);
	}


	public ExecutorService getExecutor()
	{
		return executor;
	}


}
