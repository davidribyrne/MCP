package mcp.knowledgebase.nodeLibrary;


import mcp.knowledgebase.KnowledgeBase;
import space.dcce.commons.node_database.NodeType;


public class Hostnames
{
	public static final NodeType HOSTNAME = KnowledgeBase.INSTANCE.getByName("HOSTNAME", "Hostname");
	public static final NodeType DOMAIN = KnowledgeBase.INSTANCE.getByName("DOMAIN", "Domain");
	

	
	private Hostnames()
	{
	}
}
