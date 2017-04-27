package mcp.knowledgebase.attributes;

public interface URL extends NodeAttribute
{
	public Iterable<String> getMethods();
	public Iterable<String> getParameters(String method);
}
