package mcp.events.listeners;

import java.util.Collection;

import mcp.events.events.NodeUpdateEvent;



public interface NodeUpdateListener extends McpEventListener
{
	public void handleEvent(NodeUpdateEvent reconEvent);


	public Collection<NodeUpdateType> getNodeUpdateEventTypes();
}
