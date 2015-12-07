package mcp.knowledgebase.attributes.other;

import mcp.knowledgebase.attributes.NodeAttributeImpl;
import mcp.knowledgebase.sources.Source;
import net.dacce.commons.netaddr.SimpleInetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.*;

public class ResolvedAddressImpl extends NodeAttributeImpl implements ResolvedAddress
{
	private final SimpleInetAddress address;

	public ResolvedAddressImpl(Instant time, Source source, SimpleInetAddress address)
	{
		super(time, source);
		this.address = address;
	}

	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.other.ResolvedAddress#getAddress()
	 */
	@Override
	public SimpleInetAddress getAddress()
	{
		return address;
	}
}
