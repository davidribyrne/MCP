package mcp.knowledgebase.nodes;

import java.util.List;

public interface MacAddress extends Node
{

	public byte[] getMacAddress();

	public String getMacVendor();

	public void setMacVendor(String macVendor);

	public List<IPAddress> getIPAddresses();
	
	public void addIPAddress(IPAddress ipAddress);
	
	public Host getHost();
	
	public void setHost(Host host);
}
