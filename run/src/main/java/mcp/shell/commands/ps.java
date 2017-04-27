package mcp.shell.commands;

import java.util.List;

import org.crsh.cli.Command;
import org.crsh.cli.Option;
import org.crsh.cli.Usage;
import org.crsh.command.BaseCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.jobmanager.executors.ExecutionScheduler;
import mcp.jobmanager.executors.Executor;
import mcp.jobmanager.jobs.JobState;

public class ps extends BaseCommand
{
	private final static Logger logger = LoggerFactory.getLogger(ps.class);

	@Usage("show job data")
	@Command
	public Object main(
			@Usage("list running jobs (default). Use explicit option to combine with other job states.") @Option(names = { "r", "running" }) Boolean running,
			@Usage("list jobs that are queued but not started") @Option(names = { "q", "queued" }) Boolean queued,
			@Usage("list completed jobs") @Option(names = { "c", "complete" }) Boolean completed,
			@Usage("list failed jobs") @Option(names = { "f", "failed" }) Boolean failed,
			@Usage("list entire job history") @Option(names = { "a", "all" }) Boolean all)
	{
		all = (all != null);

		completed = all || (completed != null);
		queued = all || (queued != null);
		running = all || (running != null);
		failed = all || (failed != null);
		
		// Default to running being enabled
		if (! (completed || queued))
			running = true; 
		
		StringBuilder sb = new StringBuilder();
		
		if (running)
		{
			sb.append("Running jobs:\n");
			sb.append(getJobs(JobState.RUNNING));
		}

		if (completed)
		{
			sb.append("Completed jobs:\n");
			sb.append(getJobs(JobState.COMPLETE));
		}

		if (queued)
		{
			sb.append("Queued jobs:\n");
			sb.append(getJobs(JobState.UNSTARTED));
		}
		
		if (failed)
		{
			sb.append("Failed jobs:\n");
			sb.append(getJobs(JobState.FAILED));
		}

		return sb.toString();
	}
	
	private String getJobs(JobState state)
	{
		StringBuilder sb = new StringBuilder();
		List<Executor<?>> runningJobsList = ExecutionScheduler.getInstance().getJobs().get(state);
		for (Executor<?> job: runningJobsList)
		{
			sb.append("\t").append(job.getName()).append("\n");
		}
		sb.append("\n");
		return sb.toString();
	}
}
