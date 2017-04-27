package mcp.knowledgebase.nodes;

public interface HttpRequest extends Node
{
	public Iterable<String> getParameters();
}
