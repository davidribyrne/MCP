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

	
	protected NmapPortScan(String protocol)
	{
		super(protocol);
	}
	

	@Override
	public void initialize()
	{
		ExecutionScheduler.getInstance().registerListener(McpStartEvent.class, this);
	}

	protected abstract NmapFlag getProtocolFlag();
	protected abstract Option getPorts();
	protected abstract Option getTopScan();
	protected abstract Option getTopPorts();
	protected abstract String getAllCapsProtocol();

	@Override
	public void handleEvent(McpStartEvent event)
	{
		if (getPorts().isValueSet(true))
		{
			NmapScan definedPortsScanJob = new NmapScan("Defined " + getAllCapsProtocol() + " ports", Scope.instance.getTargetAddresses());
			definedPortsScanJob.setResolve(false);
			definedPortsScanJob.addFlag(NmapFlag.NO_PING);
			definedPortsScanJob.addFlag(getProtocolFlag());
			definedPortsScanJob.addFlag(NmapFlag.PORT_SPEC, getPorts().getValue());
			definedPortsScanJob.execute();
		}
		if (getTopScan().isEnabled())
		{
			NmapScan topScanJob = new NmapScan("Top " + getAllCapsProtocol() + " ports", Scope.instance.getTargetAddresses());
			topScanJob.setResolve(false);
			topScanJob.addFlag(NmapFlag.NO_PING);
			topScanJob.addFlag(getProtocolFlag());
			topScanJob.addFlag(NmapFlag.TOP_PORTS, getTopPorts().getValue());
			topScanJob.execute();
		}
	}
}
