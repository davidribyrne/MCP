package mcp.events.listeners;

import java.util.Collection;

import mcp.events.NodeUpdateEventType;
import mcp.events.events.NodeUpdateEvent;


public interface NodeUpdateListener extends ReconEventListener
{
	public void handleEvent(NodeUpdateEvent reconEvent);


	public Collection<NodeUpdateEventType> getNodeUpdateEventTypes();
}
