package mcp.knowledgebase.attributes.host;

import java.time.Instant;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import mcp.knowledgebase.attributes.ScoredNodeAttributeImpl;
import mcp.knowledgebase.nodes.Host;
import mcp.knowledgebase.sources.Source;


public class OSGuessImpl extends ScoredNodeAttributeImpl implements OSGuess
{

	private String vendor;
	private String osFamily;
	private String version;
	private String deviceType;
	private String longName;


	public OSGuessImpl(Instant time, Source source, Host parent)
	{
		super(time, source, parent);
	}
	
	
	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.attributes.host.OSGuess#getVendor()
	 */
	@Override
	public String getVendor()
	{
		return vendor;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.attributes.host.OSGuess#getOsFamily()
	 */
	@Override
	public String getOsFamily()
	{
		return osFamily;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.attributes.host.OSGuess#getVersion()
	 */
	@Override
	public String getVersion()
	{
		return version;
	}



	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.attributes.host.OSGuess#getDeviceType()
	 */
	@Override
	public String getDeviceType()
	{
		return deviceType;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.attributes.host.OSGuess#getLongName()
	 */
	@Override
	public String getLongName()
	{
		return longName;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.attributes.host.OSGuess#setVendor(java.lang.String)
	 */
	@Override
	public void setVendor(String vendor)
	{
		this.vendor = vendor;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.attributes.host.OSGuess#setOsFamily(java.lang.String)
	 */
	@Override
	public void setOsFamily(String osFamily)
	{
		this.osFamily = osFamily;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.attributes.host.OSGuess#setVersion(java.lang.String)
	 */
	@Override
	public void setVersion(String version)
	{
		this.version = version;
	}

	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.attributes.host.OSGuess#setDeviceType(java.lang.String)
	 */
	@Override
	public void setDeviceType(String deviceType)
	{
		this.deviceType = deviceType;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.attributes.host.OSGuess#setLongName(java.lang.String)
	 */
	@Override
	public void setLongName(String longName)
	{
		this.longName = longName;
	}


	@Override
	public Host getParent()
	{
		return (Host) super.getParent();
	}
	
	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(vendor)
				.append(osFamily).append(version).append(deviceType).append(longName).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof OSGuessImpl))
			return false;
		if (obj == this)
			return true;
		
		OSGuessImpl o = (OSGuessImpl) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(vendor, o.vendor)
				.append(osFamily, o.osFamily).append(version, o.version)
				.append(deviceType, o.deviceType).append(longName, o.longName).isEquals();
	}
	
	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).append("vendor", vendor)
				.append("osFamily", osFamily).append("version", version)
				.append("deviceType", deviceType).append("longName", longName).build();
	}

}

