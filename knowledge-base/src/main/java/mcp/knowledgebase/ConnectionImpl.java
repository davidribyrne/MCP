package mcp.knowledgebase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class ConnectionImpl extends UniqueElementImpl implements Connection
{
	private final static Logger logger = LoggerFactory.getLogger(ConnectionImpl.class);
	private final Node nodeA;
	private final Node nodeB;
	

	public ConnectionImpl(Node nodeA, Node nodeB)
	{
		this(null, nodeA, nodeB);
	}
	

	/**
	 * Use only from KnowledgeBaseImpl
	 * @param uuid
	 */
	ConnectionImpl(UUID uuid, Node nodeA, Node nodeB)
	{
		super(uuid);
		this.nodeA = nodeA;
		this.nodeB = nodeB;
		nodeA.addConnection(this);
		nodeB.addConnection(this);
	}



	@Override
	public Node getNodeA()
	{
		return nodeA;
	}


	@Override
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
