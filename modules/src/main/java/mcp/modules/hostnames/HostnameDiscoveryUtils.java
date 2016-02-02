package mcp.modules.hostnames;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mcp.knowledgebase.KnowledgeBaseImpl;
import mcp.knowledgebase.nodes.Address;
import mcp.knowledgebase.nodes.Hostname;
import mcp.knowledgebase.scope.Scope;
import net.dacce.commons.dns.client.DnsTransaction;
import net.dacce.commons.dns.client.Resolver;
import net.dacce.commons.dns.exceptions.DnsClientConnectException;
import net.dacce.commons.dns.exceptions.DnsClientException;
import net.dacce.commons.dns.exceptions.DnsNoRecordFoundException;
import net.dacce.commons.dns.exceptions.DnsResponseTimeoutException;
import net.dacce.commons.dns.records.ARecord;
import net.dacce.commons.dns.records.AbstractAddressRecord;
import net.dacce.commons.dns.records.AbstractHostnameRecord;
import net.dacce.commons.dns.records.MxRecord;
import net.dacce.commons.dns.records.ResourceRecord;
import net.dacce.commons.dns.utils.DomainUtils;
import net.dacce.commons.netaddr.SimpleInetAddress;
import java.util.*;


public class HostnameDiscoveryUtils
{
	private final static Logger logger = LoggerFactory.getLogger(HostnameDiscoveryUtils.class);

	public static Resolver resolver;
	private static int autoRegisteredDomains = 0;
	private static Map<String, Integer> autoRegisteredSubDomains = new HashMap<String, Integer>();

	/**
	 * Look for in-scope hostnames in a list of transactions and add them to the KB
	 * 
	 * @param transactions
	 */
	public static void reviewDnsTransactions(List<DnsTransaction> transactions)
	{
		for (DnsTransaction transaction : transactions)
		{
			if (transaction.hasAnswer())
			{
				for (ResourceRecord record : transaction.getAnswers())
				{
					reviewResourceRecord(record);
				}
			}
		}
	}

	public static void reviewResourceRecord(ResourceRecord record)
	{
		if (record instanceof AbstractAddressRecord)
		{
			AbstractAddressRecord r = (AbstractAddressRecord) record;
			reviewHostname(r.getDomainName(), r.getAddress());
		}
		else if (record instanceof AbstractHostnameRecord)
		{
			AbstractHostnameRecord r = (AbstractHostnameRecord) record;
			List<SimpleInetAddress> addresses = resolveAndReviewHostname(r.getValue());
			for (SimpleInetAddress address : addresses)
			{
				reviewHostname(record.getDomainName(), address);
			}
		}
		else
		{
			logger.debug("Unsupported DNS record type: " + record.getRecordType().toString());
		}
	}

	/**
	 * Resolve hostname, check to see if the address is in-scope, and add it to
	 * the KB if appropriate.
	 * 
	 * @param hostname
	 */
	public static List<SimpleInetAddress> resolveAndReviewHostname(String hostname)
	{
		List<SimpleInetAddress> addresses;
		try
		{
			addresses = resolver.simpleResolve(hostname, true);
		}
		catch (DnsResponseTimeoutException | DnsClientConnectException e)
		{
			logger.warn("Problem resolving \"" + hostname + "\": " + e.getLocalizedMessage(), e);
			return null;
		}
		catch (DnsNoRecordFoundException e)
		{
			logger.trace("No record for \"" + hostname + "\"");
			return null;
		}

		for (SimpleInetAddress address : addresses)
		{
			reviewHostname(hostname, address);
		}
		return addresses;
	}

	public static void reviewHostname(String hostname, SimpleInetAddress address)
	{
		if (Scope.instance.isInScope(address))
		{
			logger.debug("Hostname " + hostname + "->" + address.toString() + " was discovered and is in-scope.");
			Address a = KnowledgeBaseImpl.getInstance().getOrCreateAddressNode(address);
			Hostname hostnameNode = KnowledgeBaseImpl.getInstance().getOrCreateHostname(hostname);
			hostnameNode.addAddress(a);
			registerDomainsInHostname(hostname);
		}
		else
		{
			logger.trace("Hostname " + hostname + "->" + address.toString() + " was discovered, but is not in-scope.");
		}
	}

	/**
	 * Adds domains and subdomains to KB as appropriate
	 * 
	 * @param hostname
	 */
	public static void registerDomainsInHostname(String hostname)
	{
		synchronized (autoRegisteredSubDomains)
		{
			String domain = DomainUtils.getDomain(hostname);
			if (HostnameDiscoveryGeneralOptions.getInstance().getBannedDomainsOption().getValues().contains(domain))
			{
				logger.trace("Skipping registration of domain and sub-domains in " + hostname + " because " + 
						domain + " is on the banned domain list.");
				return;
			}
			if (HostnameDiscoveryGeneralOptions.getInstance().getAutoAddDomainsOption().isEnabled())
			{
				int limit = HostnameDiscoveryGeneralOptions.getInstance().getMaxAutoAddDomains();
				if (autoRegisteredDomains < limit)
				{
					autoRegisteredDomains++;
					KnowledgeBaseImpl.getInstance().addDomain(domain);
				}
				else
				{
					logger.debug("Failed to auto-register domain for " + hostname + " because the auto-add limit (" + limit + ") was reached.");
				}
			}

			if (HostnameDiscoveryGeneralOptions.getInstance().getAutoAddSubDomainsOption().isEnabled())
			{
				int limit = HostnameDiscoveryGeneralOptions.getInstance().getMaxAutoAddSubDomains();
				int count = autoRegisteredSubDomains.get(domain);
				if (count < limit )
				{
					autoRegisteredSubDomains.put(domain, count + 1);
					for (String subdomain : DomainUtils.getSubdomains(hostname))
					{
						KnowledgeBaseImpl.getInstance().addDomain(subdomain);
					}
				}
				else
				{
					logger.debug("Failed to auto-register sub domains for " + hostname + " because the auto-add limit (" + limit + ") was reached.");
				}
			}
		}
	}

	private HostnameDiscoveryUtils()
	{
	}
}
