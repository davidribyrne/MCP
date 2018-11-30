package mcp.knowledgebase;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Range;

import space.dcce.commons.general.CollectionUtils;
import space.dcce.commons.general.NotImplementedException;
import space.dcce.commons.general.StringUtils;
import space.dcce.commons.netaddr.Addresses;
import space.dcce.commons.netaddr.IP4Range;
import space.dcce.commons.netaddr.InvalidIPAddressFormatException;
import space.dcce.commons.netaddr.SimpleInet4Address;
import space.dcce.commons.netaddr.SimpleInet6Address;
import space.dcce.commons.netaddr.SimpleInetAddress;


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
		if (address instanceof SimpleInet6Address)
		{
			throw new NotImplementedException();
		}
		SimpleInet4Address a = (SimpleInet4Address) address;
		synchronized (this)
		{
			return includeAddresses.contains(a) && !excludeAddresses.contains(a);
		}
	}
	


	/**
	 * 
	 * @return All in-scope addresses
	 */
	public Addresses getTargetAddresses()
	{
		synchronized (this)
		{
			
			if ((targetAddresses == null) || changed)
			{
				targetAddresses = new Addresses();
				
				List<IP4Range> tmpRanges = new ArrayList<IP4Range>(includeAddresses.getBlocks());
				
				while (!tmpRanges.isEmpty())
				{
					IP4Range range = tmpRanges.remove(0);
					boolean include = true;
					for (IP4Range exBlock: excludeAddresses.getBlocks())
					{
						
						int exStart = exBlock.getStart();
						int exEnd = exBlock.getEnd();
						
						if (range.contains(exStart - 1))
						{
							tmpRanges.add(new IP4Range(range.getStart(), exStart - 1));
							if (range.contains(exEnd + 1))
							{
								tmpRanges.add(new IP4Range(exEnd + 1, range.getEnd()));
							}
							include = false;
						}
						else if (range.contains(exEnd + 1))
						{
							tmpRanges.add(new IP4Range(exEnd + 1, range.getEnd()));
							include = false;
						}
						else if (range.getStart() >=  exStart && range.getEnd() <= exEnd )
						{
							// The entire include block is excluded
							include = false;
							break;
						}
					}
					if (include)
					{
						targetAddresses.add(range);
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

			return sb.toString();
		}
	}
}