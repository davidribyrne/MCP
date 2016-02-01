package mcp.knowledgebase.attributes;

import java.time.Instant;
import mcp.knowledgebase.KbElement;
import mcp.knowledgebase.sources.Source;


public interface NodeAttribute extends KbElement
{

	public Instant getTime();
	public Source getSource();
}
