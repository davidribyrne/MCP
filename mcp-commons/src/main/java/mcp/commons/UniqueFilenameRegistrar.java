package mcp.commons;

import java.util.HashSet;
import java.util.Set;

/**
 * Enforces unique filenames
 * @author dbyrne
 *
 */
public class UniqueFilenameRegistrar
{

	private static final Set<String> filenames = new HashSet<String>();
	
	private UniqueFilenameRegistrar()
	{
		
	}
	
	public synchronized static String registerFilename(String filename)
	{
		if (filenames.contains(filename))
		{
			throw new IllegalStateException("Duplicate filename: " + filename);
		}
		filenames.add(filename);
		return filename;
	}
	
}
