package mcp.knowledgebase.attributes.host;

import mcp.knowledgebase.attributes.NodeAttributeImpl;
import mcp.knowledgebase.sources.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.*;

public class IcmpResponseImpl extends NodeAttributeImpl implements IcmpResponse
{
	private final byte[] data;
	
	public IcmpResponseImpl(Instant time, Source source, byte[] data)
	{
		super(time, source);
		this.data = data;
	}

	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.host.IcmpResponse#getData()
	 */
	@Override
	public byte[] getData()
	{
		return data;
	}



}
