<<<<<<< Updated upstream
package mcp.knowledgebase.nodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.attributes.URL;

import java.util.*;

public class WebsiteImpl extends NodeImpl implements Website
{
	private final static Logger logger = LoggerFactory.getLogger(WebsiteImpl.class);


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


	public WebsiteImpl(Node parent)
	{
		super(parent);
	}


	@Override
	public Iterable<Port> getServices()
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Iterable<URL> getURLs()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
=======
package mcp.knowledgebase.nodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.attributes.URL;

import java.util.*;

public class WebsiteImpl extends NodeImpl implements Website
{
	private final static Logger logger = LoggerFactory.getLogger(WebsiteImpl.class);


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


	public WebsiteImpl(Node parent)
	{
		super(parent);
	}


	@Override
	public Iterable<Port> getServices()
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Iterable<URL> getURLs()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
>>>>>>> Stashed changes
