package mcp.jobmanager.executors;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mcp.jobmanager.jobs.JobState;
import net.dacce.commons.general.CollectionUtils;
import net.dacce.commons.general.FileUtils;


public class CommandLineExecutor extends Executor
{
	private String executable;
	private List<String> arguments;
	private String startingDirectory;
	private String stdoutFilename;
	private String stderrFilename;
	private boolean appendOutput;
	private Process process;
	private String programName;
	private JobState state = JobState.UNSTARTED;

	final static Logger logger = LoggerFactory.getLogger(CommandLineExecutor.class);


	public CommandLineExecutor(String programName, String jobName, String executable, List<String> arguments, String startingDirectory)
	{
		this(programName, jobName, executable, arguments, startingDirectory, null, null, false);
	}


	public CommandLineExecutor(String programName, String jobName, String executable, List<String> arguments, String startingDirectory,
			String stdoutFilename)
	{
		this(programName, jobName, executable, arguments, startingDirectory, stdoutFilename, null, false);
	}


	public CommandLineExecutor(String programName, String jobName, String executable, List<String> arguments, String startingDirectory,
			String stdoutFilename, String stderrFilename)
	{
		this(programName, jobName, executable, arguments, startingDirectory, stdoutFilename, stderrFilename, false);
	}


	public CommandLineExecutor(String programName, String jobName, String executable, List<String> arguments, String startingDirectory,
			String stdoutFilename, boolean appendOutput)
	{
		this(programName, jobName, executable, arguments, startingDirectory, stdoutFilename, null, appendOutput);
	}


	public CommandLineExecutor(String programName, String jobName, String executable, List<String> arguments, String startingDirectory,
			String stdoutFilename, String stderrFilename, boolean appendOutput)
	{
		super(jobName);
		this.executable = executable;
		this.arguments = arguments;
		this.startingDirectory = startingDirectory;
		this.stdoutFilename = stdoutFilename;
		this.stderrFilename = stderrFilename;
		this.appendOutput = appendOutput;
		this.programName = programName;
	}


	@Override
	public void run()
	{
		List<String> args = new ArrayList<String>(arguments.size() + 1);
		args.add(executable);
		args.addAll(arguments);
		ProcessBuilder pb = new ProcessBuilder(args);
		pb.directory(new File(startingDirectory));
		if (stdoutFilename != null)
		{
			Redirect stdRedirect;
			if (appendOutput)
			{
				stdRedirect = Redirect.appendTo(new File(stdoutFilename));
			}
			else
			{
				stdRedirect = Redirect.to(new File(stdoutFilename));
			}
			pb.redirectOutput(stdRedirect);
			if (stdoutFilename == stderrFilename)
			{
				pb.redirectErrorStream(true);
			}
			else if (stderrFilename != null)
			{
				if (appendOutput)
				{
					pb.redirectError(Redirect.appendTo(new File(stderrFilename)));
				}
				else
				{
					pb.redirectError(new File(stderrFilename));
				}
			}
		}
		logger.debug("Starting command: " + FileUtils.formatFileName(executable) + CollectionUtils.joinObjects(" ", arguments));
		try
		{
			process = pb.start();
			state = JobState.RUNNING;
		}
		catch (IOException e)
		{
			String message = "Failed to start command line process (" + getName() + "): " + e.getLocalizedMessage();
			logger.error(message, e);
			state = JobState.FAILED;
		}

	}


	@Override
	public JobState getState()
	{
		if ((state == JobState.RUNNING) && !process.isAlive())
		{
			state = JobState.COMPLETE;
		}
		return state;
	}


	@Override
	public String getProgramName()
	{
		return programName;
	}

	
	@Override
	public String toString()
	{
		ToStringBuilder tsb = new ToStringBuilder(this).appendSuper(super.toString()).append("executable", executable)
				.append("executable", executable).append("startingDirectory", startingDirectory).append("stdoutFilename", stdoutFilename)
				.append("stderrFilename", stderrFilename).append("appendOutput", appendOutput)
				.append("programName", programName).append("state", state)
				.append("arguments", CollectionUtils.joinObjects(", ", arguments));
		return tsb.build();
	}

}
