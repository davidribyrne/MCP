package mcp.knowledgebase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import space.dcce.commons.general.NotImplementedException;
import space.dcce.commons.general.UnexpectedException;


public class KnowledgeBase
{
	private final static Logger logger = LoggerFactory.getLogger(KnowledgeBase.class);

	public static final KnowledgeBase instance = new KnowledgeBase();

	public static KnowledgeBaseStorage storage = new KnowledgeBaseStorage();


	private KnowledgeBase()
	{

	}


	public void initializeStorage(String path) throws SQLException, IOException
	{
		storage.loadOrCreate(path);
	}


	public boolean createNodeIfPossible(NodeType nodeType, String value)
	{
		synchronized (this)
		{
			if (nodeExists(nodeType, value))
				return false;
			new Node(nodeType, value);
			return true;
		}
	}

	/**
	 * 
	 * @param uuid
	 * @return Null if connection not found
	 */
	public Connection getConnection(UUID uuid)
	{
		/*
		 * First, try cache, then try database. If those don't work, return null;
		 */
		Connection c = ConnectionCache.instance.getConnection(uuid);
		if (c != null)
			return c;
		try
		{
			return storage.getConnection(uuid);
		}
		catch (SQLException e)
		{
			throw new UnexpectedException("Database error: " + e.getMessage(), e);
		}
	}

	/**
	 * 
	 * Requires complete match of all nodes
	 * 
	 * @param nodes
	 * @return
	 */
	public Connection getOrCreateConnection(Node... nodes)
	{
		return getOrCreateConnection(false, nodes);
	}


	/**
	 * 
	 * @param acceptPartial
	 *            Return a connection that contains all of the specified nodes, but may contain other nodes too
	 * @param nodes
	 * @return
	 */
	public Connection getOrCreateConnection(boolean acceptPartial, Node... nodes)
	{
		synchronized (this)
		{
			// First, check to see if the connection already exists

			for (Node node : nodes)
			{
				for (UUID uuid : node.getConnections())
				{
					Connection c = getConnection(uuid);
					if (acceptPartial)
					{
						if (c.containsNodes(nodes))
							return c;
					}
					else
					{
						if (c.nodesMatch(nodes))
							return c;
					}
				}
			}

			// No match, so create new connection

			Connection c = new Connection(nodes);
			for (Node node : nodes)
			{
				node.addConnection(c);
			}
			return c;
		}
	}


	public Node getOrCreateNode(NodeType nodeType, String value)
	{
		synchronized (this)
		{
			Node node = getNode(nodeType, value);
			if (node != null)
				return node;

			return new Node(nodeType, value);
		}
	}


	private Node getNode(NodeType nodeType, String value)
	{
		/*
		 * First, try cache, then try database. If those don't work, it must not exist, so create it.
		 */
		Node n = NodeCache.getInstance().getNode(nodeType, value);
		if (n != null)
			return n;
		try
		{
			return storage.getNode(nodeType, value);
		}
		catch (SQLException e)
		{
			throw new UnexpectedException("Database error: " + e.getMessage(), e);
		}
	}


	public void storeNewNodeType(NodeType nodeType)
	{
		storage.addNodeType(nodeType);
	}


	private boolean nodeExists(NodeType nodeType, String value)
	{
		return (NodeCache.getInstance().isNodeInCache(nodeType, value) || storage.nodeExists(nodeType, value));
	}


	public Iterable<Node> getAllNodesByType(NodeType type)
	{
		throw new NotImplementedException();
	}


}
