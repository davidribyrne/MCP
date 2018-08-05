package mcp.modules.hostnames;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.events.events.NodeCreationEvent;
import mcp.events.listeners.NodeCreationListener;
import mcp.jobmanager.executors.ExecutionScheduler;
import mcp.knowledgebase.NodeType;
import mcp.knowledgebase.nodeLibrary.Common;
import mcp.knowledgebase.NodeType;
import space.dcce.commons.cli.OptionGroup;
import space.dcce.commons.dns.client.DnsTransaction;
import space.dcce.commons.dns.exceptions.DnsClientConnectException;
import space.dcce.commons.dns.exceptions.DnsResponseTimeoutException;
import space.dcce.commons.dns.messages.QuestionRecord;
import space.dcce.commons.dns.records.RecordType;
import space.dcce.commons.general.FileUtils;
import space.dcce.commons.general.StringUtils;

public class CommonHostnames extends HostnameDiscoveryModule implements NodeCreationListener
{
	private final static Logger logger = LoggerFactory.getLogger(CommonHostnames.class);

	private List<String> commonNames;
	@SuppressWarnings("rawtypes")
	private final Collection nodeTypes = Collections.singletonList(Common.DOMAIN); 

	public CommonHostnames()
	{
		super("Common hostnames");
	}


	@SuppressWarnings("unchecked")
	@Override
	public Collection<NodeType> getMonitorNodeTypes()
	{
		return nodeTypes;
	}


	@Override
	public void initialize()
	{
		if (HostnameDiscoveryGeneralOptions.getInstance().getTestCommonHostnamesOption().isEnabled())
		{
			ExecutionScheduler.getInstance().registerListener(NodeCreationEvent.class, this);
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
	public void handleEvent(NodeCreationEvent reconEvent)
	{
		String domainName = new String(reconEvent.getNode().getValue());
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
