package mcp.knowledgebase.attributes.host;

public enum IcmpResponseType
{
	ECHO(8), TIMESTAMP(14), MASK(18);

	private final int code;


	IcmpResponseType(int code)
	{
		this.code = code;
	}


	public int getCode()
	{
		return code;
	}

}
