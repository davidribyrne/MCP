package mcp;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import space.dcce.commons.dns.client.DnsTransaction;
import space.dcce.commons.dns.client.Resolver;
import space.dcce.commons.dns.exceptions.DnsClientConnectException;
import space.dcce.commons.dns.exceptions.DnsNoRecordFoundException;
import space.dcce.commons.dns.exceptions.DnsResponseTimeoutException;
import space.dcce.commons.dns.messages.QuestionRecord;
import space.dcce.commons.dns.records.RecordType;
import space.dcce.commons.dns.records.ResourceRecord;
import space.dcce.commons.general.CollectionUtils;
import space.dcce.commons.general.FileUtils;
import space.dcce.commons.netaddr.IP4Utils;
import space.dcce.commons.netaddr.InvalidIPAddressFormatException;
import space.dcce.commons.netaddr.SimpleInetAddress;

public class Test
{
	private final static Logger logger = LoggerFactory.getLogger(Test.class);



	public static void main(String[] args) throws DnsClientConnectException, InvalidIPAddressFormatException
	{
		
		

	}
	

}
