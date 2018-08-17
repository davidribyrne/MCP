package mcp.knowledgebase;

import java.util.*;
import java.lang.ref.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public abstract class Cache<T>
{
	private final static Logger logger = LoggerFactory.getLogger(Cache.class);


	protected final Object cacheLock = new Object();


	private int operationCount = 0;

	protected abstract Object getKey(T value);


	protected HashMap<Object, WeakReference<T>> cache;


	protected Cache()
	{
		cache = new HashMap<Object, WeakReference<T>>(1000);
	}


	public void addItem(T item)
	{

		synchronized (cacheLock)
		{
			Object key = getKey(item);
			if (cache.containsKey(key))
			{
				throw new IllegalStateException("Item already exists in cache");
			}
			cache.put(key, new WeakReference<T>(item));
		}
		prune();
	}


	protected synchronized int prune()
	{
		if (++operationCount >= 1000)
		{
			operationCount = 0;
			synchronized (cacheLock)
			{

				int count = 0;
				for (Object key : Collections.unmodifiableSet(cache.keySet()))
				{
					if (cache.get(key).get() == null)
					{
						cache.remove(key);
					}
				}

				return count;
			}
		}
		return 0;
	}


}
