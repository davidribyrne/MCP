//package mcp.events;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import mcp.events.events.ElementCreationEvent;
//import mcp.events.events.ExecutorCompleteEvent;
//import mcp.events.events.McpCompleteEvent;
//import mcp.events.events.McpEvent;
//import mcp.events.events.McpStartEvent;
//import mcp.events.events.ModuleRunCompleteEvent;
//import mcp.events.listeners.ExecutorCompleteListener;
//import mcp.events.listeners.McpCompleteListener;
//import mcp.events.listeners.McpEventListener;
//import mcp.events.listeners.McpStartListener;
//import mcp.events.listeners.ModuleRunCompleteListener;
//import mcp.events.listeners.NodeCreationListener;
//import mcp.jobmanager.executors.ExecutionScheduler;
//import mcp.jobmanager.executors.Executor;
//import mcp.knowledgebase.nodes.Node;
//import net.dacce.commons.general.MapOfLists;
//
//
//public class EventDispatcher
//{
//	private final static Logger logger = LoggerFactory.getLogger(EventDispatcher.class);
//
//
//
//	public static EventDispatcher getInstance()
//	{
//		return instance;
//	}
//}
