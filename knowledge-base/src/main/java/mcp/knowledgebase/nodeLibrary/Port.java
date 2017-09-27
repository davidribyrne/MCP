package mcp.knowledgebase.nodeLibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.AttributeType;
import mcp.knowledgebase.DataType;
import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.Node;
import mcp.knowledgebase.NodeType;
import mcp.knowledgebase.DataType;

import java.util.*;

public class Port
{
	public static final NodeType PORT_TYPE = AttributeType.getByName("PORT_STATUS", "Port status");

	public static final Node PORT_OPEN = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE, "Open".getBytes());

	public static final AttributeType PORT_STATE = AttributeType.getByName("PORT_STATUS", "Port status");
	public static final AttributeType PORT_NUMBER = AttributeType.getByName("PORT_NUMBER", "Port number");

	private Port()
	{
	}

	
}
