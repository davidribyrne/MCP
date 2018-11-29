package mcp.knowledgebase;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionCache extends Cache<Connection>
{
	private final static Logger logger = LoggerFactory.getLogger(ConnectionCache.class);


	public final static ConnectionCache instance = new ConnectionCache();

	public ConnectionCache()
	{
	}


	@Override
	protected Object getKey(Connection value)
	{
		return value.getID();
	}
	
	public Connection getConnection(UUID id)
	{
		prune();
		synchronized (cacheLock)
		{
			return cache.get(id).get();
		}
	}
}
