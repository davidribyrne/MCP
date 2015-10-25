package mcp.events.listeners;

import mcp.events.events.ReconCompleteEvent;


public interface ReconCompleteListener extends ReconEventListener
{
	public void handleEvent(ReconCompleteEvent reconEvent);

}
