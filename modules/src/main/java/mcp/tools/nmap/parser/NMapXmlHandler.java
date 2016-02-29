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

import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import mcp.knowledgebase.KnowledgeBaseImpl;
import mcp.knowledgebase.attributes.host.OSGuess;
import mcp.knowledgebase.attributes.host.OSGuessImpl;
import mcp.knowledgebase.attributes.port.PortResponse;
import mcp.knowledgebase.attributes.port.PortState;
import mcp.knowledgebase.attributes.port.PortStateImpl;
import mcp.knowledgebase.attributes.port.PortStateReason;
import mcp.knowledgebase.attributes.port.ServiceDescription;
import mcp.knowledgebase.attributes.port.ServiceDescriptionImpl;
import mcp.knowledgebase.attributes.port.ServiceReason;
import mcp.knowledgebase.nodes.Address;
import mcp.knowledgebase.nodes.Hostname;
import mcp.knowledgebase.nodes.Port;
import mcp.knowledgebase.nodes.PortType;
import mcp.knowledgebase.sources.NmapScanSource;
import net.dacce.commons.netaddr.IPUtils;
import net.dacce.commons.netaddr.InvalidIPAddressFormatException;
import net.dacce.commons.netaddr.MacUtils;


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


	private boolean isCpeData = false;
	private final NmapScanSource source;
	private Address currentAddress;
//	private Host currentHost;
	private Port currentPort;
	private OSGuess currentOSGuess;
	private byte[] currentMac;
	private String currentMacVendor;
	private final Instant scanTime;

	// https://svn.nmap.org/nmap/docs/nmap.dtd


	public NMapXmlHandler(Instant scanTime, NmapScanSource source)
	{
		this.scanTime = scanTime;
		this.source = source;
	}
	
	@Override
	public void startDocument() throws SAXException
	{
	}


	private void makeCurrentOSGuess()
	{
		if (currentOSGuess == null)
		{
			currentOSGuess = new OSGuessImpl(scanTime, source, currentAddress.getHost() );
			currentAddress.getHost().addOSGuess(currentOSGuess);
		}
	}


	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{

		if (qName.equals(NmapDtdStrings.NMAPRUN_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.SCANINFO_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.DEBUGGING_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.VERBOSE_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.HOST_TAG))
		{
			currentAddress = null;
			currentPort = null;
			currentOSGuess = null;
			currentMac = null;
			currentMacVendor = null;
		}
		if (qName.equals(NmapDtdStrings.STATUS_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.ADDRESS_TAG))
		{
			String type = attributes.getValue(NmapDtdStrings.ADDRTYPE_ATTR).toLowerCase();
			if (type.equals("ipv4"))
			{
				try
				{
					currentAddress = KnowledgeBaseImpl.getInstance().getOrCreateAddressNode(IPUtils.fromString(attributes.getValue(NmapDtdStrings.ADDR_ATTR)));
					currentAddress.setMacAddress(currentMac);
					currentAddress.setMacVendor(currentMacVendor);
				}
				catch (InvalidIPAddressFormatException e)
				{
					logger.warn("Nmap seems to have put an invalid IP address (" + attributes.getValue(NmapDtdStrings.ADDR_ATTR) + ")in a file: " + e.getLocalizedMessage(), e);
				}
			}
			else if (type.equals("mac"))
			{
				currentMac = MacUtils.getMacFromString(attributes.getValue(NmapDtdStrings.ADDR_ATTR));
				currentMacVendor = attributes.getValue(NmapDtdStrings.VENDOR_ATTR);
				currentAddress.setMacAddress(currentMac);
				currentAddress.setMacVendor(currentMacVendor);
			}
		}
		if (qName.equals(NmapDtdStrings.HOSTNAMES_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.HOSTNAME_TAG))
		{
			Hostname hostname = KnowledgeBaseImpl.getInstance().getOrCreateHostname(attributes.getValue(NmapDtdStrings.NAME_ATTR));
			hostname.addAddress(currentAddress);
		}
		if (qName.equals(NmapDtdStrings.PORTS_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.PORT_TAG))
		{
			PortType type = PortType.fromNmap(attributes.getValue(NmapDtdStrings.PROTOCOL_ATTR));
			int number = Integer.valueOf(attributes.getValue(NmapDtdStrings.PORTID_ATTR));
			currentPort = currentAddress.getOrCreatePort(type, number);
		}
		if (qName.equals(NmapDtdStrings.STATE_TAG))
		{
			PortState state = new PortStateImpl(currentPort, scanTime, source, 
					PortResponse.parseNmapText(attributes.getValue(NmapDtdStrings.STATE_ATTR)), 
					PortStateReason.parseNmapText(attributes.getValue(NmapDtdStrings.REASON_ATTR)));
			currentPort.addState(state);
		}
		if (qName.equals(NmapDtdStrings.SERVICE_TAG))
		{
			ServiceDescription description = new ServiceDescriptionImpl(currentPort, scanTime, source, 
					attributes.getValue(NmapDtdStrings.NAME_ATTR), 
					ServiceReason.fromNmap(attributes.getValue(NmapDtdStrings.METHOD_ATTR)));
			currentPort.setServiceDescription(description);
		}
		if (qName.equals(NmapDtdStrings.OS_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.PORT_USED_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.OSCLASS_TAG))
		{
			makeCurrentOSGuess();
			currentOSGuess.setDeviceType(attributes.getValue(NmapDtdStrings.TYPE_ATTR));
			currentOSGuess.setVendor(attributes.getValue(NmapDtdStrings.VENDOR_ATTR));
			currentOSGuess.setVersion(attributes.getValue(NmapDtdStrings.VERSION_ATTR));
			currentOSGuess.setOsFamily(attributes.getValue(NmapDtdStrings.OSFAMILY_ATTR));
			currentOSGuess.setConfidence(Integer.getInteger(attributes.getValue(NmapDtdStrings.ACCURACY_ATTR)));
		}
		if (qName.equals(NmapDtdStrings.OS_MATCH_TAG))
		{
			makeCurrentOSGuess();
		}
		if (qName.equals(NmapDtdStrings.DISTANCE_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.TCP_SEQUENCE_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.TCP_TS_SEQUENCE_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.TIMES_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.UPTIME_TAG))
		{
			currentAddress.getHost().addNote("Up since " + attributes.getValue(NmapDtdStrings.LASTBOOT_ATTR));
		}
		if (qName.equals(NmapDtdStrings.RUNSTATS_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.FINISHED_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.HOSTS_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.CPE_ATTR))
		{
		}

		// set the previousQName for comparison to later elements
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
		if (qName.equals(NmapDtdStrings.NMAPRUN_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.SCANINFO_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.DEBUGGING_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.VERBOSE_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.HOST_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.STATUS_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.ADDRESS_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.HOSTNAME_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.HOSTNAMES_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.PORTS_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.PORT_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.STATE_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.SERVICE_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.OS_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.PORT_USED_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.OSCLASS_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.OS_MATCH_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.DISTANCE_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.TCP_SEQUENCE_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.TCP_TS_SEQUENCE_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.TIMES_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.UPTIME_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.RUNSTATS_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.FINISHED_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.HOSTS_TAG))
		{
		}
		if (qName.equals(NmapDtdStrings.CPE_ATTR))
		{
		}
	}


	@Override
	public void endDocument() throws SAXException
	{
	}



}
