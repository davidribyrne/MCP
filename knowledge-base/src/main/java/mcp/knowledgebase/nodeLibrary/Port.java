package mcp.knowledgebase.nodeLibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.NodeType;
import mcp.knowledgebase.nodes.Node;

import java.util.*;

public class Port
{
	public static final NodeType PORT = NodeType.getByName("PORT", "Network port");
	public static final NodeType PORT_TYPE = NodeType.getByName("PORT_TYPE", "Port type");
	public static final NodeType PORT_STATE = NodeType.getByName("PORT_STATE", "Port state");
	public static final NodeType PORT_NUMBER = NodeType.getByName("PORT_NUMBER", "Port number");

	public static final Node PORT_OPEN = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE, "Open".getBytes());


	private Port()
	{
	}

	
}
