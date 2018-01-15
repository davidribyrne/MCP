package mcp.knowledgebase.nodes;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.Connection;
import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.UniqueDatum;
import net.dacce.commons.general.UniqueList;

public abstract class Node extends UniqueDatum
{
	private final static Logger logger = LoggerFactory.getLogger(Node.class);
	private final UniqueList<Connection> connections;
	private final Timestamp creationTime;

	public Node(	)
	{
		super();
		creationTime = Timestamp.from(Instant.now());
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
	Node(UUID uuid, Timestamp creationTime)
	{
		super(uuid);
		this.creationTime = creationTime;
		this.connections = new UniqueList<Connection>(false);
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
