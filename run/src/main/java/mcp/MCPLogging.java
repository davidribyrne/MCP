package mcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import mcp.commons.WorkingDirectories;
import mcp.modules.GeneralOptions;
import mcp.options.MCPOptions;


public class MCPLogging
{
	private final static Logger logger = LoggerFactory.getLogger(MCPLogging.class);


	static void setupLogger()
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
				break;
			default:
				level = Level.WARN;
		}

		ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		rootLogger.setLevel(Level.ALL);
		LoggerContext loggerContext = rootLogger.getLoggerContext();


		// Setup console logging
		ThresholdFilter consoleLevelFilter = new ThresholdFilter();
		consoleLevelFilter.setLevel(level.toString());
		consoleLevelFilter.start();

		PatternLayoutEncoder consoleEncoder = new PatternLayoutEncoder();
		consoleEncoder.setContext(loggerContext);
		consoleEncoder.setPattern("%-5level: %message%n");
		consoleEncoder.start();

		ConsoleAppender<ILoggingEvent> ca = new ConsoleAppender<ILoggingEvent>();
		ca.addFilter(consoleLevelFilter);
		ca.setEncoder(consoleEncoder);
		ca.setContext(loggerContext);
		ca.start();
		rootLogger.addAppender(ca);


		// Setup file logging
		PatternLayoutEncoder fileEncoder = new PatternLayoutEncoder();
		fileEncoder.setContext(loggerContext);
		fileEncoder.setPattern("%d{HH:mm:ss.SSS} %-5level: %message%n");
		fileEncoder.start();

		FileAppender<ILoggingEvent> fa = new FileAppender<ILoggingEvent>();
		fa.setAppend(true);
		fa.setContext(loggerContext);
		fa.setEncoder(fileEncoder);
		fa.setFile(WorkingDirectories.getWorkingDirectory() + "mcp.log");
		fa.start();
		rootLogger.addAppender(fa);


		logger.debug("Running configuration:");
		for (String line : MCPOptions.getInstance().getOptionSummary())
		{
			logger.debug(line);
		}
	}

}
