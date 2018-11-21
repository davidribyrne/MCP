package mcp.knowledgebase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import space.dcce.commons.general.NoNullHashSet;
import space.dcce.commons.general.UniqueList;


public class Connection extends UniqueDatum
{
	private final static Logger logger = LoggerFactory.getLogger(Connection.class);
	private final NoNullHashSet<UUID> nodes;


	private Connection(Node... nodes)
	{
		this(null, nodes);
	}


	/**
	 * Use only from KnowledgeBase
	 * 
	 * @param uuid
	 */
	Connection(UUID uuid, Node... nodes)
	{
		super(uuid);
		this.nodes = new NoNullHashSet<UUID>();
		for (Node node: nodes)
		{
			addNode(node);
		}
		ConnectionCache.instance.addItem(this);
	}

	/**
	 * 
	 * Requires complete match of all nodes
	 * @param nodes
	 * @return
	 */
	static public Connection getOrCreateConnection(Node... nodes)
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
	static public Connection getOrCreateConnection(boolean acceptPartial, Node... nodes)
	{
		// First, check to see if the connection already exists

		for (Node node : nodes)
		{
			for (Connection c : node.getConnections())
			{
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



	/**
	 * 
	 * @param nodes
	 * @return true if all the connection nodes exactly match the nodes passed
	 */
	public boolean nodesMatch(Node... nodes)
	{
		return this.nodes.equals(new NoNullHashSet<Node>(nodes));
	}


	/**
	 * 
	 * @param nodes
	 * @return true if all of the passed nodes are part of the connection. Other nodes may be in the connection too.
	 */
	public boolean containsNodes(Node... nodes)
	{
		NoNullHashSet<UUID> temp = new NoNullHashSet<UUID>(nodes.length);
		for (Node node: nodes)
		{
			temp.add(node.getID());
		}
		return this.nodes.containsAll(temp);
	}


	/**
	 * 
	 * @return read-only list of nodes
	 */
	public Set<UUID> getNodes()
	{
		return Collections.unmodifiableSet(nodes);
	}


	public void addNode(Node node)
	{
		nodes.add(node.getID());
		node.addConnection(this);
		KnowledgeBase.storage.addConnection(getID(), node);
	}

	void restoreNode(UUID id)
	{
		nodes.add(id);
	}


	public void addNodes(Node ...nodes)
	{
		for(Node node: nodes)
		{
			addNode(node);
		}
	}


	@Override
	public boolean equals(Object obj)
	{
		Connection c = (Connection) obj;
		return nodes.equals(c.nodes);
	}


	@Override
	public int hashCode()
	{
		return nodes.hashCode();
	}

}
