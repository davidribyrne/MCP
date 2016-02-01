package mcp.knowledgebase.attributes.host;


import mcp.knowledgebase.attributes.NodeAttribute;
import mcp.knowledgebase.nodes.Host;


public interface IcmpResponse extends NodeAttribute
{

	public byte[] getData();
	
	@Override
	public Host getParent();
}
