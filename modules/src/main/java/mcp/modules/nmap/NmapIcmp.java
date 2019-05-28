package mcp.modules.nmap;

import mcp.events.events.McpStartEvent;
import mcp.events.listeners.McpStartListener;
import mcp.jobmanager.executors.ExecutionScheduler;
import mcp.knowledgebase.Scope;
import mcp.modules.Modules;
import mcp.tools.nmap.NmapFlag;
import mcp.tools.nmap.NmapScan;
import space.dcce.commons.cli.Option;
import space.dcce.commons.cli.OptionGroup;


public class NmapIcmp extends NmapModule implements McpStartListener
{

	private OptionGroup group;
	private Option icmpEchoScan;
	private Option icmpMaskScan;
	private Option icmpTimeScan;
	private Option protocolScan;


	@Override
	protected void initializeOptions()
	{
		group = ((NmapGeneralOptions) Modules.instance.getModuleInstance(NmapGeneralOptions.class))
				.getOptions().addOptionGroup("ICMP", "Nmap ICMP scans");

		icmpEchoScan = group.addOption(null, "icmpEchoScan", "Run nmap ICMP echo (ping) scan.");
		icmpMaskScan = group.addOption(null, "icmpMaskScan", "Run nmap ICMP netmask scan.");
		icmpTimeScan = group.addOption(null, "icmpTimeScan", "Run nmap ICMP timestamp scan.");
		protocolScan = group.addOption(null, "protocolScan", "Run nmap IP protocol scans.");
	}
	
	static public OptionGroup getOptions()
	{
		return null;
	}

	public NmapIcmp()
	{
		super("Nmap ICMP");
	}
	


	@Override
	public void initialize()
	{
		ExecutionScheduler.getInstance().registerListener(McpStartEvent.class, this);
		
		if (Modules.instance.getGeneralOptions().getBasicReconOption().isEnabled())
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




}
