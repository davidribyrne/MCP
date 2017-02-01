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


public class CommandLineExecutor extends Executor<Process>
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
	private int expectedExitValue;

	final static Logger logger = LoggerFactory.getLogger(CommandLineExecutor.class);




	/**
	 * 
	 * @param programName
	 * @param jobName
	 * @param executable
	 * @param arguments
	 * @param startingDirectory
	 * @param stdoutFilename
	 * @param stderrFilename
	 * @param appendOutput
	 */
	public CommandLineExecutor(String programName, String jobName, String executable, List<String> arguments, String startingDirectory,
			String stdoutFilename, String stderrFilename, boolean appendOutput, int expectedExitValue)
	{
		super(jobName);
		this.executable = executable;
		this.arguments = arguments;
		this.startingDirectory = startingDirectory;
		this.stdoutFilename = stdoutFilename;
		this.stderrFilename = stderrFilename;
		this.appendOutput = appendOutput;
		this.programName = programName;
		this.expectedExitValue = expectedExitValue;
	}


	@Override
	public Process call()
	{
		List<String> args = new ArrayList<String>(arguments.size() + 1);
		args.add(executable);
		args.addAll(arguments);
		ProcessBuilder processBuilder = new ProcessBuilder(args);
		processBuilder.directory(new File(startingDirectory));
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
			processBuilder.redirectOutput(stdRedirect);
			if (stdoutFilename == stderrFilename)
			{
				processBuilder.redirectErrorStream(true);
			}
			else if (stderrFilename != null)
			{
				if (appendOutput)
				{
					processBuilder.redirectError(Redirect.appendTo(new File(stderrFilename)));
				}
				else
				{
					processBuilder.redirectError(new File(stderrFilename));
				}
			}
		}
		logger.debug("Starting command: " + FileUtils.formatFileName(executable) + CollectionUtils.joinObjects(" ", arguments));
		try
		{
			process = processBuilder.start();
			state = JobState.RUNNING;
		}
		catch (IOException e)
		{
			String message = "Failed to start command line process (" + getName() + "): " + e.getLocalizedMessage();
			logger.error(message, e);
			state = JobState.FAILED;
		}
		return process;
	}


	@Override
	public JobState getState()
	{
		if ((state == JobState.RUNNING) && !process.isAlive())
		{
			if (process.exitValue() != expectedExitValue)
			{
				state = JobState.FAILED;
			}
			else
			{
				state = JobState.COMPLETE;
			}
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
