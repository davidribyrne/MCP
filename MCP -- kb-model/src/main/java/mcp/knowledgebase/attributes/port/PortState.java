package mcp.knowledgebase.attributes.port;


import mcp.knowledgebase.attributes.NodeAttribute;
import mcp.knowledgebase.nodes.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public interface PortState extends NodeAttribute
{

	public PortResponse getResponse();


	public PortStateReason getReason();

}
