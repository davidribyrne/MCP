package mcp.events.events;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import mcp.knowledgebase.nodes.Node;


public class NodeCreationEvent extends McpEvent
{
	private final Node node;
	
	public NodeCreationEvent(Node node)
	{
		this.node = node;
	}

	public Node getNode()
	{
		return node;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append(node.toString()).build();
	}
}
