package mcp.knowledgebase.attributes.port;

public enum PortResponse
{
	OPEN, OPEN_FILTERED, FILTERED, CLOSED, WRAPPED, UNKNOWN;

	public static PortResponse parseNmapText(String text)
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
