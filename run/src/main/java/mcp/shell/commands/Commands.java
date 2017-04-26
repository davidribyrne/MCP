<<<<<<< Updated upstream
package mcp.shell.commands;

import org.crsh.command.BaseCommand;
import org.crsh.shell.impl.command.ExternalResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

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
=======
package mcp.shell.commands;

import org.crsh.command.BaseCommand;
import org.crsh.shell.impl.command.ExternalResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

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
>>>>>>> Stashed changes
