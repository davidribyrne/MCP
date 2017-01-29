package mcp.modules;

import java.util.ArrayList;
import java.util.List;

import mcp.events.events.McpCompleteEvent;
import mcp.events.events.McpStartEvent;
import mcp.events.listeners.McpEventListener;
import mcp.moduleLoader.ExternalModuleLoader;
import mcp.modules.hostnames.CommonHostnames;
import mcp.modules.hostnames.HostnameDiscoveryGeneralOptions;
import mcp.modules.nmap.NmapGeneralOptions;
import mcp.modules.nmap.NmapIcmp;
import mcp.modules.nmap.NmapTcp;
import mcp.modules.nmap.NmapUdp;
import mcp.options.MCPOptions;
import net.dacce.commons.cli.OptionGroup;
import net.dacce.commons.general.UnexpectedException;


public class Modules
{
	private final static Modules instance = new Modules();
	private List<Module> normalModules;
	private List<ExternalModule> externalModules = new ArrayList<ExternalModule>(1);


	private Modules()
	{
		Class[] normalModuleClassess = new Class[] {
				CommonHostnames.class,
				InputFileMonitor.class,
				NmapIcmp.class,
				NmapTcp.class,
				NmapUdp.class,
				SimpleKbDumper.class
		};
		normalModules = new ArrayList<Module>(normalModuleClassess.length);
		for (Class clazz : normalModuleClassess)
		{
			try
			{
				normalModules.add((Module) clazz.newInstance());
			}
			catch (IllegalAccessException | InstantiationException e)
			{
				throw new UnexpectedException(e);
			}
		}
	}


	private void populateCoreOptions()
	{
		MCPOptions.getInstance().addOptionContainer(GeneralOptions.getInstance().getOptions());
		MCPOptions.getInstance().addOptionContainer(ScopeInitializer.getInstance().getOptions());
		MCPOptions.getInstance().addOptionContainer(NmapGeneralOptions.getInstance().getOptions());
		MCPOptions.getInstance().addOptionContainer(HostnameDiscoveryGeneralOptions.getInstance().getOptions());
	}


	public void populateOptions()
	{
		populateCoreOptions();
		for (Module module : normalModules)
		{
			MCPOptions.getInstance().addOptionContainer(module.getOptions());
		}
		/*
		 * External modules are loaded after core options are processed
		 */
		externalModules = ExternalModuleLoader.loadModules();

		for (ExternalModule module : externalModules)
		{
			MCPOptions.getInstance().addOptionContainer(module.getOptions());
		}
	}


	public void initializeCoreModules()
	{
		GeneralOptions.getInstance().initialize();
		ScopeInitializer.getInstance().initialize();
		NmapGeneralOptions.getInstance().initialize();
	}


	public void initializeOtherModules()
	{
		for (Module module : normalModules)
		{
			module.initialize();
		}
		for (Module module : externalModules)
		{
			module.initialize();

		}
		// NmapIcmp.getInstance().initialize();
		// NmapTcp.getInstance().initialize();
		// NmapUdp.getInstance().initialize();
		// HostnameDiscoveryGeneralOptions.getInstance().initialize();
		// CommonHostnames.getInstance().initialize();
		// SimpleKbDumper.getInstance().initialize();
	}


	public static Modules getInstance()
	{
		return instance;
	}

}
