package mcp.knowledgebase.nodes;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import mcp.knowledgebase.attributes.host.IcmpResponseType;
import net.dacce.commons.general.IndexedCache;
import net.dacce.commons.general.UnexpectedException;
import net.dacce.commons.netaddr.SimpleInetAddress;

@Entity
public class IPAddressImpl extends NodeImpl implements IPAddress
{
	private final SimpleInetAddress address;
	private IndexedCache<Port> tcpPorts;
	private IndexedCache<Port> udpPorts;
	private Host host;
	private MacAddress macAddress;
	private IcmpResponseType icmpResponse;
	
	/**
	 * ONLY THE KB SHOULD CALL THIS
	 * @param parent
	 * @param address
	 */
	public IPAddressImpl(SimpleInetAddress address)
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
			addressField = IPAddressImpl.class.getDeclaredField("address");
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

	public synchronized static Field ADDRESS_FIELD()
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
		port = cache.getMember(PortImpl.NUMBER_FIELD(), number);
		if (port == null)
		{
			port = new PortImpl(this, type, number);
			cache.add(port);
		}
		return port;
	}
	

	@Override
	public SimpleInetAddress getAddress()
	{
		return address;
	}


	@Override
	public String toString()
	{
//		CustomToStringStyle style = new CustomToStringStyle();
//		style.setContentStart("[");
//		style.setFieldSeparator(SystemUtils.LINE_SEPARATOR + "  ");
//		style.setFieldSeparatorAtStart(true);
//		style.setContentEnd(SystemUtils.LINE_SEPARATOR + "]");
//		style.setUseClassName(false);
//		style.setUseIdentityHashCode(false);
		
		ToStringBuilder tsb = new ToStringBuilder(this, ToStringStyle.JSON_STYLE);
		tsb.append("IP Address", address);
		tsb.append("MAC address", macAddress);
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

	@Override
	public MacAddress getMacAddress()
	{
		return macAddress;
	}

	@Override
	public void setMacAddress(MacAddress macAddress)
	{
		this.macAddress = macAddress;
	}

	@Override
	public void setIcmpResponse(IcmpResponseType response)
	{
		this.icmpResponse = response;
	}

	@Override
	public IcmpResponseType getIcmpResponse()
	{
		return icmpResponse;
	}

}
