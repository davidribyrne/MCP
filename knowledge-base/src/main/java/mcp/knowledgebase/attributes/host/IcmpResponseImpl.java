package mcp.knowledgebase.attributes.host;

import java.time.Instant;
import mcp.knowledgebase.attributes.NodeAttributeImpl;
import mcp.knowledgebase.nodes.Host;
import mcp.knowledgebase.sources.Source;

public class IcmpResponseImpl extends NodeAttributeImpl implements IcmpResponse
{
	private final byte[] data;
	
	public IcmpResponseImpl(Instant time, Source source, Host parent, byte[] data)
	{
		super(time, source, parent);
		this.data = data;
	}

	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.host.IcmpResponse#getData()
	 */
	@Override
	public byte[] getData()
	{
		return data;
	}

	@Override
	public Host getParent()
	{
		return (Host) super.getParent();
	}

}
