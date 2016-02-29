package mcp.events.events;

import org.apache.commons.lang3.builder.ToStringBuilder;
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

	@Override
	public String toString()
	{
		return new ToStringBuilder(this).appendSuper(super.toString()).append(attribute.toString()).build();
	}
}
