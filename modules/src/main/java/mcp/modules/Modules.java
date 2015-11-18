package mcp.modules;

import mcp.modules.nmap.NmapGeneralOptions;
import mcp.modules.nmap.NmapIcmp;
import mcp.modules.nmap.NmapTcp;
import mcp.modules.nmap.NmapUdp;
import mcp.options.ReconOptions;
import net.dacce.commons.cli.Group;


public class Modules
{

	public static void PopulateOptions()
	{
		ReconOptions.getInstance().addOptionContainer(GeneralOptions.getInstance().getOptions());
		ReconOptions.getInstance().addOptionContainer(ScopeInitializer.getInstance().getOptions());

		((Group) NmapGeneralOptions.getInstance().getOptions()).addChild(NmapIcmp.getInstance().getOptions());
		((Group) NmapGeneralOptions.getInstance().getOptions()).addChild(NmapTcp.getInstance().getOptions());
		((Group) NmapGeneralOptions.getInstance().getOptions()).addChild(NmapUdp.getInstance().getOptions());
		ReconOptions.getInstance().addOptionContainer(NmapGeneralOptions.getInstance().getOptions());
		
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
		
		SimpleKbDumper.getInstance().initialize();
	}

}
