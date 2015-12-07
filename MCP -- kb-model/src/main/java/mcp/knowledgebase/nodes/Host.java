package mcp.knowledgebase.nodes;

import java.util.Collection;
import java.util.Map;

import mcp.knowledgebase.attributes.host.OSGuess;
import net.dacce.commons.netaddr.SimpleInetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public interface Host extends Node
{

	public Map<Integer, Port> getTcpPorts();


	public Map<Integer, Port> getUdpPorts();


	public void addOSGuess(OSGuess guess);


	public Collection<Hostname> getHostnames();


	public Port getOrCreatePort(PortType type, int number);


	public SimpleInetAddress getAddress();


	public boolean isUp();


	public byte[] getMacAddress();


	public void setMacAddress(byte[] macAddress);


	public String getMacVendor();


	public void setMacVendor(String macVendor);


	public void setUp(boolean up);

}
