//package mcp.events.events;
//
//import org.apache.commons.lang3.builder.ToStringBuilder;
//import org.apache.commons.lang3.builder.ToStringStyle;
//
//public class ExecutorCompleteEvent extends McpEvent
//{
//	private final Boolean success;
//	private final String jobName;
//
//
//	public ExecutorCompleteEvent(String jobName, Boolean success)
//	{
//		this.success = success;
//		this.jobName = jobName;
//	}
//
//
//	public Boolean isSuccess()
//	{
//		return success;
//	}
//
//
//
//
//	public String getJobName()
//	{
//		return jobName;
//	}
//
//	@Override
//	public String toString()
//	{
//		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("success", success)
//				.append("jobName", jobName).build();
//	}
//
//}
