package mcp.modules.nmap;

import mcp.events.events.McpStartEvent;
import mcp.events.listeners.McpStartListener;
import mcp.jobmanager.executors.ExecutionScheduler;
import mcp.knowledgebase.Scope;
import mcp.tools.nmap.NmapFlag;
import mcp.tools.nmap.NmapScan;
import space.dcce.commons.cli.Option;
import space.dcce.commons.cli.OptionGroup;
import space.dcce.commons.validators.NumericListValidator;
import space.dcce.commons.validators.NumericValidator;


public abstract class NmapPortScan extends NmapModule implements McpStartListener
{
	private OptionGroup group;

	protected Option topScan;
	protected Option topPorts;
	protected Option topFocusedPorts;
	protected Option topFocusedScan;
	protected Option ports;
	private String titleProcotol;
	private String allCapsProtocol;
	private String allLowerProtocol;
	
	protected NmapPortScan(String protocol)
	{
		super(protocol);
		allLowerProtocol = protocol.toLowerCase();
		allCapsProtocol = protocol.toUpperCase();
		titleProcotol = allCapsProtocol.substring(0, 1) + allLowerProtocol.substring(1);
		
		topScan = new Option(null, "top" + titleProcotol + "Scan", "Scan all targets for common " + allCapsProtocol + " ports.");
		topPorts = new Option(null, "top" + titleProcotol + "Ports", "The top <n> " + allCapsProtocol + " ports to scan with nmap.", true, true, getDefaultTopPorts(), "n");
		topFocusedPorts = new Option(null, "topFocused" + titleProcotol + "Ports",
				"The top <n> " + allCapsProtocol + " ports to scan with nmap on hosts known to be up.", true, true, getDefaultTopFocusedPorts(), "n");
		topFocusedScan = new Option(null, "topFocused" + titleProcotol + "Scan",
				"Scan for the top " + allCapsProtocol + " ports on hosts known to be up.");
		ports = new Option(null, allLowerProtocol + "Ports",
				"Scan the specified " + allCapsProtocol + " ports (comma seperated) of all targets. This is useful if you already know which ports are open or are only looking for specific services.",
				true, true, null, "ports");

		topPorts.addValidator(new NumericValidator(false, 1, 65536));
		topFocusedPorts.addValidator(new NumericValidator(false, 1, 65536));
		ports.addValidator(new NumericListValidator(",", false, 0, 65535));

		group = new OptionGroup(allCapsProtocol, "Nmap " + allCapsProtocol + " scan options");
		group.addChild(ports);
		group.addChild(topScan);
		group.addChild(topPorts);
		group.addChild(topFocusedPorts);
		group.addChild(topFocusedScan);
		
		NmapGeneralOptions.getInstance().getOptions().addChild(group);
	}

	protected abstract String getDefaultTopPorts();
	protected abstract String getDefaultTopFocusedPorts();

	@Override
	public void initialize()
	{
		ExecutionScheduler.getInstance().registerListener(McpStartEvent.class, this);
	}

	protected abstract NmapFlag getProtocolFlag();

	@Override
	public void handleEvent(McpStartEvent event)
	{
		if (ports.isValueSet(true))
		{
			NmapScan definedPortsScanJob = new NmapScan("Defined " + allCapsProtocol + " ports", Scope.instance.getTargetAddresses());
			definedPortsScanJob.setResolve(false);
			definedPortsScanJob.addFlag(NmapFlag.NO_PING);
			definedPortsScanJob.addFlag(getProtocolFlag());
			definedPortsScanJob.addFlag(NmapFlag.PORT_SPEC, ports.getValue());
			definedPortsScanJob.execute();
		}
		if (topScan.isEnabled())
		{
			NmapScan topScanJob = new NmapScan("Top " + allCapsProtocol + " ports", Scope.instance.getTargetAddresses());
			topScanJob.setResolve(false);
			topScanJob.addFlag(NmapFlag.NO_PING);
			topScanJob.addFlag(getProtocolFlag());
			topScanJob.addFlag(NmapFlag.TOP_PORTS, topPorts.getValue());
			topScanJob.execute();
		}
	}


	@Override
	protected OptionGroup getOptionGroup()
	{
		return group;
	}


}
