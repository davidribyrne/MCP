package mcp.commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

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
