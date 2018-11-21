package mcp.knowledgebase.nodeLibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.NodeType;

import java.util.*;

public class Files
{
	private final static Logger logger = LoggerFactory.getLogger(Files.class);
//	public static final NodeType NETWORK_SERVICE_DESCRIPTION_REASON = NodeType.getByName("NETWORK_SERVICE_DESCRIPTION_REASON", "Service description reason");
//	public static final Node NETWORK_SERVICE_DESCRIPTION_REASON_UNKNOWN = KnowledgeBase.getInstance().getOrCreateNode(NETWORK_SERVICE_DESCRIPTION_REASON, "Unknown".getBytes());

	
	public static final NodeType PATH = NodeType.getByName("PATH", "Path");


	private Files()
	{
	}
}
