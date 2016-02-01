package mcp.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public interface PersistedObject
{
	public boolean initialized();
	public void saveToDisk();
	public String getOutputFilename();
}
