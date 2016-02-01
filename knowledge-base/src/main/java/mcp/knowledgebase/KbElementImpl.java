package mcp.knowledgebase;

import java.util.List;
import mcp.events.EventDispatcher;
import mcp.events.events.ElementCreationEvent;
import mcp.knowledgebase.nodes.Node;
import net.dacce.commons.general.SlimList;


public class KbElementImpl implements KbElement
{

	private final SlimList<String> notes;
	private final SlimList<Node> secondaryParents;
	private final Node parent;

	public KbElementImpl(Node parent)
	{
		this.parent = parent;
		notes = new SlimList<String>();
		secondaryParents = new SlimList<Node>();
	}

	protected void signalCreation()
	{
		ElementCreationEvent event = new ElementCreationEvent(this);
		EventDispatcher.getInstance().signalEvent(event);
	}

	@Override
	public List<String> getNotes()
	{
		return notes;
	}


	@Override
	public void addNote(String note)
	{
		notes.add(note);
	}

	@Override
	public Node getParent()
	{
		return parent;
	}

	@Override
	public void addSecondaryParent(Node parent)
	{
		secondaryParents.add(parent);		
	}

	@Override
	public List<Node> getSecondaryParents()
	{
		return secondaryParents;
	}

}
