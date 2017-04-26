<<<<<<< Updated upstream
package mcp.shell.commands;

import org.crsh.cli.Argument;
import org.crsh.cli.*;
import org.crsh.cli.Option;
import org.crsh.cli.Usage;
import org.crsh.command.BaseCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.KnowledgeBaseImpl;
import mcp.knowledgebase.nodes.IPAddress;
import mcp.modules.Modules;
import mcp.modules.SimpleKbDumper;

import java.util.*;
@Usage("Knowledge Base interaction\n"
		+ "commands: dump list")
@Man("The kb command allows the user to display knowledge base content")
public class kb extends BaseCommand
{
	private final static Logger logger = LoggerFactory.getLogger(kb.class);

	public static class CommandCompleter extends ListCompleter
	{
		public CommandCompleter()
		{
			super(new String[]{"list"});
		}
	}

	public static class SectionCompleter extends ListCompleter
	{
		public SectionCompleter()
		{
			super(new String[]{"addresses", "hostnames"});
		}
	}

	@Command
	@Named("dump")
	public Object dump()
	{
		SimpleKbDumper dumper = (SimpleKbDumper) Modules.getInstance().getModuleInstance(SimpleKbDumper.class);
		if (dumper != null)
		{
			return dumper.getKBDump();
		}
		else
		{
			String message = "Failed to get SimpleKbDumper instance.";
			logger.error(message);
			return message;
		}
	}
	
	@Command
	@Named("list")
	public Object list(@Argument(name = "Section", completer = SectionCompleter.class) @Usage("sections: addresses, hostnames") String section)
	{
		StringBuffer sb  = new StringBuffer();
//		if ("list".equalsIgnoreCase(command))
		{
			if ("addresses".equalsIgnoreCase(section))
			{
				for(IPAddress iPAddress: KnowledgeBaseImpl.getInstance().getIPAddressNodes())
				{
					sb.append(iPAddress.getAddress().toString()).append("\n");
				}
			}
		}
		return sb.toString();
	}
	
}
=======
package mcp.shell.commands;

import org.crsh.cli.Argument;
import org.crsh.cli.*;
import org.crsh.cli.Option;
import org.crsh.cli.Usage;
import org.crsh.command.BaseCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.KnowledgeBaseImpl;
import mcp.knowledgebase.nodes.IPAddress;
import mcp.modules.Modules;
import mcp.modules.SimpleKbDumper;

import java.util.*;
@Usage("Knowledge Base interaction\n"
		+ "commands: dump list")
@Man("The kb command allows the user to display knowledge base content")
public class kb extends BaseCommand
{
	private final static Logger logger = LoggerFactory.getLogger(kb.class);

	public static class CommandCompleter extends ListCompleter
	{
		public CommandCompleter()
		{
			super(new String[]{"list"});
		}
	}

	public static class SectionCompleter extends ListCompleter
	{
		public SectionCompleter()
		{
			super(new String[]{"addresses", "hostnames"});
		}
	}

	@Command
	@Named("dump")
	public Object dump()
	{
		SimpleKbDumper dumper = (SimpleKbDumper) Modules.getInstance().getModuleInstance(SimpleKbDumper.class);
		if (dumper != null)
		{
			return dumper.getKBDump();
		}
		else
		{
			String message = "Failed to get SimpleKbDumper instance.";
			logger.error(message);
			return message;
		}
	}
	
	@Command
	@Named("list")
	public Object list(@Argument(name = "Section", completer = SectionCompleter.class) @Usage("sections: addresses, hostnames") String section)
	{
		StringBuffer sb  = new StringBuffer();
//		if ("list".equalsIgnoreCase(command))
		{
			if ("addresses".equalsIgnoreCase(section))
			{
				for(IPAddress iPAddress: KnowledgeBaseImpl.getInstance().getIPAddressNodes())
				{
					sb.append(iPAddress.getAddress().toString()).append("\n");
				}
			}
		}
		return sb.toString();
	}
	
}
>>>>>>> Stashed changes
