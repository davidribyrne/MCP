package mcp.knowledgebase;

import space.dcce.commons.node_database.NodeDatabase;

public class KnowledgeBase extends NodeDatabase
{
	
	public final static KnowledgeBase INSTANCE = new KnowledgeBase();

	private KnowledgeBase()
	{
	}
}
