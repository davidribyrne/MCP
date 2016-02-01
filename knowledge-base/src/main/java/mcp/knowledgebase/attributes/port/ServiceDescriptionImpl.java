package mcp.knowledgebase.attributes.port;

import java.time.Instant;
import mcp.knowledgebase.attributes.ScoredNodeAttributeImpl;
import mcp.knowledgebase.nodes.Port;
import mcp.knowledgebase.sources.Source;

public class ServiceDescriptionImpl extends ScoredNodeAttributeImpl implements ServiceDescription
{

	private final String appProtocolName;
	private final ServiceReason reason;


	public ServiceDescriptionImpl(Port parent, Instant time, Source source, String appProtocolName, ServiceReason serviceReason)
	{
		super(time, source, parent);
		this.appProtocolName = appProtocolName;
		this.reason = serviceReason;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.port.ServiceDescription#getAppProtocolName()
	 */
	@Override
	public String getAppProtocolName()
	{
		return appProtocolName;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.port.ServiceDescription#getReason()
	 */
	@Override
	public ServiceReason getReason()
	{
		return reason;
	}

	public Port getParent()
	{
		return (Port) super.getParent();
	}
}
