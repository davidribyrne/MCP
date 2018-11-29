package mcp.knowledgebase.nodeLibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.Node;
import mcp.knowledgebase.NodeType;


public class Icmp
{
	final static Logger logger = LoggerFactory.getLogger(Icmp.class);

	// public static final NodeType NETWORK_SERVICE_DESCRIPTION_REASON =
	// NodeType.getByName("NETWORK_SERVICE_DESCRIPTION_REASON", "Service description reason");
	// public static final Node NETWORK_SERVICE_DESCRIPTION_REASON_UNKNOWN =
	// KnowledgeBase.getInstance().getOrCreateNode(NETWORK_SERVICE_DESCRIPTION_REASON, "Unknown".getBytes());

	public static final NodeType ICMP_RESPONSE_TYPE = NodeType.getByName("ICMP_RESPONSE_TYPE", "ICMP RESPONSE TYPE");
	
	public static final Node ICMP_RESPONSE_ARP = KnowledgeBase.instance.getOrCreateNode(ICMP_RESPONSE_TYPE, "arp response");
	public static final Node ICMP_RESPONSE_ECHO = KnowledgeBase.instance.getOrCreateNode(ICMP_RESPONSE_TYPE, "echo reply");
	public static final Node ICMP_RESPONSE_LOCALHOST = KnowledgeBase.instance.getOrCreateNode(ICMP_RESPONSE_TYPE, "localhost response");
	public static final Node ICMP_RESPONSE_TIMESTAMP = KnowledgeBase.instance.getOrCreateNode(ICMP_RESPONSE_TYPE, "timestamp response");


	// ARP(-1), LOCALHOST(-2), ECHO(8), TIMESTAMP(14), MASK(18);

	public static Node parseIcmpResponseFromNmap(String reason)
	{
		switch (reason.toLowerCase())
		{
			case "user-set":
			case "no-response":
				return null;
			case "echo-reply":
				return ICMP_RESPONSE_ECHO;
			case "arp-response":
				return ICMP_RESPONSE_ARP;
			case "localhost-response":
				return ICMP_RESPONSE_LOCALHOST;
		}
		logger.warn("The nmap status reason '" + reason + "' is unknown.");
		return null;
	}

}
