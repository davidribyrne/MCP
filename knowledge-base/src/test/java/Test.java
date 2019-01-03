
import java.io.IOException;
import java.sql.SQLException;

import mcp.knowledgebase.KnowledgeBase;

public class Test
{


	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException
	{
		KnowledgeBase.storage.createDb("AAAAAAA.db");
	}
}
