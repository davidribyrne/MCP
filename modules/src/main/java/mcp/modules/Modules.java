package mcp.modules;

import java.lang.reflect.Constructor;
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
import mcp.modules.reporting.ReportingGeneralOptions;
import mcp.modules.reporting.SimpleKbDumper;
import mcp.modules.scope.ScopeInitializer;
import space.dcce.commons.general.UnexpectedException;


/**
 * To add new standard clasess, change the normalModuleClasses array
 * 
 * @author dbyrne
 *
 */
public class Modules
{
	private final static Logger logger = LoggerFactory.getLogger(Modules.class);
	public final static Modules instance = new Modules();

	private final Map<Class<? extends Module>, Module> allModules;

	private final Map<Class<? extends Module>, Module> optionsModules;
	private final Map<Class<? extends Module>, Module> earlyInitModules;
	private final Map<Class<? extends Module>, Module> normalModules;
	private Map<Class<? extends ExternalModule>, ExternalModule> externalModules;
	
	private Class[] optionsModuleClasses;
	private Class[] earlyInitModuleClasses;
	private Class[] normalModuleClasses;
	private Class[] externalModuleClasses;
	

	
	private Modules()
	{
		// This array structure is used to set an explicit instantiation and initialization order
		
		optionsModuleClasses = new Class[] 
		{
			GeneralOptions.class,
			NmapGeneralOptions.class,
			HostnameDiscoveryGeneralOptions.class,
			ReportingGeneralOptions.class
		};
		
		earlyInitModuleClasses = new Class[] 
		{
			ScopeInitializer.class
		};
		
		normalModuleClasses = new Class[] 
		{
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
		
		allModules = new HashMap<Class<? extends Module>, Module>();
		
		optionsModules = new HashMap<Class<? extends Module>, Module>(optionsModuleClasses.length);
		earlyInitModules = new HashMap<Class<? extends Module>, Module>(earlyInitModuleClasses.length);
		normalModules = new HashMap<Class<? extends Module>, Module>(normalModuleClasses.length);
		externalModules = new HashMap<Class<? extends ExternalModule>, ExternalModule>(1);

	}

	private Module instantiate(Class<? extends Module> clazz) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Constructor c = clazz.getDeclaredConstructor();
		c.setAccessible(true);
		Module m = (Module) c.newInstance();
		allModules.put(clazz, m);
		return m;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void instantiateModules()
	{
		try
		{
			for (Class clazz : optionsModuleClasses)
			{
				Module m = instantiate(clazz);
				optionsModules.put(clazz, m);
			}

			for (Class clazz : earlyInitModuleClasses)
			{
				Module m = instantiate(clazz);
				earlyInitModules.put(clazz, m);
			}
			
			for (Class clazz : normalModuleClasses)
			{
				Module m = instantiate(clazz);
				normalModules.put(clazz, m);
			}
		}
		catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e)
		{
			logger.error("Problem instantiating modules", e);
			throw new UnexpectedException("Problem instantiating modules", e);
		}

		/*
		 * External modules are loaded after core options are processed
		 */
		externalModules = ExternalModuleLoader.loadModules("");

	}


	public Module getModuleInstance(Class<? extends Module> clazz)
	{
		return allModules.get(clazz);
	}



	@SuppressWarnings("rawtypes")
	public void initializeModules()
	{

		for (Class clazz : optionsModuleClasses)
		{
			// GeneralOptions has to be initialized earlier
			if (clazz.equals(GeneralOptions.class))
			{
				continue;
			}
			optionsModules.get(clazz).initialize();
		}
		for (Class clazz : earlyInitModuleClasses)
		{
			earlyInitModules.get(clazz).initialize();
		}
		for (Class clazz : normalModuleClasses)
		{
			normalModules.get(clazz).initialize();
		}
		for (Class clazz : externalModuleClasses)
		{
			externalModules.get(clazz).initialize();
		}
	}

	public GeneralOptions getGeneralOptions()
	{
		return (GeneralOptions) getModuleInstance(GeneralOptions.class);
	}

}
