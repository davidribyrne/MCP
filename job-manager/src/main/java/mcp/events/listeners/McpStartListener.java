package mcp.events.listeners;

import mcp.events.events.McpStartEvent;

//TODO Change to or add ScopeChangeListener
public interface McpStartListener extends McpEventListener
{
	public void handleEvent(McpStartEvent event);
}
