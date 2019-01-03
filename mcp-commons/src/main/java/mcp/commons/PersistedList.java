package mcp.commons;

import java.util.ArrayList;

public class PersistedList<E> extends ArrayList<E> implements PersistedObject
{


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
