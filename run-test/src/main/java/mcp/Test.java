package mcp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.dacce.commons.dns.client.DnsTransaction;
import net.dacce.commons.dns.client.Resolver;
import net.dacce.commons.dns.exceptions.DnsClientConnectException;
import net.dacce.commons.dns.exceptions.DnsNoRecordFoundException;
import net.dacce.commons.dns.exceptions.DnsResponseTimeoutException;
import net.dacce.commons.dns.messages.QuestionRecord;
import net.dacce.commons.dns.records.RecordType;
import net.dacce.commons.dns.records.ResourceRecord;
import net.dacce.commons.general.CollectionUtils;
import net.dacce.commons.general.FileUtils;

public class Test
{
	private final static Logger logger = LoggerFactory.getLogger(Test.class);


	static Resolver r;


	public static void main(String[] args) throws DnsClientConnectException
	{
		List<String> servers = new ArrayList<String>();
		logger.info("Starting");
		servers.add("208.109.255.28");
//		servers.add("8.8.8.8");
//		servers.add("8.8.4.4");
//
//		servers.add("84.200.69.80");
//		servers.add("84.200.70.40");
//
//		servers.add("209.244.0.3");
//		servers.add("209.244.0.4");
//
//		servers.add("8.26.56.26");
//		servers.add("8.20.247.20");
//
//		servers.add("208.67.222.222");
//		servers.add("208.67.220.220");
//
//		servers.add("");
//		servers.add("");
//
//		servers.add("");
//		servers.add("");

		r = new Resolver(servers, true);
//		r = Resolver.createPublicServersResolver();
		r.setRoundRobinDuplicateCount(4);
		r.setRoundRobin(true);
		r.setResponseTimeout(200000);
		r.setMaxRequestsPerServerPerSecond(50);
		r.setMaxTotalRequestsPerSecond(500);
//		rq("long.dacce.net", RecordType.TXT);

//		rq("dacce.net", RecordType.AXFR);
//		r("www.google.com");
		r("asdf.google.com");
		r("www.google.com");
		
		rq("www.dacce.net", RecordType.A);
		rq("dacce.net", RecordType.ANY);
		rq("dacce.net", RecordType.MX);
//		rq("www.google.com", RecordType.A);
//		bulk();
	}
	
	private static void bulk() throws FileNotFoundException, IOException
	{
		List<DnsTransaction> transactions = new ArrayList<DnsTransaction>();
		List<String> hosts = FileUtils.readLines("C:\\temp\\b");
		
//		Collections.shuffle(hosts);
		int count = 0;
		for (String host: hosts)
		{
			if (count++ > 500)
				break;
			transactions.add(new DnsTransaction(new QuestionRecord(host, RecordType.A), true));
		}
		
		try
		{
			long start = System.currentTimeMillis();
			int i = r.bulkQuery(transactions, true, true);
			long end = System.currentTimeMillis();
			System.out.println(i + " unanswered\n" + (end-start) + "ms");
		}
		catch (DnsClientConnectException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (DnsResponseTimeoutException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		for (DnsTransaction transaction: transactions)
//		{
//			QuestionRecord question = transaction.getQuestion();
//			System.out.println(question.getDomainName());
//			for (ResourceRecord answer: transaction.getResponses())
//			{
//				System.out.println(answer.toString());
//			}
//			System.out.println();
//		}
		
	}
	
	private static void rq(String s, RecordType type) throws DnsClientConnectException
	{
		System.out.println("\n" + s);
		QuestionRecord q = new QuestionRecord(s, type);

		try
		{
			List<ResourceRecord> records = r.query(q, true, true);
			System.out.println(CollectionUtils.joinObjects("\n", records));
		}
		catch (DnsResponseTimeoutException e)
		{
			System.out.println("Timeout");
		}
		catch (DnsNoRecordFoundException e)
		{
			System.out.println("Does not exist");
		}
	}
	
	private static void r(String s) throws DnsClientConnectException
	{
		System.out.println("\n" + s);
		try
		{
			System.out.println(CollectionUtils.joinObjects("\n", r.simpleResolve(s, true)));
		}
		catch (DnsResponseTimeoutException e)
		{
			System.out.println("Timeout on " + s);
		}
		catch (DnsNoRecordFoundException e)
		{
			System.out.println(s + " does not exist");
		}
	}
}
