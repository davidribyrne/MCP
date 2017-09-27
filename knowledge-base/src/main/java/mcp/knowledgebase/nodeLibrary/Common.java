package mcp.knowledgebase.nodeLibrary;


import mcp.knowledgebase.DataType;
import mcp.knowledgebase.DataType;


public class Common
{
	public static final DataType IPV4_ADDRESS = DataType.getByName("IPV4_ADDRESS", "IPv4 address");
	public static final DataType IPV6_ADDRESS = DataType.getByName("IPV6_ADDRESS", "IPv6 address");
	public static final DataType MAC_ADDRESS = DataType.getByName("MAC_ADDRESS", "MAC address");
	public static final DataType HOSTNAME = DataType.getByName("HOSTNAME", "Hostname");
	public static final DataType DOMAIN = DataType.getByName("DOMAIN", "Domain");
	
	public static final DataType VENDOR = DataType.getByName("VENDOR", "Vendor");
	public static final DataType SOFTWARE_PRODUCT_FAMILY = DataType.getByName("SOFTWARE_PRODUCT_FAMILY", "Software product family");
	public static final DataType SOFTWARE_PRODUCT_VERSION = DataType.getByName("SOFTWARE_PRODUCT_VERSION", "Softare product version");

	
	private Common()
	{
	}
}
