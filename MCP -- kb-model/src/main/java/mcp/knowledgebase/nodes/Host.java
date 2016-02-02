package mcp.knowledgebase.nodes;

import java.util.List;
import mcp.knowledgebase.attributes.host.OSGuess;


public interface Host extends Node
{

	public void addOSGuess(OSGuess guess);

	public List<Address> getAddresses();

	public boolean isUp();

	public void setUp(boolean up);

}
