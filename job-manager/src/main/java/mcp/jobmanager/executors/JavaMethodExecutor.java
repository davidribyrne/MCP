package mcp.jobmanager.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.jobmanager.jobs.JobState;

import java.util.*;

public class JavaMethodExecutor<T> extends Executor<T>
{
	private final static Logger logger = LoggerFactory.getLogger(JavaMethodExecutor.class);


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
