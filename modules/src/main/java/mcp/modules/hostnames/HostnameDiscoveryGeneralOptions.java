package mcp.modules.hostnames;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.commons.WorkingDirectories;
import mcp.events.events.McpStartEvent;
import mcp.events.listeners.McpStartListener;
import mcp.jobmanager.executors.ExecutionScheduler;
import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.nodeLibrary.Hostnames;
import mcp.modules.Module;
import mcp.modules.Modules;
import mcp.options.MCPOptions;
import space.dcce.commons.cli.Option;
import space.dcce.commons.cli.OptionGroup;
import space.dcce.commons.dns.client.Resolver;
import space.dcce.commons.dns.client.cache.DnsDiskCache;
import space.dcce.commons.general.FileUtils;
import space.dcce.commons.validators.IPAddressValidator;
import space.dcce.commons.validators.NumericValidator;
import space.dcce.commons.validators.PathState;
import space.dcce.commons.validators.PathValidator;
import space.dcce.commons.validators.Requirement;

public class HostnameDiscoveryGeneralOptions extends Module implements McpStartListener
{
	private final static Logger logger = LoggerFactory.getLogger(HostnameDiscoveryGeneralOptions.class);

	private OptionGroup group;
	private Option dnsServersOption;
	private Option publicDnsServersOption;
	
	private Option enableHostnameDiscoveryOption;
	
	private Option testCommonHostnamesOption;
	private Option commonHostnamesFileOption;
	private Option autoAddDomainsOption;
	private Option maxAutoAddDomainOption;
	private Option autoAddSubDomainsOption;
	private Option maxAutoAddSubDomainOption;
	
	private Option testSslCertsOption;
	private Option testRootPagesOption;
	private Option testZoneTransfersOption;
	
	private Option knownDomainsFileOption;
	private Option knownDomainsOption;
	private Option knownHostnamesFileOption;
	private Option knownHostnamesOption;
	private Option bannedDomainsOption;

	private int maxAutoAddDomains;
	private int maxAutoAddSubDomains;
	
