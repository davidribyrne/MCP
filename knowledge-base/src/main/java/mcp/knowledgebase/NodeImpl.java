package mcp.knowledgebase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dacce.commons.general.UniqueList;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

public class NodeImpl extends UniqueElementImpl implements Node
{
	private final static Logger logger = LoggerFactory.getLogger(NodeImpl.class);
	private final byte[] value;
	private final UniqueList<Connection> connections;
	private final NodeType type;
	private final Timestamp creationTime;

	public NodeImpl(	NodeType type, byte [] value)
	{
		creationTime = Timestamp.from(Instant.now());
		this.type = type;
		this.value = value;
		connections = new UniqueList<Connection>(1);
		
		KnowledgeBaseImpl.getInstance().addNode(this);
	}
	
	
	/**
	 * Only use from KnowledgeBaseImpl
	 * @param uuid
	 * @param creationTime
	 * @param type
	 * @param value
	 */
	NodeImpl(UUID uuid, Timestamp creationTime, NodeType type, byte [] value)
	{
		super(uuid);
		this.creationTime = creationTime;
		this.type = type;
		this.value = value;
		this.connections = new UniqueList<Connection>(false);
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


	@Override
	public byte[] getValue()
	{
		return value;
	}


	@Override
	public NodeType getType()
	{
		return type;
	}

	

	@Override
	public Iterable<Connection> getConnections()
	{
		return Collections.unmodifiableList(connections);
	}



	@Override
	public void addConnection(Connection connection)
	{
		connections.add(connection);
	}


	@Override
	public Timestamp getCreationTime()
	{
		return creationTime;
	}
}
