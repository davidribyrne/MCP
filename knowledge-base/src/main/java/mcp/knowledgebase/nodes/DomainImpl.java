package mcp.knowledgebase.nodes;

import mcp.knowledgebase.nodes.Domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DomainImpl extends NodeImpl implements Domain
{

	private final String name;

	@Override
	public String toString()
	{
		return name;
	}


	@Override
	public int hashCode()
	{
		return name.hashCode();
	}


	@Override
	public boolean equals(Object obj)
	{
		return name.equals(obj);
	}


	public DomainImpl(String name)
	{
		this.name = name;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Domain#getName()
	 */
	@Override
	public String getName()
	{
		return name;
	}
}
