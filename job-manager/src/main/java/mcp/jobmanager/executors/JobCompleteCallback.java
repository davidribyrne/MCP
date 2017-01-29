package mcp.jobmanager.executors;

import mcp.jobmanager.jobs.JobState;


public interface JobCompleteCallback
{
	public void jobComplete(JobState result);
}
