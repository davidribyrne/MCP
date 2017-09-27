package mcp.knowledgebase.nodeLibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.DataType;
import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.Node;
import mcp.knowledgebase.DataType;

import java.util.*;

public class NetworkService
{
	public static final DataType NETWORK_SERVICE_DESCRIPTION = DataType.getByName("NETWORK_SERVICE_DESCRIPTION", "Service description");

	public static final DataType NETWORK_SERVICE_DESCRIPTION_REASON = DataType.getByName("NETWORK_SERVICE_DESCRIPTION_REASON", "Service description reason");
	public static final Node NETWORK_SERVICE_DESCRIPTION_REASON_UNKNOWN = KnowledgeBase.getInstance().getOrCreateNode(NETWORK_SERVICE_DESCRIPTION_REASON, "Unknown".getBytes());
	public static final Node NETWORK_SERVICE_DESCRIPTION_REASON_TABLE = KnowledgeBase.getInstance().getOrCreateNode(NETWORK_SERVICE_DESCRIPTION_REASON, "Table".getBytes());
	public static final Node NETWORK_SERVICE_DESCRIPTION_REASON_SCAN = KnowledgeBase.getInstance().getOrCreateNode(NETWORK_SERVICE_DESCRIPTION_REASON, "Scan".getBytes());
	public static final Node NETWORK_SERVICE_DESCRIPTION_REASON_INTERACTION = KnowledgeBase.getInstance().getOrCreateNode(NETWORK_SERVICE_DESCRIPTION_REASON, "Interaction".getBytes());

	
	public static final DataType NETWORK_SERVICE_SOFTWARE_GUESS = DataType.getByName("NETWORK_SERVICE_SOFTWARE_GUESS", "Service software guess");
	
	


	private NetworkService()
	{
	}
}
