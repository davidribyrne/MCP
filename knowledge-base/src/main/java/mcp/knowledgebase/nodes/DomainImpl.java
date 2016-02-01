package mcp.knowledgebase.nodes;

import java.lang.reflect.Field;
import net.dacce.commons.general.UnexpectedException;


public class DomainImpl extends NodeImpl implements Domain
{

	private final String name;
	public DomainImpl(String name)
	{
		super(null);
		this.name = name;
		signalCreation();
	}

	@Override
	public String toString()
	{
		return name;
	}


	@Override
	public int hashCode()
	{
		return name.hashCode();
	}


	@Override
	public boolean equals(Object obj)
	{
		return name.equals(obj);
	}


	private static Field nameField;

	private static void initializeFields()
	{
		try
		{
			nameField = HostImpl.class.getDeclaredField("name");
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
	 * @see mcp.knowledgebase.nodes.impl.Domain#getName()
	 */
	@Override
	public String getName()
	{
		return name;
	}
}
