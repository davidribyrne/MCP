package mcp.knowledgebase.nodes.snapshots;


public class HostData extends SnapshotData
{
	private boolean up;


	public boolean isUp()
	{
		return up;
	}


	public void setUp(boolean up)
	{
		this.up = up;
	}

}
