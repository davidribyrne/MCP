package mcp.knowledgebase.nodes;

import mcp.knowledgebase.attributes.URL;

public interface Website extends Node
{
	public Iterable<Port> getServices();
	public Iterable<URL> getURLs();
}
