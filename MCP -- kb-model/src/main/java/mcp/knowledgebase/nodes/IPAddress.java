package mcp.knowledgebase.nodes;

import mcp.knowledgebase.attributes.host.IcmpResponseType;
import net.dacce.commons.netaddr.SimpleInetAddress;


public interface IPAddress extends Node
{
	public MacAddress getMacAddress();
	
	public void setMacAddress(MacAddress macAddress);
	
	public Port getOrCreatePort(PortType type, int number);

	public Iterable<Port> getTcpPorts();

	public Iterable<Port> getUdpPorts();

	public SimpleInetAddress getAddress();
	
	public Host getHost();

	public void setHost(Host host);
	
	public void setIcmpResponse(IcmpResponseType response);
	
	public IcmpResponseType getIcmpResponse();

}
