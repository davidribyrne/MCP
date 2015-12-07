package mcp.events;

import java.util.ArrayList;
import java.util.List;

import mcp.events.events.ExecutorCompleteEvent;
import mcp.events.events.ModuleRunCompleteEvent;
import mcp.events.events.NodeUpdateEvent;
import mcp.events.events.McpCompleteEvent;
import mcp.events.events.McpEvent;
import mcp.events.events.McpStartEvent;
import mcp.events.listeners.ExecutorCompleteListener;
import mcp.events.listeners.ModuleRunCompleteListener;
import mcp.events.listeners.NodeUpdateType;
import mcp.events.listeners.NodeUpdateListener;
import mcp.events.listeners.McpCompleteListener;
import mcp.events.listeners.McpEventListener;
import mcp.events.listeners.McpStartListener;
import mcp.events.listeners.nodeUpdate.UpdateAction;
import net.dacce.commons.general.MapOfLists;
import net.dacce.commons.general.UniqueList;


public class EventDispatcher
{
	private final static EventDispatcher instance = new EventDispatcher();
	private MapOfLists<Class<? extends McpEvent>, McpEventListener> listeners;
	private List<NodeUpdateListener> nodeListeners;
	private MapOfLists<String, ExecutorCompleteListener> executorListeners;


	private EventDispatcher()
	{
		listeners = new MapOfLists<Class<? extends McpEvent>, McpEventListener>();
		nodeListeners = new ArrayList<NodeUpdateListener>();
		executorListeners = new MapOfLists<String, ExecutorCompleteListener>();
	}


	public void registerListener(Class<? extends McpEvent> eventClass, McpEventListener listener)
	{
		if (eventClass == NodeUpdateEvent.class)
		{
			nodeListeners.add((NodeUpdateListener) listener);
		}
		else if (eventClass == ExecutorCompleteEvent.class)
		{
			ExecutorCompleteListener l = (ExecutorCompleteListener) listener;
			for (String program : l.getProgramNames())
			{
				executorListeners.put(program, l);
			}
		}
		else
		{
			listeners.put(eventClass, listener);
		}
	}


	public void signalEvent(ExecutorCompleteEvent event)
	{
		for (ExecutorCompleteListener listener : executorListeners.get(event.getProgram()))
		{
			listener.handleEvent(event);
		}
	}



	public void signalEvent(ModuleRunCompleteEvent event)
	{
		for (McpEventListener listener : listeners.get(ModuleRunCompleteEvent.class))
		{
			((ModuleRunCompleteListener) listener).handleEvent(event);
		}
	}


	// TODO: Need to make this more efficient
	public void signalEvent(NodeUpdateEvent event)
	{
		for (NodeUpdateListener listener : nodeListeners)
		{
			for(NodeUpdateType updateType: listener.getNodeUpdateEventTypes())
			{
				if (updateType.getUpdateAction().equals(event.getAction()))
				{
					if (updateType.getNodeClass().isInstance(event.getNode()))
					{
						listener.handleEvent(event);
						break;
					}
				}
			}
		}
	}


	public void signalEvent(McpStartEvent event)
	{
		for (McpEventListener listener : listeners.get(McpStartEvent.class))
		{
			((McpStartListener) listener).handleEvent(event);
		}
	}

	public void signalEvent(McpCompleteEvent event)
	{
		for (McpEventListener listener : listeners.get(McpCompleteEvent.class))
		{
			((McpCompleteListener) listener).handleEvent(event);
		}
	}


	public static EventDispatcher getInstance()
	{
		return instance;
	}
}
