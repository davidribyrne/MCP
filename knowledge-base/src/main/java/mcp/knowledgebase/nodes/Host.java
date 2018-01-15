package mcp.knowledgebase.nodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.*;

public class Host extends Node
{
	private final static Logger logger = LoggerFactory.getLogger(Host.class);


	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		return sb.toString();
	}


	@Override
	public int hashCode()
	{
		return super.hashCode();
	}


	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj);
	}


	public Host()
	{
	}


	public Host(UUID uuid, Timestamp creationTime)
	{
		super(uuid, creationTime);
	}
}
