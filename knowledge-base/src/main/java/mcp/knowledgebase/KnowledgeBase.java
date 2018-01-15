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

import mcp.knowledgebase.nodes.Node;
import net.dacce.commons.general.FileUtils;
import net.dacce.commons.general.NotImplementedException;
import net.dacce.commons.general.UnexpectedException;


public class KnowledgeBase
{
	private final static Logger logger = LoggerFactory.getLogger(KnowledgeBase.class);
	private final static String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private java.sql.Connection dbConnection;

	private static final KnowledgeBase instance = new KnowledgeBase();


	public KnowledgeBase()
	{

		try
		{
			Class.forName(DRIVER).newInstance();
		}
		catch (InstantiationException e)
		{
			throw new UnexpectedException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new UnexpectedException(e);
		}
		catch (ClassNotFoundException e)
		{
			System.err.println(e.getLocalizedMessage());
			System.err.println(e.toString());
			System.err.println("You need to have a driver for the database.");
			System.exit(1);
		}
	}


	public void load(String path) throws FileNotFoundException, SQLException
	{
		if (!FileUtils.exists(path))
		{
			throw new FileNotFoundException("Couldn't find '" + path + "'");
		}

		dbConnection = DriverManager.getConnection("jdbc:derby:" + path + ";create=false");
		configureConnection();
	}


	public void createDb(String path) throws SQLException, IOException
	{
		if (FileUtils.exists(path))
		{
			throw new FileAlreadyExistsException("There is already a file at '" + path + "'");
		}

		dbConnection = DriverManager.getConnection("jdbc:derby:" + path + ";create=true");
		configureConnection();

		File schemaFile = new File(this.getClass().getClassLoader().getResource("schema.sql").getFile());
		Statement statement = dbConnection.createStatement();
		for (String command : FileUtils.readFileToString(schemaFile).split("//////////"))
		{
			if (command.matches("^\\s*$"))
				continue;

			statement.execute(command);
		}

		statement.close();

	}


	private void configureConnection() throws SQLException
	{
		dbConnection.setAutoCommit(true);
	}


	public void shutdown() throws SQLException
	{
		DriverManager.getConnection("jdbc:derby:;shutdown=true");
	}

	public void addNode(Node node)
	{
		try
		{
			PreparedStatement psInsert = dbConnection.prepareStatement(
					"insert into NODES (id, type, time, value) values (?, ?, ?, ?)");

			psInsert.setString(1, node.getID().toString());
			psInsert.setString(2, node.getType().getID().toString());
			psInsert.setTimestamp(3, node.getCreationTime());
			psInsert.setString(4, new String(node.getValue()));
			psInsert.execute();
			psInsert.close();
		}
		catch (SQLException e)
		{
			logger.error("Unexpected problem running SQL for inserting a new node type", e);
			throw new UnexpectedException(e);
		}
		
	}

	public void addNodeType(DataType nodeType)
	{
		try
		{
			PreparedStatement psInsert = dbConnection.prepareStatement(
					"insert into TYPES (id, name, description) values (?, ?, ?)");

			psInsert.setString(1, nodeType.getID().toString());
			psInsert.setString(2, nodeType.getName());
			psInsert.setString(1, nodeType.getDescription());
			psInsert.execute();
			psInsert.close();
		}
		catch (SQLException e)
		{
			logger.error("Unexpected problem running SQL for inserting a new node type", e);
			throw new UnexpectedException(e);
		}
	}
	
	public synchronized boolean createNodeIfPossible(DataType nodeType, byte[] value)
	{
		return NodeCache.getInstance().createNodeIfPossible(nodeType, value);
	}
	
	public Node getOrCreateNode(DataType nodeType, byte[] value)
	{
		return NodeCache.getInstance().getOrCreateNode(nodeType, value);
	}

	public boolean nodeExists(DataType nodeType, byte[] value)
	{
		return NodeCache.getInstance().nodeExists(nodeType, value);
	}

	public Iterable<Node> getAllNodesByType(DataType type)
	{
		throw new NotImplementedException();
	}

	public static KnowledgeBase getInstance()
	{
		return instance;
	}

}