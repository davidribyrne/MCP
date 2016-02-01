package mcp.knowledgebase.nodes;

import mcp.knowledgebase.KbElementImpl;


public abstract class NodeImpl extends KbElementImpl implements Node
{

	public NodeImpl(Node parent)
	{
		super(parent);
	}
	
}
