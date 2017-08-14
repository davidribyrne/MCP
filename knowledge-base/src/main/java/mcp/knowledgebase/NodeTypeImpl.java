package mcp.knowledgebase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NodeTypeImpl extends UniqueElementImpl implements NodeType
{
	private static final Map<UUID, NodeTypeImpl> uuids = new HashMap<UUID, NodeTypeImpl>(5); 

	public static final String IPV4_ADDRESS = "IPV4_ADDRESS";
	public static final String IPV6_ADDRESS = "IPV6_ADDRESS";
	public static final String MAC_ADDRESS = "MAC_ADDRESS";
	public static final String HOSTNAME = "HOSTNAME";
	public static final String DOMAIN = "DOMAIN";
	
	
	private String name;
	private String description;
	
	static
	{
		getByName(IPV4_ADDRESS, "IPv4 address");
		getByName(IPV6_ADDRESS, "IPv6 address");
		getByName(MAC_ADDRESS, "MAC address");
		getByName(HOSTNAME, "Hostname");
		getByName(DOMAIN, "Domain");
	}
	
	
	
	public static NodeTypeImpl getByName(String name)
	{
		return getByName(name, "");
	}
	
	
	/**
	 * 
	 * @param name
	 * @param description Ignored if the name was previously used
	 * @return
	 */
	public synchronized static NodeTypeImpl getByName(String name, String description)
	{
		UUID uuid = UUID.nameUUIDFromBytes(name.getBytes());
		NodeTypeImpl nodeType;
		if (uuids.containsKey(uuid))
		{
			nodeType = uuids.get(uuid);
		}
		else
		{
			nodeType = new NodeTypeImpl(uuid, name, description);
		}
		return nodeType;
	}
	
	private NodeTypeImpl(UUID uuid, String name, String description)
	{
		super(uuid);
		this.name = name;
		this.description = description;
		KnowledgeBaseImpl.getInstance().addNodeType(this);
	}

	
	@Override
	public String getName()
	{
		return name;
	}


	@Override
	public String getDescription()
	{
		return description;
	}

}
