package mcp.knowledgebase.nodeLibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.Node;
import mcp.knowledgebase.NodeType;

import java.util.*;

public class PortResponse
{
	public static final NodeType PORT_STATUS = NodeType.getByName("PORT_STATUS", "Port status");
	
	public static final Node PORT_OPEN = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATUS, "Open".getBytes());
	public static final Node PORT_OPEN_FILTERED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATUS, "Open|filtered".getBytes());
	public static final Node PORT_FILTERED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATUS, "Filtered".getBytes());
	public static final Node PORT_CLOSED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATUS, "Closed".getBytes());
	public static final Node PORT_WRAPPED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATUS, "Wrapped".getBytes());
	public static final Node PORT_UNKNOWN = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATUS, "Unknown".getBytes());
	
	


	public static Node parseNmapText(String text)
	{
		switch (text.toLowerCase())
		{
			case "open":
				return PORT_OPEN;
			case "open|filtered":
				return PORT_OPEN_FILTERED;
			case "filtered":
				return PORT_FILTERED;
			case "closed":
				return PORT_CLOSED;
			case "wrapped":
				return PORT_WRAPPED;
		}
		return PORT_UNKNOWN;
	}


	private PortResponse()
	{
	}
}
