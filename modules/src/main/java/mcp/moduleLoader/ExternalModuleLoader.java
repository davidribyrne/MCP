package mcp.moduleLoader;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.modules.ExternalModule;
import space.dcce.commons.general.UnexpectedException;


public class ExternalModuleLoader
{
	private final static Logger logger = LoggerFactory.getLogger(ExternalModuleLoader.class);
	private final static String MODULE_LIST_FILE = "module-list.txt";


	private ExternalModuleLoader()
	{
	}


	private static List<Class> getSuperClasses(Class clazz)
	{
		List<Class> superClasses = new ArrayList<Class>();
		Class tmp = clazz.getSuperclass();
		while (tmp != null)
		{
			superClasses.add(tmp);
			tmp = tmp.getSuperclass();
		}

		return superClasses;
	}


	public static Class<? extends ExternalModule>[] getModuleClasses(String path)
	{
		List<Class<? extends ExternalModule>> classes = new ArrayList<Class<? extends ExternalModule>>();
		// for (File jarPath : getJarURLs("d:\\seafile\\mcp\\mcp\\sample-module\\target"))
		for (File jarPath : getJarURLs(path))
		{
			List<String> jarClassNames = getModuleClasses(jarPath);
			if (!jarClassNames.isEmpty())
			{
				URL[] urls;
				try
				{
					urls = new URL[] { jarPath.toURI().toURL() };
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
						Class clazz = Class.forName(className, true, loader);

						if (getSuperClasses(clazz).contains(ExternalModule.class))
						{
							classes.add(clazz);
						}
						else
						{
							logger.error("The class " + className + " in " + jarPath.toString() + " is not a subclass of "
									+ ExternalModule.class.getSimpleName());
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

		return classes.toArray(new Class[] {});
	}


	public static Map<Class<? extends ExternalModule>, ExternalModule> loadModules(String path)
	{
		Map<Class<? extends ExternalModule>, ExternalModule> modules = new HashMap<Class<? extends ExternalModule>, ExternalModule>();
		// for (File jarPath : getJarURLs("d:\\seafile\\mcp\\mcp\\sample-module\\target"))
		for (File jarPath : getJarURLs(path))
		{
			List<String> jarClassNames = getModuleClasses(jarPath);
			if (!jarClassNames.isEmpty())
			{
				URL[] urls;
				try
				{
					urls = new URL[] { jarPath.toURI().toURL() };
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
						Class clazz = Class.forName(className, true, loader);
						try
						{
							Object instance = clazz.newInstance();
							if (instance instanceof ExternalModule)
							{
								modules.put(clazz, (ExternalModule) instance);
							}
							else
							{
								logger.error("The class " + className + " in " + jarPath.toString() + " is not a subclass of "
										+ ExternalModule.class.getSimpleName());
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
		File[] files = folder.listFiles(jarsFilter);
		if (files != null)
		{
			for (File jar : files)
			{
				jars.add(jar);
			}
		}
		return jars;
	}
}
