package mcp.events.listeners;

import mcp.events.events.ModuleRunCompleteEvent;

public interface ModuleRunCompleteListener extends McpEventListener
{
	public void handleEvent(ModuleRunCompleteEvent reconEvent);
}
