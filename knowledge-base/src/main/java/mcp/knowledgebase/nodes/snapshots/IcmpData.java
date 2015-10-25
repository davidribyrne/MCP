package mcp.knowledgebase.nodes.snapshots;

import mcp.knowledgebase.nodes.IcmpType;


public class IcmpData extends SnapshotData
{
	private IcmpType type;


	public IcmpData()
	{
	}


	public IcmpType getType()
	{
		return type;
	}


	public void setType(IcmpType type)
	{
		this.type = type;
	}

}
