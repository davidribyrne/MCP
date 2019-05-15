package mcp.knowledgebase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import space.dcce.commons.node_database.NodeDatabase;

import java.util.*;

public class KnowledgeBase extends NodeDatabase
{
	private final static Logger logger = LoggerFactory.getLogger(KnowledgeBase.class);
	
	public final static KnowledgeBase INSTANCE = new KnowledgeBase();

	private KnowledgeBase()
	{
	}
}
