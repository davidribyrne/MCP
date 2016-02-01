package mcp.knowledgebase.attributes.hostname;

import mcp.knowledgebase.attributes.NodeAttribute;
import net.dacce.commons.netaddr.SimpleInetAddress;


public interface ResolvedAddress extends NodeAttribute
{

	public SimpleInetAddress getAddress();
}
