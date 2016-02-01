package mcp.knowledgebase.attributes.port;

import java.time.Instant;
import mcp.knowledgebase.attributes.NodeAttributeImpl;
import mcp.knowledgebase.nodes.Port;
import mcp.knowledgebase.sources.Source;

public class PortStateImpl extends NodeAttributeImpl implements PortState
{
	private final PortResponse response;
	private final PortStateReason reason;

	public PortStateImpl(Port parent, Instant time, Source source, PortResponse response, PortStateReason reason)
	{
		super(time, source, parent);
		this.reason = reason;
		this.response = response;
	}

	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.port.PortState#getResponse()
	 */
	@Override
	public PortResponse getResponse()
	{
		return response;
	}

	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.port.PortState#getReason()
	 */
	@Override
	public PortStateReason getReason()
	{
		return reason;
	}
	
	@Override
	public Port getParent()
	{
		return (Port) super.getParent();
	}
}
