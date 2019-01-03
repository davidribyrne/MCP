package mcp.modules.scope;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.commons.WorkingDirectories;
import mcp.knowledgebase.Scope;
import mcp.modules.Module;
import space.dcce.commons.cli.Option;
import space.dcce.commons.cli.OptionGroup;
import space.dcce.commons.general.FileUtils;
import space.dcce.commons.general.InitializationException;
import space.dcce.commons.general.StringUtils;
import space.dcce.commons.netaddr.Addresses;
import space.dcce.commons.netaddr.InvalidIPAddressFormatException;
import space.dcce.commons.validators.IPAddressValidator;
import space.dcce.commons.validators.PathState;
import space.dcce.commons.validators.PathValidator;
import space.dcce.commons.validators.Requirement;


public class ScopeInitializer extends Module
{

	private final static Logger logger = LoggerFactory.getLogger(ScopeInitializer.class);
	private final static ScopeInitializer instance = new ScopeInitializer();

	private static OptionGroup group;
	private static Option targetIPFile;
	private static Option targetIP;
	private static Option excludeIPFile;
	private static Option excludeIP;
	private static Option excludeUnroutable;
	private static Option routingTableFile;
	private static Option listScopeOption;

	static
	{
		targetIPFile = new Option(null, "ipsFile",
				"File that contains a list of IP addresses to target.", true, true, "ips.txt",
				"filename");
		targetIP = new Option(null, "target",
				"Manually add targeted addresses. This can be used multiple times.", true, true, null, "IP-address|CIDR|IP-range");
		targetIP.setMultipleCalls(true);
		excludeIPFile = new Option(null, "excludeIpsFile",
				"File that contains a list of IP addresses to exclude.", true, true,
				"exclude-ips.txt",
				"filename");
		excludeIP = new Option(
				null,
				"exclude",
				"Manually exclude addresses. This allows a block of targets to be defined, but some of them to be excluded. It can be used multiple times.",
				true, true, null, "IP-address|CIDR|IP-range");
		excludeIP.setMultipleCalls(true);
		excludeUnroutable = new Option("", "excludeUnroutable",
				"Block scanning of addresses that are not in the provided routing table (see --routingTableFile");
		routingTableFile = new Option("", "routingTableFile", "File that contains a list of routable subnets in CIDR format.",
				true, true, "routing-table.txt", "filename");

		listScopeOption = new Option("l", "listScope", "Calculate and display the IP addresses in scope");


		PathState targetPathState = new PathState();
		targetPathState.directory = Requirement.MUST_NOT;
		targetPathState.readable = Requirement.MUST;
		targetPathState.exists = Requirement.MUST;
		PathValidator pathValidator = new PathValidator(targetPathState);
		targetIPFile.addValidator(pathValidator);
		excludeIPFile.addValidator(pathValidator);
		routingTableFile.addValidator(pathValidator);

		IPAddressValidator ipValidator = new IPAddressValidator(false, true);
		targetIP.addValidator(ipValidator);
		excludeIP.addValidator(ipValidator);

		group = new OptionGroup("Scope Definition", "What IP addresses to include, what to exclude. All files are one address/CIDR/range per line.");
		group.addChild(targetIPFile);
		group.addChild(targetIP);
		group.addChild(excludeIPFile);
		group.addChild(excludeIP);
		group.addChild(excludeUnroutable);
		group.addChild(routingTableFile);
		group.addChild(listScopeOption);
	}


	private ScopeInitializer()
	{
		super("Scope initializer");
	}


	public static ScopeInitializer getInstance()
	{
		return instance;
	}


	private Addresses parseTargetFile(String filename, String description, Option option)
	{
		Addresses addresses = new Addresses("Addresses from " + filename);
		try
		{
			long start = System.currentTimeMillis();
			List<String> lines = FileUtils.readLines(filename);
			logger.trace(description + " time - " + (System.currentTimeMillis() - start));
			if (lines.size() > 200000)
			{
				logger.warn("Loading " + description + " (" + filename + "). It has " + lines.size() + " lines so it may take a while.");
			}
			for (String l : lines)
			{
				String line = l.replaceAll("\\s", "");
				if (!line.isEmpty())
				{
					try
					{
						addresses.add(line);
					}
					catch (InvalidIPAddressFormatException e)
					{
						logger.warn("Invalid address in " + description + " IP file (" + filename + "): " + line, e);
					}
				}
			}
			if (lines.size() > 1000)
			{
				logger.warn("Finished reading " + description + " (" + filename + ").");
			}

		}
		catch (FileNotFoundException e)
		{
			if (option.isEnabled())
			{
				String message = "Couldn't find " + description + " file " + filename + ": " + e.getLocalizedMessage();
				logger.error(message, e);
				throw new InitializationException(message, e);
			}
			logger.debug(description + " file not found. Not a problem.");
		}
		catch (IOException e)
		{
			throw new IllegalArgumentException("Problem reading IP " + description + " file (" + filename + ")", e);
		}
		return addresses;
	}


	@Override
	public void initialize()
	{

		String targetIPFileName;

		// Weird call here because the default value doesn't include the path (to make the help pretty)
		if (targetIPFile.isValueSet(false))
			targetIPFileName = targetIPFile.getValue();
		else
			targetIPFileName = WorkingDirectories.getWorkingDirectory() + targetIPFile.getValue();


		if (!StringUtils.isEmptyOrNull(targetIPFileName))
		{
			Scope.instance.setIncludeAddresses(parseTargetFile(targetIPFileName, "target", targetIPFile));
		}
		else
		{
			logger.trace("How is this possible????");
		}

		String excludeIPFileName;

		if (excludeIPFile.isValueSet(false))
			excludeIPFileName = excludeIPFile.getValue();
		else
			excludeIPFileName = WorkingDirectories.getWorkingDirectory() + excludeIPFile.getValue();

		if (!StringUtils.isEmptyOrNull(excludeIPFileName))
		{
			Scope.instance.setExcludeAddresses(parseTargetFile(excludeIPFileName, "exclude", excludeIPFile));
		}
		else
		{
			logger.trace("How is this possible????");
		}




		for (String addressBlock : targetIP.getValues())
		{
			try
			{
				Scope.instance.addIncludeAddressBlock(addressBlock);
			}
			catch (InvalidIPAddressFormatException e)
			{
				logger.warn("Invalid target address in command line (" + addressBlock + ")", e);
			}
		}

		for (String addressBlock : excludeIP.getValues())
		{
			try
			{
				Scope.instance.addExcludeAddressBlock(addressBlock);
			}
			catch (InvalidIPAddressFormatException e)
			{
				logger.warn("Invalid target address in command line (" + addressBlock + ")", e);
			}
		}
		
		
		String routeFileName;

		if (routingTableFile.isValueSet(false))
			routeFileName = routingTableFile.getValue();
		else
			routeFileName = WorkingDirectories.getWorkingDirectory() + routingTableFile.getValue();

		if (excludeUnroutable.isEnabled())
		{
			Scope.instance.setRoutingTable(parseTargetFile(routeFileName, "routing", routingTableFile));
		}

		

		if (listScopeOption.isEnabled())
		{
			String s = Scope.instance.toString();
			logger.trace(s);
			System.out.println(s);
		}

	}


	public static OptionGroup getOptions()
	{
		return group;
	}
}
