package mcp.knowledgebase.nodeLibrary;


import mcp.knowledgebase.NodeType;


public class Network
{
	public static final NodeType IPV4_ADDRESS = NodeType.getByName("IPV4_ADDRESS", "IPv4 address");
	public static final NodeType IPV6_ADDRESS = NodeType.getByName("IPV6_ADDRESS", "IPv6 address");
	public static final NodeType MAC_ADDRESS = NodeType.getByName("MAC_ADDRESS", "MAC address");
	

	
	private Network()
	{
	}
}
