
import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.KnowledgeBase;

public class Test
{
	private final static Logger logger = LoggerFactory.getLogger(Test.class);


	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException
	{
		KnowledgeBase kb = new KnowledgeBase();
		kb.createDb("AAAAAAA.db");
	}
}
