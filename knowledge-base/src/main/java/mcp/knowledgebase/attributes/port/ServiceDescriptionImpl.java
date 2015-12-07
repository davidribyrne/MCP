package mcp.knowledgebase.attributes.port;

import mcp.knowledgebase.attributes.NodeAttributeImpl;
import mcp.knowledgebase.attributes.ScoredNodeAttributeImpl;
import mcp.knowledgebase.sources.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.*;

public class ServiceDescriptionImpl extends ScoredNodeAttributeImpl implements ServiceDescription
{

	private final String appProtocolName;
	private final ServiceReason reason;


	public ServiceDescriptionImpl(Instant time, Source source, String appProtocolName, ServiceReason serviceReason)
	{
		super(time, source);
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


}
