package mcp.knowledgebase.attributes.port;


import mcp.knowledgebase.attributes.NodeAttribute;
import mcp.knowledgebase.attributes.ScoredNodeAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public interface ServiceDescription extends ScoredNodeAttribute
{

	public String getAppProtocolName();


	public ServiceReason getReason();

}
