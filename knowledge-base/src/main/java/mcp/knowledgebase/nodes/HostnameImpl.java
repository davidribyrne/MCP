package mcp.knowledgebase.nodes;

import java.lang.reflect.Field;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import net.dacce.commons.general.UnexpectedException;
import net.dacce.commons.general.UniqueList;


public class HostnameImpl extends NodeImpl implements Hostname
{
	private final String name;
	private final UniqueList<Address> addresses;


	public HostnameImpl(String name)
	{
		super(null);
		this.name = name;
		addresses = new UniqueList<Address>(1);
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Hostname#addAddress(net.dacce.commons.netaddr.SimpleInetAddress)
	 */
	@Override
	public void addAddress(Address address)
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
	
	@Override
	public String toString()
	{
		return new ToStringBuilder(this).appendSuper(super.toString()).append("name", name)
				.append("addresses", addresses).build();
	}


	@Override
	public List<Address> getAddresses()
	{
		return addresses;
	}
}