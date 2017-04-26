package mcp.events.events;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append(attribute.toString()).build();
	}
}
