package mcp.knowledgebase;

import mcp.knowledgebase.nodes.Address;
import mcp.knowledgebase.nodes.Domain;
import mcp.knowledgebase.nodes.Host;
import mcp.knowledgebase.nodes.Hostname;
import net.dacce.commons.general.IndexedCache;
import net.dacce.commons.netaddr.SimpleInetAddress;


public interface KnowledgeBase
{

	public IndexedCache<Host> getHosts();


	public IndexedCache<Hostname> getHostnames();

	/**
	 * Don't forget to check the domains through HostnameDiscoveryUtils
	 * @param name
	 * @return
	 */
	public Hostname getOrCreateHostname(String name);

	public IndexedCache<Domain> getDomains();

	/**
	 * 
	 * @param name
	 * @return True if this is a new domain
	 */
	public boolean addDomain(String name);

	/**
	 * This will also create the host node if it isn't yet created.
	 * @return
	 */
	public IndexedCache<Address> getAddressNodes();
	
	public Address getOrCreateAddressNode(SimpleInetAddress address);
}