	@Override
	protected void initializeOptions()
	{
		group = MCPOptions.instance.addOptionGroup("Hostname discovery", "");

		dnsServersOption = group.addOption(null, "dnsServer", "DNS servers to use for hostname discovery. Seperate multiple values with commas.  "
				+ "If no servers are provided, an attempt will be made to detect the system servers. This feature isn't built into Java, "
				+ "so unusual systems may fail.", 
				true, true, null, "IP address");
		publicDnsServersOption = group.addOption(null, "publicDnsServers", "Use a built-in pool of public DNS servers (Google, Level 3, etc).");


		enableHostnameDiscoveryOption = group.addOption("d", "hostnameDiscovery", "Enable typical hostname discovery options. Currently equivalent to "
				+ "--testCommonHostnames --autoAddDomains --autoAddSubDomains --testSSLCerts --testRootPages --testZoneTransfers");
		testCommonHostnamesOption = group.addOption(null, "testCommonHostnames", "Test a list of common hostnames against every known domain.");
		commonHostnamesFileOption = group.addOption(null, "commonHostnamesFile", "Path to file with common hostnames to test. Each line is a seperate hostname. "
				+ "Defaults to a built-in list.", 
				true, true, "", "filename");
		
		autoAddDomainsOption = group.addOption(null, "autoAddDomains", "Automatically add domains to the master list as they are discovered. "
				+ "The master list of domains is tested for relevant enabled options; zone transfers, common hostnames, etc. This should "
				+ "be used with caution, particulary in environments that host many domains.");
		maxAutoAddDomainOption = group.addOption(null, "maxAutoAddDomains", "Limit the number of domains that are added automatically. This is "
				+ "important to prevent the list from ballooning.", true, true, "10", "n");
		
		autoAddSubDomainsOption = group.addOption(null, "autoAddSubDomains", "Automatically detect and add subdomains for existing domains.");
		maxAutoAddSubDomainOption = group.addOption(null, "maxAutoAddSubDomains", "Limit the number of subdomains (per domain) that are added automatically.", 
				true, true, "10", "n");
		
		testSslCertsOption = group.addOption(null, "testSSLCerts", "Request certificates from SSL/TLS services to discover hostnames.");
		testRootPagesOption = group.addOption(null, "testRootPages", "Test hostnames in links discovered on a web server's root page.");
		testZoneTransfersOption = group.addOption(null, "testZoneTransfers", "Request zone transfers for discovered domains from discovered or provided DNS servers.");
		
		knownDomainsFileOption = group.addOption(null, "knownDomainsFile", "File that contains a list of known domains to include in hostname discovery. "
				+ "One domain per line.", true, true, "known-domains.txt", "filename");

		knownDomainsOption = group.addOption(null, "knownDomains", "Comma seperated list of known domains to include in hostname discovery.", 
				true, true, "", "domains");

		knownHostnamesFileOption = group.addOption(null, "knownHostnamesFile", "File that contains a list of known hostnames to include in hostname discovery. "
				+ "They are still tested. One hostname per line.", true, true, "known-hostnames.txt", "filename");

		knownHostnamesOption = group.addOption(null, "knownHostnames", "Comma seperated list of known hostnames to include in hostname discovery. "
				+ "They are still tested.", true, true, "", "hostnames");

		bannedDomainsOption = group.addOption(null, "bannedDomains", "Comma seperated list of domains to exclude from brute force hostname discovery. This is useful for "
				+ "prventing discover against ISPs and hosting environments.", 
				true, true, "cisco.com,pacbell.net,sprintlink.net,barracudanetworks.com,qwest.net,sumtotalsystems.com,savvis.net,akadns.net,"
						+ ".net,akamaiedge.net,akamai.net,akamaitechnologies.com,speakeasy.net,.arpa,level3.net,bellsouth.net,swbell.net,"
						+ "frontiernet.net,surewest.net,sbcglobal.net,verizon.net,megapath.net,spcsdns.net,amazonaws.com,mindspring.com,"
						+ "comcastbusiness.net,rr.com", "domains");


		PathState domainFilePathState = new PathState();
		domainFilePathState.directory = Requirement.MUST_NOT;
		domainFilePathState.readable = Requirement.MUST;
		domainFilePathState.exists = Requirement.MUST;
		PathValidator pathValidator = new PathValidator(domainFilePathState);

		commonHostnamesFileOption.addValidator(pathValidator);
		knownHostnamesFileOption.addValidator(pathValidator);
		knownDomainsFileOption.addValidator(pathValidator);

		IPAddressValidator ipValidator = new IPAddressValidator(false, false);
		dnsServersOption.addValidator(ipValidator);
		
		NumericValidator countValidator = new NumericValidator(false, 1, Integer.MAX_VALUE); 
		maxAutoAddDomainOption.addValidator(countValidator);
		maxAutoAddSubDomainOption.addValidator(countValidator);
		
	}
	
	
	public HostnameDiscoveryGeneralOptions()
	{
		super("Hostname discovery general options");

	}


	@Override
	public void initialize()
	{
		ExecutionScheduler.getInstance().registerListener(McpStartEvent.class, this);
		if (dnsServersOption.isValueSet(true))
		{
			HostnameDiscoveryUtils.resolver = new Resolver(dnsServersOption.getValues(), true);
		}
		else if (publicDnsServersOption.isEnabled())
		{
			HostnameDiscoveryUtils.resolver = Resolver.createPublicServersResolver();
			HostnameDiscoveryUtils.resolver.setRoundRobinDuplicateCount(2);
		}
		else
		{
			HostnameDiscoveryUtils.resolver = new Resolver();
		}
		HostnameDiscoveryUtils.resolver.setCache(new DnsDiskCache(getDnsCacheFilename()));

		
		
		if (Modules.instance.getGeneralOptions().getBasicReconOption().isEnabled())
		{
			enableHostnameDiscoveryOption.forceEnabled();
		}
		
		if(enableHostnameDiscoveryOption.isEnabled())
		{
			testCommonHostnamesOption.forceEnabled();
			autoAddDomainsOption.forceEnabled();
			autoAddSubDomainsOption.forceEnabled();
			testSslCertsOption.forceEnabled();
			testRootPagesOption.forceEnabled();
			testZoneTransfersOption.forceEnabled();
		}
		
		
		maxAutoAddDomains = Integer.valueOf(maxAutoAddDomainOption.getValue());
		maxAutoAddSubDomains = Integer.valueOf(maxAutoAddSubDomainOption.getValue());
	}
	
