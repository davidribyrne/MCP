package mcp.knowledgebase.nodeLibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.nodes.Node;

import java.util.*;

public enum PortState
{
	OPEN("open"), 
	OPEN_FILTERED("open|filtered"), 
	FILTERED("filtered"),
	CLOSED("closed"),
	WRAPPED("wrapped"),
	UNKNOWN("unknown");
	
	
	
	private String description;
	
	private PortState(String description)
	{
		this.description = description;
	}
	


	public static PortState parseNmapText(String text)
	{
		switch (text.toLowerCase())
		{
			case "open":
				return OPEN;
			case "open|filtered":
				return OPEN_FILTERED;
			case "filtered":
				return FILTERED;
			case "closed":
				return CLOSED;
			case "wrapped":
				return WRAPPED;
		}
		return UNKNOWN;
	}



	public String getDescription()
	{
		return description;
	}


}
