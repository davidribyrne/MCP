package mcp.events;

import mcp.events.events.ExecutorCompleteEvent;
import mcp.events.events.ModuleRunCompleteEvent;
import mcp.events.events.NodeUpdateEvent;
import mcp.events.events.ReconCompleteEvent;
import mcp.events.events.ReconEvent;
import mcp.events.events.ReconStartEvent;
import mcp.events.listeners.ExecutorCompleteListener;
import mcp.events.listeners.ModuleRunCompleteListener;
import mcp.events.listeners.NodeUpdateListener;
import mcp.events.listeners.ReconCompleteListener;
import mcp.events.listeners.ReconEventListener;
import mcp.events.listeners.ReconStartListener;
import net.dacce.commons.general.MapOfLists;


public class EventDispatcher
{
	private final static EventDispatcher instance = new EventDispatcher();
	private MapOfLists<Class<? extends ReconEvent>, ReconEventListener> listeners;
	private MapOfLists<NodeUpdateEventType, NodeUpdateListener> nodeListeners;
	private MapOfLists<String, ExecutorCompleteListener> executorListeners;


	private EventDispatcher()
	{
		listeners = new MapOfLists<Class<? extends ReconEvent>, ReconEventListener>();
		nodeListeners = new MapOfLists<NodeUpdateEventType, NodeUpdateListener>();
		executorListeners = new MapOfLists<String, ExecutorCompleteListener>();
	}


	public void registerListener(Class<? extends ReconEvent> eventClass, ReconEventListener listener)
	{
		if (eventClass == NodeUpdateEvent.class)
		{
			NodeUpdateListener l = (NodeUpdateListener) listener;
			for (NodeUpdateEventType type : l.getNodeUpdateEventTypes())
			{
				nodeListeners.put(type, l);
			}
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
		for (ReconEventListener listener : listeners.get(ModuleRunCompleteEvent.class))
		{
			((ModuleRunCompleteListener) listener).handleEvent(event);
		}
	}


	public void signalEvent(NodeUpdateEvent event)
	{
		for (ReconEventListener listener : listeners.get(NodeUpdateEvent.class))
		{
			((NodeUpdateListener) listener).handleEvent(event);
		}
	}


	public void signalEvent(ReconStartEvent event)
	{
		for (ReconEventListener listener : listeners.get(ReconStartEvent.class))
		{
			((ReconStartListener) listener).handleEvent(event);
		}
	}

	public void signalEvent(ReconCompleteEvent event)
	{
		for (ReconEventListener listener : listeners.get(ReconCompleteEvent.class))
		{
			((ReconCompleteListener) listener).handleEvent(event);
		}
	}


	public static EventDispatcher getInstance()
	{
		return instance;
	}
}
