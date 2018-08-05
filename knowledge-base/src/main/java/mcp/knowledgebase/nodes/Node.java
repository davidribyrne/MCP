package mcp.knowledgebase.nodes;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.Connection;
import mcp.knowledgebase.NodeType;
import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.UniqueDatum;
import space.dcce.commons.general.UniqueList;

public abstract class Node extends UniqueDatum
{
	private final static Logger logger = LoggerFactory.getLogger(Node.class);
	private final UniqueList<Connection> connections;
	private final Timestamp creationTime;
	private final NodeType nodeType;
	private final String value;



	public Node(NodeType nodeType, String value)
	{
		super();
		this.value = value;
		this.nodeType = nodeType;
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
	Node(NodeType NodeType, String value, UUID uuid, Timestamp creationTime)
	{
		super(uuid);
		this.value = value;
		this.nodeType = NodeType;
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


	public String getValue()
	{
		return value;
	}


	public NodeType getNodeType()
	{
		return nodeType;
	}
}
