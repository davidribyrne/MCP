package mcp.modules.nmap;

import mcp.events.events.McpStartEvent;
import mcp.events.listeners.McpStartListener;
import mcp.jobmanager.executors.ExecutionScheduler;
import mcp.knowledgebase.scope.Scope;
import mcp.modules.GeneralOptions;
import mcp.modules.Module;
import mcp.tools.nmap.NmapFlag;
import mcp.tools.nmap.NmapScan;
import net.dacce.commons.cli.Option;
import net.dacce.commons.cli.OptionContainer;
import net.dacce.commons.cli.OptionGroup;


public class NmapIcmp extends NmapModule implements McpStartListener
{

	private OptionGroup group;
	private Option icmpEchoScan;
	private Option icmpMaskScan;
	private Option icmpTimeScan;
	private Option protocolScan;


	public NmapIcmp()
	{
		super("Nmap ICMP");
		icmpEchoScan = new Option(null, "icmpEchoScan", "Run nmap ICMP echo (ping) scan.");
		icmpMaskScan = new Option(null, "icmpMaskScan", "Run nmap ICMP netmask scan.");
		icmpTimeScan = new Option(null, "icmpTimeScan", "Run nmap ICMP timestamp scan.");
		protocolScan = new Option(null, "protocolScan", "Run nmap IP protocol scans.");
		
		group = new OptionGroup("ICMP", "Nmap ICMP scans");
		group.addChild(icmpEchoScan);
		group.addChild(icmpMaskScan);
		group.addChild(icmpTimeScan);
		group.addChild(protocolScan);
	}
	


	@Override
	public void initialize()
	{
		ExecutionScheduler.getInstance().registerListener(McpStartEvent.class, this);
		
		if (GeneralOptions.getInstance().getBasicReconOption().isEnabled())
		{
			icmpEchoScan.forceEnabled();
		}

	}


	@Override
	public void handleEvent(McpStartEvent event)
	{
		if (icmpEchoScan.isEnabled())
		{
			NmapScan echoScan = new NmapScan("ICMP Echo", Scope.instance.getTargetAddresses());
			echoScan.setResolve(false);
			echoScan.addFlag(NmapFlag.ICMP_ECHO_DISCOVERY);
			echoScan.addFlag(NmapFlag.PING_SCAN);
			echoScan.execute();
		}

		if (icmpMaskScan.isEnabled())
		{
			NmapScan echoScan = new NmapScan("ICMP Netmask", Scope.instance.getTargetAddresses());
			echoScan.setResolve(false);
			echoScan.addFlag(NmapFlag.NETMASK_REQUEST_DISCOVERY);
			echoScan.addFlag(NmapFlag.PING_SCAN);
			echoScan.execute();
		}

		if (icmpTimeScan.isEnabled())
		{
			NmapScan echoScan = new NmapScan("ICMP Timestamp", Scope.instance.getTargetAddresses());
			echoScan.setResolve(false);
			echoScan.addFlag(NmapFlag.TIMESTAMP_DISCOVERY);
			echoScan.addFlag(NmapFlag.PING_SCAN);
			echoScan.execute();
		}

	}



	@Override
	protected OptionGroup getOptionGroup()
	{
		return group;
	}




}
