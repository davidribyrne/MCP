
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import mcp.knowledgebase.Persistence;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

public class Test
{
	private final static Logger logger = LoggerFactory.getLogger(Test.class);



	public Test() throws Exception
	{

	}


	public static void main(String[] args)throws Exception
	{
		new Test();
	}
}
