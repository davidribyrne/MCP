package mcp.modules.nmap;

import mcp.modules.Modules;
import mcp.tools.nmap.NmapFlag;
import space.dcce.commons.cli.Option;
import space.dcce.commons.cli.OptionGroup;


public class NmapUdp extends NmapPortScan
{
	private final static String PROTOCOL = "UDP";

	static public OptionGroup getOptions()
	{
		return null;
	}


	public NmapUdp()
	{
		super(PROTOCOL);
	}


	@Override
	public void initialize()
	{
		super.initialize();
		if (Modules.instance.getGeneralOptions().getBasicReconOption().isEnabled())
		{
			ports.setDefaultValue("53,67,68,69,111,123,135,137,138,139,161,162,445,500,514,520,631,1434,1604,4500,5353,10000");
		}
	}


	@Override
	protected NmapFlag getProtocolFlag()
	{
		return NmapFlag.UDP_SCAN;
	}


	protected String getDefaultTopPorts()
	{
		return "5";
	}


	protected String getDefaultTopFocusedPorts()
	{
		return "15";
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
