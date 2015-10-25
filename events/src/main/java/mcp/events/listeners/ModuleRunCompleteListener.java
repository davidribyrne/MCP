package mcp.events.listeners;

import mcp.events.events.ModuleRunCompleteEvent;

public interface ModuleRunCompleteListener extends ReconEventListener
{
	public void handleEvent(ModuleRunCompleteEvent reconEvent);
}
