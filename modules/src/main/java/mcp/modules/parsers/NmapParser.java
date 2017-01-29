package mcp.modules.parsers;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.dacce.commons.cli.OptionContainer;
import java.io.File;
import java.util.*;

public class NmapParser implements Parser
{
	private final static Logger logger = LoggerFactory.getLogger(NmapParser.class);
	private final static NmapParser instance = new NmapParser();
	
	public static NmapParser getInstance()
	{
		return instance;
	}
	
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
}
