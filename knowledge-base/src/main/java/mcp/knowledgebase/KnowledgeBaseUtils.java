package mcp.knowledgebase;

import mcp.knowledgebase.nodeLibrary.HostStatusReason;
import mcp.knowledgebase.nodeLibrary.Hostnames;
import mcp.knowledgebase.nodeLibrary.Network;
import mcp.knowledgebase.nodeLibrary.Port;
import space.dcce.commons.general.UnexpectedException;
import space.dcce.commons.netaddr.InvalidIPAddressFormatException;
import space.dcce.commons.netaddr.SimpleInetAddress;

public class KnowledgeBaseUtils
{

	private KnowledgeBaseUtils()
	{
	}
	
	
	public static boolean IsAddressActive(Node address)
	{
		NodeType type = address.getNodeType();
		
		if (!(type == Network.IPV4_ADDRESS || type == Network.IPV6_ADDRESS))
		{
			throw new IllegalArgumentException("Node must be an IP address type");
		}
		
		
		return null != address.hasConnectionTo(Port.PORT_STATE_OPEN, HostStatusReason.HOST_REASON_ARP, HostStatusReason.HOST_REASON_ICMP_ECHO,
				HostStatusReason.HOST_REASON_ICMP_LOCALHOST, HostStatusReason.HOST_REASON_ICMP_TIMESTAMP) ||
				null != address.hasConnectionToTypes(Hostnames.HOSTNAME);
	}
	
	
	public static boolean isKnownSubnet(SimpleInetAddress subnet, int mask)
	{
		int addresses = 1 << mask;
		for (int i = 0; i < addresses; i++)
		{
			SimpleInetAddress address;
			try
			{
				address = subnet.addressAddition(i);
			}
			catch (InvalidIPAddressFormatException e)
			{
				throw new UnexpectedException("This shouldn't have happened since we precalculated the net range.", e);
			}
			Node addressNode = KnowledgeBase.instance.getNode(Network.IPV4_ADDRESS, address.toString());
			if (addressNode == null)
				continue;
			
			if (KnowledgeBaseUtils.IsAddressActive(addressNode))
			{
				return true;
			}
		}
		return false;
	}

}
