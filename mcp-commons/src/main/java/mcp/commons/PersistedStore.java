package mcp.commons;

import java.util.List;
import net.dacce.commons.general.UniqueList;

public class PersistedStore
{

	private final PersistedStore instance = new PersistedStore();

	private final List<PersistedObject> objects;
	private PersistedStore()
	{
		objects = new UniqueList<PersistedObject>(false);
	}

	public void add(PersistedObject object)
	{
		objects.add(object);
		UniqueFilenameRegistrar.registerFilename(object.getOutputFilename());
	}
	

	public PersistedStore getInstance()
	{
		return instance;
	}



	public List<PersistedObject> getObjects()
	{
		return objects;
	}


}
