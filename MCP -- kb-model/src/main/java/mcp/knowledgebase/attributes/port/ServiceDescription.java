package mcp.knowledgebase.attributes.port;


import mcp.knowledgebase.attributes.ScoredNodeAttribute;
import mcp.knowledgebase.nodes.Port;


public interface ServiceDescription extends ScoredNodeAttribute
{

	public String getAppProtocolName();


	public ServiceReason getReason();
	
	@Override
	public Port getParent();
}
