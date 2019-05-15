package mcp.knowledgebase.nodeLibrary;


import mcp.knowledgebase.KnowledgeBase;
import space.dcce.commons.node_database.NodeType;


public class Network
{
	public static final NodeType IPV4_ADDRESS = KnowledgeBase.INSTANCE.getByName("IPV4_ADDRESS", "IPv4 address");
	public static final NodeType IPV6_ADDRESS = KnowledgeBase.INSTANCE.getByName("IPV6_ADDRESS", "IPv6 address");
	public static final NodeType MAC_ADDRESS = KnowledgeBase.INSTANCE.getByName("MAC_ADDRESS", "MAC address");
	

	
	private Network()
	{
	}
}
