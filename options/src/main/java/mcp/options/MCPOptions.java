package mcp.options;

import java.util.ArrayList;
import java.util.List;

import space.dcce.commons.cli.GnuParser;
import space.dcce.commons.cli.HelpFormatter;
import space.dcce.commons.cli.Option;
import space.dcce.commons.cli.OptionContainer;
import space.dcce.commons.cli.OptionGroup;
import space.dcce.commons.cli.RootOptions;
import space.dcce.commons.cli.exceptions.ParseException;
import space.dcce.commons.general.CollectionUtils;


public class MCPOptions
{
	public final static MCPOptions instance = new MCPOptions();
	private RootOptions rootOptions;
	private boolean parsed = false;

	private Option help;


	private MCPOptions()
	{
		rootOptions = new RootOptions();
		help = rootOptions.addOption("h", "help", "Print this message.");
	}


	public void parseCommandline(String args[])
	{
		try
		{
			GnuParser.parse(rootOptions, args, false);
			parsed = true;
		}
		catch (ParseException e)
		{
			printHelp();
			System.out.println("\nProblem with command line option: " + e.getMessage());
			System.exit(1);
		}
		if ((args.length == 0) || help.isEnabled())
		{
			printHelp();
			System.exit(0);
		}
	}


	public List<String> getOptionSummary()
	{
		List<String> lines = new ArrayList<String>();
		if (!parsed)
		{
			throw new IllegalStateException("Command line not parsed yet");
		}
		for (OptionContainer oc : rootOptions.getChildren())
		{
			if (oc instanceof Option)
			{
				Option option = (Option) oc;
				StringBuilder sb = new StringBuilder();
				sb.append("\t");
				sb.append(option.getName());
				if (option.isArgAccepted())
				{
					sb.append(" = '");
					sb.append(CollectionUtils.joinObjects("', '", option.getValues()));
					sb.append("'");
				}
				else
				{
					if (option.isEnabled())
					{
						sb.append(" = true");
					}
					else
					{
						sb.append(" = false");
					}
				}
				lines.add(sb.toString());
			}
		}

		return lines;
	}


	private void printHelp()
	{
		System.out.println(HelpFormatter.makeHelp(80, "java -jar mcp.jar [options]", "", rootOptions, 3, ""));
	}


	public Option addOption(String shortOption, String longOption, String description)
	{
		return rootOptions.addOption(shortOption, longOption, description);
	}


	public Option addOption(String shortOption, String longOption, String description, boolean argAccepted, boolean argRequired, String defaultValue,
			String argDescription)
	{
		return rootOptions.addOption(shortOption, longOption, description, argAccepted, argRequired, defaultValue, argDescription);
	}


	public OptionGroup addOptionGroup(String name, String description)
	{
		return rootOptions.addOptionGroup(name, description);
	}


}
