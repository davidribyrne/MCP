package mcp.modules.nmap;

import java.time.Instant;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
import mcp.knowledgebase.nodeLibrary.HostStatusReason;
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
import space.dcce.commons.netaddr.SimpleInet4Address;
import space.dcce.commons.netaddr.SimpleInetAddress;
import space.dcce.commons.validators.NumericValidator;


public class NmapSubNetScan extends NmapModule implements McpStartListener
{
	private final static Logger logger = LoggerFactory.getLogger(NmapSubNetScan.class);

	static private OptionGroup group;
	static private Option subnetMask;
	static private Option enableSubnetScans;
	private int mask;
	int count = 0;

	static
	{
		group = new OptionGroup("Subnets", "Nmap subnet scans. If one active device is discovered in a subnet, the rest of the subnet is scanned.");

		enableSubnetScans = new Option(null, "subnetScan", "Run subnet scans.");
		group.addChild(enableSubnetScans);

		subnetMask = new Option(null, "subnetMask", "Assumed subnet mask.", true, true, "24", "bits");
		subnetMask.addValidator(new NumericValidator(false, 1, 32));
		group.addChild(subnetMask);

	}

	private Map<Integer, List<Integer>> subnets;


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
		mask = Integer.valueOf(subnetMask.getValue());
		ExecutionScheduler.getInstance().registerListener(McpStartEvent.class, this);
	}


	private void sortIntoSubnets()
	{
		int[] allAddresses = Scope.instance.getTargetAddresses().getAllAddresses();
		logger.trace("Sorting into subnets. This may take a while for large ranges");
		int bitmask = 0xFFFFFFFF << (32 - mask);
		for (int address : allAddresses)
		{
			int subnet = bitmask & address;

			if (!subnets.containsKey(subnet))
			{
				subnets.put(subnet, new LinkedList<Integer>());
			}
			List<Integer> subnetContents = subnets.get(subnet);
			subnetContents.add(address);
		}
		logger.trace("Done sorting into subnets.");
	}


	private void pruneActiveSubnets()
	{
		logger.trace("Starting to prune active subnets");

		for (Iterator<Integer> iterator = subnets.keySet().iterator(); iterator.hasNext();)
		{
			Integer subnet = iterator.next();
			List<Integer> subnetAddresses = subnets.get(subnet);

			int lastIP = subnetAddresses.remove(0);
			Node addressNode = KnowledgeBase.instance.getNode(Network.IPV4_ADDRESS, IP4Utils.intToString(lastIP));
			if (subnetAddresses.isEmpty() || (addressNode != null && KnowledgeBaseUtils.IsAddressActive(addressNode)))
			{
				iterator.remove();
			}
		}
		logger.trace("Finished pruning active subnets");

	}


	private void runNextScan()
	{
		logger.trace("Starting subnet scan #" + ++count);
		List<Integer> targets = new ArrayList<Integer>(subnets.size());
		if (count > 1)
		{
			pruneActiveSubnets();
		}

		for (List<Integer> subnet : subnets.values())
		{
			targets.add(subnet.get(0));
		}

		SubnetScan pingScan = new SubnetScan("Subnet scan " + count, intsToAddresses(targets));
		pingScan.setResolve(false);
		pingScan.addFlag(NmapFlag.MIN_PROBE_PARALLELIZATION, "1000");
		pingScan.addFlag(NmapFlag.MAX_RETRIES, "2");
		pingScan.addFlag(NmapFlag.PING_SCAN);

		pingScan.execute();
	}


	private Addresses intsToAddresses(Iterable<Integer> ints)
	{
		Addresses a = new Addresses();
		for (int i : ints)
		{
			try
			{
				a.add(IP4Utils.intToAddress(i));
			}
			catch (InvalidIPAddressFormatException e)
			{
				throw new UnexpectedException("An invalid IP address shouldn't be possible here.", e);
			}
		}
		return a;
	}


	@Override
	public void handleEvent(McpStartEvent event)
	{
		if (enableSubnetScans.isEnabled())
		{
			subnets = new HashMap<Integer, List<Integer>>();
			sortIntoSubnets();
			runNextScan();
		}
	}


	private class SubnetScan extends NmapScan
	{

		@Override
		public void jobComplete(JobState result)
		{
			super.jobComplete(result);
			Runnable r = new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						runNextScan();
					}
					catch (Throwable t)
					{
						logger.error("Throwable caught in NmapSubNetScan: " + t.getLocalizedMessage(), t);
					}

				}
			};
			ExecutionScheduler.getInstance().executeImmediately(r);
		}


		public SubnetScan(String jobName, Addresses targets)
		{
			super(jobName, targets);
		}

	}

}
