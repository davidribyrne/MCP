package mcp.knowledgebase.nodes;

import java.util.List;

import mcp.events.EventDispatcher;
import mcp.events.events.NodeUpdateEvent;
import mcp.events.listeners.NodeUpdateType;
import mcp.events.listeners.nodeUpdate.UpdateAction;
import mcp.knowledgebase.nodes.Node;
import net.dacce.commons.general.UniqueList;


public abstract class NodeImpl implements Node
{
	private final UniqueList<String> notes;


	public NodeImpl()
	{
		notes = new UniqueList<String>(1);
	}

	protected void signalCreation()
	{
		NodeUpdateEvent event = new NodeUpdateEvent(this, UpdateAction.CREATED);
		EventDispatcher.getInstance().signalEvent(event);
	}

	protected void signalEdit()
	{
		NodeUpdateEvent event = new NodeUpdateEvent(this, UpdateAction.EDITED);
		EventDispatcher.getInstance().signalEvent(event);
	}

	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Node#getNotes()
	 */
	@Override
	public List<String> getNotes()
	{
		return notes;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Node#addNote(java.lang.String)
	 */
	@Override
	public void addNote(String note)
	{
		notes.add(note);
	}
}
