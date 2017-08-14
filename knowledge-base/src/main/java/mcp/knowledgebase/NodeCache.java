package mcp.knowledgebase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import com.google.common.cache.*;

public class NodeCache
{
	private final static Logger logger = LoggerFactory.getLogger(NodeCache.class);

	private final static NodeCache instance = new NodeCache();

	private NodeCache()
	{
		
		
//		LoadingCache<Key, Graph> graphs = CacheBuilder.newBuilder()
//			       .maximumSize(1000)
//			       .expireAfterWrite(10, TimeUnit.MINUTES)
//			       .removalListener(MY_LISTENER)
//			       .build(
//			           new CacheLoader<Key, Graph>() {
//			             public Graph load(Key key) throws AnyException {
//			               return createExpensiveGraph(key);
//			             }
//			           });

	}
	
	public Node getOrCreateNode(NodeType nodeType, byte[] value)
	{
		return null;
	}

	public static NodeCache getInstance()
	{
		return instance;
	}
}
