package mcp.modules;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.moduleLoader.ExternalModuleLoader;
import mcp.modules.hostnames.CommonHostnames;
import mcp.modules.hostnames.HostnameDiscoveryGeneralOptions;
import mcp.modules.listeners.HttpTransactionListener;
import mcp.modules.nmap.NmapGeneralOptions;
import mcp.modules.nmap.NmapIcmp;
import mcp.modules.nmap.NmapSubNetScan;
import mcp.modules.nmap.NmapTcp;
import mcp.modules.nmap.NmapUdp;
import mcp.modules.reporting.SimpleKbDumper;
import mcp.modules.scope.ScopeInitializer;
import mcp.options.MCPOptions;
import space.dcce.commons.cli.OptionContainer;
import space.dcce.commons.cli.OptionGroup;
import space.dcce.commons.general.UnexpectedException;


/**
 * To add new standard clasess, change the normalModuleClasses array
 * 
 * @author dbyrne
 *
 */
public class Modules
{
	private final Map<Class<? extends Module>, Module> normalModules;
	private Map<Class<? extends ExternalModule>, ExternalModule> externalModules;
	private static Class[] normalModuleClasses;
	private static Class[] externalModuleClasses;
	private final static Logger logger = LoggerFactory.getLogger(Modules.class);

	static
	{
		normalModuleClasses = new Class[] {
				CommonHostnames.class,
				InputFileMonitor.class,
				NmapIcmp.class,
				NmapTcp.class,
				NmapUdp.class,
				NmapSubNetScan.class,
				SimpleKbDumper.class,
				HttpTransactionListener.class
		};
		externalModuleClasses = ExternalModuleLoader.getModuleClasses("");
	}
	// Instance must be below the other static code
	private final static Modules instance = new Modules();


	private Modules()
	{
		normalModules = new HashMap<Class<? extends Module>, Module>(normalModuleClasses.length);
		externalModules = new HashMap<Class<? extends ExternalModule>, ExternalModule>(1);

	}


	public void instantiateModules()
	{
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

		/*
		 * External modules are loaded after core options are processed
		 */
		externalModules = ExternalModuleLoader.loadModules("");

	}


//	public Module getModuleInstance(Class<? extends Module> clazz)
//	{
//		if (normalModules.containsKey(clazz))
//			return normalModules.get(clazz);
//
//		if (externalModules.containsKey(clazz))
//			return externalModules.get(clazz);
//
//		return null;
//	}


	private void populateCoreOptions()
	{
		MCPOptions.getInstance().addChild(GeneralOptions.getOptions());
		MCPOptions.getInstance().addChild(ScopeInitializer.getOptions());
		MCPOptions.getInstance().addChild(NmapGeneralOptions.getOptions());
		MCPOptions.getInstance().addChild(HostnameDiscoveryGeneralOptions.getOptions());
	}


	public void populateOptions()
	{
		populateCoreOptions();
//		OptionGroup standardModulesOptionGroup = new OptionGroup("Standard Modules", "Standard Modules");

		for (Class clazz : normalModuleClasses)
		{
			try
			{
				MCPOptions.getInstance().addChild((OptionContainer) clazz.getDeclaredMethod("getOptions").invoke(null));
			}
			catch (@SuppressWarnings("unused") NoSuchMethodException e)
			{
				logger.trace("Class " + clazz.getName() + " does not have a static getOptions method");
			}
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e)
			{
				throw new UnexpectedException(e);
			}
		}
//		MCPOptions.getInstance().addChild(standardModulesOptionGroup);


		/*
		 * External modules are loaded after core options are processed
		 */

		OptionGroup externalModulesOptionGroup = new OptionGroup("External Modules", "");

		for (Class clazz : externalModuleClasses)
		{
			try
			{
				externalModulesOptionGroup.addChild((OptionContainer) clazz.getDeclaredMethod("getOptions").invoke(null));
			}
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
			{
				throw new UnexpectedException(e);
			}
		}
		MCPOptions.getInstance().addChild(externalModulesOptionGroup);
	}



	public void initializeCoreModules()
	{
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
