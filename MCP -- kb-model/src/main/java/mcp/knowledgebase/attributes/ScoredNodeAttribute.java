package mcp.knowledgebase.attributes;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;


public interface ScoredNodeAttribute extends NodeAttribute
{

	public int getConfidence();

}
