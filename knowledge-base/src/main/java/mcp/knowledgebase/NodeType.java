package mcp.knowledgebase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class NodeType extends DataType
{
	private final static Logger logger = LoggerFactory.getLogger(NodeType.class);


	public NodeType()
	{
	}
	
	
	public NodeType(UUID uuid, String name, String description)
	{
		super(uuid, name, description);
	}


	public NodeType(UUID uuid)
	{
		super(uuid);
	}


	public static synchronized NodeType getByName(String name, String description)
	{
		UUID uuid = UUID.nameUUIDFromBytes(name.getBytes());
		DataType t = DataType.getByName(name);
		if (t == null)
		{
			t = new NodeType(uuid, name, description);
			DataType.addDataType(t);
		}
		try
		{
			return (NodeType) t;
		}
		catch (ClassCastException e)
		{
			logger.error("Node and attribute types have the same name (+ " + name + ")", e);
			throw e;
		}
	}

}