
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.KnowledgeBaseImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class Test
{
	private final static Logger logger = LoggerFactory.getLogger(Test.class);


	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException
	{
		KnowledgeBaseImpl kb = new KnowledgeBaseImpl();
		kb.createDb("AAAAAAA.db");
	}
}
