package mcp.modules;

import mcp.events.EventDispatcher;
import mcp.events.events.ReconCompleteEvent;
import mcp.events.listeners.ReconCompleteListener;
import mcp.knowledgebase.KnowledgeBase;
import net.dacce.commons.cli.OptionContainer;
import net.dacce.commons.general.CollectionUtils;
import net.dacce.commons.general.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimpleKbDumper extends Module implements ReconCompleteListener
{
	final static Logger logger = LoggerFactory.getLogger(SimpleKbDumper.class);
	private static SimpleKbDumper instance = new SimpleKbDumper();
	
	public static SimpleKbDumper getInstance()
	{
		return instance;
	}
	
	private SimpleKbDumper()
	{
	}

	@Override
	public void initialize()
	{
		EventDispatcher.getInstance().registerListener(ReconCompleteEvent.class, this);
	}


	@Override
	public void handleEvent(ReconCompleteEvent reconEvent)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Scope:\n");
		sb.append(StringUtils.indentText(1, true, KnowledgeBase.getInstance().getScope().toString()));
		
		sb.append("\n\nHosts:\n");
		sb.append(StringUtils.indentText(1, true, CollectionUtils.joinObjects("\n", KnowledgeBase.getInstance().getHosts())));
		
		sb.append("\n\nAll hostnames:\n");
		sb.append(StringUtils.indentText(1, true, CollectionUtils.joinObjects("\n", KnowledgeBase.getInstance().getHostnames())));
		
		System.out.println(sb.toString());
	}

	@Override
	public OptionContainer getOptions()
	{
		return null;
	}
}
