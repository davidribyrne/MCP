package mcp.modules.hostnames;

import mcp.modules.GeneralOptions;
import mcp.modules.Module;
import mcp.modules.nmap.NmapGeneralOptions;
import net.dacce.commons.cli.OptionGroup;
import net.dacce.commons.cli.Option;
import net.dacce.commons.cli.OptionContainer;
import net.dacce.commons.validators.IPAddressValidator;
import net.dacce.commons.validators.NumericValidator;
import net.dacce.commons.validators.PathState;
import net.dacce.commons.validators.PathValidator;
import net.dacce.commons.validators.Requirement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;

public class HostnameDiscoveryGeneralOptions extends Module
{
	private final static Logger logger = LoggerFactory.getLogger(HostnameDiscoveryGeneralOptions.class);

	private static final HostnameDiscoveryGeneralOptions instance = new HostnameDiscoveryGeneralOptions();

	private OptionGroup group;
	private Option dnsServersOption;
	
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


	
	public HostnameDiscoveryGeneralOptions()
	{

		dnsServersOption = new Option(null, "dnsServer", "DNS servers to use for hostname discovery. Seperate multiple values with commas.  "
				+ "If no servers are provided, an attempt will be made to detect the system servers. This feature isn't built into Java, "
				+ "so unusual systems may fail.", 
				true, true, null, "IP address");

		enableHostnameDiscoveryOption = new Option("d", "hostnameDiscovery", "Enable typical hostname discovery options. Currently equivalent to "
				+ "--testCommonHostnames --autoAddDomains --autoAddSubDomains --testSSLCerts --testRootPages --testZoneTransfers");
		testCommonHostnamesOption = new Option(null, "testCommonHostnames", "Test a list of common hostnames against every known domain.");
		commonHostnamesFileOption = new Option(null, "commonHostnamesFile", "Path to file with common hostnames to test. Each line is a seperate hostname. "
				+ "Defaults to a built-in list.", 
				true, true, "", "filename");
		
		autoAddDomainsOption = new Option(null, "autoAddDomains", "Automatically add domains to the master list as they are discovered. "
				+ "The master list of domains is tested for relevant enabled options; zone transfers, common hostnames, etc. This should "
				+ "be used with caution, particulary in environments that host many domains.");
		maxAutoAddDomainOption = new Option(null, "maxAutoAddDomains", "Limit the number of domains that are added automatically. This is "
				+ "important to prevent the list from ballooning.", true, true, "10", "n");
		
		autoAddSubDomainsOption = new Option(null, "autoAddSubDomains", "Automatically detect and add subdomains for existing domains.");
		maxAutoAddSubDomainOption = new Option(null, "maxAutoAddSubDomains", "Limit the number of subdomains (per domain) that are added automatically.", 
				true, true, "10", "n");
		
		testSslCertsOption = new Option(null, "testSSLCerts", "Request certificates from SSL/TLS services to discover hostnames.");
		testRootPagesOption = new Option(null, "testRootPages", "Test hostnames in links discovered on a web server's root page.");
		testZoneTransfersOption = new Option(null, "testZoneTransfers", "Request zone transfers for discovered domains from discovered or provided DNS servers.");
		
		knownDomainsFileOption = new Option(null, "knownDomainsFile", "File that contains a list of known domains to include in hostname discovery. "
				+ "One domain per line.", true, true, "known-domains.txt", "filename");

		knownDomainsOption = new Option(null, "knownDomains", "Comma seperated list of known domains to include in hostname discovery.", 
				true, true, "", "domains");

		knownHostnamesFileOption = new Option(null, "knownHostnamesFile", "File that contains a list of known hostnames to include in hostname discovery. "
				+ "They are still tested. One hostname per line.", true, true, "known-hostnames.txt", "filename");

		knownHostnamesOption = new Option(null, "knownHostnames", "Comma seperated list of known hostnames to include in hostname discovery. "
				+ "They are still tested.", true, true, "", "hostnames");

		bannedDomainsOption = new Option(null, "bannedDomains", "Comma seperated list of domains to exclude from brute force hostname discovery. This is useful for "
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
		
		group = new OptionGroup("Hostname discovery", "Hostname discovery.");
		group.addChild(dnsServersOption);
		group.addChild(enableHostnameDiscoveryOption);
		group.addChild(testCommonHostnamesOption);
		group.addChild(commonHostnamesFileOption);
		group.addChild(autoAddDomainsOption);
		group.addChild(maxAutoAddDomainOption);
		group.addChild(autoAddSubDomainsOption);
		group.addChild(maxAutoAddSubDomainOption);
		group.addChild(testSslCertsOption);
		group.addChild(testRootPagesOption);
		group.addChild(testZoneTransfersOption);
		group.addChild(knownDomainsFileOption);
		group.addChild(knownDomainsOption);
		group.addChild(knownHostnamesFileOption);
		group.addChild(knownHostnamesOption);
		group.addChild(bannedDomainsOption);
	}


	@Override
	public void initialize()
	{
		if (GeneralOptions.getInstance().getBasicReconOption().isEnabled())
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
	}


	@Override
	public OptionContainer getOptions()
	{
		return group;
	}


	public static HostnameDiscoveryGeneralOptions getInstance()
	{
		return instance;
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
}
