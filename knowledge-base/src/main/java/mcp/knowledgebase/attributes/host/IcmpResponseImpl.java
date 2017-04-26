package mcp.knowledgebase.attributes.host;

import java.time.Instant;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(data).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof IcmpResponseImpl))
			return false;
		if (obj == this)
			return true;
		
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(data, ((IcmpResponseImpl) obj).data).isEquals();
	}
	
	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).append("data", data).build();
	}
	
}
