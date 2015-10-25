package mcp.knowledgebase.nodes;

import java.lang.reflect.Field;

import net.dacce.commons.general.CollectionUtils;
import net.dacce.commons.general.UnexpectedException;
import net.dacce.commons.general.UniqueList;
import net.dacce.commons.netaddr.SimpleInetAddress;


public class Hostname extends Node
{
	private final String name;
	private final UniqueList<SimpleInetAddress> addresses;


	public Hostname(String name)
	{
		this.name = name;
		addresses = new UniqueList<SimpleInetAddress>(1);
	}


	public void addAddress(SimpleInetAddress address)
	{
		addresses.add(address);
	}

	private static Field nameField;


	private static void initializeFields()
	{
		try
		{
			nameField = Hostname.class.getDeclaredField("name");
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


	public String getName()
	{
		return name;
	}
	
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
