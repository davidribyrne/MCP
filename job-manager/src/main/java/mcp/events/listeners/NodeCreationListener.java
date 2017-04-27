package mcp.events.listeners;

import java.util.Collection;

import mcp.events.events.ElementCreationEvent;
import mcp.knowledgebase.nodes.Node;



public interface NodeCreationListener extends McpEventListener
{
	public void handleEvent(ElementCreationEvent reconEvent);


	public Collection<Class<? extends Node>> getNodeMonitorClasses();
}
