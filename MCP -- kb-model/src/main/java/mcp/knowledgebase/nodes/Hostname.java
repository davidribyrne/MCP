package mcp.knowledgebase.nodes;

import java.util.List;
import net.dacce.commons.netaddr.SimpleInetAddress;


public interface Hostname extends Node
{

	public void addAddress(AddressNode address);

	public String getName();

	public List<AddressNode> getAddresses();

}
