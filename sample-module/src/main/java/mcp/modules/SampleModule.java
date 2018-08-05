package mcp.modules;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.events.events.McpCompleteEvent;
import mcp.events.events.McpStartEvent;
import mcp.events.listeners.McpCompleteListener;
import mcp.events.listeners.McpStartListener;
import mcp.jobmanager.executors.ExecutionScheduler;
import space.dcce.commons.cli.ModuleOption;

public class SampleModule extends ExternalModule implements McpStartListener, McpCompleteListener
{
	final static Logger logger = LoggerFactory.getLogger(SampleModule.class);

	private ModuleOption testStringOption;
	public SampleModule()
	{
		super("Sample Module", "sample", "This is just a sample module");
		testStringOption = new ModuleOption("testString", "Just a test string to repeat.", true, true, "", "string");
		addOption(testStringOption);
	}

	// Do stuff before the module starts doing work
	@Override
	public void initialize()
	{
		ExecutionScheduler.getInstance().registerListener(McpStartEvent.class, this);
		ExecutionScheduler.getInstance().registerListener(McpCompleteEvent.class, this);
	}

	public void handleEvent(McpCompleteEvent reconEvent)
	{
		logger.info("MCP is all done - " + testStringOption.getValue());
	}

	public void handleEvent(McpStartEvent event)
	{
		logger.info("MCP is starting - " + testStringOption.getValue());
	}
}
