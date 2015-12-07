package mcp.knowledgebase.attributes.port;

import java.time.Instant;

import mcp.knowledgebase.attributes.NodeAttributeImpl;
import mcp.knowledgebase.attributes.ScoredNodeAttributeImpl;
import mcp.knowledgebase.sources.Source;


public class SoftwareGuessImpl extends ScoredNodeAttributeImpl implements SoftwareGuess
{
	private final String product;
	private final String vendor;
	private final String version;
	
	
	public SoftwareGuessImpl(Instant time, Source source, String product, String vendor, String version)
	{
		super(time, source);
		this.product = product;
		this.vendor = vendor;
		this.version = version;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.port.SoftwareGuess#getProduct()
	 */
	@Override
	public String getProduct()
	{
		return product;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.port.SoftwareGuess#getVendor()
	 */
	@Override
	public String getVendor()
	{
		return vendor;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.port.SoftwareGuess#getVersion()
	 */
	@Override
	public String getVersion()
	{
		return version;
	}


}
