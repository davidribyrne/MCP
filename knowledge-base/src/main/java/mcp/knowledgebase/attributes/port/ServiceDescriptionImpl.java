package mcp.knowledgebase.attributes.port;

import java.time.Instant;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(appProtocolName)
				.append(reason).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ServiceDescriptionImpl))
			return false;
		if (obj == this)
			return true;
		
		ServiceDescriptionImpl o = (ServiceDescriptionImpl) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(appProtocolName,  o.appProtocolName)
				.append(reason, o.reason).isEquals();
	}
	
	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).append("appProtocolName", appProtocolName)
				.append("reason", reason).build();
	}

}
