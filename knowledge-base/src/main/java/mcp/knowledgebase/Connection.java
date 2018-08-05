package mcp.knowledgebase;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.nodes.Node;

public class Connection extends UniqueDatum
{
	private final static Logger logger = LoggerFactory.getLogger(Connection.class);
	private final Node nodeA;
	private final Node nodeB;
	

	public Connection(Node nodeA, Node nodeB)
	{
		this(null, nodeA, nodeB);
	}
	

	/**
	 * Use only from KnowledgeBaseImpl
	 * @param uuid
	 */
	Connection(UUID uuid, Node nodeA, Node nodeB)
	{
		super(uuid);
		this.nodeA = nodeA;
		this.nodeB = nodeB;
		nodeA.addConnection(this);
		nodeB.addConnection(this);
	}



	public Node getNodeA()
	{
		return nodeA;
	}


	public Node getNodeB()
	{
		return nodeB;
	}
	
	

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		return sb.toString();
	}


	@Override
	public int hashCode()
	{
		return super.hashCode();
	}


	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj);
	}

}
