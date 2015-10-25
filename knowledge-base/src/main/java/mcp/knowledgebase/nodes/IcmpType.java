package mcp.knowledgebase.nodes;

public enum IcmpType
{
	ECHO(8), TIMESTAMP(14), MASK(18);

	private final int code;


	IcmpType(int code)
	{
		this.code = code;
	}


	public int getCode()
	{
		return code;
	}

}
