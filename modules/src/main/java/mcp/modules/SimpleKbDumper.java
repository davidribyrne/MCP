package mcp.modules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mcp.events.events.McpCompleteEvent;
import mcp.events.listeners.McpCompleteListener;
import mcp.jobmanager.executors.ExecutionScheduler;
import mcp.knowledgebase.KnowledgeBaseImpl;
import mcp.knowledgebase.scope.Scope;
import net.dacce.commons.cli.OptionContainer;
import net.dacce.commons.general.CollectionUtils;
import net.dacce.commons.general.StringUtils;


public class SimpleKbDumper extends Module implements McpCompleteListener
{
	final static Logger logger = LoggerFactory.getLogger(SimpleKbDumper.class);
	
	public SimpleKbDumper()
	{
		super("Simple KB dumper");
	}

	@Override
	public void initialize()
	{
		ExecutionScheduler.getInstance().registerListener(McpCompleteEvent.class, this);
	}


	@Override
	public void handleEvent(McpCompleteEvent reconEvent)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Simple KB dumper output:\n");
		sb.append("Scope:\n");
		sb.append(StringUtils.indentText(1, true, Scope.instance.toString()));
		
		sb.append("\n\nHosts:\n");
		sb.append(StringUtils.indentText(1, true, CollectionUtils.joinObjects("\n", KnowledgeBaseImpl.getInstance().getHosts())));
		
		sb.append("\n\nAll hostnames:\n");
		sb.append(StringUtils.indentText(1, true, CollectionUtils.joinObjects("\n", KnowledgeBaseImpl.getInstance().getHostnames())));
		
		logger.info(sb.toString());
	}

	@Override
	public OptionContainer getOptions()
	{
		return null;
	}
}
