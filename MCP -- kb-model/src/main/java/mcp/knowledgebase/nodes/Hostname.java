package mcp.knowledgebase.nodes;

public interface Hostname extends Node
{

	public void addAddress(IPAddress iPAddress);

	public String getName();

	public Iterable<IPAddress> getAddresses();
	
}
