<<<<<<< Updated upstream
package mcp.shell;

import org.crsh.plugin.CRaSHPlugin;
import org.crsh.plugin.PluginDiscovery;
import org.crsh.shell.impl.command.CRaSHShellFactory;
import org.crsh.telnet.TelnetPlugin;
import org.crsh.telnet.term.processor.ProcessorIOHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.modules.GeneralOptions;

import java.util.*;

public class MCPShellPluginDiscovery implements PluginDiscovery
{
	private final static Logger logger = LoggerFactory.getLogger(MCPShellPluginDiscovery.class);
	private final List<CRaSHPlugin<?>> plugins;
	
	public MCPShellPluginDiscovery()
	{
		plugins = new ArrayList<CRaSHPlugin<?>>();
		if (GeneralOptions.getInstance().getTelnetOption().isEnabled())
		{
			plugins.add(new TelnetPlugin());
		}
		plugins.add(new CRaSHShellFactory());
		plugins.add(new ProcessorIOHandler());
	}

	@Override
	public Iterable<CRaSHPlugin<?>> getPlugins()
	{
		return plugins;
	}
}
=======
package mcp.shell;

import org.crsh.plugin.CRaSHPlugin;
import org.crsh.plugin.PluginDiscovery;
import org.crsh.shell.impl.command.CRaSHShellFactory;
import org.crsh.telnet.TelnetPlugin;
import org.crsh.telnet.term.processor.ProcessorIOHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.modules.GeneralOptions;

import java.util.*;

public class MCPShellPluginDiscovery implements PluginDiscovery
{
	private final static Logger logger = LoggerFactory.getLogger(MCPShellPluginDiscovery.class);
	private final List<CRaSHPlugin<?>> plugins;
	
	public MCPShellPluginDiscovery()
	{
		plugins = new ArrayList<CRaSHPlugin<?>>();
		if (GeneralOptions.getInstance().getTelnetOption().isEnabled())
		{
			plugins.add(new TelnetPlugin());
		}
		plugins.add(new CRaSHShellFactory());
		plugins.add(new ProcessorIOHandler());
	}

	@Override
	public Iterable<CRaSHPlugin<?>> getPlugins()
	{
		return plugins;
	}
}
>>>>>>> Stashed changes
