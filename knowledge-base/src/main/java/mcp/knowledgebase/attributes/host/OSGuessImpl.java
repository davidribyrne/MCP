package mcp.knowledgebase.attributes.host;

import java.time.Instant;

import mcp.knowledgebase.attributes.NodeAttributeImpl;
import mcp.knowledgebase.attributes.ScoredNodeAttributeImpl;
import mcp.knowledgebase.attributes.host.OSGuess;
import mcp.knowledgebase.sources.Source;
import net.dacce.commons.general.StringUtils;


public class OSGuessImpl extends ScoredNodeAttributeImpl implements OSGuess
{

	private String vendor;
	private String osFamily;
	private String version;
	private String deviceType;
	private String longName;


	public OSGuessImpl(Instant time, Source source)
	{
		super(time, source);
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
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if (!StringUtils.isEmptyOrNull(vendor))
			sb.append("\nVendor: " + vendor);

		if (!StringUtils.isEmptyOrNull(osFamily))
			sb.append("\nOS Family: " + osFamily);

		if (!StringUtils.isEmptyOrNull(version))
			sb.append("\nVersion: " + version);

		if (!StringUtils.isEmptyOrNull(longName))
			sb.append("\nLong name: " + longName);

		if (!StringUtils.isEmptyOrNull(deviceType))
			sb.append("\nDevice type: " + deviceType);

		sb.append("\nConfidence: " + getConfidence());

		return sb.toString().trim();
	}
	
}

