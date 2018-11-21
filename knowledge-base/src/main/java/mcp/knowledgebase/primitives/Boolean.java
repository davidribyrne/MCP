package mcp.knowledgebase.primitives;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class Boolean extends Primitive
{
	private boolean value;

	public Boolean getValue()
	{
		return new Boolean(value);
	}

	public Boolean(boolean value)
	{
		super();
		this.value = value;
	}

	public void setValue(boolean value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return value + "";
	}
	
}
