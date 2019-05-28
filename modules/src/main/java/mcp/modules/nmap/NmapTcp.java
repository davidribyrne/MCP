package mcp.modules.nmap;

import mcp.modules.Modules;
import mcp.tools.nmap.NmapFlag;
import space.dcce.commons.cli.Option;



public class NmapTcp extends NmapPortScan
{
	private final static String PROTOCOL = "TCP";
	
	public NmapTcp()
	{
		super(PROTOCOL);
	}

	
	@Override
	public void initialize()
	{
		super.initialize();
		if (Modules.instance.getGeneralOptions().getBasicReconOption().isEnabled())
		{
			topScan.forceEnabled();
		}
	}

	@Override
	protected NmapFlag getProtocolFlag()
	{
		return NmapFlag.TCP_SYN_SCAN;
	}

	@Override
	protected String getDefaultTopPorts()
	{
		return "1000";
	}

	@Override
	protected String getDefaultTopFocusedPorts()
	{
		return "65536";
	}

	@Override
	protected Option getPorts()
	{
		return ports;
	}

	@Override
	protected Option getTopScan()
	{
		return topScan;
	}

	@Override
	protected Option getTopPorts()
	{
		return topPorts;
	}


	@Override
	protected String getProtocol()
	{
		return PROTOCOL;
	}

}
