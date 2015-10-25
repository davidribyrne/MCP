package mcp.knowledgebase.nodes;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import mcp.knowledgebase.KnowledgeBase;
import net.dacce.commons.general.IndexedCache;
import net.dacce.commons.general.StringUtils;
import net.dacce.commons.general.UnexpectedException;
import net.dacce.commons.general.UniqueList;
import net.dacce.commons.netaddr.MacUtils;
import net.dacce.commons.netaddr.SimpleInetAddress;


public class Host extends Node
{
	final private IndexedCache<Port> tcpPorts;
	final private IndexedCache<Port> udpPorts;
	final private SimpleInetAddress address;
	private boolean up = false;
	final private UniqueList<OSGuess> osGuesses;
	private byte[] macAddress;
	private String macVendor;


	public Host(SimpleInetAddress address)
	{
		this.address = address;
		tcpPorts = new IndexedCache<Port>();
		udpPorts = new IndexedCache<Port>();
		osGuesses = new UniqueList<OSGuess>(1);
	}


	@SuppressWarnings("unchecked")
	public Map<Integer, Port> getTcpPorts()
	{
		return (Map<Integer, Port>) tcpPorts.getIndex(Port.NUMBER_FIELD());
	}


	@SuppressWarnings("unchecked")
	public Map<Integer, Port> getUdpPorts()
	{
		return (Map<Integer, Port>) udpPorts.getIndex(Port.NUMBER_FIELD());
	}


	public void addOSGuess(OSGuess guess)
	{
		osGuesses.add(guess);
	}


	public Collection<Hostname> getHostnames()
	{
		return KnowledgeBase.getInstance().getHostnames(address);
	}


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
		port = (Port) cache.getMember(Port.NUMBER_FIELD(), number);
		if (port == null)
		{
			port = new Port(type, number);
			cache.add(port);
		}
		return port;
	}


	public SimpleInetAddress getAddress()
	{
		return address;
	}

	private static Field addressField;


	private static void initializeFields()
	{
		try
		{
			addressField = Host.class.getDeclaredField("address");
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


	public boolean isUp()
	{
		return up;
	}


	public UniqueList<OSGuess> getOsGuesses()
	{
		return osGuesses;
	}


	public byte[] getMacAddress()
	{
		return macAddress;
	}


	public void setMacAddress(byte[] macAddress)
	{
		this.macAddress = macAddress;
	}


	public String getMacVendor()
	{
		return macVendor;
	}


	public void setMacVendor(String macVendor)
	{
		this.macVendor = macVendor;
	}


	public void setUp(boolean up)
	{
		this.up = up;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("IP address: ");
		sb.append(address.toString());
		sb.append("\nActive: ");
		sb.append(up ? "yes" : "no");
		sb.append("\nMAC address: ");
		sb.append(MacUtils.getHexString(macAddress));
		if (!StringUtils.isEmptyOrNull(macVendor))
			sb.append(" (" + macVendor + ")");
		if (!osGuesses.isEmpty())
		{
			sb.append("\nOS Guesses:\n");
			int i = 0;
			for(OSGuess guess: osGuesses)
			{
				sb.append("\tGuess #" + i + "\n");
				sb.append(StringUtils.indentText(1, true, guess.toString()));
				sb.append("\n");
			}
		}
		Collection<Hostname> names = getHostnames();
		if (!names.isEmpty())
		{
			sb.append("\nHostnames:\n");
			boolean first = true;
			for(Hostname hostname: getHostnames())
			{
				if(first)
				{
					first = false;
				}
				else
				{
					sb.append("\n");
				}
				sb.append("\t");
				sb.append(hostname.getName());
			}
		}
		sb.append("\nPort status:\n");
		sb.append(StringUtils.indentText(1, true, tcpPorts.toString()));
		sb.append(StringUtils.indentText(1, true, udpPorts.toString()));
		
		return sb.toString();
	}
	
	
}
