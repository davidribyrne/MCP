package mcp.knowledgebase.attributes;

import mcp.knowledgebase.sources.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.*;

public abstract class NodeAttributeImpl implements NodeAttribute
{
	private final static Logger logger = LoggerFactory.getLogger(NodeAttributeImpl.class);

	private final Instant time;
	private final Source source;


	public NodeAttributeImpl(Instant time, Source source)
	{
		this.time = time;
		this.source = source;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.NodeAttribute#getTime()
	 */
	@Override
	public Instant getTime()
	{
		return time;
	}




	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.NodeAttribute#getSource()
	 */
	@Override
	public Source getSource()
	{
		return source;
	}


	
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


}
