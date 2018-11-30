package mcp.modules;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.commons.WorkingDirectories;
import mcp.knowledgebase.Scope;
import space.dcce.commons.cli.Option;
import space.dcce.commons.cli.OptionContainer;
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

	static
	{
		targetIPFile = new Option(null, "ipsFile",
				"File that contains a list of IP addresses to target. They can be in an nmap format (i.e. CIDRs and ranges).", true, true, "ips.txt",
				"filename");
		targetIP = new Option(null, "target",
				"Manually add a targeted address/CIDR/address range. This can be used multiple times.", true, true, null, "IP-address|CIDR|IP-range");
		targetIP.setMultipleCalls(true);
		excludeIPFile = new Option(null, "excludeIpsFile",
				"File that contains a list of IP addresses to exclude. They can be in an nmap format (i.e. CIDRs and ranges).", true, true,
				"exclude-ips.txt",
				"filename");
		excludeIP = new Option(
				null,
				"exclude",
				"Manually exclude a targeted address/CIDR/address range. This allows a block of targets to be defined, but some of them to be excluded. It can be used multiple times.",
				true, true, null, "IP-address|CIDR|IP-range");

		PathState targetPathState = new PathState();
		targetPathState.directory = Requirement.MUST_NOT;
		targetPathState.readable = Requirement.MUST;
		targetPathState.exists = Requirement.MUST;
		PathValidator pathValidator = new PathValidator(targetPathState);
		targetIPFile.addValidator(pathValidator);
		excludeIPFile.addValidator(pathValidator);

		IPAddressValidator ipValidator = new IPAddressValidator(false, true);
		targetIP.addValidator(ipValidator);
		excludeIP.addValidator(ipValidator);

		group = new OptionGroup("Scope Definition", "");
		group.addChild(targetIPFile);
		group.addChild(targetIP);
		group.addChild(excludeIPFile);
		group.addChild(excludeIP);

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
		Addresses addresses = new Addresses(100000);
		try
		{
			for (String l : FileUtils.readLines(filename))
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
						logger.warn("Invalid address in " + description + " IP file (" + filename + "): " + line);
					}
				}
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
			else
			{
				logger.debug(description + " file not found. Not a problem.");
			}
		}
		catch (IOException e)
		{
			throw new IllegalArgumentException("Problem reading IP " + description + " file (" + filename + ")", e);
		}
		addresses.trimToSize();
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

		String excludeIPFileName;

		if (excludeIPFile.isValueSet(false))
			excludeIPFileName = excludeIPFile.getValue();
		else
			excludeIPFileName = WorkingDirectories.getWorkingDirectory() + excludeIPFile.getValue();

		if (!StringUtils.isEmptyOrNull(excludeIPFileName))
		{
			Scope.instance.setExcludeAddresses(parseTargetFile(excludeIPFileName, "target", excludeIPFile));
		}

		for (String addressBlock : targetIP.getValues())
		{
			try
			{
				Scope.instance.addIncludeAddressBlock(addressBlock);
			}
			catch (InvalidIPAddressFormatException e)
			{
				logger.warn("Invalid target address in command line (" + addressBlock + ")");
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
				logger.warn("Invalid target address in command line (" + addressBlock + ")");
			}
		}
	}


	public static OptionContainer getOptions()
	{
		return group;
	}
}
