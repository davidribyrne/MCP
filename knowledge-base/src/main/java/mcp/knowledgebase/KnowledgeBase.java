package mcp.knowledgebase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.primitives.Primitive;
import space.dcce.commons.general.FileUtils;
import space.dcce.commons.general.NotImplementedException;
import space.dcce.commons.general.UnexpectedException;


public class KnowledgeBase
{
	private final static Logger logger = LoggerFactory.getLogger(KnowledgeBase.class);

	private static final KnowledgeBase instance = new KnowledgeBase();

	public static KnowledgeBaseStorage storage = new KnowledgeBaseStorage();

	private KnowledgeBase()
	{
		
	}

	public boolean createNodeIfPossible(NodeType nodeType, String value)
	{
		synchronized (this)
		{
			if (nodeExists(nodeType, value))
				return false;
			new Node(nodeType, value);
			return true;
		}
	}


	public Node getOrCreateNode(NodeType nodeType, String value)
	{
		synchronized (this)
		{
			Node node = getNode(nodeType, value);
			if (node != null)
				return node;

			return new Node(nodeType, value);
		}
	}


	private Node getNode(NodeType nodeType, String value)
	{
		Node n = NodeCache.getInstance().getNode(nodeType, value);
		if (n != null)
			return n;
		try
		{
			return storage.getNode(nodeType, value);
		}
		catch (SQLException e)
		{
			throw new UnexpectedException("Database error: " + e.getMessage(), e);
		}
	}

	public void storeNewNodeType(NodeType nodeType)
	{
		storage.addNodeType(nodeType);
	}



	private boolean nodeExists(NodeType nodeType, String value)
	{
		return (NodeCache.getInstance().isNodeInCache(nodeType, value) || storage.nodeExists(nodeType, value));
	}


	public Iterable<Node> getAllNodesByType(NodeType type)
	{
		throw new NotImplementedException();
	}


	public static KnowledgeBase getInstance()
	{
		return instance;
	}



}
