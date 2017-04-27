package mcp.knowledgebase.nodes;

import mcp.knowledgebase.attributes.host.OSGuess;


public interface Host extends Node
{

	public void addOSGuess(OSGuess guess);

	public Iterable<IPAddress> getAddresses();

	public boolean isUp();

	public void setUp(boolean up);

}
