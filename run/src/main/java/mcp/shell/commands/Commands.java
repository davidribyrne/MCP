package mcp.shell.commands;

import org.crsh.shell.impl.command.ExternalResolver;

public class Commands
{
	
	private Commands()
	{
	}

	public static void loadCommands()
	{
		ExternalResolver.INSTANCE.addCommand("date", "show the date", date.class);
		ExternalResolver.INSTANCE.addCommand("ps", "show jobs", ps.class);
		ExternalResolver.INSTANCE.addCommand("kb", "display knowledge base contents", kb.class);
	}

}
