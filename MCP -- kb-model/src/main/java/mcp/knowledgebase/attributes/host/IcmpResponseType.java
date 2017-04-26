package mcp.knowledgebase.attributes.host;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum IcmpResponseType
{
	/**
	 * ARP obviously isn't ICMP, but nmap handles it the same way
	 */
	ARP(-1), LOCALHOST(-2), ECHO(8), TIMESTAMP(14), MASK(18);
	final static Logger logger = LoggerFactory.getLogger(IcmpResponseType.class);
	private final int code;


	IcmpResponseType(int code)
	{
		this.code = code;
	}


	public int getCode()
	{
		return code;
	}
	
	public static IcmpResponseType parseFromNmap(String reason)
	{
		switch (reason.toLowerCase())
		{
			case "user-set":
			case "no-response":
				return null;
			case "echo-reply": 
				return ECHO;
			case "arp-response":
				return ARP;
			case "localhost-response":
				return LOCALHOST;
		}
		logger.warn("The nmap status reason '" + reason + "' is unknown.");
		return null;
	}
}
