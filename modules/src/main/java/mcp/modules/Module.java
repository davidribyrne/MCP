package mcp.modules;

public abstract class Module
{
	public abstract void initialize();
	protected abstract void initializeOptions();
	private final String name;
	protected Module(String name)
	{
		this.name = name;
		initializeOptions();
	}
	
	public String getName()
	{
		return name;
	}
	

}
