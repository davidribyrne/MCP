package mcp.knowledgebase.nodes;


import mcp.knowledgebase.attributes.port.PortState;
import mcp.knowledgebase.attributes.port.ServiceDescription;
import mcp.knowledgebase.attributes.port.SoftwareGuess;


public interface Port extends Node
{

	public void setServiceDescription(ServiceDescription description);


	public void setSoftwareGuess(SoftwareGuess guess);


	public PortType getType();


	public int getNumber();


	public IPAddress getAddressNode();


//	public AttributeHistory<PortState> getStateHistory();

	
	public void setState(PortState state);


//	public ScoredAttributeHistory<ServiceDescription> getServiceDescription();


//	public ScoredAttributeHistory<SoftwareGuess> getSoftwareGuess();

}
