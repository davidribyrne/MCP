package mcp.knowledgebase;

import space.dcce.commons.general.NotImplementedException;
import space.dcce.commons.general.Range;
import space.dcce.commons.general.StringUtils;
import space.dcce.commons.netaddr.Addresses;
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
	private Addresses unroutableAddresses;
	private Addresses routingTable;

	public static final Scope instance = new Scope();


	private Scope()
	{
		includeAddresses = new Addresses("Included addresses");
		excludeAddresses = new Addresses("Excluded addresses");
		routingTable = new Addresses("Routing table");

		// We'll assume everything is routable unless a table is provided
		routingTable.add(new Range(0, 0xFFFFFFFF));
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
			return includeAddresses.contains(a) && !excludeAddresses.contains(a) && routingTable.contains(a);
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
			if (changed)
			{
				Addresses beforeRouting = includeAddresses.difference(excludeAddresses);
				targetAddresses = beforeRouting.intersection(routingTable);
				unroutableAddresses = beforeRouting.difference(targetAddresses);
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


	public void setRoutingTable(Addresses routingTable)
	{
		synchronized (this)
		{
			changed = true;
			this.routingTable = routingTable;
		}
	}


	@Override
	public String toString()
	{
		synchronized (this)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("MCP Scope:\n\tInclude:\n");
			sb.append(StringUtils.indentText(2, true, includeAddresses.toString()));

			sb.append("\n\n\tExclude:\n");
			if (excludeAddresses.isEmpty())
			{
				sb.append("\t\tnone");
			}
			else
			{
				sb.append(StringUtils.indentText(2, true, excludeAddresses.toString()));
			}

//			sb.append("\n\n\tRoutable addresses:\n");
//			sb.append(StringUtils.indentText(2, true, routingTable.toString()));

			sb.append("\n\n\tTargeted addresses:\n");
			sb.append(StringUtils.indentText(2, true, "ranges: " + getTargetAddresses().toString(false)));
			sb.append("\n");
			sb.append(StringUtils.indentText(2, true, "cidrs: " + getTargetAddresses().getCIDRList(", ")));
			

			sb.append("\n\n\tUnroutable addresses:\n");
			sb.append(StringUtils.indentText(2, true, unroutableAddresses.toString(false)));

			
			return sb.toString();
		}
	}


}
