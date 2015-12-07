package mcp.events.events;

import mcp.events.listeners.NodeUpdateType;
import mcp.events.listeners.nodeUpdate.UpdateAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class NodeUpdateEvent extends McpEvent
{
	private final static Logger logger = LoggerFactory.getLogger(NodeUpdateEvent.class);

	private final Object node;
	private final UpdateAction action;
	
	public NodeUpdateEvent(Object node, UpdateAction action)
	{
		this.node = node;
		this.action = action;
	}

	public Object getNode()
	{
		return node;
	}

	public UpdateAction getAction()
	{
		return action;
	}




}
