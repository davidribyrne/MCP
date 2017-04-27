package mcp.modules.parsers;

import java.util.ArrayList;
import java.util.List;

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
