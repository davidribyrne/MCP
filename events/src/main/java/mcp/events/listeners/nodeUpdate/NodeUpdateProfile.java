package mcp.events.listeners.nodeUpdate;
import java.util.*;

public class NodeUpdateProfile
{

	private final List<NodeType> monitorCreated;
	private final List<NodeType> monitorEdited;
	

	public NodeUpdateProfile()
	{
		monitorCreated = new ArrayList<NodeType>(1);
		monitorEdited = new ArrayList<NodeType>(1);
	}
	
	public void addMonitorCreated(NodeType nodeType)
	{
		monitorCreated.add(nodeType);
	}

	public void addMonitorEdited(NodeType nodeType)
	{
		monitorEdited.add(nodeType);
	}

	public List<NodeType> getMonitorCreated()
	{
		return monitorCreated;
	}

	public List<NodeType> getMonitorEdited()
	{
		return monitorEdited;
	}
	
	
}
