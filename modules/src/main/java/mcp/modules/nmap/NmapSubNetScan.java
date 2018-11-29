package mcp.modules.nmap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.events.events.McpStartEvent;
import mcp.events.listeners.McpStartListener;
import mcp.jobmanager.executors.ExecutionScheduler;
import mcp.jobmanager.jobs.JobState;
import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.Scope;
import mcp.modules.GeneralOptions;
import mcp.modules.InputFileMonitor;
import mcp.tools.nmap.NmapFlag;
import mcp.tools.nmap.NmapScan;
import space.dcce.commons.cli.Option;
import space.dcce.commons.cli.OptionGroup;
import space.dcce.commons.netaddr.Addresses;


public class NmapSubNetScan extends NmapModule implements McpStartListener
{
	private final static Logger logger = LoggerFactory.getLogger(NmapSubNetScan.class);

	static private OptionGroup group;
	static private Option subnetMask;
	static private Option enableSubnetScans;


	static
	{
		group = new OptionGroup("Subnet", "Nmap subnet scans");

		enableSubnetScans = new Option(null, "subnetScan", "Run subnet scans.");
		group.addChild(enableSubnetScans);

		subnetMask = new Option(null, "subnetMask", "Run nmap ICMP echo (ping) scan.", true, true, "24", "bits");
		group.addChild(subnetMask);

	}

	private  

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
		int mask;
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
		
				
		if (enableSubnetScans.isEnabled())
		{
			
			ExecutionScheduler.getInstance().registerListener(McpStartEvent.class, this);
		}
	}


	@Override
	public void handleEvent(McpStartEvent event)
	{
		
		NmapScan echoScan = new NmapScan("ICMP Echo", Scope.instance.getTargetAddresses());
		echoScan.setResolve(false);
		echoScan.addFlag(NmapFlag.ICMP_ECHO_DISCOVERY);
		echoScan.addFlag(NmapFlag.PING_SCAN);
		echoScan.execute();

		Addresses all = Scope.instance.getTargetAddresses();
		
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
