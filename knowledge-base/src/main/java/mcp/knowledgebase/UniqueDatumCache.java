package mcp.knowledgebase;

import java.lang.ref.SoftReference;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UniqueDatumCache<D extends UniqueDatum> extends Cache<D>
{
	private final static Logger logger = LoggerFactory.getLogger(UniqueDatumCache.class);


	public UniqueDatumCache()
	{
	}


	@Override
	protected Object getKey(D value)
	{
		return value.getID();
	}
	
	public D getValue(UUID id)
	{
		prune();
		synchronized (cacheLock)
		{
			SoftReference<D> wr = cache.get(id);
			if (wr == null)
				return null;
			return cache.get(id).get();
		}
	}
}
