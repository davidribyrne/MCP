package mcp.events.listeners;

import mcp.events.events.McpCompleteEvent;


public interface McpCompleteListener extends McpEventListener
{
	public void handleEvent(McpCompleteEvent reconEvent);

}
