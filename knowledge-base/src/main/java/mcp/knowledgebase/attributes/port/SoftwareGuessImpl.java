package mcp.knowledgebase.attributes.port;

import java.time.Instant;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import mcp.knowledgebase.attributes.ScoredNodeAttributeImpl;
import mcp.knowledgebase.nodes.Port;
import mcp.knowledgebase.sources.Source;


public class SoftwareGuessImpl extends ScoredNodeAttributeImpl implements SoftwareGuess
{
	private final String product;
	private final String vendor;
	private final String version;
	
	
	public SoftwareGuessImpl(Port parent, Instant time, Source source, String product, String vendor, String version)
	{
		super(time, source, parent);
		this.product = product;
		this.vendor = vendor;
		this.version = version;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.port.SoftwareGuess#getProduct()
	 */
	@Override
	public String getProduct()
	{
		return product;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.port.SoftwareGuess#getVendor()
	 */
	@Override
	public String getVendor()
	{
		return vendor;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.port.SoftwareGuess#getVersion()
	 */
	@Override
	public String getVersion()
	{
		return version;
	}


	@Override
	public Port getParent()
	{
		return (Port) super.getParent();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(product)
				.append(vendor).append(version).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof SoftwareGuessImpl))
			return false;
		if (obj == this)
			return true;
		
		SoftwareGuessImpl o = (SoftwareGuessImpl) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(product, o.product)
				.append(vendor, o.vendor).append(version, o.version).isEquals();
	}
	
	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).append("product", product)
				.append("vendor", vendor).append("version", version).build();
	}

}
