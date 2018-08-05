package mcp.modules;

import java.util.HashMap;
import java.util.Map;

import mcp.moduleLoader.ExternalModuleLoader;
import mcp.modules.hostnames.CommonHostnames;
import mcp.modules.hostnames.HostnameDiscoveryGeneralOptions;
import mcp.modules.listeners.HttpTransactionListener;
import mcp.modules.nmap.NmapGeneralOptions;
import mcp.modules.nmap.NmapIcmp;
import mcp.modules.nmap.NmapTcp;
import mcp.modules.nmap.NmapUdp;
import mcp.options.MCPOptions;
import space.dcce.commons.cli.OptionGroup;
import space.dcce.commons.general.UnexpectedException;



/**
 * To add new standard clasess, change the normalModuleClasses array
 * @author dbyrne
 *
 */
public class Modules
{
	private final static Modules instance = new Modules();
	private final Map<Class<? extends Module>, Module> normalModules;
	private Map<Class<? extends ExternalModule>, ExternalModule> externalModules;	

	private Modules()
	{
		Class[] normalModuleClasses = new Class[] {
				CommonHostnames.class,
				InputFileMonitor.class,
				NmapIcmp.class,
				NmapTcp.class,
				NmapUdp.class,
				SimpleKbDumper.class,
				HttpTransactionListener.class
		};
		normalModules = new HashMap<Class<? extends Module>, Module>(normalModuleClasses.length);
		externalModules = new HashMap<Class<? extends ExternalModule>, ExternalModule>(1);
		for (Class clazz : normalModuleClasses)
		{
			try
			{
				normalModules.put(clazz, (Module) clazz.newInstance());
			}
			catch (IllegalAccessException | InstantiationException e)
			{
				throw new UnexpectedException(e);
			}
		}
	}

	public Module getModuleInstance(Class<? extends Module> clazz)
	{
		if (normalModules.containsKey(clazz))
			return normalModules.get(clazz);
		
		if (externalModules.containsKey(clazz))
			return externalModules.get(clazz);
		
		return null;
	}

	private void populateCoreOptions()
	{
		MCPOptions.getInstance().addChild(GeneralOptions.getInstance().getOptions());
		MCPOptions.getInstance().addChild(ScopeInitializer.getInstance().getOptions());
		MCPOptions.getInstance().addChild(NmapGeneralOptions.getInstance().getOptions());
		MCPOptions.getInstance().addChild(HostnameDiscoveryGeneralOptions.getInstance().getOptions());
	}


	public void populateOptions()
	{
		populateCoreOptions();
		OptionGroup standardModulesOptionGroup = new OptionGroup("Standard Modules", "Standard Modules");
		
		for (Module module : normalModules.values())
		{
			standardModulesOptionGroup.addChild(module.getOptions());
		}
		MCPOptions.getInstance().addChild(standardModulesOptionGroup);
		
		
		/*
		 * External modules are loaded after core options are processed
		 */
		externalModules = ExternalModuleLoader.loadModules("");

		OptionGroup externalModulesOptionGroup = new OptionGroup("External Modules", "External Modules");

		for (ExternalModule module: externalModules.values())
		{
			externalModulesOptionGroup.addChild(module.getOptions());
		}
		MCPOptions.getInstance().addChild(externalModulesOptionGroup);
	}


	public void initializeCoreModules()
	{
		GeneralOptions.getInstance().initialize();
		ScopeInitializer.getInstance().initialize();
		NmapGeneralOptions.getInstance().initialize();
	}


	public void initializeOtherModules()
	{
		for (Module module : normalModules.values())
		{
			module.initialize();
		}
		for (Module module : externalModules.values())
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
