package mcp.knowledgebase.nodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dacce.commons.netaddr.SimpleInet4Address;

import java.sql.Timestamp;
import java.util.*;

public class Ip4Address extends Node
{
	private final static Logger logger = LoggerFactory.getLogger(Ip4Address.class);

	private final SimpleInet4Address address;
	
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


	public Ip4Address(SimpleInet4Address address)
	{
		super();
		this.address = address;
	}


	public Ip4Address(UUID uuid, Timestamp creationTime, SimpleInet4Address address)
	{
		super(uuid, creationTime);
		this.address = address;
	}


	public SimpleInet4Address getAddress()
	{
		return address;
	}
}
