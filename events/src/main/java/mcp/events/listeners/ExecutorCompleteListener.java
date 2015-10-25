package mcp.events.listeners;

import mcp.events.events.ExecutorCompleteEvent;


public interface ExecutorCompleteListener extends ReconEventListener
{
	public void handleEvent(ExecutorCompleteEvent event);


	public String[] getProgramNames();
}
