package mcp.modules.nmap;

import mcp.modules.GeneralOptions;
import mcp.tools.nmap.NmapFlag;


public class NmapUdp extends NmapPortScan
{
	private static final NmapUdp instance = new NmapUdp();

	public static NmapUdp getInstance()
	{
		return instance;
	}


	private NmapUdp()
	{
		super("udp");
	}

	@Override
	public void initialize()
	{
		super.initialize();
		if (GeneralOptions.getInstance().getBasicReconOption().isEnabled())
		{
			ports.setDefaultValue("53,67,68,69,111,123,135,137,138,139,161,162,445,500,514,520,631,1434,1604,4500,5353,10000");
		}
	}


	@Override
	protected NmapFlag getProtocolFlag()
	{
		return NmapFlag.UDP_SCAN;
	}


	@Override
	protected String getDefaultTopPorts()
	{
		return "5";
	}


	@Override
	protected String getDefaultTopFocusedPorts()
	{
		return "15";
	}
}
