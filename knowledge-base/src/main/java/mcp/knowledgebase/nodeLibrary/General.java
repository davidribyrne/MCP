package mcp.knowledgebase.nodeLibrary;

import mcp.knowledgebase.KnowledgeBase;
import space.dcce.commons.node_database.NodeType;

public class General
{

//	public static final NodeType NETWORK_SERVICE_DESCRIPTION_REASON = database.getByName("NETWORK_SERVICE_DESCRIPTION_REASON", "Service description reason");
//	public static final Node NETWORK_SERVICE_DESCRIPTION_REASON_UNKNOWN = KnowledgeBase.getInstance().getOrCreateNode(NETWORK_SERVICE_DESCRIPTION_REASON, "Unknown".getBytes());

	
	public static final NodeType NOTE = KnowledgeBase.INSTANCE.getByName("NOTE", "Note");
	public static final NodeType COMMAND_LINE = KnowledgeBase.INSTANCE.getByName("COMMAND_LINE", "Command line - executable and parameters");
	
}
