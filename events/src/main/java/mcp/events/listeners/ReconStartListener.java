package mcp.events.listeners;

import mcp.events.events.ReconStartEvent;

public interface ReconStartListener extends ReconEventListener
{
	public void handleEvent(ReconStartEvent event);
}
