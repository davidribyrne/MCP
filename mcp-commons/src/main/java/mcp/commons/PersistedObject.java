package mcp.commons;

public interface PersistedObject
{
	public boolean initialized();
	public void saveToDisk();
	public String getOutputFilename();
}
