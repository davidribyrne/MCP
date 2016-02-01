package mcp.knowledgebase.attributes.port;


import mcp.knowledgebase.attributes.NodeAttribute;
import mcp.knowledgebase.nodes.Port;


public interface PortState extends NodeAttribute
{

	public PortResponse getResponse();


	public PortStateReason getReason();
	@Override
	public Port getParent();
}
