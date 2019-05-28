package mcp;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.jobmanager.executors.ExecutionScheduler;
import mcp.knowledgebase.KnowledgeBase;
import mcp.modules.GeneralOptions;
import mcp.modules.Modules;
import mcp.options.MCPOptions;
import mcp.shell.MCPShell;


public class MCP
{
	final static Logger logger = LoggerFactory.getLogger(MCP.class);


	public static void main(String[] args)
	{
		@SuppressWarnings("unused")
		MCP rm = new MCP(args);
	}


	private MCP(String[] args)
	{
		Modules.instance.instantiateModules();
		MCPOptions.instance.parseCommandline(args);

		GeneralOptions generalOptions = (GeneralOptions) Modules.instance.getModuleInstance(GeneralOptions.class);
		try
		{
			String path = generalOptions.getWorkingDirectoryOption().getValue();
			KnowledgeBase.INSTANCE.initializeStorage(path + File.separator + "mcp.db");
		}
		catch (FileNotFoundException e)
		{
			logger.error("Could not open or create database file: " + e.getLocalizedMessage(), e);
			System.exit(1);
		}
		catch (SQLException e)
		{
			logger.error("Problem loading or creating database: " + e.getLocalizedMessage(), e);
			System.exit(2);
		}
		catch (IOException e)
		{
			logger.error("IO problem loading or creating database: " + e.getLocalizedMessage(), e);
			System.exit(2);
		}


		generalOptions.initialize();
		MCPLogging.setupLogger();

		Modules.instance.initializeModules();
		ExecutionScheduler.getInstance().start();
		MCPShell.setupShell();

		waitForQueue();
	}


	private void waitForQueue()
	{
		while (!ExecutionScheduler.getInstance().isQueueEmpty() || MCPShell.isActive())
		{
			Object o = new Object();
			synchronized (o)
			{
				try
				{
					o.wait(100);
				}
				catch (InterruptedException e)
				{
					logger.debug("Main thread interrupted", e);
				}
			}
		}

	}




}
