package mcp.modules.parsers;

import java.io.File;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import space.dcce.commons.cli.OptionContainer;

public class NmapParser implements Parser
{
	
	private final static NmapParser instance = new NmapParser();
	
	@Override
	public String toString()
	{
		return new ToStringBuilder(this)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder()
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		return new EqualsBuilder()
				.isEquals();
	}

	public NmapParser()
	{
	}

	@Override
	public String getDir()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OptionContainer getOptions()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parse(File file)
	{
		// TODO Auto-generated method stub
		
	}

	public static NmapParser getInstance()
	{
		return instance;
	}
}
