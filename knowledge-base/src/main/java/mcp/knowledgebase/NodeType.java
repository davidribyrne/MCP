package mcp.knowledgebase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NodeType extends UniqueDatum
{
	private final static Logger logger = LoggerFactory.getLogger(NodeType.class);

	private static final Map<UUID, NodeType> TYPES = new HashMap<UUID, NodeType>(5);
	protected String name;
	protected String description;
	private Object lock = new Object();

	
	private NodeType(UUID uuid, String name, String description)
	{
		this(uuid, name, description, false);
	}

	public NodeType(UUID uuid, String name, String description, boolean restore)
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
			if (!restore)
			{
				KnowledgeBase.instance.storeNewNodeType(this);
			}
		}
	}

	public static synchronized NodeType getByID(UUID id)
	{
		return TYPES.get(id);
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
