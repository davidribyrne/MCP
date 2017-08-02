
import org.apache.ibatis.jdbc.ScriptRunner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import mcp.knowledgebase.KbElementImpl;
import mcp.knowledgebase.Persistence;
import mcp.knowledgebase.nodes.DomainImpl;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;


public class HTest
{
	private final static Logger logger = LoggerFactory.getLogger(HTest.class);

	// private SessionFactory sessionFactory;


	public HTest() throws Exception
	{

	}


	public static void main(String[] args) throws Exception
	{
		SessionFactory sessionFactory = null;
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();
		try
		{
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		}
		catch (Exception e)
		{
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy(registry);
		}


		Session session = sessionFactory.openSession();
		try
		{
			session.beginTransaction();
			DomainImpl d = new DomainImpl("ibm2.com");


			session.save(d);
			session.getTransaction().commit();
			session.close();

			// now lets pull events from the database and list them
			session = sessionFactory.openSession();
			session.beginTransaction();
			List result = session.createQuery("from Elements").list();
			for (Object o : result)
			{
				System.out.println(o.toString());
			}
			session.getTransaction().commit();
		}
		catch (Exception e)
		{
			System.err.println(e.toString());
			System.err.println(e.getStackTrace());
		}
		finally
		{
			session.close();
			System.exit(0);
		}
	}
}
