package mcp.shell;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Level;

import org.crsh.plugin.Embedded;
import org.crsh.plugin.PluginContext;
import org.crsh.plugin.PluginDiscovery;
import org.crsh.vfs.FS;


public class MCPBootstrap extends Embedded
{
	private Map<String, Object> attributes;
	private PluginDiscovery discovery;
	private ClassLoader loader;
	private PluginContext context;
	private ExecutorService executor;


	public MCPBootstrap()
	{
		attributes = new HashMap<String, Object>();
		loader = MCPBootstrap.class.getClassLoader();
//		discovery = new ServiceLoaderDiscovery(loader);
		discovery = new MCPShellPluginDiscovery();
	}


	public void bootstrap()
	{
		context = super.start(attributes, discovery, loader);
	}


	@Override
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
