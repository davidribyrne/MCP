package mcp.commons;


public class WorkingDirectories
{
	private static String workingDirectory;
	private static String scanDataDirectory;
	private static String resumeDirectory;
	private static String inputFileDirectory;

	private WorkingDirectories()
	{
	}


	public static String getWorkingDirectory()
	{
		return workingDirectory;
	}


	public static void setWorkingDirectory(String workingDirectory)
	{
		WorkingDirectories.workingDirectory = workingDirectory;
	}


	public static String getScanDataDirectory()
	{
		return scanDataDirectory;
	}


	public static void setScanDataDirectory(String scanDataDirectory)
	{
		WorkingDirectories.scanDataDirectory = scanDataDirectory;
	}


	public static String getResumeDirectory()
	{
		return resumeDirectory;
	}


	public static void setResumeDirectory(String resumeDirectory)
	{
		WorkingDirectories.resumeDirectory = resumeDirectory;
	}


	public static String getInputFileDirectory()
	{
		return inputFileDirectory;
	}


	public static void setInputFileDirectory(String inputFileDirectory)
	{
		WorkingDirectories.inputFileDirectory = inputFileDirectory;
	}
}
