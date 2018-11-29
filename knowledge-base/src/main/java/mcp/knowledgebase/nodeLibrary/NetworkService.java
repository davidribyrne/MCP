package mcp.knowledgebase.nodeLibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.Node;
import mcp.knowledgebase.NodeType;

public class NetworkService
{
	final static Logger logger = LoggerFactory.getLogger(NetworkService.class);

	public static final NodeType NETWORK_SERVICE_DESCRIPTION = NodeType.getByName("NETWORK_SERVICE_DESCRIPTION", "Service description");
	public static final NodeType NETWORK_SERVICE_SOFTWARE_GUESS = NodeType.getByName("NETWORK_SERVICE_SOFTWARE_GUESS", "Service software guess");
	public static final NodeType NETWORK_SERVICE_DESCRIPTION_REASON = NodeType.getByName("NETWORK_SERVICE_DESCRIPTION_REASON", "Service description reason");

	public static final Node NETWORK_SERVICE_DESCRIPTION_REASON_UNKNOWN = KnowledgeBase.instance.getOrCreateNode(NETWORK_SERVICE_DESCRIPTION_REASON, "Unknown");
	public static final Node NETWORK_SERVICE_DESCRIPTION_REASON_TABLE = KnowledgeBase.instance.getOrCreateNode(NETWORK_SERVICE_DESCRIPTION_REASON, "Table");
	public static final Node NETWORK_SERVICE_DESCRIPTION_REASON_SCAN = KnowledgeBase.instance.getOrCreateNode(NETWORK_SERVICE_DESCRIPTION_REASON, "Scan");
	public static final Node NETWORK_SERVICE_DESCRIPTION_REASON_INTERACTION = KnowledgeBase.instance.getOrCreateNode(NETWORK_SERVICE_DESCRIPTION_REASON, "Interaction");

	public static Node serviceReasonFromNmap(String reason)
	{
		switch (reason.toLowerCase())
		{
			case "table":
				return NETWORK_SERVICE_DESCRIPTION_REASON_TABLE;
			case "probed":
				return NETWORK_SERVICE_DESCRIPTION_REASON_SCAN;
		}
		logger.warn("The Nmap service reson '" + reason + "' is unknown.");
		
		return NETWORK_SERVICE_DESCRIPTION_REASON_UNKNOWN;
	}


	private NetworkService()
	{
	}
}
