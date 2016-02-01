package mcp.events.events;

import mcp.knowledgebase.attributes.NodeAttribute;

public class AttributeUpdateEvent extends McpEvent
{
	private final NodeAttribute attribute;
	
	public AttributeUpdateEvent(NodeAttribute attribute)
	{
		this.attribute = attribute;
	}
	
	public NodeAttribute getAttribute()
	{
		return attribute;
	}

}
