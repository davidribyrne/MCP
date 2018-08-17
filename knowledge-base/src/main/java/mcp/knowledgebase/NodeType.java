package mcp.knowledgebase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;


public class NodeType extends UniqueDatum
{
	private final static Logger logger = LoggerFactory.getLogger(NodeType.class);

	private static final Map<UUID, NodeType> TYPES = new HashMap<UUID, NodeType>(5);
	protected String name;
	protected String description;
	private Object lock = new Object();


	public NodeType(UUID uuid, String name, String description)
	{
		super(uuid);
		synchronized (lock)
		{
			this.name = name;
			this.description = description;
			if (TYPES.containsKey(getID()))
			{
				throw new IllegalStateException("Type already existed in TYPES");
			}
			TYPES.put(getID(), this);
			KnowledgeBase.getInstance().storeNewNodeType(this);
		}
	}


	public static synchronized NodeType getByName(String name, String description)
	{

		UUID uuid = UUID.nameUUIDFromBytes(name.getBytes());
		if (TYPES.containsKey(uuid))
		{
			return TYPES.get(uuid);
		}

		return new NodeType(uuid, name, description);
	}


	public String getName()
	{
		return name;
	}


	public String getDescription()
	{
		return description;
	}

}
