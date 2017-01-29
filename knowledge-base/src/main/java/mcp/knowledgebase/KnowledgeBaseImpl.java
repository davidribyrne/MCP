package mcp.knowledgebase;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.esotericsoftware.yamlbeans.YamlException;
import mcp.commons.WorkingDirectories;
import mcp.events.events.ElementCreationEvent;
import mcp.jobmanager.executors.ExecutionScheduler;
import mcp.knowledgebase.nodes.Address;
import mcp.knowledgebase.nodes.AddressImpl;
import mcp.knowledgebase.nodes.Domain;
import mcp.knowledgebase.nodes.DomainImpl;
import mcp.knowledgebase.nodes.Host;
import mcp.knowledgebase.nodes.HostImpl;
import mcp.knowledgebase.nodes.Hostname;
import mcp.knowledgebase.nodes.HostnameImpl;
import net.dacce.commons.general.IndexedCache;
import net.dacce.commons.general.MultiClassIndexedCache;
import net.dacce.commons.general.YamlUtils;
import net.dacce.commons.netaddr.SimpleInetAddress;


public class KnowledgeBaseImpl extends MultiClassIndexedCache implements KnowledgeBase
{
	final static Logger logger = LoggerFactory.getLogger(KnowledgeBaseImpl.class);

	private static KnowledgeBase instance;


	private KnowledgeBaseImpl()
	{
		super(true);
	}

	public synchronized void saveToDisk()
	{
		try
		{
			YamlUtils.writeObject(getOutputFilename(), this);
		}
		catch (IOException e)
		{
			logger.error("Failed to save knowledge base to disk (" + getOutputFilename() + "): " + e.getLocalizedMessage(), e);
		}
	}

	public static synchronized KnowledgeBase getInstance()
	{
		if (instance == null)
		{
			try
			{
				instance = (KnowledgeBase) YamlUtils.readObject(getOutputFilename(), KnowledgeBaseImpl.class);
			}
			catch (FileNotFoundException e)
			{
				logger.trace("Knowledge base yaml file not found (" + getOutputFilename() + "). Creating new object.", e);
			}
			catch (YamlException e)
			{
				logger.warn("Problem reading YAML for knowledge base: " + e.getLocalizedMessage(), e);
			}
			if (instance == null)
			{
				instance = new KnowledgeBaseImpl();
			}
		}

		return instance;
	}
	
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see mcp.knowledgebase.Kb#getOrCreateHostname(java.lang.String)
	 */
	@Override
	public synchronized Hostname getOrCreateHostname(String name)
	{
		Hostname hostname = (Hostname) getMember(Hostname.class, HostnameImpl.NAME_FIELD(), name);
		if (hostname == null)
		{
			logger.trace(name + " was added to the KB as a new hostname.");
			hostname = new HostnameImpl(name);
			add(Hostname.class, hostname);
		}
		return hostname;
	}

	
	@Override
	public synchronized boolean addDomain(String name)
	{
		Domain domain = (Domain) getMember(Domain.class, DomainImpl.NAME_FIELD(), name);
		if (domain != null)
			return false;

		logger.trace(name + " was added to the KB as a new domain.");
		domain = new DomainImpl(name);
		add(Domain.class, domain);
		return true;
	}


	@Override
	public synchronized Address getOrCreateAddressNode(SimpleInetAddress address)
	{
		Address addressNode = (Address) getMember(Address.class, AddressImpl.ADDRESS_FIELD(), address);
		if (addressNode == null)
		{
			logger.trace(address.toString() + " was added to the KB as a new address.");
			addressNode = new AddressImpl(address);
			add(Address.class, addressNode);
			addressNode.setHost(new HostImpl(addressNode));
		}
		return addressNode;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see mcp.knowledgebase.Kb#getHosts()
	 */
	@Override
	public IndexedCache<Host> getHosts()
	{
		return (IndexedCache<Host>) getMembers(Host.class);
	} 

	/*
	 * (non-Javadoc)
	 * 
	 * @see mcp.knowledgebase.Kb#getHostnames()
	 */
	@Override
	public IndexedCache<Hostname> getHostnames()
	{
		return (IndexedCache<Hostname>) getMembers(Hostname.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mcp.knowledgebase.Kb#getDomains()
	 */
	@Override
	public IndexedCache<Domain> getDomains()
	{
		return (IndexedCache<Domain>) getMembers(Domain.class);
	}

	@Override
	public IndexedCache<Address> getAddressNodes()
	{
		return (IndexedCache<Address>) getMembers(Address.class);
	}


	public static String getOutputFilename()
	{
		return WorkingDirectories.getResumeDirectory() + "knowledge-base";
	}

	@Override
	public void add(Class clazz, Object object) throws IllegalArgumentException
	{
		super.add(clazz, object);
		ExecutionScheduler.getInstance().signalEvent(new ElementCreationEvent((KbElement) object));
	}
}
