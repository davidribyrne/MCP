package mcp;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import mcp.commons.WorkingDirectories;
import mcp.events.events.McpStartEvent;
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
		setupOptions(args);
		try
		{
			KnowledgeBase.instance.initializeStorage(GeneralOptions.getWorkingDirectoryOption().getValue());
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


		Modules.getInstance().instantiateModules();
		Modules.getInstance().initializeCoreModules();
		MCPLogging.setupLogger();
		Modules.getInstance().initializeOtherModules();
		ExecutionScheduler.getInstance().signalEvent(new McpStartEvent());
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


	private void setupOptions(String[] args)
	{
		Modules.getInstance().populateOptions();
		MCPOptions.getInstance().parseCommandline(args);
	}



}
