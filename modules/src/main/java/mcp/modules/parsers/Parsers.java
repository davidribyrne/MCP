package mcp.modules.parsers;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class Parsers
{
	private static final Parsers instance = new Parsers();
	private List<Parser> parsers;
	
	private Parsers()
	{
		parsers = new ArrayList<Parser>(1);
		parsers.add(NmapParser.getInstance());
	}
	
	public List<Parser> getParsers()
	{
		return parsers;
	}

	public static Parsers getInstance()
	{
		return instance;
	}
}
