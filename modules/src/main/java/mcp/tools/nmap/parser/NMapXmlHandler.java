/*
 * Copyright (c) 2010, nmap4j.org
 *
 * All rights reserved.
 *
 * This license covers only the Nmap4j library. To use this library with
 * Nmap, you must also comply with Nmap's license. Including Nmap within
 * commercial applications or appliances generally requires the purchase
 * of a commercial Nmap license (see http://nmap.org/book/man-legal.html).
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the nmap4j.org nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package mcp.tools.nmap.parser;

import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.nodes.Host;
import mcp.knowledgebase.nodes.Hostname;
import mcp.knowledgebase.nodes.OSGuess;
import mcp.knowledgebase.nodes.Port;
import mcp.knowledgebase.nodes.PortState;
import mcp.knowledgebase.nodes.PortStateReason;
import mcp.knowledgebase.nodes.PortType;
import mcp.knowledgebase.nodes.ServiceReason;
import net.dacce.commons.netaddr.IPUtils;
import net.dacce.commons.netaddr.InvalidIPAddressFormatException;
import net.dacce.commons.netaddr.MacUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * This class is the implementation of the DefaultHandler and receives
 * notifications from the SAX parser when nodes are parsed.
 * <p>
 * From the NMap4J API standpoint, there should be little reason for you to hook directly into this class (though you
 * can if you want to).
 *
 * @author jsvede
 *
 */
public class NMapXmlHandler extends DefaultHandler
{

	final static Logger logger = LoggerFactory.getLogger(NMapXmlHandler.class);
	private long parseStartTime = 0;

	private long parseEndTime = 0;


	private boolean isCpeData = false;

	private String previousQName;

	public final static String NMAPRUN_TAG = "nmaprun";

	public final static String SCANNER_ATTR = "scanner";
	public final static String ARGS_ATTR = "args";
	public final static String START_ATTR = "start";
	public final static String STARTSTR_ATTR = "startstr";
	public final static String VERSION_ATTR = "version";
	public final static String XML_OUTPUT_VERSION_ATTR = "xmloutputversion";
	public final static String SCANINFO_TAG = "scaninfo";

	public final static String TYPE_ATTR = "type";
	public final static String PROTOCOL_ATTR = "protocol";
	public final static String NUM_SERVICES_ATTR = "numservices";
	public final static String SERVICES_ATTR = "services";
	public static final String DEBUGGING_TAG = "debugging";
	public static final String LEVEL_TAG = "level";
	public final static String VERBOSE_TAG = "verbose";
	public final static String LEVEL_ATTR = "level";
	public final static String HOST_TAG = "host";
	public final static String STARTTIME_ATTR = "starttime";
	public final static String ENDTIME_ATTR = "endtime";
	public final static String STATUS_TAG = "status";
	public final static String STATE_ATTR = "state";
	public final static String REASON_ATTR = "reason";
	public final static String ADDRESS_TAG = "address";

	public final static String ADDR_ATTR = "addr";
	public final static String ADDRTYPE_ATTR = "addrtype";
	public final static String HOSTNAMES_TAG = "hostnames";
	public final static String HOSTNAME_TAG = "hostname";
	public final static String NAME_ATTR = "name";
	public final static String PORTS_TAG = "ports";
	public final static String PORT_TAG = "port";

	public final static String STATE_TAG = "state";

	public final static String REASON_TTL_ATTR = "reason_ttl";
	public final static String SERVICE_TAG = "service";

	public final static String PRODUCT_ATTR = "product";
	public final static String METHOD_ATTR = "method";
	public final static String CONF_ATTR = "conf";
	public final static String OS_TYPE_ATTR = "ostype";
	public final static String EXTRA_INFO_ATTR = "extrainfo";
	public final static String OS_TAG = "os";
	public final static String PORT_USED_TAG = "portused";

	public final static String PROTO_ATTR = "proto";
	public final static String PORTID_ATTR = "portid";
	public final static String OSCLASS_TAG = "osclass";

	public final static String VENDOR_ATTR = "vendor";
	public final static String OSFAMILY_ATTR = "osfamily";
	public final static String OSGEN_ATTR = "osgen";
	public final static String ACCURACY_ATTR = "accuracy";
	public final static String OS_MATCH_TAG = "osmatch";

