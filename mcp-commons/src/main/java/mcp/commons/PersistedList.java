package mcp.commons;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistedList<E> extends ArrayList<E> implements PersistedObject
{
	private final static Logger logger = LoggerFactory.getLogger(PersistedList.class);


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
