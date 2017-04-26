<<<<<<< Updated upstream
package mcp.knowledgebase.nodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public interface MacAddress extends Node
{

	public byte[] getMacAddress();

	public String getMacVendor();

	public void setMacVendor(String macVendor);

	public List<IPAddress> getIPAddresses();
	
	public void addIPAddress(IPAddress ipAddress);
	
	public Host getHost();
	
	public void setHost(Host host);
}
=======
package mcp.knowledgebase.nodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public interface MacAddress extends Node
{

	public byte[] getMacAddress();

	public String getMacVendor();

	public void setMacVendor(String macVendor);

	public List<IPAddress> getIPAddresses();
	
	public void addIPAddress(IPAddress ipAddress);
	
	public Host getHost();
	
	public void setHost(Host host);
}
>>>>>>> Stashed changes
