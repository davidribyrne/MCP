package mcp.events.listeners;

import mcp.events.events.McpStartEvent;

public interface McpStartListener extends McpEventListener
{
	public void handleEvent(McpStartEvent event);
}
