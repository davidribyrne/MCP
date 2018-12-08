package mcp.jobmanager.executors;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.jobmanager.jobs.JobState;
import space.dcce.commons.general.CollectionUtils;
import space.dcce.commons.general.FileUtils;


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
	private int expectedExitValue;
//	private boolean requiresRoot;

	final static Logger logger = LoggerFactory.getLogger(CommandLineExecutor.class);
	private String rootPassword;

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
//		this.requiresRoot = requiresRoot;
	}


	@Override
	public Process call()
	{
		boolean sudo = false;
		List<String> args = new ArrayList<String>(arguments.size() + 1);

		/*
		if (requiresRoot && !OSUtils.isRoot())
		{
			sudo = true;
			args.add("/bin/bash");
			args.add("-c");
			args.add("sudo");
			args.add("-S");
		}
*/
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
			if (stdoutFilename.equals(stderrFilename))
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
			if (sudo)
			{
				InputStreamReader input = new InputStreamReader(process.getInputStream());
				OutputStreamWriter output = new OutputStreamWriter(process.getOutputStream());

				int bytes;
				char buffer[] = new char[1024];

				while ((bytes = input.read(buffer, 0, 1024)) != -1)
				{
					if (bytes == 0)
						continue;
					String data = String.valueOf(buffer, 0, bytes);
					if (data.toLowerCase().contains("password"))
					{
						output.write(rootPassword + "\n");
						output.flush();
					}
				}
			}

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


	public String getProgramName()
	{
		return programName;
	}


	@Override
	public String toString()
	{
		ToStringBuilder tsb = new ToStringBuilder(this).appendSuper(super.toString()).append("executable", executable)
				.append("startingDirectory", startingDirectory).append("stdoutFilename", stdoutFilename)
				.append("stderrFilename", stderrFilename).append("appendOutput", appendOutput)
				.append("programName", programName).append("state", state)
				.append("arguments", CollectionUtils.joinObjects(", ", arguments));
		return tsb.build();
	}


	public void setRootPassword(String rootPassword)
	{
		this.rootPassword = rootPassword;
	}

}
