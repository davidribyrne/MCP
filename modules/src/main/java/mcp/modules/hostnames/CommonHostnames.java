package mcp.modules.hostnames;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mcp.events.events.ElementCreationEvent;
import mcp.events.listeners.NodeCreationListener;
import mcp.jobmanager.executors.ExecutionScheduler;
import mcp.knowledgebase.nodes.Domain;
import mcp.knowledgebase.nodes.Node;
import mcp.modules.Module;
import net.dacce.commons.cli.OptionContainer;
import net.dacce.commons.cli.OptionGroup;
import net.dacce.commons.dns.client.DnsTransaction;
import net.dacce.commons.dns.exceptions.DnsClientConnectException;
import net.dacce.commons.dns.exceptions.DnsResponseTimeoutException;
import net.dacce.commons.dns.messages.QuestionRecord;
import net.dacce.commons.dns.records.RecordType;
import net.dacce.commons.general.FileUtils;
import net.dacce.commons.general.StringUtils;

public class CommonHostnames extends HostnameDiscoveryModule implements NodeCreationListener
{
	private final static Logger logger = LoggerFactory.getLogger(CommonHostnames.class);

	private List<String> commonNames;
	@SuppressWarnings("rawtypes")
	private final Collection nodeTypes = Collections.singletonList(Domain.class); 

	public CommonHostnames()
	{
		super("Common hostnames");
	}


	@SuppressWarnings("unchecked")
	@Override
	public Collection<Class<? extends Node>> getNodeMonitorClasses()
	{
		return nodeTypes;
	}


	@Override
	public void initialize()
	{
		if (HostnameDiscoveryGeneralOptions.getInstance().getTestCommonHostnamesOption().isEnabled())
		{
			ExecutionScheduler.getInstance().registerListener(ElementCreationEvent.class, this);
			loadHostnames();
		}
	}

	private void loadHostnames()
	{
		if (HostnameDiscoveryGeneralOptions.getInstance().getCommonHostnamesFileOption().isValueSet(false))
		{
			String filename = HostnameDiscoveryGeneralOptions.getInstance().getCommonHostnamesFileOption().getValue();
			try
			{
				commonNames = StringUtils.trim(FileUtils.readConfigFileLines(filename));
			}
			catch (IOException e)
			{
				logger.warn("Failed to read common hostnames from " + filename + " (default list will be used): " + e.getLocalizedMessage(), e);
			}
		}
		if (commonNames == null)
		{
			URL url = HostnameDiscoveryGeneralOptions.class.getResource("/common-host-names.txt");
			try
			{
				commonNames = StringUtils.trim(FileUtils.readConfigFileLines(url));
			}
			catch (IOException e)
			{
				logger.warn("Failed to read built-in common hostname list: " + e.getLocalizedMessage(), e);
				commonNames = Collections.emptyList();
			}
		}
	}


	@Override
	public void handleEvent(ElementCreationEvent reconEvent)
	{
		String domainName = ((Domain) reconEvent.getElement()).getName();
		List<DnsTransaction> transactions = new ArrayList<DnsTransaction>(commonNames.size());
		for (String name: commonNames)
		{
			transactions.add(new DnsTransaction(new QuestionRecord(name + "." + domainName, RecordType.A), true));
		}
		try
		{
			HostnameDiscoveryUtils.resolver.bulkQuery(transactions, true, true);
		}
		catch (DnsClientConnectException e)
		{
			logger.warn("DNS connection timeout, which is very odd: " + e.getLocalizedMessage(), e);
		}
		catch (DnsResponseTimeoutException e)
		{
			logger.warn("DNS response timeout. Proceeding with recieved responses in common hostname test for " + domainName + ": " + e.getLocalizedMessage(), e);
		}
		
		HostnameDiscoveryUtils.reviewDnsTransactions(transactions);
	}


	@Override
	protected OptionGroup getOptionGroup()
	{
		return null;
	}
}
