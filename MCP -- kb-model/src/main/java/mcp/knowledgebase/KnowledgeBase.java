package mcp.knowledgebase;

import java.sql.Connection;
import java.sql.SQLException;

public interface KnowledgeBase
{
	public void addNodeType(NodeType nodeType);
	public void addNode(Node node);

//
//	public Iterable<Node> getNodesByType(NodeType type);
//
//	public Iterable<Node> getAllNodes();
//	
//	public void addNode(Node node);

}
