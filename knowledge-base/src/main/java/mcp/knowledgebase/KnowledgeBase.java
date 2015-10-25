package mcp.knowledgebase;

import java.util.Collection;

import mcp.knowledgebase.nodes.Host;
import mcp.knowledgebase.nodes.Hostname;
import mcp.knowledgebase.scope.Scope;
import net.dacce.commons.general.IndexedCache;
import net.dacce.commons.general.UniqueList;
import net.dacce.commons.netaddr.SimpleInetAddress;


public class KnowledgeBase
{
	private static final KnowledgeBase instance = new KnowledgeBase();

	private final Scope scope;
	private final IndexedCache<Host> hosts;
	private final IndexedCache<Hostname> hostnames;


	private KnowledgeBase()
	{
		scope = new Scope();
		hosts = new IndexedCache<Host>();
		hostnames = new IndexedCache<Hostname>();
	}


	public static KnowledgeBase getInstance()
	{
		return instance;
	}

	public Collection<Hostname> getHostnames(SimpleInetAddress address)
	{
		UniqueList<Hostname> results = new UniqueList<Hostname>(1);
		for (Hostname hostname: hostnames)
		{
			if (hostname.containsAddress(address))
			{
				results.add(hostname);
			}
		}
		
		return results;
	}
	
	public Scope getScope()
	{
		return scope;
	}


	public Host getOrCreateHost(SimpleInetAddress address)
	{
		Host host = (Host) hosts.getMember(Host.ADDRESS_FIELD(), address);
		if (host == null)
		{
			host = new Host(address);
			hosts.add(host);
		}
		return host;
	}


	public Hostname getOrCreateHostname(String name)
	{
		Hostname hostname = (Hostname) hostnames.getMember(Hostname.NAME_FIELD(), name);
		if (hostname == null)
		{
			hostname = new Hostname(name);
			hostnames.add(hostname);
		}
		return hostname;
	}


	public IndexedCache<Host> getHosts()
	{
		return hosts;
	}


	public IndexedCache<Hostname> getHostnames()
	{
		return hostnames;
	}
}
