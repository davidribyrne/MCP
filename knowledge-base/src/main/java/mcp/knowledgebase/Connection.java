package mcp.knowledgebase;

import java.util.Collections;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import space.dcce.commons.general.NoNullHashSet;
import space.dcce.commons.general.UniqueList;


public class Connection extends UniqueDatum
{
	private final static Logger logger = LoggerFactory.getLogger(Connection.class);
	private final UniqueList<UUID> nodes;


	/**
	 * Saves to the database
	 * @param nodes
	 */
	Connection(Node... nodes)
	{
		this(null, nodes);
		KnowledgeBase.storage.addConnection(getID(), nodes);
	}


	/**
	 * Use only from KnowledgeBaseStorage and internally. 
	 * It does NOT save to the database.  
	 * 
	 * @param uuid
	 */
	Connection(UUID uuid, Node... nodes)
	{
		super(uuid);
		this.nodes = new UniqueList<UUID>(false);
		for (Node node: nodes)
		{
			addNode(node);
		}
		ConnectionCache.instance.addItem(this);
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
	public Iterable<UUID> getNodes()
	{
		return Collections.unmodifiableList(nodes);
	}


	synchronized public void addNode(Node node)
	{
		nodes.add(node.getID());
		node.addConnection(this);
		KnowledgeBase.storage.updateConnection(getID(), node);
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
