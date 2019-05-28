package mcp.modules.reporting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.events.events.McpNormalExitEvent;
import mcp.events.listeners.McpNormalExitListener;
import mcp.jobmanager.executors.ExecutionScheduler;
import space.dcce.commons.node_database.Connection;
import mcp.knowledgebase.KnowledgeBase;
import space.dcce.commons.node_database.Node;
import space.dcce.commons.node_database.NodeType;
import mcp.knowledgebase.nodeLibrary.Network;
import mcp.modules.Module;
import mcp.modules.Modules;
import space.dcce.commons.cli.Option;
import space.dcce.commons.general.StringUtils;


public class SimpleKbDumper extends Module implements McpNormalExitListener
{
	
	private final static Logger logger = LoggerFactory.getLogger(SimpleKbDumper.class);

	private Option dumpKBOption;

	protected void initializeOptions()
	{
		dumpKBOption = ((ReportingGeneralOptions)Modules.instance.getModuleInstance(ReportingGeneralOptions.class)).getOptions().addOption("", "dumpKB", "Dump the knowledge base contents just before exit.");
	}
	
	public SimpleKbDumper()
	{
		super("Simple KB dumper");
	}

	@Override
	public void initialize()
	{
		if (dumpKBOption.isEnabled())
		{
			ExecutionScheduler.getInstance().registerListener(McpNormalExitEvent.class, this);
		}
	}

	private static class Dumper
	{
		StringBuilder sb = new StringBuilder();
		
		private Dumper()
		{
		}
		
		static String getKBDump()
		{
			Dumper d = new Dumper();
			d.dump();
			return d.sb.toString();
		}
		
		private void dump()
		{
			dumpAllType(Network.IPV4_ADDRESS);
		}
		
		private void dumpAllType(NodeType type)
		{
			for (Node node: KnowledgeBase.INSTANCE.getAllNodesByType(type))
			{
				dumpRootNode(node, 0);
			}
		}
		
		private void dumpRootNode(Node root, int tabs)
		{
			sb.append(StringUtils.indentText(tabs, true, root.getNodeType().getDescription() + ": " + root.getValue())).append("\n");
			int i = 1;
			for(Connection connection: root.getConnections())
			{
				sb.append(StringUtils.indentText(tabs + 1, true, "Connection #" + i++));
				for (Node n: connection.getNodes())
				{
					dumpRootNode(n, tabs + 2);
				}
			}
		}
	}

	public static String getKBDump()
	{
		return Dumper.getKBDump();
	}
	

	@Override
	public void handleEvent(McpNormalExitEvent event)
	{
		String dump = getKBDump();
		logger.info(dump);
		System.out.println(dump);
	}
}
