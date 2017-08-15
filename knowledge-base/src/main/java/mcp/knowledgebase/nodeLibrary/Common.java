package mcp.knowledgebase.nodeLibrary;


import mcp.knowledgebase.NodeType;


public class Common
{
	public static final NodeType IPV4_ADDRESS = NodeType.getByName("IPV4_ADDRESS", "IPv4 address");
	public static final NodeType IPV6_ADDRESS = NodeType.getByName("IPV6_ADDRESS", "IPv6 address");
	public static final NodeType MAC_ADDRESS = NodeType.getByName("MAC_ADDRESS", "MAC address");
	public static final NodeType HOSTNAME = NodeType.getByName("HOSTNAME", "Hostname");
	public static final NodeType DOMAIN = NodeType.getByName("DOMAIN", "Domain");
	
	public static final NodeType VENDOR = NodeType.getByName("VENDOR", "Vendor");
	public static final NodeType SOFTWARE_PRODUCT_FAMILY = NodeType.getByName("SOFTWARE_PRODUCT_FAMILY", "Software product family");
	public static final NodeType SOFTWARE_PRODUCT_VERSION = NodeType.getByName("SOFTWARE_PRODUCT_VERSION", "Softare product version");

	
	private Common()
	{
	}
}
