package mcp.modules.nmap;

import mcp.modules.GeneralOptions;
import mcp.tools.nmap.NmapFlag;
import space.dcce.commons.cli.Option;
import space.dcce.commons.cli.OptionGroup;
import space.dcce.commons.validators.NumericListValidator;
import space.dcce.commons.validators.NumericValidator;



public class NmapTcp extends NmapPortScan
{
	static private OptionGroup group;
	static protected Option topScan;
	static protected Option topPorts;
	static protected Option topFocusedPorts;
	static protected Option topFocusedScan;
	static protected Option ports;
	static private String titleProcotol;
	static private String allCapsProtocol;
	static private String allLowerProtocol;

	/*
	 * I hate hate hate this copy and paste job. Need to have a better solution.
	 */
	static
	{
		String protocol = "UDP";
		allLowerProtocol = protocol.toLowerCase();
		allCapsProtocol = protocol.toUpperCase();
		titleProcotol = allCapsProtocol.substring(0, 1) + allLowerProtocol.substring(1);

		topScan = new Option(null, "top" + titleProcotol + "Scan", "Scan all targets for common " + allCapsProtocol + " ports.");
		topPorts = new Option(null, "top" + titleProcotol + "Ports", "The top <n> " + allCapsProtocol + " ports to scan with nmap.", true, true,
				getDefaultTopPorts(), "n");
		topFocusedPorts = new Option(null, "topFocused" + titleProcotol + "Ports",
				"The top <n> " + allCapsProtocol + " ports to scan with nmap on hosts known to be up.", true, true, getDefaultTopFocusedPorts(), "n");
		topFocusedScan = new Option(null, "topFocused" + titleProcotol + "Scan",
				"Scan for the top " + allCapsProtocol + " ports on hosts known to be up.");
		ports = new Option(null, allLowerProtocol + "Ports",
				"Scan the specified " + allCapsProtocol
						+ " ports (comma seperated) of all targets. This is useful if you already know which ports are open or are only looking for specific services.",
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

		NmapGeneralOptions.getOptions().addChild(group);
	}

	static public OptionGroup getOptions()
	{
		return null;
	}


	public NmapTcp()
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

	protected static String getDefaultTopPorts()
	{
		return "1000";
	}

	static protected String getDefaultTopFocusedPorts()
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
	protected String getAllCapsProtocol()
	{
		return allCapsProtocol;
	}
}
