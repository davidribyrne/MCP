package mcp.jobmanager.executors;

import mcp.jobmanager.jobs.JobState;


public abstract class Executor
{
	private final String name;
	private Callback callbackObject;


	public Executor(String name)
	{
		this.name = name;
	}


	public abstract void run();


	
	public boolean isRunning()
	{
		return getState() == JobState.RUNNING;
	}


	public String getName()
	{
		return name;
	}


	public abstract String getProgramName();


	public abstract JobState getState();


	public Callback getCallbackObject()
	{
		return callbackObject;
	}


	public void setCallback(Callback callbackObject)
	{
		this.callbackObject = callbackObject;
	}
}
