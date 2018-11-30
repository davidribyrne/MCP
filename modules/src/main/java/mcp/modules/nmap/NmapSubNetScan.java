package mcp.modules.nmap;

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
	private int position;


	static
	{
		group = new OptionGroup("Subnet", "Nmap subnet scans");

		enableSubnetScans = new Option(null, "subnetScan", "Run subnet scans.");
		group.addChild(enableSubnetScans);

		subnetMask = new Option(null, "subnetMask", "Run nmap ICMP echo (ping) scan.", true, true, "24", "bits");
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
		position = 0;
		mask = Integer.valueOf(subnetMask.getValue());


		if (!enableSubnetScans.isEnabled())
		{
			return;
		}


		subnets = new HashMap<Integer, List<Integer>>();
		sortIntoSubnets();

		ExecutionScheduler.getInstance().registerListener(McpStartEvent.class, this);

	}


	private void sortIntoSubnets()
	{
		int[] allAddresses = Scope.instance.getTargetAddresses().getAllAddresses();
		int bitmask = 0xFFFFFFFF << mask;
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
	}


	private void pruneActiveSubnets()
	{
		Map<Integer, List<Integer>> tmpSubnets = Collections.unmodifiableMap(subnets);

		for (Integer subnet : tmpSubnets.keySet())
		{
			List<Integer> subnetAddresses = tmpSubnets.get(subnet);
			if (subnetAddresses.isEmpty())
			{
				subnets.remove(subnet);
			}
			else
			{
				for (Integer address : subnetAddresses)
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
	}


	private void runNextScan()
	{
		List<Integer> targets = new ArrayList<Integer>(subnets.size());
		pruneActiveSubnets();

		for (List<Integer> subnet : subnets.values())
		{
			targets.add(subnet.remove(0));
		}
		position++;

		SubnetScan pingScan = new SubnetScan("Subnet scan", intsToAddresses(targets));
		pingScan.setResolve(false);
		pingScan.addFlag(NmapFlag.MIN_PROBE_PARALLELIZATION, "1000");
		pingScan.addFlag(NmapFlag.MAX_RETRIES, "2");
		pingScan.addFlag(NmapFlag.PING_SCAN);
		pingScan.execute();
	}

	private Addresses intsToAddresses(Iterable<Integer> ints)
	{
		Addresses a = new Addresses();
		for (int i: ints)
		{
			try
			{
				a.add(IP4Utils.decimalToAddress(i));
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
