package mcp.knowledgebase.nodeLibrary;

import mcp.knowledgebase.KnowledgeBase;
import space.dcce.commons.node_database.NodeType;

public class Files
{
//	public static final NodeType NETWORK_SERVICE_DESCRIPTION_REASON = NodeType.getByName("NETWORK_SERVICE_DESCRIPTION_REASON", "Service description reason");
//	public static final Node NETWORK_SERVICE_DESCRIPTION_REASON_UNKNOWN = KnowledgeBase.getInstance().getOrCreateNode(NETWORK_SERVICE_DESCRIPTION_REASON, "Unknown".getBytes());

	
	public static final NodeType PATH = KnowledgeBase.INSTANCE.getByName("PATH", "Path");


	private Files()
	{
	}
}
