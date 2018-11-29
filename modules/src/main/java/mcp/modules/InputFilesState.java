package mcp.modules;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;
import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.commons.PersistedObject;
import space.dcce.commons.general.FileUtils;
import space.dcce.commons.general.UnexpectedException;

public class InputFilesState implements PersistedObject
{
	private final static Logger logger = LoggerFactory.getLogger(InputFilesState.class);

	transient private WatchService fileWatcher;
	
	@Override
	public String toString()
	{
		return new ToStringBuilder(this)
				.toString();
	}


	public InputFilesState()
	{
		try
		{
			fileWatcher = FileSystems.getDefault().newWatchService();
		}
		catch (IOException e)
		{
			logger.error("This shouldn't have happened: " + e.toString());
			throw new UnexpectedException(e);
		}
	}
	
	public void register(Path path) throws IOException
	{
		Collection<File> files = FileUtils.listFiles(path.toFile(), null, false);
		for(File file: files)
		{
			//TODO finish
		}
		path.register(fileWatcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
	}

	@Override
	public boolean initialized()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void saveToDisk()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String getOutputFilename()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
