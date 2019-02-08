package mcp.events.listeners;

import mcp.events.events.McpNormalExitEvent;

public interface McpNormalExitListener extends McpEventListener
{
	public void handleEvent(McpNormalExitEvent event);
}
