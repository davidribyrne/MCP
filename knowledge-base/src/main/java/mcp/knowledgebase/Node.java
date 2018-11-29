package mcp.knowledgebase;

import java.util.Collections;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import space.dcce.commons.general.UniqueList;

public class Node extends UniqueDatum
{
	private final static Logger logger = LoggerFactory.getLogger(Node.class);
	private final UniqueList<UUID> connections;
	private final NodeType nodeType;
	private final String value;



	public Node(NodeType nodeType, String value)
	{
		this(null, nodeType, value);
		KnowledgeBase.storage.addNode(this);
	}
	
	
	/**
	 * Only use from KnowledgeBaseStorage
	 * @param uuid
	 * @param value Cannot be null
	 * @param type Cannot be null
	 */
	Node(UUID uuid, NodeType nodeType, String value) throws NullPointerException
	{
		super(uuid == null ? constructUUID(nodeType, value): uuid);
		if (value == null)
			throw new NullPointerException("Node value cannot be null");
		this.value = value;
		this.nodeType = nodeType;
		this.connections = new UniqueList<UUID>(false);
		NodeCache.getInstance().addItem(this);
	}

	private static UUID constructUUID(NodeType nodeType, String value)
	{
		return UUID.nameUUIDFromBytes((nodeType.getID().toString() + value).getBytes());
	}

	public Iterable<UUID> getConnections()
	{
		return Collections.unmodifiableList(connections);
	}


	public void addConnection(Connection connection)
	{
		if (connection == null)
		{
			throw new NullPointerException("Connection cannot be null");
		}
		connections.add(connection.getID());
	}


//	public Timestamp getCreationTime()
//	{
//		return creationTime;
//	}


	public String getValue()
	{
		return value;
	}


	public NodeType getNodeType()
	{
		return nodeType;
	}
}
