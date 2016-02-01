package mcp.events.events;

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


}
