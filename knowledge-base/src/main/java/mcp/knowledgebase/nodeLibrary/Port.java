package mcp.knowledgebase.nodeLibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.Node;
import mcp.knowledgebase.NodeType;

import java.util.*;

public class Port
{
	final static Logger logger = LoggerFactory.getLogger(Port.class);

	public static final NodeType PORT_TYPE = NodeType.getByName("PORT_TYPE", "Port type");
	public static final NodeType PORT_STATE = NodeType.getByName("PORT_STATE", "Port state");
	public static final NodeType PORT_STATE_REASON = NodeType.getByName("PORT_STATE_REASON", "Port state reason");
	public static final NodeType PORT_NUMBER = NodeType.getByName("PORT_NUMBER", "Port number");

	public static final Node PORT_STATE_OPEN = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE, "Open");
	public static final Node PORT_STATE_OPEN_FILTERERD = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE, "Open or filtered");
	public static final Node PORT_STATE_FILTERED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE, "Filtered");
	public static final Node PORT_STATE_CLOSED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE, "Closed");
	public static final Node PORT_STATE_WRAPPED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE, "Wrapped");
	public static final Node PORT_STATE_UNKNOWN = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE, "Unknown");

	
	public static final Node PORT_TYPE_UDP = KnowledgeBase.getInstance().getOrCreateNode(PORT_TYPE, "UDP");
	public static final Node PORT_TYPE_TCP = KnowledgeBase.getInstance().getOrCreateNode(PORT_TYPE, "TCP");
	public static final Node PORT_TYPE_IP = KnowledgeBase.getInstance().getOrCreateNode(PORT_TYPE, "IP");
	public static final Node PORT_TYPE_STCP = KnowledgeBase.getInstance().getOrCreateNode(PORT_TYPE, "STCP");
	public static final Node PORT_TYPE_UNKNOWN = KnowledgeBase.getInstance().getOrCreateNode(PORT_TYPE, "Unknown");

	
	public static Node portStateFromNmap(String text)
	{
		switch (text.toLowerCase())
		{
			case "open":
				return PORT_STATE_OPEN;
			case "open|filtered":
				return PORT_STATE_OPEN_FILTERERD;
			case "filtered":
				return PORT_STATE_FILTERED;
			case "closed":
				return PORT_STATE_CLOSED;
			case "wrapped":
				return PORT_STATE_WRAPPED;
		}
		logger.warn("The Nmap port state '" + text + "' is unknown.");

		return PORT_STATE_UNKNOWN;
	}



	public static Node portTypeFromNmap(String name)
	{
		switch (name.toLowerCase())
		{
			case "udp":
				return PORT_TYPE_UDP;
			case "tcp":
				return PORT_TYPE_TCP;
			case "ip":
				return PORT_TYPE_IP;
			case "stcp":
				return PORT_TYPE_STCP;
		}
		logger.warn("The Nmap port type '" + name + "' is unknown.");
		return PORT_TYPE_UNKNOWN;
	}

	
	
	private Port()
	{
	}

	
}
