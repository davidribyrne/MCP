package mcp.modules.nmap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.events.events.McpStartEvent;
import mcp.events.listeners.McpStartListener;
import mcp.jobmanager.executors.ExecutionScheduler;
import mcp.jobmanager.jobs.JobState;
import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.KnowledgeBaseUtils;
import mcp.knowledgebase.Node;
import mcp.knowledgebase.Scope;
import mcp.knowledgebase.nodeLibrary.Icmp;
import mcp.knowledgebase.nodeLibrary.Network;
import mcp.knowledgebase.nodeLibrary.Port;
import mcp.modules.GeneralOptions;
import mcp.modules.InputFileMonitor;
import mcp.tools.nmap.NmapFlag;
import mcp.tools.nmap.NmapScan;
import space.dcce.commons.cli.Option;
import space.dcce.commons.cli.OptionGroup;
import space.dcce.commons.general.UnexpectedException;
import space.dcce.commons.netaddr.Addresses;
import space.dcce.commons.netaddr.IP4Utils;
import space.dcce.commons.netaddr.InvalidIPAddressFormatException;
import space.dcce.commons.netaddr.SimpleInetAddress;


public class NmapSubNetScan extends NmapModule implements McpStartListener
{
	private final static Logger logger = LoggerFactory.getLogger(NmapSubNetScan.class);

	static private OptionGroup group;
	static private Option subnetMask;
	static private Option enableSubnetScans;
	private int mask;
	private int position;


	static
	{
		group = new OptionGroup("Subnet", "Nmap subnet scans");

		enableSubnetScans = new Option(null, "subnetScan", "Run subnet scans.");
		group.addChild(enableSubnetScans);

		subnetMask = new Option(null, "subnetMask", "Run nmap ICMP echo (ping) scan.", true, true, "24", "bits");
		group.addChild(subnetMask);

	}

	private Map<SimpleInetAddress, Addresses> subnets;


	static public OptionGroup getOptions()
	{
		return group;
	}


	public NmapSubNetScan()
	{
		super("Nmap subnet scan");
	}


	@Override
	public void initialize()
	{
		position = 0;
		try
		{
			mask = Integer.valueOf(subnetMask.getValue());
		}
		catch (NumberFormatException e)
		{
			logger.error("Subnet mask value is not a number");
			throw new IllegalArgumentException("Subnet mask value is not a number", e);
		}

		if (mask < 0 || mask > 32)
		{
			logger.error("Subnet mask must be from 0-32 inclusive");
			throw new IllegalArgumentException("Subnet mask must be from 0-32 inclusive");
		}


		if (!enableSubnetScans.isEnabled())
		{
			return;
		}
		
		
		
		subnets = new HashMap<SimpleInetAddress, Addresses>();
		sortIntoSubnets();
		
		ExecutionScheduler.getInstance().registerListener(McpStartEvent.class, this);
		
	}

	private void sortIntoSubnets()
	{
		Addresses allAddresses = Scope.instance.getTargetAddresses();
		for (SimpleInetAddress address: allAddresses.getAddresses())
		{
			SimpleInetAddress subnet = address.getNetworkAddress(mask);
			if (!subnets.containsKey(subnet))
			{
				subnets.put(subnet, new Addresses(300));
			}
			Addresses subnetContents = subnets.get(subnet);
			subnetContents.add(address);
		}
		for (Addresses a: subnets.values())
		{
			a.trimToSize();
		}
	}
	
	private void pruneActiveSubnets()
	{
		Map<SimpleInetAddress, Addresses> tmpSubnets = Collections.unmodifiableMap(subnets); 
		
		for(SimpleInetAddress subnet: tmpSubnets.keySet())
		{
			Addresses subnetAddresses = tmpSubnets.get(subnet);
			for (SimpleInetAddress address: subnetAddresses.getAddresses())
			{
				Node addressNode = KnowledgeBase.instance.getNode(Network.IPV4_ADDRESS, address.toString());
				if (KnowledgeBaseUtils.IsAddressActive(addressNode))
				{
					subnets.remove(subnet);
					break;
				}
			}
		}
	}
	
	private void runNextScan()
	{
		Addresses targets = new Addresses(1000);
		pruneActiveSubnets();
		
		for(SimpleInetAddress subnet: subnets.keySet())
		{
			try
			{
				SimpleInetAddress target = subnet.addressAddition(position);
				if (Scope.instance.isInScope(target))
				{
					targets.add(target);
				}
			}
			catch (InvalidIPAddressFormatException e)
			{
				throw new UnexpectedException(e);
			}
		}
		targets.trimToSize();
		position++;
		
		NmapScan pingScan = new NmapScan("Subnet scan", targets);
		pingScan.setResolve(false);
		pingScan.addFlag(NmapFlag.MIN_PROBE_PARALLELIZATION, "1000");
		pingScan.addFlag(NmapFlag.MAX_RETRIES, "2");
		pingScan.addFlag(NmapFlag.PING_SCAN);
		pingScan.execute();
	}
	

	@Override
	public void handleEvent(McpStartEvent event)
	{
		runNextScan();
	}


	private class SubnetScan extends NmapScan
	{

		@Override
		public void jobComplete(JobState result)
		{
			super.jobComplete(result);
			
		}


		public SubnetScan(String jobName, Addresses targets)
		{
			super(jobName, targets);
		}

	}

}
