//package mcp.knowledgebase;
//
//
//import java.util.*;
//
//
//public abstract class DataType extends UniqueDatum
//{
//
//	private static final Map<UUID, DataType> uuids = new HashMap<UUID, DataType>(5);
//	protected String name;
//	protected String description;
//
//
//	/**
//	 * 
//	 * @param name
//	 * @param description Ignored if the name was previously used
//	 * @return
//	 */
//	public static synchronized DataType getByName(String name, String description)
//	{
//		UUID uuid = UUID.nameUUIDFromBytes(name.getBytes());
//		if (uuids.containsKey(uuid))
//		{
//			return uuids.get(uuid);
//		}
//		else
//		{
//			return null;
//		}
//	}
//	
//	protected static synchronized void addDataType(DataType dataType)
//	{
//		uuids.put(dataType.getID(), dataType);
//	}
//
//
//	public DataType()
//	{
//		super();
//	}
//
//
//	public DataType(UUID uuid)
//	{
//		super(uuid);
//	}
//	
//	public DataType(UUID uuid, String name, String description)
//	{
//		super(uuid);
//		this.name = name;
//		this.description = description;
//		KnowledgeBase.getInstance().addNodeType(this);
//	}
//
//
//
//	public String getName()
//	{
//		return name;
//	}
//
//
//	public String getDescription()
//	{
//		return description;
//	}
//
//}
