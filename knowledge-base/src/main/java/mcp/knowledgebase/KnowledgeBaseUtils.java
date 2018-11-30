package mcp.knowledgebase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.nodeLibrary.Hostnames;
import mcp.knowledgebase.nodeLibrary.Icmp;
import mcp.knowledgebase.nodeLibrary.Network;
import mcp.knowledgebase.nodeLibrary.Port;
import space.dcce.commons.general.UnexpectedException;
import space.dcce.commons.netaddr.InvalidIPAddressFormatException;
import space.dcce.commons.netaddr.SimpleInetAddress;

import java.util.*;

public class KnowledgeBaseUtils
{
	private final static Logger logger = LoggerFactory.getLogger(KnowledgeBaseUtils.class);

	private KnowledgeBaseUtils()
	{
	}
	
	
	public static boolean IsAddressActive(Node address)
	{
		NodeType type = address.getNodeType();
		
		if (type == Network.IPV4_ADDRESS || type == Network.IPV6_ADDRESS)
		{
			throw new IllegalArgumentException("Node must be an IP address type");
		}
		
		
		return null != address.hasConnectionTo(Port.PORT_STATE_OPEN, Icmp.ICMP_RESPONSE_ARP, Icmp.ICMP_RESPONSE_ECHO,
				Icmp.ICMP_RESPONSE_LOCALHOST, Icmp.ICMP_RESPONSE_TIMESTAMP) ||
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
