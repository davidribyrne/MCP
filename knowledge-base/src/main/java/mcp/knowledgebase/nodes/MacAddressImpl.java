package mcp.knowledgebase.nodes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dacce.commons.general.UnexpectedException;
import net.dacce.commons.netaddr.MacUtils;

public class MacAddressImpl extends NodeImpl implements MacAddress
{
	private final static Logger logger = LoggerFactory.getLogger(MacAddressImpl.class);
	private final byte[] macAddress;
	private String macVendor;
	private List<IPAddress> ipAddresses;
	private Host host;
	private static Field nameField;


	private static void initializeFields()
	{
		try
		{
			nameField = MacAddressImpl.class.getDeclaredField("macAddress");
			nameField.setAccessible(true);
		}
		catch (NoSuchFieldException e)
		{
			throw new UnexpectedException("This shouldn't have happened: " + e.getMessage(), e);
		}
		catch (SecurityException e)
		{
			throw new UnexpectedException("This shouldn't have happened: " + e.getMessage(), e);
		}
	}


	public synchronized static Field NAME_FIELD()
	{
		if (nameField == null)
		{
			initializeFields();
		}
		return nameField;
	}


	public MacAddressImpl(byte[] macAddress)
	{
		super(null);
		this.macAddress = macAddress;
		ipAddresses = new ArrayList<IPAddress>(1);
	}

	@Override
	public byte[] getMacAddress()
	{
		return macAddress;
	}


	@Override
	public String getMacVendor()
	{
		return macVendor;
	}


	@Override
	public void setMacVendor(String macVendor)
	{
		this.macVendor = macVendor;
	}

	@Override
	public String toString()
	{
		ToStringBuilder tsb = new ToStringBuilder(this, ToStringStyle.JSON_STYLE);
		tsb.append("MAC address", MacUtils.getHexString(macAddress));
		tsb.append("macVendor", macVendor);
		return tsb.build();
	}

	@Override
	public List<IPAddress> getIPAddresses()
	{
		return Collections.unmodifiableList(ipAddresses);
	}

	@Override
	public void addIPAddress(IPAddress ipAddress)
	{
		ipAddresses.add(ipAddress);
	}

	@Override
	public Host getHost()
	{
		return host;
	}

	@Override
	public void setHost(Host host)
	{
		this.host = host;
	}
}
