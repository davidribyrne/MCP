package mcp.knowledgebase.nodes;

public enum PortState
{
	OPEN, OPEN_FILTERED, FILTERED, CLOSED, WRAPPED, UNKNOWN;

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
}
