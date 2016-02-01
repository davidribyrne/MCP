package mcp.knowledgebase.attributes.port;


import mcp.knowledgebase.attributes.ScoredNodeAttribute;
import mcp.knowledgebase.nodes.Port;


public interface SoftwareGuess extends ScoredNodeAttribute
{

	public String getProduct();


	public String getVendor();


	public String getVersion();
	
	@Override
	public Port getParent();
}
