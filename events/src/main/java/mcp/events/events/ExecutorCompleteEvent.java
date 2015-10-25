package mcp.events.events;

public class ExecutorCompleteEvent extends ReconEvent
{
	private final Boolean success;
	private final String program;
	private final String jobName;


	public ExecutorCompleteEvent(String program, String jobName, Boolean success)
	{
		this.success = success;
		this.program = program;
		this.jobName = jobName;
	}


	public Boolean isSuccess()
	{
		return success;
	}


	public String getProgram()
	{
		return program;
	}


	public String getJobName()
	{
		return jobName;
	}


}
