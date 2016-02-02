package mcp.knowledgebase.nodes;

import java.lang.reflect.Field;
import java.util.List;
import mcp.knowledgebase.attributes.ScoredAttributeHistory;
import mcp.knowledgebase.attributes.ScoredAttributeHistoryImpl;
import mcp.knowledgebase.attributes.host.OSGuess;
import net.dacce.commons.general.CollectionUtils;
import net.dacce.commons.general.StringUtils;
import net.dacce.commons.general.UnexpectedException;
import net.dacce.commons.general.UniqueList;


public class HostImpl extends NodeImpl implements Host
{
	final private List<Address> addresses;
	private boolean up = false;
	private final ScoredAttributeHistory<OSGuess> osGuess;

	public HostImpl(Address address)
	{
		super(null);
		addresses = new UniqueList<Address>(1);
		osGuess = new ScoredAttributeHistoryImpl<OSGuess>();
		addresses.add(address);
		signalCreation();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mcp.knowledgebase.nodes.impl.Host#addOSGuess(mcp.knowledgebase.nodes.attributes.host.OSGuess)
	 */
	@Override
	public void addOSGuess(OSGuess guess)
	{
		osGuess.addValue(guess);
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

	public static Field ADDRESSES_FIELD()
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
		StringBuilder sb = new StringBuilder();
		sb.append("IP address(es): ");
		sb.append(CollectionUtils.joinObjects(", ", addresses));
		sb.append("\nActive: ");
		sb.append(up ? "yes" : "no");
		if (!osGuess.isEmpty())
		{
			sb.append("\nOS Guesses:\n");
			int i = 0;
			for (OSGuess guess : osGuess)
			{
				sb.append("\tGuess #" + i + "\n");
				sb.append(StringUtils.indentText(1, true, guess.toString()));
				sb.append("\n");
			}
		}

		return sb.toString();
	}

	@Override
	public List<Address> getAddresses()
	{
		return addresses;
	}

}
