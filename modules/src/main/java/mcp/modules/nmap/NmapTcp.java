package mcp.modules.nmap;

import mcp.modules.GeneralOptions;
import mcp.tools.nmap.NmapFlag;



public class NmapTcp extends NmapPortScan
{
	private static final NmapTcp instance = new NmapTcp();

	public static NmapTcp getInstance()
	{
		return instance;
	}
	
	private NmapTcp()
	{
		super("tcp");
	}

	@Override
	public void initialize()
	{
		super.initialize();
		if (GeneralOptions.getInstance().getBasicReconOption().isEnabled())
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
}
