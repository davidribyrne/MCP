package mcp.knowledgebase.nodeLibrary;


import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.Node;
import mcp.knowledgebase.NodeType;


public class PortStateReason
{
	public static final NodeType PORT_STATE_REASON = NodeType.getByName("PORT_STATE_REASON", "Port state reason");

	public static final Node PORT_STATE_REASON_NMAP_ABORT = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "abort");
	public static final Node PORT_STATE_REASON_NMAP_ACCES = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "perm-denied");
	public static final Node PORT_STATE_REASON_NMAP_ADDRESSMASKREPLY = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "addressmask-reply");
	public static final Node PORT_STATE_REASON_NMAP_ADMINPROHIBITED = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "admin-prohibited");
	public static final Node PORT_STATE_REASON_NMAP_ARPRESPONSE = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "arp-response");
	public static final Node PORT_STATE_REASON_NMAP_BEYONDSCOPE = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "beyond-scope");
	public static final Node PORT_STATE_REASON_NMAP_CONACCEPT = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "syn-ack");
	public static final Node PORT_STATE_REASON_NMAP_CONREFUSED = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "conn-refused");
	public static final Node PORT_STATE_REASON_NMAP_DESTUNREACH = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "dest-unreach");
	public static final Node PORT_STATE_REASON_NMAP_ECHOREPLY = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "echo-reply");
	public static final Node PORT_STATE_REASON_NMAP_HOSTPROHIBITED = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "host-prohibited");
	public static final Node PORT_STATE_REASON_NMAP_HOSTUNREACH = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "host-unreach");
	public static final Node PORT_STATE_REASON_NMAP_INITACK = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "init-ack");
	public static final Node PORT_STATE_REASON_NMAP_IPIDCHANGE = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "ipid-change");
	public static final Node PORT_STATE_REASON_NMAP_LOCALHOST = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "localhost-response");
	public static final Node PORT_STATE_REASON_NMAP_NDRESPONSE = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "nd-response");
	public static final Node PORT_STATE_REASON_NMAP_NETPROHIBITED = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "net-prohibited");
	public static final Node PORT_STATE_REASON_NMAP_NETUNREACH = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "net-unreach");
	public static final Node PORT_STATE_REASON_NMAP_NOIPIDCHANGE = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "no-ipid-change");
	public static final Node PORT_STATE_REASON_NMAP_NORESPONSE = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "no-response");
	public static final Node PORT_STATE_REASON_NMAP_NOROUTE = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "no-route");
	public static final Node PORT_STATE_REASON_NMAP_PARAMPROBLEM = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "param-problem");
	public static final Node PORT_STATE_REASON_NMAP_PORTUNREACH = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "port-unreach");
	public static final Node PORT_STATE_REASON_NMAP_PROTORESPONSE = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "proto-response");
	public static final Node PORT_STATE_REASON_NMAP_PROTOUNREACH = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "proto-unreach");
	public static final Node PORT_STATE_REASON_NMAP_REJECTROUTE = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "reject-route");
	public static final Node PORT_STATE_REASON_NMAP_RESETPEER = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "reset");
	public static final Node PORT_STATE_REASON_NMAP_SCRIPT = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "script-set");
	public static final Node PORT_STATE_REASON_NMAP_SOURCEQUENCH = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "source-quench");
	public static final Node PORT_STATE_REASON_NMAP_SYNACK = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "syn-ack");
	public static final Node PORT_STATE_REASON_NMAP_SYN = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "split-handshake-syn");
	public static final Node PORT_STATE_REASON_NMAP_TCPRESPONSE = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "tcp-response");
	public static final Node PORT_STATE_REASON_NMAP_TIMEEXCEEDED = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "time-exceeded");
	public static final Node PORT_STATE_REASON_NMAP_TIMESTAMPREPLY = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "timestamp-reply");
	public static final Node PORT_STATE_REASON_NMAP_UDPRESPONSE = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "udp-response");
	public static final Node PORT_STATE_REASON_NMAP_UNKNOWN = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "unknown-response");
	public static final Node PORT_STATE_REASON_NMAP_USER = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "user-set");
	public static final Node PORT_STATE_REASON_UNKNOWN = KnowledgeBase.instance.getOrCreateNode(PORT_STATE_REASON, "unknown");


	private PortStateReason()
	{
	}
}
