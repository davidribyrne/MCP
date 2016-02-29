package mcp.knowledgebase.attributes;

import java.time.Instant;
import org.apache.commons.lang3.builder.ToStringBuilder;
import mcp.knowledgebase.nodes.Node;
import mcp.knowledgebase.sources.Source;

public abstract class ScoredNodeAttributeImpl extends NodeAttributeImpl implements ScoredNodeAttribute
{
	private int confidence;
	
	public ScoredNodeAttributeImpl(Instant time, Source source, Node parent)
	{
		super(time, source, parent);
	}

	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.ScoredNodeAttribute#getConfidence()
	 */
	@Override
	public int getConfidence()
	{
		return confidence;
	}

	public void setConfidence(int confidence)
	{
		this.confidence = confidence;
	}

	
	@Override
	public String toString()
	{
		return new ToStringBuilder(this).appendSuper(super.toString()).append("confidence", confidence).build();
	}


}