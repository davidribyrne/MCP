package mcp.knowledgebase.attributes.port;

import mcp.knowledgebase.attributes.NodeAttributeImpl;
import mcp.knowledgebase.sources.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.*;

public class PortStateImpl extends NodeAttributeImpl implements PortState
{
	private final PortResponse response;
	private final PortStateReason reason;

	public PortStateImpl(Instant time, Source source, PortResponse response, PortStateReason reason)
	{
		super(time, source);
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
}
