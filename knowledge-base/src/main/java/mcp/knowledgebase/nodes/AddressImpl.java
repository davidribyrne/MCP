package mcp.knowledgebase.nodes;

import java.lang.reflect.Field;
import org.apache.commons.lang3.builder.ToStringBuilder;
import net.dacce.commons.general.IndexedCache;
import net.dacce.commons.general.UnexpectedException;
import net.dacce.commons.netaddr.MacUtils;
import net.dacce.commons.netaddr.SimpleInetAddress;

public class AddressImpl extends NodeImpl implements Address
{
	private final SimpleInetAddress address;
	final private IndexedCache<Port> tcpPorts;
	final private IndexedCache<Port> udpPorts;
	private byte[] macAddress;
	private String macVendor;
	private Host host;
	

	/**
	 * ONLY THE KB SHOULD CALL THIS
	 * @param parent
	 * @param address
	 */
	public AddressImpl(SimpleInetAddress address)
	{
		super(null);
		this.address = address;
		tcpPorts = new IndexedCache<Port>();
		udpPorts = new IndexedCache<Port>();
	}


	private static Field addressField;

	private static void initializeFields()
	{
		try
		{
			addressField = AddressImpl.class.getDeclaredField("address");
			addressField.setAccessible(true);
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

	public static Field ADDRESS_FIELD()
	{
		if (addressField == null)
		{
			initializeFields();
		}
		return addressField;
	}
	
	@Override
	public Port getOrCreatePort(PortType type, int number)
	{
		Port port;
		IndexedCache<Port> cache;
		if (type == PortType.TCP)
		{
			cache = tcpPorts;
		}
		else
		{
			cache = udpPorts;
		}
		port = (Port) cache.getMember(PortImpl.NUMBER_FIELD(), number);
		if (port == null)
		{
			port = new PortImpl(this, type, number);
			cache.add(port);
		}
		return port;
	}
	
	@Override
	public byte[] getMacAddress()
	{
		return macAddress;
	}

	@Override
	public void setMacAddress(byte[] macAddress)
	{
		this.macAddress = macAddress;
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
	public SimpleInetAddress getAddress()
	{
		return address;
	}


	@Override
	public String toString()
	{
		ToStringBuilder tsb = new ToStringBuilder(this);
		tsb.append("MAC address", MacUtils.getHexString(macAddress));
		tsb.append("macVendor", macVendor);
		tsb.append("tcpPorts", tcpPorts);
		tsb.append("udpPorts", udpPorts);
		return tsb.build();
	}

	@Override
	public IndexedCache<Port> getTcpPorts()
	{
		return tcpPorts;
	}

	@Override
	public IndexedCache<Port> getUdpPorts()
	{
		return udpPorts;
	}

	public Host getHost()
	{
		return host;
	}

	public void setHost(Host host)
	{
		this.host = host;
	}

}
