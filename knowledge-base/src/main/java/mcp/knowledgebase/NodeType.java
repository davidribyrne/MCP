package mcp.knowledgebase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class NodeType extends UniqueDatum
{
	private final static Logger logger = LoggerFactory.getLogger(NodeType.class);

	private static final Map<UUID, NodeType> uuids = new HashMap<UUID, NodeType>(5);
	protected String name;
	protected String description;


	public NodeType()
	{
	}
	
	
	public NodeType(UUID uuid, String name, String description)
	{
		super(uuid);
		this.name = name;
		this.description = description;
	}


	public NodeType(UUID uuid)
	{
		super(uuid);
	}



	public static synchronized NodeType getByName(String name, String description)
	{
		
		UUID uuid = UUID.nameUUIDFromBytes(name.getBytes());
		if (uuids.containsKey(uuid))
		{
			return uuids.get(uuid);
		}

		NodeType type = new NodeType(uuid, name, description);
		uuids.put(type.getID(), type);
	
		return type;
		
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
