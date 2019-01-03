package mcp.jobmanager.executors;

import mcp.jobmanager.jobs.JobState;

public class JavaMethodExecutor<T> extends Executor<T>
{


	public JavaMethodExecutor(String name)
	{
		super(name);
	}



	@Override
	public T call()
	{
		return null;
	}




	@Override
	public JobState getState()
	{
		return null;
	}
}
