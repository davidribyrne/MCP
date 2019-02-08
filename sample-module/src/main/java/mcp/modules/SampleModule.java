package mcp.modules;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.events.events.McpStartEvent;
import mcp.events.listeners.McpStartListener;
import mcp.jobmanager.executors.ExecutionScheduler;
import space.dcce.commons.cli.Option;
import space.dcce.commons.cli.OptionContainer;
import space.dcce.commons.cli.OptionGroup;

public class SampleModule extends ExternalModule implements McpStartListener
{
	final static Logger logger = LoggerFactory.getLogger(SampleModule.class);

	private static OptionGroup options;
	private static Option testStringOption;
	static 
	{
		options = new OptionGroup("sample-module-options", "Description");
		testStringOption = new Option("", "testString", "Just a test string to repeat.", true, true, "", "string");

	}

	
	
	public SampleModule()
	{
		super("Sample Module", "sample", "This is just a sample module");
	}

	// Do stuff before the module starts doing work
	@Override
	public void initialize()
	{
		ExecutionScheduler.getInstance().registerListener(McpStartEvent.class, this);
	}

//	public void handleEvent(McpCompleteEvent reconEvent)
//	{
//		logger.info("MCP is all done - " + testStringOption.getValue());
//	}

	public void handleEvent(McpStartEvent event)
	{
		logger.info("MCP is starting - " + testStringOption.getValue());
	}
	

	public static OptionContainer getOptions()
	{
		return options;
	}
}
