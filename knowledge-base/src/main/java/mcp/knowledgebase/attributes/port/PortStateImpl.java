package mcp.knowledgebase.attributes.port;

import java.time.Instant;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
	
	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(response)
				.append(reason).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof PortStateImpl))
			return false;
		if (obj == this)
			return true;
		PortStateImpl psi = (PortStateImpl) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(response, psi.response)
				.append(reason, psi.reason).isEquals();
	}
	
	@Override
	public String toString()
	{
		ToStringBuilder tsb = new ToStringBuilder(this, ToStringStyle.JSON_STYLE);
//		tsb.appendSuper(super.toString());
		tsb.append("response", response);
		tsb.append("reason", reason);
		return tsb.toString();
	}

}
