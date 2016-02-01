package mcp.knowledgebase;

import mcp.knowledgebase.nodes.AddressNode;
import mcp.knowledgebase.nodes.Domain;
import mcp.knowledgebase.nodes.Host;
import mcp.knowledgebase.nodes.Hostname;
import net.dacce.commons.general.IndexedCache;
import net.dacce.commons.netaddr.SimpleInetAddress;


public interface KnowledgeBase
{

	public IndexedCache<Host> getHosts();

	public Host getOrCreateHost(AddressNode address);

	public IndexedCache<Hostname> getHostnames();

	public Hostname getOrCreateHostname(String name);

	public IndexedCache<Domain> getDomains();

	public Domain getOrCreateDomain(String name);

	public IndexedCache<AddressNode> getAddressNodes();
	
	public AddressNode getOrCreateAddressNode(SimpleInetAddress address);
}
