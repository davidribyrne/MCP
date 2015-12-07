package mcp.knowledgebase.nodes;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;


public interface Node
{

	public List<String> getNotes();


	public void addNote(String note);

}
