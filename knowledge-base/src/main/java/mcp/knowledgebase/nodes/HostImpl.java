package mcp.knowledgebase.nodes;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.attributes.ScoredAttributeHistory;
import mcp.knowledgebase.attributes.ScoredAttributeHistoryImpl;
import mcp.knowledgebase.attributes.host.OSGuess;
import mcp.knowledgebase.attributes.host.OSGuessImpl;
import mcp.knowledgebase.nodes.Host;
import mcp.knowledgebase.nodes.Hostname;
import mcp.knowledgebase.nodes.Port;
import mcp.knowledgebase.nodes.PortType;
import net.dacce.commons.general.IndexedCache;
import net.dacce.commons.general.StringUtils;
import net.dacce.commons.general.UnexpectedException;
import net.dacce.commons.general.UniqueList;
import net.dacce.commons.netaddr.MacUtils;
import net.dacce.commons.netaddr.SimpleInetAddress;


public class HostImpl extends NodeImpl implements Host
{
	final private IndexedCache<Port> tcpPorts;
	final private IndexedCache<Port> udpPorts;
	final private SimpleInetAddress address;
	private boolean up = false;
	private final ScoredAttributeHistory<OSGuess> osGuess;
	private byte[] macAddress;
	private String macVendor;


	public HostImpl(SimpleInetAddress address)
	{
		this.address = address;
		tcpPorts = new IndexedCache<Port>();
		udpPorts = new IndexedCache<Port>();
		osGuess = new ScoredAttributeHistoryImpl<OSGuess>();
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Host#getTcpPorts()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<Integer, Port> getTcpPorts()
	{
		return (Map<Integer, Port>) tcpPorts.getIndex(PortImpl.NUMBER_FIELD());
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Host#getUdpPorts()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<Integer, Port> getUdpPorts()
	{
		return (Map<Integer, Port>) udpPorts.getIndex(PortImpl.NUMBER_FIELD());
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Host#addOSGuess(mcp.knowledgebase.nodes.attributes.host.OSGuess)
	 */
	@Override
	public void addOSGuess(OSGuess guess)
	{
		osGuess.addValue(guess);
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Host#getHostnames()
	 */
	@Override
	public Collection<Hostname> getHostnames()
	{
		return KnowledgeBase.getInstance().getHostnames(address);
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Host#getOrCreatePort(mcp.knowledgebase.nodes.impl.PortType, int)
	 */
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


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Host#getAddress()
	 */
	@Override
	public SimpleInetAddress getAddress()
	{
		return address;
	}

	private static Field addressField;


	private static void initializeFields()
	{
		try
		{
			addressField = HostImpl.class.getDeclaredField("address");
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


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Host#isUp()
	 */
	@Override
	public boolean isUp()
	{
		return up;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Host#getMacAddress()
	 */
	@Override
	public byte[] getMacAddress()
	{
		return macAddress;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Host#setMacAddress(byte[])
	 */
	@Override
	public void setMacAddress(byte[] macAddress)
	{
		this.macAddress = macAddress;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Host#getMacVendor()
	 */
	@Override
	public String getMacVendor()
	{
		return macVendor;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Host#setMacVendor(java.lang.String)
	 */
	@Override
	public void setMacVendor(String macVendor)
	{
		this.macVendor = macVendor;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Host#setUp(boolean)
	 */
	@Override
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
		if (!osGuess.isEmpty())
		{
			sb.append("\nOS Guesses:\n");
			int i = 0;
			for(OSGuess guess: osGuess)
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
