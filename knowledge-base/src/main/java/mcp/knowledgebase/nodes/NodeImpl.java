package mcp.knowledgebase.nodes;

import javax.persistence.Entity;

import mcp.knowledgebase.KbElementImpl;

@Entity
public abstract class NodeImpl extends KbElementImpl implements Node
{
	public NodeImpl(Node parent)
	{
		super(parent);
	}
	
	protected NodeImpl()
	{
		
	}
}