	private void handleKnownHostnames()
	{
		logger.info("Handling known hostnames.");
		String hostnamesPath;
		if (knownHostnamesFileOption.isValueSet(false))
		{
			hostnamesPath = knownHostnamesFileOption.getValue();
		}
		else
		{
			hostnamesPath = WorkingDirectories.getWorkingDirectory() + knownHostnamesFileOption.getValue();
		}
		
		for (String hostname: knownHostnamesOption.getValues())
		{
			HostnameDiscoveryUtils.resolveAndReviewHostname(hostname);
		}
		try
		{
			for (String hostname: FileUtils.readLines(hostnamesPath))
			{
				HostnameDiscoveryUtils.resolveAndReviewHostname(hostname);
			}
		}
		catch (@SuppressWarnings("unused") IOException e)
		{
			logger.debug("Failed to read known hostnames file (" + hostnamesPath + "). "
					+ "This is only a problem if you expected data to be there.");
		}
	}

	private void handleKnownDomains()
	{
		logger.info("Handling known domains.");
		String knownDomainsPath;
		if (knownDomainsFileOption.isValueSet(false))
		{
			knownDomainsPath = knownDomainsFileOption.getValue();
		}
		else
		{
			knownDomainsPath = WorkingDirectories.getWorkingDirectory() + knownDomainsFileOption.getValue();
		}
		
		try
		{
			for (String domain: FileUtils.readLines(knownDomainsPath))
			{
				KnowledgeBase.INSTANCE.getOrCreateNode(Hostnames.DOMAIN, domain);
			}
		}
		catch (IOException e)
		{
			logger.debug("Failed to read known domains file (" + knownDomainsPath + "). "
					+ "This is only a problem if you expected data to be there.", e);
		}
		for (String domain: knownDomainsOption.getValues())
		{
			KnowledgeBase.INSTANCE.getOrCreateNode(Hostnames.DOMAIN, domain);
		}

	}

	public OptionGroup getOptions()
	{
		return group;
	}


	public Option getDnsServersOption()
	{
		return dnsServersOption;
	}


	public Option getEnableHostnameDiscoveryOption()
	{
		return enableHostnameDiscoveryOption;
	}


	public Option getTestCommonHostnamesOption()
	{
		return testCommonHostnamesOption;
	}


	public Option getCommonHostnamesFileOption()
	{
		return commonHostnamesFileOption;
	}


	public Option getAutoAddDomainsOption()
	{
		return autoAddDomainsOption;
	}


	public Option getMaxAutoAddDomainOption()
	{
		return maxAutoAddDomainOption;
	}


	public Option getAutoAddSubDomainsOption()
	{
		return autoAddSubDomainsOption;
	}


	public Option getMaxAutoAddSubDomainOption()
	{
		return maxAutoAddSubDomainOption;
	}


	public Option getTestSslCertsOption()
	{
		return testSslCertsOption;
	}


	public Option getTestRootPagesOption()
	{
		return testRootPagesOption;
	}


	public Option getTestZoneTransfersOption()
	{
		return testZoneTransfersOption;
	}


	public Option getKnownDomainsFileOption()
	{
		return knownDomainsFileOption;
	}


	public Option getKnownDomainsOption()
	{
		return knownDomainsOption;
	}


	public Option getKnownHostnamesFileOption()
	{
		return knownHostnamesFileOption;
	}


	public Option getKnownHostnamesOption()
	{
		return knownHostnamesOption;
	}


	public Option getBannedDomainsOption()
	{
		return bannedDomainsOption;
	}
	
	public static String getDnsCacheFilename()
	{
		return WorkingDirectories.getResumeDirectory() + "dns-cache";
	}


	public int getMaxAutoAddDomains()
	{
		return maxAutoAddDomains;
	}


	public int getMaxAutoAddSubDomains()
	{
		return maxAutoAddSubDomains;
	}


	@Override
	public void handleEvent(McpStartEvent event)
	{
		handleKnownDomains();
		handleKnownHostnames();

	}


}
