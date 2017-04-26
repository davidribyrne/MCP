package mcp.events.events;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import mcp.knowledgebase.KbElement;

public class ElementCreationEvent extends McpEvent
{
	private final KbElement element;
	
	public ElementCreationEvent(KbElement element)
	{
		this.element = element;
	}

	public KbElement getElement()
	{
		return element;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append(element.toString()).build();
	}
}
