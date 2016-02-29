package mcp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import mcp.commons.WorkingDirectories;
import mcp.events.EventDispatcher;
import mcp.events.events.McpCompleteEvent;
import mcp.events.events.McpStartEvent;
import mcp.modules.GeneralOptions;
import mcp.modules.Modules;
import mcp.options.ReconOptions;


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
		Modules.initializePrescanModules();
		setupLogger();
		Modules.initializeOtherModules();
		EventDispatcher.getInstance().signalEvent(new McpStartEvent());
		EventDispatcher.getInstance().signalEvent(new McpCompleteEvent());
		
		
	}


	private void setupOptions(String[] args)
	{
		Modules.PopulateOptions();
		ReconOptions.getInstance().parseCommandline(args);
	}


	private void setupLogger()
	{
		Level level;
		switch (GeneralOptions.getInstance().getVerbose())
		{
			case 0:
				level = Level.OFF;
				break;
			case 1:
				level = Level.ERROR;
				break;
			case 2:
				level = Level.WARN;
				break;
			case 3:
				level = Level.INFO;
				break;
			case 4:
				level = Level.DEBUG;
				break;
			case 5:
				level = Level.TRACE;
				break;
			case 6:
				level = Level.ALL;
			default:
				level = Level.WARN;
		}
		ch.qos.logback.classic.Logger rootConsoleLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		rootConsoleLogger.setLevel(level);
		LoggerContext consoleContext = rootConsoleLogger.getLoggerContext();

		PatternLayoutEncoder consoleEncoder = new PatternLayoutEncoder();
		consoleEncoder.setContext(consoleContext);
		consoleEncoder.setPattern("%-5level: %message%n");
		consoleEncoder.start();

		ConsoleAppender<ILoggingEvent> ca = new ConsoleAppender<ILoggingEvent>();
		ca.setEncoder(consoleEncoder);
		ca.setContext(consoleContext);
		ca.start();
		rootConsoleLogger.addAppender(ca);


		ch.qos.logback.classic.Logger rootFileLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		rootFileLogger.setLevel(Level.DEBUG);
		LoggerContext fileContext = rootFileLogger.getLoggerContext();

		PatternLayoutEncoder fileEncoder = new PatternLayoutEncoder();
		fileEncoder.setContext(fileContext);
		fileEncoder.setPattern("%d{HH:mm:ss.SSS} %-5level: %message%n");
		fileEncoder.start();

		FileAppender<ILoggingEvent> fa = new FileAppender<ILoggingEvent>();
		fa.setAppend(true);
		fa.setContext(fileContext);
		fa.setEncoder(fileEncoder);
		fa.setFile(WorkingDirectories.getWorkingDirectory() + "mcp.log");
		fa.start();
		rootFileLogger.addAppender(fa);
		

		logger.debug("Running configuration:");
		for (String line: ReconOptions.getInstance().getOptionSummary())
		{
			logger.debug(line);
		}
	}

}
