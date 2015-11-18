package mcp.jobmanager.executors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mcp.events.EventDispatcher;
import mcp.events.events.ExecutorCompleteEvent;
import mcp.jobmanager.jobs.JobState;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExecutorManager implements Runnable
{
	private static ExecutorManager instance;

	private Map<JobState, List<Executor>> jobs;
	private Thread thread;
	private boolean running;
	
	final static Logger logger = LoggerFactory.getLogger(ExecutorManager.class);
	
	private ExecutorManager()
	{
		jobs = new HashMap<JobState, List<Executor>>();
		for (JobState state : JobState.values())
		{
			jobs.put(state, new ArrayList<Executor>(1));
		}
		thread = new Thread(instance);
		running = true;
		thread.start();
	}


	public void execute(Executor executor)
	{
		executor.run();
		synchronized(jobs)
		{
			jobs.get(JobState.RUNNING).add(executor);
		}
	}


	public static ExecutorManager getInstance()
	{
		if (instance == null)
		{
			instance = new ExecutorManager();
		}
		return instance;
	}

	public boolean isQueueEmpty()
	{
		synchronized(jobs)
		{
			if (jobs.get(JobState.RUNNING).isEmpty())
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void run()
	{
		Object o = new Object();
		while (running)
		{
			synchronized (jobs)
			{
				for (Executor executor : jobs.get(JobState.RUNNING))
				{
					if (!executor.isRunning())
					{
						jobs.get(JobState.RUNNING).remove(executor);
						ExecutorCompleteEvent event = new ExecutorCompleteEvent(executor.getProgramName(), executor.getName(),
								executor.getState() == JobState.COMPLETE);
						jobs.get(executor.getState()).add(executor);
						executor.getCallbackObject().jobComplete(executor.getState());
						EventDispatcher.getInstance().signalEvent(event);
					}
				}
			}
			synchronized(o)
			{
				try
				{
					o.wait(100);
				}
				catch (InterruptedException e)
				{
					logger.debug("ExecutorManager interupted");
				}
			}
		}
	}

}
