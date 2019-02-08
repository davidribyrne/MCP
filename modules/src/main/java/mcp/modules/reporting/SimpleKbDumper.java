package mcp.modules.reporting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.events.events.McpNormalExitEvent;
import mcp.events.listeners.McpEventListener;
import mcp.events.listeners.McpNormalExitListener;
import mcp.jobmanager.executors.ExecutionScheduler;
import mcp.knowledgebase.Connection;
import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.Node;
import mcp.knowledgebase.NodeType;
import mcp.knowledgebase.nodeLibrary.Network;
import mcp.modules.Module;
import space.dcce.commons.cli.Option;
import space.dcce.commons.cli.OptionContainer;
import space.dcce.commons.cli.OptionGroup;
import space.dcce.commons.general.StringUtils;


public class SimpleKbDumper extends Module implements McpNormalExitListener
{
	
	private final static Logger logger = LoggerFactory.getLogger(SimpleKbDumper.class);
	private static OptionGroup options;
	private static Option dumpKBOption;

	static
	{
		dumpKBOption = new Option("d", "dumpKB", "Dump the knowledge base contents just before exit.");
		ReportingGeneralOptions.getOptions().addChild(dumpKBOption);
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
			for (Node node: KnowledgeBase.instance.getAllNodesByType(type))
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
	

	
	public static OptionContainer getOptions()
	{
		return options;
	}

	@Override
	public void handleEvent(McpNormalExitEvent event)
	{
		String dump = getKBDump();
		logger.info(dump);
		System.out.println(dump);
	}
}