	public final static String LINE_ATTR = "line";
	public final static String DISTANCE_TAG = "distance";

	public final static String VALUE_ATTR = "value";
	public final static String TCP_SEQUENCE_TAG = "tcpsequence";

	public final static String INDEX_ATTR = "index";
	public final static String DIFFICULTY_ATTR = "difficulty";
	public final static String VALUES_ATTR = "values";
	public final static String TCP_TS_SEQUENCE_TAG = "tcptssequence";

	public final static String CLASS_ATTR = "class";
	public final static String TIMES_TAG = "times";

	public final static String SRTT_ATTR = "srtt";
	public final static String RTTVAR_ATTR = "rttvar";
	public final static String TO_ATTR = "to";
	public final static String UPTIME_TAG = "uptime";

	public final static String SECONDS_ATTR = "seconds";
	public final static String LASTBOOT_ATTR = "lastboot";
	public final static String RUNSTATS_TAG = "runstats";
	public final static String FINISHED_TAG = "finished";

	public final static String TIME_ATTR = "time";
	public final static String TIMESTR_ATTR = "timestr";
	public final static String ELAPSED_ATTR = "elapsed";
	public final static String HOSTS_TAG = "hosts";

	public final static String UP_ATTR = "up";
	public final static String DOWN_ATTR = "down";
	public final static String TOTAL_ATTR = "total";
	public final static String CPE_ATTR = "cpe";


	public NMapXmlHandler()
	{
	}

	private Host currentHost;
	private Port currentPort;
	private OSGuess currentOSGuess;
	private byte[] currentMac;
	private String currentMacVendor;


	// https://svn.nmap.org/nmap/docs/nmap.dtd

	@Override
	public void startDocument() throws SAXException
	{
		parseStartTime = System.currentTimeMillis();
	}


