package mcp.events.events;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ExecutorCompleteEvent extends McpEvent
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

	@Override
	public String toString()
	{
		return new ToStringBuilder(this).appendSuper(super.toString()).append("success", success)
				.append("program", program).append("jobName", jobName).build();
	}

}
