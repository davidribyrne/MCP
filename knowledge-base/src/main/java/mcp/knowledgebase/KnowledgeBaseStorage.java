package mcp.knowledgebase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import space.dcce.commons.general.FileUtils;
import space.dcce.commons.general.UnexpectedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class KnowledgeBaseStorage
{
	private final static Logger logger = LoggerFactory.getLogger(KnowledgeBaseStorage.class);


	private final static String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private java.sql.Connection dbConnection;
	private final static String FILENAME = "mcp.db";

	public KnowledgeBaseStorage()
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


	public void loadOrCreate(String path) throws SQLException, IOException
	{
		String file = path + File.separator + FILENAME;
		if (!FileUtils.exists(file))
		{
			createDb(file);
		}
		else
		{
			dbConnection = DriverManager.getConnection("jdbc:derby:" + file + ";create=false");
			configureConnection();
		}
		loadTypes();
	}


	private void loadTypes() throws SQLException
	{
		String query = "SELECT id, name, description FROM types";

		ResultSet rs = dbConnection.prepareStatement(query).executeQuery();
		while (rs.next())
		{
			UUID uuid = UUID.fromString(rs.getString(1));
			String name = rs.getString(2);
			String description = rs.getString(3);
			new NodeType(uuid, name, description, true);
		}

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
					"INSERT INTO nodes (id, type, value) VALUES (?, ?, ?)");

			psInsert.setString(1, node.getID().toString());
			psInsert.setString(2, node.getNodeType().getID().toString());
			// psInsert.setTimestamp(3, node.getCreationTime());
			psInsert.setString(3, new String(node.getValue()));
			psInsert.execute();
			psInsert.close();
		}
		catch (SQLException e)
		{
			logger.error("Unexpected problem running SQL for inserting a new node type", e);
			throw new UnexpectedException(e);
		}
	}


	public void addConnection(UUID id, Node... nodes) throws UnexpectedException
	{
		try
		{
			PreparedStatement psInsert = dbConnection.prepareStatement(
					"INSERT INTO connections (id, nodeID) VALUES (?, ?)");

			for (Node node : nodes)
			{

				psInsert.setString(1, id.toString());
				psInsert.setString(2, node.getID().toString());
				psInsert.execute();
			}
			psInsert.close();

		}
		catch (SQLException e)
		{
			logger.error("Unexpected problem running SQL for inserting a new node type", e);
			throw new UnexpectedException(e);
		}

	}


	public boolean nodeExists(NodeType nodeType, String value)
	{
		String query = "SELECT id "
				+ "FROM nodes "
				+ "WHERE "
				+ "type = ? "
				+ "AND value = ? ";


		PreparedStatement statement;
		try
		{
			statement = dbConnection.prepareStatement(query);
			statement.setString(1, nodeType.getID().toString());
			statement.setString(2, value);
			ResultSet rs = statement.executeQuery();
			if (rs.next())
			{
				return true;
			}

		}
		catch (SQLException e)
		{
			throw new UnexpectedException("Unexpected problem running SQL for nodeExists", e);
		}
		return false;
	}


	public Node getNode(NodeType nodeType, String value) throws SQLException, IllegalStateException
	{
		String query = "SELECT id "
				+ "FROM nodes "
				+ "WHERE "
				+ "type = ? "
				+ "AND value = ? ";


		PreparedStatement statement = dbConnection.prepareStatement(query);
		statement.setString(1, nodeType.getID().toString());
		statement.setString(2, value);
		ResultSet rs = statement.executeQuery();
		UUID id = null;
		if (rs.next())
		{
			id = UUID.fromString(rs.getString(1));

		}
		else
		{
			throw new IllegalStateException("Node not found");
		}

		if (rs.next())
		{
			throw new IllegalStateException("More than one node returned to getNode");
		}

		Node node = new Node(nodeType, value, id);
		loadNodeConnections(node);
		return node;
	}

	private void loadNodeConnections(Node node) throws SQLException
	{
		String query = "SELECT id "
				+ "FROM connections "
				+ "WHERE nodeID = ?";
		
		PreparedStatement statement = dbConnection.prepareStatement(query);
		statement.setString(1, node.getID().toString());
		ResultSet rs = statement.executeQuery();
		while (rs.next())
		{
			UUID uuid = UUID.fromString(rs.getString(1));
			Connection c = ConnectionCache.instance.getConnection(uuid);
			if (c == null)
			{
				c = loadConnection(uuid);
			}
			node.addConnection(c);
		}

	}
	
	private Connection loadConnection(UUID uuid) throws SQLException
	{
		String query = "SELECT nodeID "
				+ "FROM connections "
				+ "WHERE id = ?";
		
		PreparedStatement statement = dbConnection.prepareStatement(query);
		statement.setString(1, uuid.toString());
		ResultSet rs = statement.executeQuery();
		Connection connection = new Connection(uuid);
		while (rs.next())
		{
			UUID nodeID = UUID.fromString(rs.getString(1));
			connection.restoreNode(nodeID);
		}
		statement.close();
		rs.close();
		return connection;
	}

	public void addNodeType(NodeType nodeType)
	{
		try
		{
			PreparedStatement psInsert = dbConnection.prepareStatement(
					"INSERT INTO types (id, name, description) VALUES (?, ?, ?)");

			psInsert.setString(1, nodeType.getID().toString());
			psInsert.setString(2, nodeType.getName());
			psInsert.setString(3, nodeType.getDescription());
			psInsert.execute();
			psInsert.close();
		}
		catch (SQLException e)
		{
			logger.error("Unexpected problem running SQL for inserting a new node type", e);
			throw new UnexpectedException(e);
		}
	}


}
