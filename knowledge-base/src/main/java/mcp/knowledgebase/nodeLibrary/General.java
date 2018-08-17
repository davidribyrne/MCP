package mcp.knowledgebase.nodeLibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.NodeType;

import java.util.*;

public class General
{
	private final static Logger logger = LoggerFactory.getLogger(General.class);

//	public static final NodeType NETWORK_SERVICE_DESCRIPTION_REASON = NodeType.getByName("NETWORK_SERVICE_DESCRIPTION_REASON", "Service description reason");
//	public static final Node NETWORK_SERVICE_DESCRIPTION_REASON_UNKNOWN = KnowledgeBase.getInstance().getOrCreateNode(NETWORK_SERVICE_DESCRIPTION_REASON, "Unknown".getBytes());

	
	public static final NodeType NOTE = NodeType.getByName("NOTE", "Note");
	public static final NodeType COMMAND_LINE = NodeType.getByName("COMMAND_LINE", "Command line - executable and parameters");
	
}
