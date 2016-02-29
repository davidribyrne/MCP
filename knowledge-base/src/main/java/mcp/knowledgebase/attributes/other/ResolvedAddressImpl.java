package mcp.knowledgebase.attributes.other;

import java.time.Instant;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
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
	
	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(address).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ResolvedAddressImpl))
			return false;
		if (obj == this)
			return true;
		
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(address, ((ResolvedAddressImpl) obj).address).isEquals();
	}
	
	@Override
	public String toString()
	{
		return new ToStringBuilder(this).appendSuper(super.toString()).append(address).build();
	}

}
