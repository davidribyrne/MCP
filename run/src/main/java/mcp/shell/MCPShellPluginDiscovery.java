package mcp.shell;

import java.util.ArrayList;
import java.util.List;

import org.crsh.plugin.CRaSHPlugin;
import org.crsh.plugin.PluginDiscovery;
import org.crsh.shell.impl.command.CRaSHShellFactory;
import org.crsh.telnet.TelnetPlugin;
import org.crsh.telnet.term.processor.ProcessorIOHandler;

import mcp.modules.GeneralOptions;

public class MCPShellPluginDiscovery implements PluginDiscovery
{
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
