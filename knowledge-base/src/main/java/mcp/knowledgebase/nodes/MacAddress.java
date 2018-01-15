package mcp.knowledgebase.nodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.*;

public class MacAddress extends Node
{
	private final static Logger logger = LoggerFactory.getLogger(MacAddress.class);

	private final byte[] addresss;

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


	public MacAddress(byte[] address)
	{
		super();
		this.addresss = address;
	}


	public MacAddress(UUID uuid, Timestamp creationTime, byte[] address)
	{
		super(uuid, creationTime);
		this.addresss = address;
	}


	public byte[] getAddresss()
	{
		return addresss;
	}
}
