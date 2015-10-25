package mcp.knowledgebase.nodes;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public enum ServiceReason
{
	UNKNOWN(0), TABLE(1), SCAN(2), INTERACTION(3);

	private final int score;
	final static Logger logger = LoggerFactory.getLogger(ServiceReason.class);


	ServiceReason(int score)
	{
		this.score = score;
	}


	public int getScore()
	{
		return score;
	}


	public static ServiceReason fromNmap(String reason)
	{
		switch (reason.toLowerCase())
		{
			case "table":
				return TABLE;
			case "probe":
				return SCAN;
		}
		logger.warn("The Nmap service reson '" + reason + "' is unknown.");
		
		return UNKNOWN;
	}
}
