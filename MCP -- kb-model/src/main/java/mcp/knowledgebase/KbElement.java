package mcp.knowledgebase;

import java.util.List;
import mcp.knowledgebase.nodes.Node;

public interface KbElement
{
	public List<String> getNotes();
	public void addNote(String note);
	public Node getParent();
	public void addSecondaryParent(Node parent);
	public List<Node> getSecondaryParents();
}
