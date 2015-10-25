package mcp.knowledgebase.nodes;

import net.dacce.commons.general.StringUtils;


public class OSGuess
{
	private String vendor;
	private String osFamily;
	private String version;
	private int confidence = -1;
	private String deviceType;
	private String longName;


	public String getVendor()
	{
		return vendor;
	}


	public String getOsFamily()
	{
		return osFamily;
	}


	public String getVersion()
	{
		return version;
	}


	public int getConfidence()
	{
		return confidence;
	}


	public String getDeviceType()
	{
		return deviceType;
	}


	public String getLongName()
	{
		return longName;
	}


	public void setVendor(String vendor)
	{
		this.vendor = vendor;
	}


	public void setOsFamily(String osFamily)
	{
		this.osFamily = osFamily;
	}


	public void setVersion(String version)
	{
		this.version = version;
	}


	public void setConfidence(int confidence)
	{
		this.confidence = confidence;
	}


	public void setDeviceType(String deviceType)
	{
		this.deviceType = deviceType;
	}


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

		if (confidence >= 0)
			sb.append("\nConfidence: " + confidence);

		return sb.toString().trim();
	}
}

