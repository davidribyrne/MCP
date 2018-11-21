package mcp.knowledgebase.primitives;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class ByteArray extends Primitive
{
	private byte[] value;

	public byte[] getValue()
	{
		return value;
	}

	public ByteArray(byte[] value)
	{
		super();
		this.value = value;
	}

	public void setValue(byte[] value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return new String(value);
	}
}

