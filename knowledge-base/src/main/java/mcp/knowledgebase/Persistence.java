package mcp.knowledgebase;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Persistence
{
	private final static Logger logger = LoggerFactory.getLogger(Persistence.class);
	private SessionFactory sessionFactory;
	private Session session;
	
	public Persistence() 
	{
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();
		sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		session = sessionFactory.openSession();
	}
	
	public void shutdown()
	{
		sessionFactory.close();
	}

	public Session getSession()
	{
		return session;
	}
}
