package mcp.knowledgebase.nodes;

import java.util.List;

import net.dacce.commons.general.UniqueList;


public abstract class Node
{
	private final UniqueList<String> notes;


	public Node()
	{
		notes = new UniqueList<String>(1);
	}


	public List<String> getNotes()
	{
		return notes;
	}


	public void addNode(String note)
	{
		notes.add(note);
	}
}
