package mcp.events.listeners;

import mcp.events.events.ExecutorCompleteEvent;


public interface ExecutorCompleteListener extends McpEventListener
{
	public void handleEvent(ExecutorCompleteEvent event);


	public String[] getProgramNames();
}
