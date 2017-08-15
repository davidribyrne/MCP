package mcp.knowledgebase.nodeLibrary;


import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.Node;
import mcp.knowledgebase.NodeType;


public class PortStateReason
{
	public static final NodeType PORT_STATE_REASON = NodeType.getByName("PORT_STATE_REASON", "Port state reason");

	public static final Node PORT_STATE_REASON_NMAP_ABORT = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "abort".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_ACCES = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "perm-denied".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_ADDRESSMASKREPLY = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "addressmask-reply".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_ADMINPROHIBITED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "admin-prohibited".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_ARPRESPONSE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "arp-response".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_BEYONDSCOPE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "beyond-scope".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_CONACCEPT = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "syn-ack".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_CONREFUSED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "conn-refused".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_DESTUNREACH = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "dest-unreach".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_ECHOREPLY = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "echo-reply".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_HOSTPROHIBITED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "host-prohibited".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_HOSTUNREACH = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "host-unreach".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_INITACK = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "init-ack".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_IPIDCHANGE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "ipid-change".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_LOCALHOST = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "localhost-response".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_NDRESPONSE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "nd-response".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_NETPROHIBITED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "net-prohibited".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_NETUNREACH = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "net-unreach".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_NOIPIDCHANGE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "no-ipid-change".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_NORESPONSE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "no-response".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_NOROUTE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "no-route".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_PARAMPROBLEM = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "param-problem".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_PORTUNREACH = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "port-unreach".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_PROTORESPONSE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "proto-response".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_PROTOUNREACH = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "proto-unreach".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_REJECTROUTE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "reject-route".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_RESETPEER = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "reset".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_SCRIPT = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "script-set".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_SOURCEQUENCH = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "source-quench".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_SYNACK = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "syn-ack".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_SYN = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "split-handshake-syn".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_TCPRESPONSE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "tcp-response".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_TIMEEXCEEDED = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "time-exceeded".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_TIMESTAMPREPLY = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "timestamp-reply".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_UDPRESPONSE = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "udp-response".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_UNKNOWN = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "unknown-response".getBytes());
	public static final Node PORT_STATE_REASON_NMAP_USER = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "user-set".getBytes());
	public static final Node PORT_STATE_REASON_UNKNOWN = KnowledgeBase.getInstance().getOrCreateNode(PORT_STATE_REASON, "unknown".getBytes());


	private PortStateReason()
	{
	}
}
