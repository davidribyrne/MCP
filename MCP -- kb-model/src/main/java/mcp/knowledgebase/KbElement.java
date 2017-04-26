package mcp.knowledgebase;

import java.util.List;
import mcp.knowledgebase.nodes.Node;

public interface KbElement
{
	public Iterable<String> getNotes();
	public void addNote(String note);
	public Node getParent();
	public void addSecondaryParent(Node parent);
	public Iterable<Node> getSecondaryParents();
}
