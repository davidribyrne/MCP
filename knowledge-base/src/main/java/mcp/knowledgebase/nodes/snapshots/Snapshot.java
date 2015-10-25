package mcp.knowledgebase.nodes.snapshots;

import java.time.Instant;

import javax.xml.transform.Source;


public abstract class Snapshot<Data extends SnapshotData>
{
	private Instant time;
	private Source source;


	public Snapshot(Instant time, Source source)
	{
		this.time = time;
		this.source = source;
	}


	public Instant getTime()
	{
		return time;
	}


	public void setTime(Instant time)
	{
		this.time = time;
	}


	public Source getSource()
	{
		return source;
	}


	public void setSource(Source source)
	{
		this.source = source;
	}
}
