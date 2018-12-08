package mcp.knowledgebase.nodeLibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.Node;
import mcp.knowledgebase.NodeType;


public class HostStatusReason
{
	final static Logger logger = LoggerFactory.getLogger(HostStatusReason.class);

	// public static final NodeType NETWORK_SERVICE_DESCRIPTION_REASON =
	// NodeType.getByName("NETWORK_SERVICE_DESCRIPTION_REASON", "Service description reason");
	// public static final Node NETWORK_SERVICE_DESCRIPTION_REASON_UNKNOWN =
	// KnowledgeBase.getInstance().getOrCreateNode(NETWORK_SERVICE_DESCRIPTION_REASON, "Unknown".getBytes());

	public static final NodeType HOST_STATUS_REASON_TYPE = NodeType.getByName("ICMP_RESPONSE_TYPE", "ICMP RESPONSE TYPE");
	
	public static final Node HOST_REASON_ARP = KnowledgeBase.instance.getOrCreateNode(HOST_STATUS_REASON_TYPE, "arp response");
	public static final Node HOST_REASON_ICMP_ECHO = KnowledgeBase.instance.getOrCreateNode(HOST_STATUS_REASON_TYPE, "echo reply");
	public static final Node HOST_REASON_ICMP_LOCALHOST = KnowledgeBase.instance.getOrCreateNode(HOST_STATUS_REASON_TYPE, "localhost response");
	public static final Node HOST_REASON_ICMP_TIMESTAMP = KnowledgeBase.instance.getOrCreateNode(HOST_STATUS_REASON_TYPE, "timestamp response");
	public static final Node HOST_REASON_ICMP_NET_UNREACHABLE = KnowledgeBase.instance.getOrCreateNode(HOST_STATUS_REASON_TYPE, "Net unreachable");
	public static final Node HOST_REASON_SYN_ACK = KnowledgeBase.instance.getOrCreateNode(HOST_STATUS_REASON_TYPE, "TCP SYN/ACK");


	// ARP(-1), LOCALHOST(-2), ECHO(8), TIMESTAMP(14), MASK(18);

	public static Node parseIcmpResponseFromNmap(String reason)
	{
		switch (reason.toLowerCase())
		{
			case "user-set":
			case "no-response":
				return null;
			case "echo-reply":
				return HOST_REASON_ICMP_ECHO;
			case "arp-response":
				return HOST_REASON_ARP;
			case "localhost-response":
				return HOST_REASON_ICMP_LOCALHOST;
			case "net-unreach":
				return HOST_REASON_ICMP_NET_UNREACHABLE;
			case "syn-ack":
				return HOST_REASON_SYN_ACK;
		}
		logger.warn("The nmap status reason '" + reason + "' is unknown.");
		return null;
	}

}
