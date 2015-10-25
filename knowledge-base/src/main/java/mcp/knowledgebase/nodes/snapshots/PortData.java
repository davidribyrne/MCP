package mcp.knowledgebase.nodes.snapshots;

import mcp.knowledgebase.nodes.PortState;


public class PortData extends SnapshotData
{
	private PortState state;


	public PortState getState()
	{
		return state;
	}


	public void setState(PortState state)
	{
		this.state = state;
	}

}
