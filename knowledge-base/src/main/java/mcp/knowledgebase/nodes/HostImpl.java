package mcp.knowledgebase.nodes;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import mcp.knowledgebase.attributes.host.OSGuess;
import net.dacce.commons.general.UnexpectedException;
import net.dacce.commons.general.UniqueList;

@Entity
public class HostImpl extends NodeImpl implements Host
{
    @ElementCollection(targetClass=String.class)
	private List<IPAddress> addresses;

    private boolean up = false;
	private OSGuess osGuess;

	public HostImpl(IPAddress iPAddress)
	{
		super(null);
		addresses = new UniqueList<IPAddress>(1);
		addresses.add(iPAddress);
	}

	private HostImpl()
	{
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see mcp.knowledgebase.nodes.impl.Host#addOSGuess(mcp.knowledgebase.nodes.attributes.host.OSGuess)
	 */
	@Override
	public void setOSGuess(OSGuess guess)
	{
		osGuess = guess;
	}

	private static Field addressesField;

	private static void initializeFields()
	{
		try
		{
			addressesField = HostImpl.class.getDeclaredField("addresses");
			addressesField.setAccessible(true);
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

	public synchronized static Field ADDRESSES_FIELD()
	{
		if (addressesField == null)
		{
			initializeFields();
		}
		return addressesField;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mcp.knowledgebase.nodes.impl.Host#isUp()
	 */
	@Override
	public boolean isUp()
	{
		return up;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).append("addresses", addresses)
				.append("active", up).append("osGuesses", osGuess).build();
	}

	@Override
	public List<IPAddress> getAddresses()
	{
		return addresses;
	}

}
