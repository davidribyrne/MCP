package mcp.modules;

import mcp.modules.hostnames.CommonHostnames;
import mcp.modules.hostnames.HostnameDiscoveryGeneralOptions;
import mcp.modules.nmap.NmapGeneralOptions;
import mcp.modules.nmap.NmapIcmp;
import mcp.modules.nmap.NmapTcp;
import mcp.modules.nmap.NmapUdp;
import mcp.options.ReconOptions;
import net.dacce.commons.cli.OptionGroup;


public class Modules
{

	public static void PopulateOptions()
	{
		ReconOptions.getInstance().addOptionContainer(GeneralOptions.getInstance().getOptions());
		ReconOptions.getInstance().addOptionContainer(ScopeInitializer.getInstance().getOptions());

		((OptionGroup) NmapGeneralOptions.getInstance().getOptions()).addChild(NmapIcmp.getInstance().getOptions());
		((OptionGroup) NmapGeneralOptions.getInstance().getOptions()).addChild(NmapTcp.getInstance().getOptions());
		((OptionGroup) NmapGeneralOptions.getInstance().getOptions()).addChild(NmapUdp.getInstance().getOptions());
		ReconOptions.getInstance().addOptionContainer(NmapGeneralOptions.getInstance().getOptions());
		
		ReconOptions.getInstance().addOptionContainer(HostnameDiscoveryGeneralOptions.getInstance().getOptions());
	}


	public static void initializePrescanModules()
	{
		GeneralOptions.getInstance().initialize();
		ScopeInitializer.getInstance().initialize();
		NmapGeneralOptions.getInstance().initialize();
	}


	public static void initializeOtherModules()
	{
		NmapIcmp.getInstance().initialize();
		NmapTcp.getInstance().initialize();
		NmapUdp.getInstance().initialize();
		
		HostnameDiscoveryGeneralOptions.getInstance().initialize();
		CommonHostnames.getInstance().initialize();
		
		SimpleKbDumper.getInstance().initialize();
	}

}
