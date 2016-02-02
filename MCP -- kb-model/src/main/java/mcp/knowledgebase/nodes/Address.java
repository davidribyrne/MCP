package mcp.knowledgebase.nodes;

import net.dacce.commons.general.IndexedCache;
import net.dacce.commons.netaddr.SimpleInetAddress;


public interface Address extends Node
{
	public Port getOrCreatePort(PortType type, int number);

	public byte[] getMacAddress();

	public IndexedCache<Port> getTcpPorts();

	public IndexedCache<Port> getUdpPorts();

	public void setMacAddress(byte[] macAddress);

	public String getMacVendor();

	public void setMacVendor(String macVendor);

	public SimpleInetAddress getAddress();
	
	public Host getHost();

	public void setHost(Host host);
}
