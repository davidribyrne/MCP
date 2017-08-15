package mcp.knowledgebase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NodeType extends UniqueElement
{
	private static final Map<UUID, NodeType> uuids = new HashMap<UUID, NodeType>(5); 

	private String name;
	private String description;
	
	
	public static NodeType getByName(String name)
	{
		return getByName(name, "");
	}
	
	
	/**
	 * 
	 * @param name
	 * @param description Ignored if the name was previously used
	 * @return
	 */
	public synchronized static NodeType getByName(String name, String description)
	{
		UUID uuid = UUID.nameUUIDFromBytes(name.getBytes());
		NodeType nodeType;
		if (uuids.containsKey(uuid))
		{
			nodeType = uuids.get(uuid);
		}
		else
		{
			nodeType = new NodeType(uuid, name, description);
		}
		return nodeType;
	}
	
	private NodeType(UUID uuid, String name, String description)
	{
		super(uuid);
		this.name = name;
		this.description = description;
		KnowledgeBase.getInstance().addNodeType(this);
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
