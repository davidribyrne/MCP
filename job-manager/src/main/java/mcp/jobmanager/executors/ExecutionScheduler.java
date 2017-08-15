package mcp.jobmanager.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.events.events.NodeCreationEvent;
import mcp.events.events.ExecutorCompleteEvent;
import mcp.events.events.McpCompleteEvent;
import mcp.events.events.McpEvent;
import mcp.events.events.McpStartEvent;
import mcp.events.events.ModuleRunCompleteEvent;
import mcp.events.listeners.ExecutorCompleteListener;
import mcp.events.listeners.McpEventListener;
import mcp.events.listeners.McpStartListener;
import mcp.events.listeners.ModuleRunCompleteListener;
import mcp.events.listeners.NodeCreationListener;
import mcp.jobmanager.jobs.JobState;
import mcp.knowledgebase.NodeType;
import mcp.knowledgebase.Node;
import net.dacce.commons.general.MapOfLists;


public class ExecutionScheduler implements Runnable
{
	private static ExecutionScheduler instance = new ExecutionScheduler();

	private final MapOfLists<JobState, Executor<?>> jobs;
	private Thread thread;
	private boolean running;
	private ScheduledThreadPoolExecutor scheduler;


	private final MapOfLists<Class<? extends McpEvent>, McpEventListener> listeners;
	private final List<NodeCreationListener> nodeListeners;
	private final MapOfLists<String, ExecutorCompleteListener> executorListeners;


	final static Logger logger = LoggerFactory.getLogger(ExecutionScheduler.class);


	private ExecutionScheduler()
	{
		scheduler = new ScheduledThreadPoolExecutor(3, new DaemonThreadFactory());

		jobs = new MapOfLists<JobState, Executor<?>>();
		jobs.seedKeys(JobState.values());
		listeners = new MapOfLists<Class<? extends McpEvent>, McpEventListener>();
		nodeListeners = new ArrayList<NodeCreationListener>();
		executorListeners = new MapOfLists<String, ExecutorCompleteListener>();

		thread = new Thread(this);
		thread.setName("MCP Execution Scheduler");
		running = true;
		thread.start();
	}

	private class DaemonThreadFactory implements ThreadFactory
	{
		@Override
		public Thread newThread(Runnable target)
		{
			Thread t = new Thread(target);
			t.setDaemon(true);
			return t;
		}
	}


	public Future<?> executeImmediately(Executor<?> executor)
	{
		synchronized (this)
		{
			Future<?> future = scheduler.submit(executor);
			executor.setFuture(future);
			jobs.get(JobState.RUNNING).add(executor);
			return future;
		}
	}


	public void executeImmediately(Runnable run)
	{
		synchronized (this)
		{
			scheduler.submit(run);
		}
	}


	/**
	 * 
	 * @param executor
	 * @param delay
	 *            Delay in milliseconds
	 * @return
	 */
	public Future<?> executeDelayed(Executor<?> executor, long delay)
	{
		synchronized (this)
		{
			Future<?> future = scheduler.schedule(executor, delay, TimeUnit.MILLISECONDS);
			executor.setFuture(future);
			jobs.get(JobState.RUNNING).add(executor);
			return future;
		}
	}


	public static ExecutionScheduler getInstance()
	{
		return instance;
	}


	@Override
	public void run()
	{
		Object o = new Object();
		while (running)
		{
			synchronized (this)
			{
				Executor<?>[] tmpJobs = jobs.get(JobState.RUNNING).toArray(new Executor<?>[0]);
				for (Executor<?> executor : tmpJobs)
				{
					if (!executor.isRunning())
					{
						jobs.get(JobState.RUNNING).remove(executor);
						ExecutorCompleteEvent event = new ExecutorCompleteEvent(executor.getProgramName(), executor.getName(),
								executor.getState() == JobState.COMPLETE);
						jobs.get(executor.getState()).add(executor);
						executor.signalCallback();
						signalEvent(event);
					}
				}
			}
			synchronized (o)
			{
				try
				{
					o.wait(100);
				}
				catch (InterruptedException e)
				{
					logger.debug("ExecutorManager interupted", e);
				}
			}
		}
	}


	public boolean isQueueEmpty()
	{
		synchronized (this)
		{
			return jobs.get(JobState.RUNNING).isEmpty()
					&& scheduler.getActiveCount() == 0
					&& scheduler.getQueue().isEmpty();
		}
	}


	@Override
	public String toString()
	{
		synchronized (this)
		{
			return new ToStringBuilder(this)
					.append("isRunning", running)
					.append("jobs running", jobs.get(JobState.RUNNING).size())
					.append("scheduler active count", scheduler.getActiveCount())
					.append("scheduler queue size", scheduler.getQueue().size())
					.build();
		}
	}


	public void registerListener(Class<? extends McpEvent> eventClass, McpEventListener listener)
	{
		synchronized (this)
		{
			if (eventClass == NodeCreationEvent.class)
			{
				nodeListeners.add((NodeCreationListener) listener);
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
	}


	/*
	 * Not synchronized since it's private and called from a synchronized block
	 */
	private void signalEvent(ExecutorCompleteEvent event)
	{
		for (ExecutorCompleteListener listener : executorListeners.get(event.getProgram()))
		{
			ExecutionScheduler.getInstance().executeImmediately(new Runnable()
			{
				@Override
				public void run()
				{
					listener.handleEvent(event);
				}
			});
		}
	}


	public void signalEvent(ModuleRunCompleteEvent event)
	{
		synchronized (this)
		{

			for (McpEventListener listener : listeners.get(ModuleRunCompleteEvent.class))
			{
				scheduler.submit(new Runnable()
				{
					@Override
					public void run()
					{
						((ModuleRunCompleteListener) listener).handleEvent(event);
					}
				});
			}
		}
	}


	public void signalEvent(NodeCreationEvent event)
	{
		Node node = event.getNode();
		synchronized (this)
		{
			for (NodeCreationListener listener : nodeListeners)
			{
				for (NodeType type : listener.getMonitorNodeTypes())
				{
					if (node.getType().equals(type))
					{
						scheduler.submit(new Runnable()
						{
							@Override
							public void run()
							{
								listener.handleEvent(event);
							}
						});
					}
				}
			}
		}
	}


	public void signalEvent(McpStartEvent event)
	{
		synchronized (this)
		{
			for (McpEventListener listener : listeners.get(McpStartEvent.class))
			{
				scheduler.submit(new Runnable()
				{
					@Override
					public void run()
					{
						((McpStartListener) listener).handleEvent(event);
					}
				});
			}
		}
	}


	public Map<JobState, List<Executor<?>>> getJobs()
	{
		synchronized (this)
		{
			return jobs.unmodifiableMapOfLists();
		}
	}

}
