package mcp.knowledgebase.attributes.port;

import java.util.HashMap;
import java.util.Map;

import mcp.knowledgebase.nodes.PortType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// From https://svn.nmap.org/nmap/portreasons.cc
public enum PortStateReason
{
	NMAP_ABORT("abort", true),
	NMAP_ACCES("perm-denied", true),
	NMAP_ADDRESSMASKREPLY("addressmask-reply", true),
	NMAP_ADMINPROHIBITED("admin-prohibited", true),
	NMAP_ARPRESPONSE("arp-response", true),
	NMAP_BEYONDSCOPE("beyond-scope", true),
	NMAP_CONACCEPT("syn-ack", true),
	NMAP_CONREFUSED("conn-refused", true),
	NMAP_DESTUNREACH("dest-unreach", true),
	NMAP_ECHOREPLY("echo-reply", true),
	NMAP_HOSTPROHIBITED("host-prohibited", true),
	NMAP_HOSTUNREACH("host-unreach", true),
	NMAP_INITACK("init-ack", true),
	NMAP_IPIDCHANGE("ipid-change", true),
	NMAP_LOCALHOST("localhost-response", true),
	NMAP_NDRESPONSE("nd-response", true),
	NMAP_NETPROHIBITED("net-prohibited", true),
	NMAP_NETUNREACH("net-unreach", true),
	NMAP_NOIPIDCHANGE("no-ipid-change", true),
	NMAP_NORESPONSE("no-response", true),
	NMAP_NOROUTE("no-route", true),
	NMAP_PARAMPROBLEM("param-problem", true),
	NMAP_PORTUNREACH("port-unreach", true),
	NMAP_PROTORESPONSE("proto-response", true),
	NMAP_PROTOUNREACH("proto-unreach", true),
	NMAP_REJECTROUTE("reject-route", true),
	NMAP_RESETPEER("reset", true),
	NMAP_SCRIPT("script-set", true),
	NMAP_SOURCEQUENCH("source-quench", true),
	NMAP_SYNACK("syn-ack", true),
	NMAP_SYN("split-handshake-syn", true),
	NMAP_TCPRESPONSE("tcp-response", true),
	NMAP_TIMEEXCEEDED("time-exceeded", true),
	NMAP_TIMESTAMPREPLY("timestamp-reply", true),
	NMAP_UDPRESPONSE("udp-response", true),
	NMAP_UNKNOWN("unknown-response", true),
	NMAP_USER("user-set", true),
	UNKNOWN("unknown", false);
	
	final private String description;
	final private boolean nmap;
	final static Logger logger = LoggerFactory.getLogger(PortType.class);
	final static Map<String, PortStateReason> lookup = new HashMap<String, PortStateReason>();
	static 
	{
		for(PortStateReason reason: PortStateReason.values())
		{
			lookup.put(reason.description, reason);
		}
	}
	PortStateReason(String description, boolean nmap)
	{
		this.description = description;
		this.nmap = nmap;
	}
	
	@Override
	public String toString()
	{
		return (nmap ? "Nmap - " : "") + description;
	}

	public static PortStateReason parseNmapText(String reason)
	{
		if (lookup.containsKey(reason))
			return lookup.get(reason);
		logger.warn("The Nmap port state reason '" + reason + "' is unknown.");
		return UNKNOWN;
	}

	public String getDescription()
	{
		return description;
	}

}
