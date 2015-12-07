package mcp.events.listeners;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import mcp.events.listeners.nodeUpdate.UpdateAction;


public class NodeUpdateType
{
	private final UpdateAction updateAction;
	private final Class<? extends Object> nodeClass;


	public NodeUpdateType(UpdateAction updateType, Class<? extends Object> nodeClass)
	{
		this.updateAction = updateType;
		this.nodeClass = nodeClass;
	}


	public UpdateAction getUpdateAction()
	{
		return updateAction;
	}


	public Class<? extends Object> getNodeClass()
	{
		return nodeClass;
	}


	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().append(updateAction).append(nodeClass).toHashCode();
	}


	@Override
	public boolean equals(Object obj)
	{
		NodeUpdateType n = (NodeUpdateType) obj; 
		return new EqualsBuilder().append(n.nodeClass, nodeClass).append(n.updateAction, updateAction).isEquals();
	}
}
