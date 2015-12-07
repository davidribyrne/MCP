package mcp.options;

import java.util.ArrayList;
import java.util.List;

import net.dacce.commons.cli.GnuParser;
import net.dacce.commons.cli.HelpFormatter;
import net.dacce.commons.cli.Option;
import net.dacce.commons.cli.OptionContainer;
import net.dacce.commons.cli.Options;
import net.dacce.commons.cli.exceptions.ParseException;
import net.dacce.commons.general.CollectionUtils;


public class ReconOptions
{
	private static ReconOptions instance;
	private Options options;
	private boolean parsed = false;

	private Option help;


	private ReconOptions()
	{
		options = new Options();
		help = new Option("h", "help", "Print this message.");
		options.addOptionContainer(help);
	}


	public void parseCommandline(String args[])
	{
		try
		{
			GnuParser.parse(options, args, false);
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
		for (Option option : options.getAllOptions())
		{
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

		return lines;
	}


	private void printHelp()
	{
		System.out.println(HelpFormatter.makeHelp(80, "java -jar reconmaster.jar [options]", "", options, 3, ""));
	}


	public static ReconOptions getInstance()
	{
		if (instance == null)
		{
			instance = new ReconOptions();
		}
		return instance;
	}


	public void addOptionContainer(OptionContainer option)
	{
		options.addOptionContainer(option);
	}

}
