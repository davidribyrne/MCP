package mcp.knowledgebase;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dacce.commons.general.UniqueList;

public class Node extends ComponentWithAttributes
{
	private final static Logger logger = LoggerFactory.getLogger(Node.class);
	private final byte[] value;
	private final UniqueList<Connection> connections;
	private final DataType type;
	private final Timestamp creationTime;

	public Node(	DataType type, byte [] value)
	{
		creationTime = Timestamp.from(Instant.now());
		this.type = type;
		this.value = value;
		connections = new UniqueList<Connection>(1);
		
		KnowledgeBase.getInstance().addNode(this);
	}
	
	
	/**
	 * Only use from KnowledgeBaseImpl
	 * @param uuid
	 * @param creationTime
	 * @param type
	 * @param value
	 */
	Node(UUID uuid, Timestamp creationTime, DataType type, byte [] value)
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


	public byte[] getValue()
	{
		return value;
	}


	public DataType getType()
	{
		return type;
	}


	public Iterable<Connection> getConnections()
	{
		return Collections.unmodifiableList(connections);
	}


	public void addConnection(Connection connection)
	{
		connections.add(connection);
	}


	public Timestamp getCreationTime()
	{
		return creationTime;
	}
}
