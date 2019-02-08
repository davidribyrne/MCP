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

import mcp.events.events.McpEvent;
import mcp.events.events.McpNormalExitEvent;
import mcp.events.events.McpStartEvent;
import mcp.events.events.ModuleRunCompleteEvent;
import mcp.events.events.NodeCreationEvent;
import mcp.events.listeners.McpEventListener;
import mcp.events.listeners.McpNormalExitListener;
import mcp.events.listeners.McpStartListener;
import mcp.events.listeners.ModuleRunCompleteListener;
import mcp.events.listeners.NodeCreationListener;
import mcp.jobmanager.jobs.JobState;
import mcp.knowledgebase.Node;
import mcp.knowledgebase.NodeType;
import space.dcce.commons.general.MapOfLists;


public class ExecutionScheduler implements Runnable
{
	private static ExecutionScheduler instance = new ExecutionScheduler();

	private final MapOfLists<JobState, Executor<?>> jobs;
	private Thread thread;
	private boolean running;
	private ScheduledThreadPoolExecutor scheduler;

	private final MapOfLists<Class<? extends McpEvent>, McpEventListener> listeners;
	private final List<NodeCreationListener> nodeListeners;
	// private final MapOfLists<String, ExecutorCompleteListener> executorListeners;


	final static Logger logger = LoggerFactory.getLogger(ExecutionScheduler.class);


	private ExecutionScheduler()
	{
		scheduler = new ScheduledThreadPoolExecutor(1, new DaemonThreadFactory("MCP scheduled executor thread"));

		jobs = new MapOfLists<JobState, Executor<?>>();
		jobs.seedKeys(JobState.values());
		listeners = new MapOfLists<Class<? extends McpEvent>, McpEventListener>();
		nodeListeners = new ArrayList<NodeCreationListener>();
		// executorListeners = new MapOfLists<String, ExecutorCompleteListener>();
	}


	public void start()
	{
		thread = new Thread(this);
		thread.setName("MCP Execution Scheduler");
		running = true;
		signalEvent(new McpStartEvent());
		thread.start();
	}

	private class DaemonThreadFactory implements ThreadFactory
	{
		String name;


		DaemonThreadFactory(String name)
		{
			this.name = name;
		}


		@Override
		public Thread newThread(Runnable target)
		{
			Thread t = new Thread(target);
			t.setDaemon(true);
			t.setName(name);
			return t;
		}
	}


	public Future<?> executeImmediately(Executor<?> executor)
	{
		synchronized (this)
		{
			logger.trace("Scheduling job for immediate execution");
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
		try
		{
			Object o = new Object();
			long count = 0;
			while (running)
			{
				synchronized (this)
				{
					if (count++ % 600 == 0)
						logStatus();
					Executor<?>[] tmpJobs = jobs.get(JobState.RUNNING).toArray(new Executor<?>[0]);
					for (Executor<?> executor : tmpJobs)
					{
						if (!executor.isRunning())
						{
							jobs.get(JobState.RUNNING).remove(executor);
							jobs.get(executor.getState()).add(executor);
							executor.signalCallback();
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
				synchronized (this)
				{
					if (isQueueEmpty())
					{
						running = false;
						logger.info("Queues are empty, sending exit event: " + this.toString());
						signalEvent(new McpNormalExitEvent());
						while (!isQueueEmpty())
						{
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
						logger.info("Exit jobs done, exiting: " + this.toString());
					}
				}
			}
		}
		catch (Throwable t)
		{
			logger.error("Uncaught exception in ExecutionScheduler.run: " + t.getLocalizedMessage(), t);
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
			// else if (eventClass == ExecutorCompleteEvent.class)
			// {
			// ExecutorCompleteListener l = (ExecutorCompleteListener) listener;
			// for (String program : l.getProgramNames())
			// {
			// executorListeners.put(program, l);
			// }
			// }
			else
			{
				listeners.put(eventClass, listener);
			}
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
						try
						{
							((ModuleRunCompleteListener) listener).handleEvent(event);
						}
						catch (Throwable t)
						{
							logger.error("Uncaught exception in ExecutionScheduler.signalEvent - ModuleRunCompleteEvent(" + event.toString() + "): "
									+ t.getLocalizedMessage(), t);
						}
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
					if (node.getNodeType().equals(type))
					{
						scheduler.submit(new Runnable()
						{
							@Override
							public void run()
							{
								try
								{
									listener.handleEvent(event);
								}
								catch (Throwable t)
								{
									logger.error("Uncaught exception in ExecutionScheduler.signalEvent - NodeCreationEvent(" + event.toString()
											+ "): " + t.getLocalizedMessage(), t);
								}
							}
						});
					}
				}
			}
		}
	}


	private void signalEvent(McpStartEvent event)
	{
		synchronized (this)
		{
			for (McpEventListener listener : listeners.get(McpStartEvent.class))
			{
				System.out.println("scheduler (before): " + scheduler.toString());
				scheduler.submit(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							((McpStartListener) listener).handleEvent(event);
						}
						catch (Throwable t)
						{
							logger.error(
									"Uncaught exception in ExecutionScheduler.signalEvent - McpStartEvent(" + event.toString() + "): "
											+ t.getLocalizedMessage(),
									t);
						}
					}
				});
				System.out.println("scheduler (after): " + scheduler.toString());
			}
		}
	}


	private void signalEvent(McpNormalExitEvent event)
	{
		synchronized (this)
		{
			for (McpEventListener listener : listeners.get(McpNormalExitEvent.class))
			{
				System.out.println("scheduler (before): " + scheduler.toString());
				scheduler.submit(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							((McpNormalExitListener) listener).handleEvent(event);
						}
						catch (Throwable t)
						{
							logger.error(
									"Uncaught exception in ExecutionScheduler.signalEvent - McpNormalExitListener(" + event.toString() + "): "
											+ t.getLocalizedMessage(),
									t);
						}
					}
				});
				System.out.println("scheduler (after): " + scheduler.toString());
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


	private void logStatus()
	{
		logger.trace("Execution queue status:\n" + toString());
	}


	public boolean isQueueEmpty()
	{
		return jobs.get(JobState.UNSTARTED).isEmpty()
				&& jobs.get(JobState.RUNNING).isEmpty()
				&& scheduler.getActiveCount() == 0
				&& scheduler.getQueue().isEmpty();
	}


	@Override
	public String toString()
	{
		synchronized (this)
		{
			return new ToStringBuilder(this)
					.append("isRunning", running)
					.append("jobs running", jobs.get(JobState.RUNNING).size())
					.append("jobs complete", jobs.get(JobState.COMPLETE).size())
					.append("jobs failed", jobs.get(JobState.FAILED).size())
					.append("jobs unstarted", jobs.get(JobState.UNSTARTED).size())
					.append("scheduler active count", scheduler.getActiveCount())
					.append("scheduler complete", scheduler.getCompletedTaskCount())
					.append("scheduler queue size", scheduler.getQueue().size())
					.build();
		}
	}

}
