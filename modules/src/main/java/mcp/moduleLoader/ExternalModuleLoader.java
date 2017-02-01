package mcp.moduleLoader;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.modules.ExternalModule;
import net.dacce.commons.general.CollectionUtils;
import net.dacce.commons.general.FileUtils;
import net.dacce.commons.general.UnexpectedException;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipInputStream;


public class ExternalModuleLoader
{
	private final static Logger logger = LoggerFactory.getLogger(ExternalModuleLoader.class);
	private final static String MODULE_LIST_FILE = "module-list.txt";


	private ExternalModuleLoader()
	{
	}


	public static List<ExternalModule> loadModules()
	{
		List<ExternalModule> modules = new ArrayList<ExternalModule>();
		for (File jarPath : getJarURLs("d:\\seafile\\mcp\\mcp\\sample-module\\target"))
		{
			List<String> jarClassNames = getModuleClasses(jarPath);
			if (!jarClassNames.isEmpty())
			{
				URL[] urls;
				try
				{
					urls = new URL[] {jarPath.toURI().toURL()};
				}
				catch (MalformedURLException e1)
				{
					throw new UnexpectedException(e1);
				}
				
				URLClassLoader loader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());
				for (String className : jarClassNames)
				{
					try
					{
						Class clazz = Class.forName (className, true, loader);
						try
						{
							Object instance = clazz.newInstance();
							if (instance instanceof ExternalModule)
							{
								modules.add((ExternalModule) instance);
							}
							else
							{
								logger.error("The class " + className + " in " + jarPath.toString() + " is not a subclass of " + ExternalModule.class.getSimpleName());
							}
						}
						catch (InstantiationException | IllegalAccessException e)
						{
							logger.error("Failed to instantiate " + className + " from " + jarPath.toString(), e);
						}
						
					}
					catch (ClassNotFoundException e)
					{
						logger.error("Failed to load " + className + " from " + jarPath.toString(), e);
					}
				}
				try
				{
					loader.close();
				}
				catch (IOException e)
				{
					throw new UnexpectedException(e);
				}
			}
		}

		return modules;
	}


	private static List<String> getModuleClasses(File file)
	{
		List<String> classes = new ArrayList<String>(1);
		JarFile jar;
		try
		{
			jar = new JarFile(file);
		}
		catch (IOException e1)
		{
			logger.error("Can't process jar file " + file.toString(), e1);
			return classes;
		}

		JarEntry moduleList = jar.getJarEntry(MODULE_LIST_FILE);
		if (moduleList != null)
		{
			int size = (int) moduleList.getSize();
			try
			{
				InputStream stream = jar.getInputStream(moduleList);
				byte[] buffer = new byte[size];
				stream.read(buffer);
				stream.close();
				String content = new String(buffer);
				classes.addAll(Arrays.asList(content.split("[\r\n]+")));
			}
			catch (IOException e)
			{
				logger.error("Can't read the module list in " + jar.getName() + ". Corrupt jar?", e);
			}
		}

		try
		{
			jar.close();
		}
		catch (IOException e)
		{
			throw new UnexpectedException(e);
		}

		return classes;
	}


	private static List<File> getJarURLs(String path)
	{
		List<File> jars = new ArrayList<File>(1);
		File folder = new File(path);
		FileFilter jarsFilter = new WildcardFileFilter("*.jar");
		for (File jar : folder.listFiles(jarsFilter))
		{
			jars.add(jar);
		}
		return jars;
	}
}
