package mcp.knowledgebase.nodes;


import mcp.knowledgebase.attributes.AttributeHistory;
import mcp.knowledgebase.attributes.ScoredAttributeHistory;
import mcp.knowledgebase.attributes.port.PortState;
import mcp.knowledgebase.attributes.port.ServiceDescription;
import mcp.knowledgebase.attributes.port.SoftwareGuess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public interface Port extends Node
{

	public void setServiceDescription(ServiceDescription description);


	public void setSoftwareGuess(SoftwareGuess guess);


	public PortType getType();


	public int getNumber();


	public Host getHost();


	public AttributeHistory<PortState> getStateHistory();

	
	public void addState(PortState state);


	public ScoredAttributeHistory<ServiceDescription> getServiceDescription();


	public ScoredAttributeHistory<SoftwareGuess> getSoftwareGuess();

}
