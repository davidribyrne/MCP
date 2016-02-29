package mcp.knowledgebase.nodes;

import java.util.List;


public interface Hostname extends Node
{

	public void addAddress(Address address);

	public String getName();

	public List<Address> getAddresses();
	
}
