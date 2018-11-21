package mcp.knowledgebase;

import java.util.*;
import java.lang.ref.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class NodeCache extends Cache<Node>
{
	private final static Logger logger = LoggerFactory.getLogger(NodeCache.class);

	private final static NodeCache instance = new NodeCache();


	public static NodeCache getInstance()
	{
		return instance;
	}
	

	private static class NodeKey
	{
		NodeType nodeType;
		Object value;


		public NodeKey(NodeType nodeType, Object value)
		{
			this.nodeType = nodeType;
			this.value = value;
		}


	}
	

	boolean isNodeInCache(NodeType nodeType, Object value)
	{
		prune();
		synchronized (cacheLock)
		{
			return cache.containsKey(new NodeKey(nodeType, value));
		}
	}
	

	public Node getNode(NodeType nodeType, Object value)
	{
		prune();
		synchronized (cacheLock)
		{
			return cache.get(new NodeKey(nodeType, value)).get();
		}
	}
	
	

	@Override
	protected Object getKey(Node node)
	{
		return new NodeKey(node.getNodeType(), node.getValue());
	}
}
