package mcp.knowledgebase;

import mcp.knowledgebase.nodes.IPAddress;
import mcp.knowledgebase.nodes.MacAddress;
import mcp.knowledgebase.nodes.Domain;
import mcp.knowledgebase.nodes.Host;
import mcp.knowledgebase.nodes.Hostname;
import net.dacce.commons.general.IndexedCache;
import net.dacce.commons.netaddr.SimpleInetAddress;


public interface KnowledgeBase
{

	public Iterable<Host> getHosts();


	public Iterable<Hostname> getHostnames();

	/**
	 * Don't forget to check the domains through HostnameDiscoveryUtils
	 * @param name
	 * @return
	 */
	public Hostname getOrCreateHostname(String name);

	public Iterable<Domain> getDomains();

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
	public Iterable<IPAddress> getIPAddressNodes();
	
	public IPAddress getOrCreateIPAddressNode(SimpleInetAddress address);
	
	public MacAddress getOrCreateMacAddressNode(byte[] hexAddress);
	
	public Iterable<MacAddress> getMacAddressNodes();
}
