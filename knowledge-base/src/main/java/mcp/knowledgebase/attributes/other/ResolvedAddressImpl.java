package mcp.knowledgebase.attributes.other;

import java.time.Instant;
import mcp.knowledgebase.attributes.NodeAttributeImpl;
import mcp.knowledgebase.attributes.hostname.ResolvedAddress;
import mcp.knowledgebase.nodes.Node;
import mcp.knowledgebase.sources.Source;
import net.dacce.commons.netaddr.SimpleInetAddress;

public class ResolvedAddressImpl extends NodeAttributeImpl implements ResolvedAddress
{
	private final SimpleInetAddress address;

	public ResolvedAddressImpl(Node parent, Instant time, Source source, SimpleInetAddress address)
	{
		super(time, source, parent);
		this.address = address;
	}

	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.other.ResolvedAddress#getAddress()
	 */
	@Override
	public SimpleInetAddress getAddress()
	{
		return address;
	}
}
