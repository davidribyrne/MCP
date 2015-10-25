package mcp.jobmanager.executors;

import mcp.jobmanager.jobs.JobState;


public interface Callback
{
	public void jobComplete(JobState result);
}
