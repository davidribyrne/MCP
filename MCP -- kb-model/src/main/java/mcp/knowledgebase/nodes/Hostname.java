package mcp.knowledgebase.nodes;

import java.util.List;


public interface Hostname extends Node
{

	public void addAddress(IPAddress iPAddress);

	public String getName();

	public Iterable<IPAddress> getAddresses();
	
}
