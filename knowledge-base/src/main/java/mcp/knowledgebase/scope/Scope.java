package mcp.knowledgebase.scope;

import net.dacce.commons.general.CollectionUtils;
import net.dacce.commons.general.StringUtils;
import net.dacce.commons.netaddr.Addresses;
import net.dacce.commons.netaddr.InvalidIPAddressFormatException;
import net.dacce.commons.netaddr.SimpleInetAddress;


public class Scope
{
	private Addresses includeAddresses;
	private Addresses excludeAddresses;
	private boolean changed;
	private Addresses targetAddresses;

	public static final Scope instance = new Scope();


	private Scope()
	{
		includeAddresses = new Addresses();
		excludeAddresses = new Addresses();
		changed = false;
	}


	public boolean isInScope(SimpleInetAddress address)
	{
		synchronized (this)
		{
			return includeAddresses.contains(address) && !excludeAddresses.contains(address);
		}
	}


	public Addresses getTargetAddresses()
	{
		synchronized (this)
		{
			if ((targetAddresses == null) || changed)
			{
				targetAddresses = new Addresses();
				for (SimpleInetAddress address : includeAddresses.getAddresses())
				{
					if (!excludeAddresses.contains(address))
					{
						targetAddresses.add(address);
					}
				}
				changed = false;
			}

			return targetAddresses;
		}
	}


	public void addIncludeAddressBlock(String addressBlock) throws InvalidIPAddressFormatException
	{
		synchronized (this)
		{
			changed = true;
			includeAddresses.add(addressBlock);
		}
	}


	public void addExcludeAddressBlock(String addressBlock) throws InvalidIPAddressFormatException
	{
		synchronized (this)
		{
			changed = true;
			excludeAddresses.add(addressBlock);
		}
	}


	public void setIncludeAddresses(Addresses includeAddresses)
	{
		synchronized (this)
		{
			this.includeAddresses = includeAddresses;
			changed = true;
		}
	}


	public void setExcludeAddresses(Addresses excludeAddresses)
	{
		synchronized (this)
		{
			this.excludeAddresses = excludeAddresses;
			changed = true;
		}
	}


	@Override
	public String toString()
	{
		synchronized (this)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("Include:\n");
			sb.append(StringUtils.indentText(1, true, includeAddresses.toString()));
			sb.append("\n\nExclude:\n");
			if (excludeAddresses.isEmpty())
			{
				sb.append("\tnone");
			}
			else
			{
				sb.append(StringUtils.indentText(1, true, excludeAddresses.toString()));
			}

			sb.append("\n\nCalculated targets:\n");
			sb.append(StringUtils.indentText(1, true, CollectionUtils.joinObjects("\n", getTargetAddresses().getAddresses())));
			return sb.toString();
		}
	}
}
