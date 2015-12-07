package mcp.knowledgebase.nodes;

import net.dacce.commons.netaddr.SimpleInetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public interface Hostname extends Node
{

	public void addAddress(SimpleInetAddress address);


	public String getName();


	public boolean containsAddress(SimpleInetAddress address);

}
