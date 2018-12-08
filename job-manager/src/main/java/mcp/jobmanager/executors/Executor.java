package mcp.jobmanager.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.apache.commons.lang3.builder.ToStringBuilder;

import mcp.jobmanager.jobs.JobState;


public abstract class Executor<T> implements Callable<T>
{
	private final String name;
	private JobCompleteCallback callbackObject;
	private Future<T> future;
	protected JobState state = JobState.UNSTARTED;

	public Executor(String name)
	{
		this.name = name;
	}



	public abstract T call();

	
	public boolean isRunning()
	{
		return getState() == JobState.RUNNING;
	}


	public String getName()
	{
		return name;
	}


//	public abstract String getProgramName();


	public JobState getState()
	{
		return state;
	}


	public void signalCallback()
	{
		callbackObject.jobComplete(getState());
	}

	public void setCallback(JobCompleteCallback callbackObject)
	{
		this.callbackObject = callbackObject;
	}
	
	@Override
	public String toString()
	{
		return new ToStringBuilder(this).append("name", name).build();
	}


	public Future getFuture()
	{
		return future;
	}


	public void setFuture(Future future)
	{
		this.future = future;
	}
}
