package mcp.knowledgebase.nodes;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum PortType
{
	
	UDP, TCP, IP, STCP, UNKNOWN;

	final static Logger logger = LoggerFactory.getLogger(PortType.class);
	public static PortType fromNmap(String name)
	{
		switch (name.toLowerCase())
		{
			case "udp":
				return UDP;
			case "tcp":
				return TCP;
			case "ip":
				return IP;
			case "stcp":
				return STCP;
		}
		logger.warn("The Nmap port type '" + name + "' is unknown.");
		return UNKNOWN;
	}
}
