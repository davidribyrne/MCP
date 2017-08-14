package mcp.events.listeners;

import java.util.Collection;

import mcp.events.events.NodeCreationEvent;
import mcp.knowledgebase.NodeType;



public interface NodeCreationListener extends McpEventListener
{
	public void handleEvent(NodeCreationEvent reconEvent);


	public Collection<NodeType> getMonitorNodeTypes();
}
