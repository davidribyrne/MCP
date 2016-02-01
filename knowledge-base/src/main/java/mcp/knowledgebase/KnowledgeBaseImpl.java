package mcp.knowledgebase;

import java.io.FileNotFoundException;
import java.io.IOException;
import mcp.commons.PersistedObject;
import mcp.commons.UniqueFilenameRegistrar;
import mcp.commons.WorkingDirectories;
import mcp.knowledgebase.nodes.AddressNode;
import mcp.knowledgebase.nodes.AddressNodeImpl;
import mcp.knowledgebase.nodes.Domain;
import mcp.knowledgebase.nodes.DomainImpl;
import mcp.knowledgebase.nodes.Host;
import mcp.knowledgebase.nodes.HostImpl;
import mcp.knowledgebase.nodes.Hostname;
import mcp.knowledgebase.nodes.HostnameImpl;
import mcp.knowledgebase.nodes.Node;
import net.dacce.commons.general.IndexedCache;
import net.dacce.commons.general.MultiClassIndexedCache;
import net.dacce.commons.general.YamlUtils;
import net.dacce.commons.netaddr.SimpleInetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.esotericsoftware.yamlbeans.YamlException;


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
	public Hostname getOrCreateHostname(String name)
	{
		Hostname hostname = (Hostname) getMember(Hostname.class, HostnameImpl.NAME_FIELD(), name);
		if (hostname == null)
		{
			hostname = new HostnameImpl(name);
			add(Hostname.class, hostname);
		}
		return hostname;
	}

	@Override
	public Domain getOrCreateDomain(String name)
	{
		Domain domain = (Domain) getMember(Domain.class, DomainImpl.NAME_FIELD(), name);
		if (domain == null)
		{
			domain = new DomainImpl(name);
			add(Domain.class, domain);
		}
		return domain;
	}


	@Override
	public synchronized AddressNode getOrCreateAddressNode(SimpleInetAddress address)
	{
		AddressNode addressNode = (AddressNode) getMember(AddressNode.class, AddressNodeImpl.ADDRESS_FIELD(), address);
		if (addressNode == null)
		{
			addressNode = new AddressNodeImpl(address);
			add(AddressNode.class, addressNode);
		}
		return addressNode;
	}

	@Override
	public Host getOrCreateHost(AddressNode address)
	{
		Host host = (Host) getMember(Host.class, HostImpl.ADDRESSES_FIELD(), address);
		if (host == null)
		{
			host = new HostImpl(address);
			add(Host.class, host);
		}
		return host;
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
	public IndexedCache<AddressNode> getAddressNodes()
	{
		return (IndexedCache<AddressNode>) getMembers(AddressNode.class);
	}


	public static String getOutputFilename()
	{
		return WorkingDirectories.getResumeDirectory() + "knowledge-base";
	}
}