	private void makeCurrentOSGuess()
	{
		if (currentOSGuess == null)
		{
			currentOSGuess = new OSGuess();
			currentHost.addOSGuess(currentOSGuess);
		}
	}


	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{

		if (qName.equals(NMAPRUN_TAG))
		{
		}
		if (qName.equals(SCANINFO_TAG))
		{
		}
		if (qName.equals(DEBUGGING_TAG))
		{
		}
		if (qName.equals(VERBOSE_TAG))
		{
		}
		if (qName.equals(HOST_TAG))
		{
			currentHost = null;
			currentPort = null;
			currentOSGuess = null;
			currentMac = null;
			currentMacVendor = null;
		}
		if (qName.equals(STATUS_TAG))
		{
		}
		if (qName.equals(ADDRESS_TAG))
		{
			String type = attributes.getValue(ADDRTYPE_ATTR).toLowerCase();
			if (type.equals("ipv4"))
			{
				try
				{
					currentHost = KnowledgeBase.getInstance().getOrCreateHost(IPUtils.fromString(attributes.getValue(ADDR_ATTR)));
					currentHost.setMacAddress(currentMac);
					currentHost.setMacVendor(currentMacVendor);
				}
				catch (InvalidIPAddressFormatException e)
				{
					logger.warn("Nmap seems to have put an invalid IP address (" + attributes.getValue(ADDR_ATTR) + ")in a file: " + e.getLocalizedMessage(), e);
				}
			}
			else if (type.equals("mac"))
			{
				currentMac = MacUtils.getMacFromString(attributes.getValue(ADDR_ATTR));
				currentMacVendor = attributes.getValue(VENDOR_ATTR);
				if (currentHost != null)
				{
					currentHost.setMacAddress(currentMac);
					currentHost.setMacVendor(currentMacVendor);
				}
			}
		}
		if (qName.equals(HOSTNAMES_TAG))
		{
		}
		if (qName.equals(HOSTNAME_TAG))
		{
			Hostname hostname = KnowledgeBase.getInstance().getOrCreateHostname(attributes.getValue(NAME_ATTR));
			hostname.addAddress(currentHost.getAddress());
		}
		if (qName.equals(PORTS_TAG))
		{
		}
		if (qName.equals(PORT_TAG))
		{
			PortType type = PortType.fromNmap(attributes.getValue(PROTOCOL_ATTR));
			int number = Integer.valueOf(attributes.getValue(PORTID_ATTR));
			currentPort = currentHost.getOrCreatePort(type, number);
		}
		if (qName.equals(STATE_TAG))
		{
			currentPort.setState(PortState.parseNmapText(attributes.getValue(STATE_ATTR)));
			currentPort.setStateReason(PortStateReason.parseNmapText(attributes.getValue(REASON_ATTR)));
		}
		if (qName.equals(SERVICE_TAG))
		{
			currentPort.setAppProtocolName(attributes.getValue(NAME_ATTR), ServiceReason.fromNmap(attributes.getValue(METHOD_ATTR)));
		}
		if (qName.equals(OS_TAG))
		{
		}
		if (qName.equals(PORT_USED_TAG))
		{
		}
		if (qName.equals(OSCLASS_TAG))
		{
			makeCurrentOSGuess();
			currentOSGuess.setDeviceType(attributes.getValue(TYPE_ATTR));
			currentOSGuess.setVendor(attributes.getValue(VENDOR_ATTR));
			currentOSGuess.setVersion(attributes.getValue(VERSION_ATTR));
			currentOSGuess.setOsFamily(attributes.getValue(OSFAMILY_ATTR));
			currentOSGuess.setConfidence(Integer.getInteger(attributes.getValue(ACCURACY_ATTR)));
		}
		if (qName.equals(OS_MATCH_TAG))
		{
			makeCurrentOSGuess();
		}
		if (qName.equals(DISTANCE_TAG))
		{
		}
		if (qName.equals(TCP_SEQUENCE_TAG))
		{
		}
		if (qName.equals(TCP_TS_SEQUENCE_TAG))
		{
		}
		if (qName.equals(TIMES_TAG))
		{
		}
		if (qName.equals(UPTIME_TAG))
		{
			currentHost.addNode("Up since " + attributes.getValue(LASTBOOT_ATTR));
		}
		if (qName.equals(RUNSTATS_TAG))
		{
		}
		if (qName.equals(FINISHED_TAG))
		{
		}
		if (qName.equals(HOSTS_TAG))
		{
		}
		if (qName.equals(CPE_ATTR))
		{
		}

		// set the previousQName for comparison to later elements
		previousQName = qName;
	}


	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException
	{
		if (isCpeData)
		{
			String cpeText = new String(ch, start, length);
			isCpeData = false;
		}
	}


	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		if (qName.equals(NMAPRUN_TAG))
		{
		}
		if (qName.equals(SCANINFO_TAG))
		{
		}
		if (qName.equals(DEBUGGING_TAG))
		{
		}
		if (qName.equals(VERBOSE_TAG))
		{
		}
		if (qName.equals(HOST_TAG))
		{
		}
		if (qName.equals(STATUS_TAG))
		{
		}
		if (qName.equals(ADDRESS_TAG))
		{
		}
		if (qName.equals(HOSTNAME_TAG))
		{
		}
		if (qName.equals(HOSTNAMES_TAG))
		{
		}
		if (qName.equals(PORTS_TAG))
		{
		}
		if (qName.equals(PORT_TAG))
		{
		}
		if (qName.equals(STATE_TAG))
		{
		}
		if (qName.equals(SERVICE_TAG))
		{
		}
		if (qName.equals(OS_TAG))
		{
		}
		if (qName.equals(PORT_USED_TAG))
		{
		}
		if (qName.equals(OSCLASS_TAG))
		{
		}
		if (qName.equals(OS_MATCH_TAG))
		{
		}
		if (qName.equals(DISTANCE_TAG))
		{
		}
		if (qName.equals(TCP_SEQUENCE_TAG))
		{
		}
		if (qName.equals(TCP_TS_SEQUENCE_TAG))
		{
		}
		if (qName.equals(TIMES_TAG))
		{
		}
		if (qName.equals(UPTIME_TAG))
		{
		}
		if (qName.equals(RUNSTATS_TAG))
		{
		}
		if (qName.equals(FINISHED_TAG))
		{
		}
		if (qName.equals(HOSTS_TAG))
		{
		}
		if (qName.equals(CPE_ATTR))
		{
		}
	}


	@Override
	public void endDocument() throws SAXException
	{
		parseEndTime = System.currentTimeMillis();
	}


	public long getExecTime()
	{
		return parseEndTime - parseStartTime;
	}

}
