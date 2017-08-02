package mcp.knowledgebase;

import java.io.Serializable;

import mcp.knowledgebase.nodes.Node;

public interface KbElement extends Serializable
{
	public Iterable<String> getNotes();
	public void addNote(String note);
	public Node getParent();
	public void addSecondaryParent(Node parent);
	public Iterable<Node> getSecondaryParents();
}
