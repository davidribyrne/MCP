package mcp.knowledgebase.nodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.nodeLibrary.PortState;

import java.sql.Timestamp;
import java.util.*;

public abstract class Port extends Node
{
	private final static Logger logger = LoggerFactory.getLogger(Port.class);

	private final int number;
	private PortState state;
	private PortStateReason portStateReason;
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		return sb.toString();
	}


	@Override
	public int hashCode()
	{
		return super.hashCode();
	}


	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj);
	}


	public Port(int number)
	{
		super();
		this.number = number;
	}


	public Port(UUID uuid, Timestamp creationTime, int number)
	{
		super(uuid, creationTime);
		this.number = number;
	}


	public int getNumber()
	{
		return number;
	}


	public PortState getState()
	{
		return state;
	}


	public void setState(PortState state)
	{
		this.state = state;
	}


	public PortStateReason getPortStateReason()
	{
		return portStateReason;
	}


	public void setPortStateReason(PortStateReason portStateReason)
	{
		this.portStateReason = portStateReason;
	}
}
