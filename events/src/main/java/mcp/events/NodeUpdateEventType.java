package mcp.events;

import mcp.knowledgebase.nodes.Node;


public class NodeUpdateEventType
{
	private final UpdateType updateType;
	private final Class<? extends Node> nodeClass;


	public NodeUpdateEventType(UpdateType updateType, Class<? extends Node> nodeClass)
	{
		this.updateType = updateType;
		this.nodeClass = nodeClass;
	}


	public UpdateType getUpdateType()
	{
		return updateType;
	}


	public Class<? extends Node> getNodeClass()
	{
		return nodeClass;
	}
}
