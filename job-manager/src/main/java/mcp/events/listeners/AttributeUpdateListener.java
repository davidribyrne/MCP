package mcp.events.listeners;

import java.util.Collection;

import mcp.events.events.AttributeUpdateEvent;
import mcp.knowledgebase.attributes.NodeAttribute;

public interface AttributeUpdateListener extends McpEventListener
{

	
	public void handleEvent(AttributeUpdateEvent reconEvent);


	public Collection<Class<? extends NodeAttribute>> getAttributeMonitorClasses();

}
