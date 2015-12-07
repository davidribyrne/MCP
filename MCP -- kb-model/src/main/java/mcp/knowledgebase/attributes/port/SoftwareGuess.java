package mcp.knowledgebase.attributes.port;


import mcp.knowledgebase.attributes.ScoredNodeAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public interface SoftwareGuess extends ScoredNodeAttribute
{

	public String getProduct();


	public String getVendor();


	public String getVersion();

}
