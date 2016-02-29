package mcp.commons;

import java.util.List;
import net.dacce.commons.general.UniqueList;

public class PersistedObjects
{

	private final PersistedObjects instance = new PersistedObjects();

	private final List<PersistedObject> objects;
	private PersistedObjects()
	{
		objects = new UniqueList<PersistedObject>();
	}

	public void add(PersistedObject object)
	{
		objects.add(object);
		UniqueFilenameRegistrar.registerFilename(object.getOutputFilename());
	}
	

	public PersistedObjects getInstance()
	{
		return instance;
	}



	public List<PersistedObject> getObjects()
	{
		return objects;
	}


}
