package mcp.knowledgebase.nodeLibrary;

import mcp.knowledgebase.KnowledgeBase;
import space.dcce.commons.node_database.NodeType;

public class SoftwareHardware
{

//	public static final NodeType NETWORK_SERVICE_DESCRIPTION_REASON = KnowledgeBase.INSTANCE.getByName("NETWORK_SERVICE_DESCRIPTION_REASON", "Service description reason");
//	public static final Node NETWORK_SERVICE_DESCRIPTION_REASON_UNKNOWN = KnowledgeBase.getInstance().getOrCreateNode(NETWORK_SERVICE_DESCRIPTION_REASON, "Unknown".getBytes());

	
	public static final NodeType VENDOR = KnowledgeBase.INSTANCE.getByName("VENDOR", "Vendor");
	public static final NodeType SOFTWARE_PRODUCT_FAMILY = KnowledgeBase.INSTANCE.getByName("SOFTWARE_PRODUCT_FAMILY", "Software product family");
	public static final NodeType SOFTWARE_PRODUCT_VERSION = KnowledgeBase.INSTANCE.getByName("SOFTWARE_PRODUCT_VERSION", "Softare product version");
	public static final NodeType HOST_OS_GUESS = KnowledgeBase.INSTANCE.getByName("HOST_OS_GUESS", "Host OS guess");
	public static final NodeType HOST_OS_GUESS_CONFIDENCE = KnowledgeBase.INSTANCE.getByName("HOST_OS_GUESS_CONFIDENCE", "Host OS guess confidence");

	public static final NodeType DEVICE_TYPE = KnowledgeBase.INSTANCE.getByName("DEVICE_TYPE", "Device type");

	
	
	private SoftwareHardware()
	{
	}
}
