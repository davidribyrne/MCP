package mcp.knowledgebase.nodeLibrary;


import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.Node;
import mcp.knowledgebase.NodeType;


public class PortStateReason
{
	public static final NodeType PORT_STATE_REASON = NodeType.getByName("PORT_STATE_REASON", "Port state reason");

	public static final Node PORT_STATE_REASON_NMAP_ABORT = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "abort");
	public static final Node PORT_STATE_REASON_NMAP_ACCES = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "perm-denied");
	public static final Node PORT_STATE_REASON_NMAP_ADDRESSMASKREPLY = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "addressmask-reply");
	public static final Node PORT_STATE_REASON_NMAP_ADMINPROHIBITED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "admin-prohibited");
	public static final Node PORT_STATE_REASON_NMAP_ARPRESPONSE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "arp-response");
	public static final Node PORT_STATE_REASON_NMAP_BEYONDSCOPE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "beyond-scope");
	public static final Node PORT_STATE_REASON_NMAP_CONACCEPT = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "syn-ack");
	public static final Node PORT_STATE_REASON_NMAP_CONREFUSED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "conn-refused");
	public static final Node PORT_STATE_REASON_NMAP_DESTUNREACH = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "dest-unreach");
	public static final Node PORT_STATE_REASON_NMAP_ECHOREPLY = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "echo-reply");
	public static final Node PORT_STATE_REASON_NMAP_HOSTPROHIBITED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "host-prohibited");
	public static final Node PORT_STATE_REASON_NMAP_HOSTUNREACH = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "host-unreach");
	public static final Node PORT_STATE_REASON_NMAP_INITACK = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "init-ack");
	public static final Node PORT_STATE_REASON_NMAP_IPIDCHANGE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "ipid-change");
	public static final Node PORT_STATE_REASON_NMAP_LOCALHOST = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "localhost-response");
	public static final Node PORT_STATE_REASON_NMAP_NDRESPONSE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "nd-response");
	public static final Node PORT_STATE_REASON_NMAP_NETPROHIBITED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "net-prohibited");
	public static final Node PORT_STATE_REASON_NMAP_NETUNREACH = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "net-unreach");
	public static final Node PORT_STATE_REASON_NMAP_NOIPIDCHANGE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "no-ipid-change");
	public static final Node PORT_STATE_REASON_NMAP_NORESPONSE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "no-response");
	public static final Node PORT_STATE_REASON_NMAP_NOROUTE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "no-route");
	public static final Node PORT_STATE_REASON_NMAP_PARAMPROBLEM = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "param-problem");
	public static final Node PORT_STATE_REASON_NMAP_PORTUNREACH = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "port-unreach");
	public static final Node PORT_STATE_REASON_NMAP_PROTORESPONSE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "proto-response");
	public static final Node PORT_STATE_REASON_NMAP_PROTOUNREACH = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "proto-unreach");
	public static final Node PORT_STATE_REASON_NMAP_REJECTROUTE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "reject-route");
	public static final Node PORT_STATE_REASON_NMAP_RESETPEER = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "reset");
	public static final Node PORT_STATE_REASON_NMAP_SCRIPT = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "script-set");
	public static final Node PORT_STATE_REASON_NMAP_SOURCEQUENCH = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "source-quench");
	public static final Node PORT_STATE_REASON_NMAP_SYNACK = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "syn-ack");
	public static final Node PORT_STATE_REASON_NMAP_SYN = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "split-handshake-syn");
	public static final Node PORT_STATE_REASON_NMAP_TCPRESPONSE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "tcp-response");
	public static final Node PORT_STATE_REASON_NMAP_TIMEEXCEEDED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "time-exceeded");
	public static final Node PORT_STATE_REASON_NMAP_TIMESTAMPREPLY = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "timestamp-reply");
	public static final Node PORT_STATE_REASON_NMAP_UDPRESPONSE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "udp-response");
	public static final Node PORT_STATE_REASON_NMAP_UNKNOWN = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "unknown-response");
	public static final Node PORT_STATE_REASON_NMAP_USER = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "user-set");
	public static final Node PORT_STATE_REASON_UNKNOWN = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "unknown");


	private PortStateReason()
	{
	}
}
