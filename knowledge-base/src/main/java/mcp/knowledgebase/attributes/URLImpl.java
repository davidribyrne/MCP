package mcp.knowledgebase.attributes;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.nodes.Node;
import mcp.knowledgebase.nodes.Website;
import mcp.knowledgebase.sources.Source;
import net.dacce.commons.general.MapOfLists;
import net.dacce.commons.general.NotImplementedException;

import java.time.Instant;
import java.util.*;


public class URLImpl extends NodeAttributeImpl implements URL
{
	private final static Logger logger = LoggerFactory.getLogger(URLImpl.class);
	private MapOfLists<String, String> params;
	private String url;


	public URLImpl(Source source, Website website)
	{
		super(Instant.now(), source, website);
		params = new MapOfLists<String, String>(1);
		throw new NotImplementedException();
	}


	@Override
	public String toString()
	{
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.JSON_STYLE);
		sb.append("params", params);
		return sb.toString();
	}


	@Override
	public int hashCode()
	{
		HashCodeBuilder hb = new HashCodeBuilder();
		hb.append(params.hashCode());
		return hb.toHashCode();
	}


	@Override
	public boolean equals(Object obj)
	{
		if (! (obj instanceof URLImpl))
			return false;
		
		URLImpl other = (URLImpl) obj; 
		EqualsBuilder eb = new EqualsBuilder();
//		eb.append(this., rhs)
		return false;
	}


	public URLImpl(Instant time, Source source, Node parent)
	{
		super(time, source, parent);
	}


	@Override
	public Iterable<String> getMethods()
	{
		return params.keySet();
	}


	@Override
	public Iterable<String> getParameters(String method)
	{
		return Collections.unmodifiableList(params.get(method));
	}
}
