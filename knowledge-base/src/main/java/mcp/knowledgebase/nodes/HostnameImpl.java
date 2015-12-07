package mcp.knowledgebase.nodes;

import java.lang.reflect.Field;

import mcp.knowledgebase.nodes.Hostname;
import net.dacce.commons.general.CollectionUtils;
import net.dacce.commons.general.UnexpectedException;
import net.dacce.commons.general.UniqueList;
import net.dacce.commons.netaddr.SimpleInetAddress;


public class HostnameImpl extends NodeImpl implements Hostname
{
	private final String name;
	private final UniqueList<SimpleInetAddress> addresses;


	public HostnameImpl(String name)
	{
		this.name = name;
		addresses = new UniqueList<SimpleInetAddress>(1);
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Hostname#addAddress(net.dacce.commons.netaddr.SimpleInetAddress)
	 */
	@Override
	public void addAddress(SimpleInetAddress address)
	{
		addresses.add(address);
	}

	private static Field nameField;


	private static void initializeFields()
	{
		try
		{
			nameField = HostnameImpl.class.getDeclaredField("name");
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


	public static Field NAME_FIELD()
	{
		if (nameField == null)
		{
			initializeFields();
		}
		return nameField;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Hostname#getName()
	 */
	@Override
	public String getName()
	{
		return name;
	}
	
	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Hostname#containsAddress(net.dacce.commons.netaddr.SimpleInetAddress)
	 */
	@Override
	public boolean containsAddress(SimpleInetAddress address)
	{
		return addresses.contains(address);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(" (");
		sb.append(CollectionUtils.joinObjects(", ", addresses));
		sb.append(")");
		
		return sb.toString();
	}
}
