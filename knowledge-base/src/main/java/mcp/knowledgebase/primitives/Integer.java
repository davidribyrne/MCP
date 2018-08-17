package mcp.knowledgebase.primitives;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class Integer extends Primitive
{
	private long value;

	public Integer(long value)
	{
		super();
		this.value = value;
	}

	public Long getValue()
	{
		return new Long(value);
	}

	public void setValue(long value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return value + "";
	}
	
}
