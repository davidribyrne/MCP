package mcp.knowledgebase.nodeLibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.DataType;
import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.Node;
import mcp.knowledgebase.DataType;

import java.util.*;

public class PortState
{
	public static final DataType PORT_STATE = DataType.getByName("PORT_STATUS", "Port status");
	
	public static final Node PORT_OPEN = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE, "Open".getBytes());
	public static final Node PORT_OPEN_FILTERED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE, "Open|filtered".getBytes());
	public static final Node PORT_FILTERED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE, "Filtered".getBytes());
	public static final Node PORT_CLOSED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE, "Closed".getBytes());
	public static final Node PORT_WRAPPED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE, "Wrapped".getBytes());
	public static final Node PORT_UNKNOWN = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE, "Unknown".getBytes());
	
	


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


	private PortState()
	{
	}
}
