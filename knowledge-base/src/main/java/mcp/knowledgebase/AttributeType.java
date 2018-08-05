//package mcp.knowledgebase;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.util.*;
//
//public class AttributeType extends DataType
//{
//	private final static Logger logger = LoggerFactory.getLogger(AttributeType.class);
//
//
//
//	public AttributeType()
//	{
//	}
//
//
//	public AttributeType(UUID uuid)
//	{
//		super(uuid);
//	}
//
//
//	public AttributeType(UUID uuid, String name, String description)
//	{
//		super(uuid, name, description);
//	}
//	
//	
//
//	public static synchronized AttributeType getByName(String name, String description)
//	{
//		UUID uuid = UUID.nameUUIDFromBytes(name.getBytes());
//		DataType t = DataType.getByName(name, description);
//		if (t == null)
//		{
//			t = new NodeType(uuid, name, description);
//			DataType.addDataType(t);
//		}
//		try
//		{
//			return (AttributeType) t;
//		}
//		catch (ClassCastException e)
//		{
//			logger.error("Node and attribute types have the same name (+ " + name + ")", e);
//			throw e;
//		}
//	}
//}
