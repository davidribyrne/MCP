package mcp.knowledgebase.nodeLibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.NodeType;

import java.util.*;

public class SoftwareHardware
{

//	public static final NodeType NETWORK_SERVICE_DESCRIPTION_REASON = NodeType.getByName("NETWORK_SERVICE_DESCRIPTION_REASON", "Service description reason");
//	public static final Node NETWORK_SERVICE_DESCRIPTION_REASON_UNKNOWN = KnowledgeBase.getInstance().getOrCreateNode(NETWORK_SERVICE_DESCRIPTION_REASON, "Unknown".getBytes());

	
	public static final NodeType VENDOR = NodeType.getByName("VENDOR", "Vendor");
	public static final NodeType SOFTWARE_PRODUCT_FAMILY = NodeType.getByName("SOFTWARE_PRODUCT_FAMILY", "Software product family");
	public static final NodeType SOFTWARE_PRODUCT_VERSION = NodeType.getByName("SOFTWARE_PRODUCT_VERSION", "Softare product version");
	public static final NodeType HOST_OS_GUESS = NodeType.getByName("HOST_OS_GUESS", "Host OS guess");
	public static final NodeType HOST_OS_GUESS_CONFIDENCE = NodeType.getByName("HOST_OS_GUESS_CONFIDENCE", "Host OS guess confidence");

	public static final NodeType DEVICE_TYPE = NodeType.getByName("DEVICE_TYPE", "Device type");

	
	
	private SoftwareHardware()
	{
	}
}
