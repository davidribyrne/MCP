package mcp.knowledgebase.attributes;

import java.time.Instant;
import mcp.knowledgebase.KbElementImpl;
import mcp.knowledgebase.nodes.Node;
import mcp.knowledgebase.sources.Source;

public abstract class NodeAttributeImpl extends KbElementImpl implements NodeAttribute
{
	private final Instant time;
	private final Source source;

	public NodeAttributeImpl(Instant time, Source source, Node parent)
	{
		super(parent);
		this.time = time;
		this.source = source;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.NodeAttribute#getTime()
	 */
	@Override
	public Instant getTime()
	{
		return time;
	}




	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.NodeAttribute#getSource()
	 */
	@Override
	public Source getSource()
	{
		return source;
	}

}
