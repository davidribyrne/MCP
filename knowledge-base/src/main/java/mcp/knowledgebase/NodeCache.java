package mcp.knowledgebase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.nodes.Node;
import net.dacce.commons.general.NotImplementedException;


public class NodeCache
{
	private final static Logger logger = LoggerFactory.getLogger(NodeCache.class);

	private final static NodeCache instance = new NodeCache();


	private NodeCache()
	{


		// LoadingCache<Key, Graph> graphs = CacheBuilder.newBuilder()
		// .maximumSize(1000)
		// .expireAfterWrite(10, TimeUnit.MINUTES)
		// .removalListener(MY_LISTENER)
		// .build(
		// new CacheLoader<Key, Graph>() {
		// public Graph load(Key key) throws AnyException {
		// return createExpensiveGraph(key);
		// }
		// });

	}

	/**
	 * Needed for complicated synchronization problems; probably not the best solution :(
	 * @param nodeType
	 * @param value
	 * @return
	 */
	synchronized boolean createNodeIfPossible(DataType nodeType, byte[] value)
	{
		throw new NotImplementedException();
	}
	
	Node getOrCreateNode(DataType nodeType, byte[] value)
	{
		if (nodeExists(nodeType, value))
			return getNodeFromCache(nodeType, value);
		else
			return createNode(nodeType, value);
	}

	boolean nodeExists(DataType nodeType, byte[] value)
	{
		throw new NotImplementedException();
	}

	private Node getNodeFromCache(DataType nodeType, byte[] value)
	{
		throw new NotImplementedException();
	}
	
	private Node createNode(DataType nodeType, byte[] value)
	{
		throw new NotImplementedException();
	}



	public static NodeCache getInstance()
	{
		return instance;
	}
}
