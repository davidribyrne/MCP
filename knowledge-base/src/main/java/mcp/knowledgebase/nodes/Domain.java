package mcp.knowledgebase.nodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.*;

public class Domain extends Node
{
	private final static Logger logger = LoggerFactory.getLogger(Domain.class);

	private final String domain;

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


	public Domain(String domain)
	{
		this.domain = domain;
	}


	public Domain(UUID uuid, Timestamp creationTime, String domain)
	{
		super(uuid, creationTime);
		this.domain = domain;
	}


	public String getDomain()
	{
		return domain;
	}
}
