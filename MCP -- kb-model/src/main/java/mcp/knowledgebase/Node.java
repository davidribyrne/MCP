package mcp.knowledgebase;

import java.sql.Timestamp;
import java.util.List;


public interface Node extends UniqueElement
{
	public byte[] getValue();
	public NodeType getType();
	public Iterable<Connection> getConnections(); 
	public void addConnection(Connection connection);
	public Timestamp getCreationTime();
}
