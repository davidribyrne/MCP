package mcp.modules;

public abstract class Module
{
	public abstract void initialize();
	private final String name;
	protected Module(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	

}
