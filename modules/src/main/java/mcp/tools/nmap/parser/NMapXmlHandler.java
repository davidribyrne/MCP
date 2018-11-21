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

import mcp.knowledgebase.Connection;
import mcp.knowledgebase.KnowledgeBase;
import mcp.knowledgebase.Node;
import mcp.knowledgebase.nodeLibrary.General;
import mcp.knowledgebase.nodeLibrary.Hostnames;
import mcp.knowledgebase.nodeLibrary.Icmp;
import mcp.knowledgebase.nodeLibrary.Network;
import mcp.knowledgebase.nodeLibrary.NetworkService;
import mcp.knowledgebase.nodeLibrary.Port;
import mcp.knowledgebase.nodeLibrary.SoftwareHardware;
import mcp.knowledgebase.primitives.Primitive;
import mcp.modules.GeneralOptions;
import space.dcce.commons.netaddr.IPUtils;
import space.dcce.commons.netaddr.InvalidIPAddressFormatException;
import space.dcce.commons.netaddr.MacUtils;
import space.dcce.commons.netaddr.SimpleInetAddress;


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
	// private final Node scanSource;
	private Node currentIPAddress;
	private String tempIPAddress;
	private Connection currentPort;
	private Integer currentPortNumber;
	private Node currentPortType;
	private Node currentPortState;
	private Node currentOSGuess;
	private Node currentMac;
	private final Instant scanTime;
	private boolean trackAll;
	private String currentServiceName;
	private Node currentServiceReason;
	private Node currentServiceDescription;
	private Node currentIcmpResponse;
	// https://svn.nmap.org/nmap/docs/nmap.dtd


	public NMapXmlHandler(Instant scanTime, Node source)
	{
		this.scanTime = scanTime;
		// this.scanSource = source;
		trackAll = GeneralOptions.getInstance().getTrackAllData().isEnabled();
	}


	@Override
	public void startDocument() throws SAXException
	{
	}


	private void makeCurrentOSGuess()
	{
		if (currentOSGuess == null)
		{
			currentOSGuess = KnowledgeBase.getInstance().getOrCreateNode(SoftwareHardware.HOST_OS_GUESS, "");
			Connection.getOrCreateConnection(currentOSGuess, currentIPAddress);
		}
	}


	private Node getCurrentIPAddress()
	{
		if (currentIPAddress == null)
		{
			currentIPAddress = KnowledgeBase.getInstance().getOrCreateNode(Network.IPV4_ADDRESS, tempIPAddress);
		}
		return currentIPAddress;
	}


	private Connection getCurrentPort()
	{
		if (currentPort == null)
		{
			Node portNumber = KnowledgeBase.getInstance().getOrCreateNode(mcp.knowledgebase.nodeLibrary.Port.PORT_NUMBER,
					currentPortNumber + "");
			currentPort = Connection.getOrCreateConnection(true, currentPortType, portNumber, currentIPAddress);
			currentPort.addNodes(currentPortState, currentServiceDescription, currentServiceReason);
		}
		return currentPort;
	}


	private Node getCurrentServiceDescription()
	{
		if (currentServiceDescription == null)
		{
			currentServiceDescription = KnowledgeBase.getInstance().getOrCreateNode(NetworkService.NETWORK_SERVICE_DESCRIPTION,
					currentServiceName);
			Connection.getOrCreateConnection(currentServiceDescription, currentServiceReason);
		}
		return currentServiceDescription;
	}


	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{
		try
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
				currentIPAddress = null;
				tempIPAddress = null;
				currentPort = null;
				currentOSGuess = null;
				currentMac = null;
				currentPortType = null;
				currentPortNumber = null;
				currentPortState = null;
				currentServiceName = null;
				currentServiceReason = null;
				currentIcmpResponse = null;
			}
			if (qName.equals(NmapDtdStrings.STATUS_TAG))
			{
				currentIcmpResponse = Icmp.parseIcmpResponseFromNmap(attributes.getValue(NmapDtdStrings.REASON_ATTR));
				// IcmpResponseType.parseFromNmap(attributes.getValue(NmapDtdStrings.REASON_ATTR));
				if (currentIcmpResponse != null && currentIPAddress != null)
				{
					Connection.getOrCreateConnection(getCurrentIPAddress(), currentIcmpResponse);
					// getCurrentIPAddress().setIcmpResponse(currentIcmpResponse);
				}
			}
			if (qName.equals(NmapDtdStrings.ADDRESS_TAG))
			{
				String type = attributes.getValue(NmapDtdStrings.ADDRTYPE_ATTR).toLowerCase();
				if (type.equals("ipv4"))
				{
					// tempIPAddress = IPUtils.fromString(attributes.getValue(NmapDtdStrings.ADDR_ATTR));
					tempIPAddress = attributes.getValue(NmapDtdStrings.ADDR_ATTR);

					if (currentMac != null)
					{
						Connection.getOrCreateConnection(getCurrentIPAddress(), currentMac);
					}
					if (currentIcmpResponse != null)
					{
						Connection.getOrCreateConnection(getCurrentIPAddress(), currentIcmpResponse);
					}
				}
				else if (type.equals("mac"))
				{
					String rawMac = attributes.getValue(NmapDtdStrings.ADDR_ATTR);
					byte[] hexMac = MacUtils.getMacFromString(rawMac);
					currentMac = KnowledgeBase.getInstance().getOrCreateNode(Network.MAC_ADDRESS, MacUtils.getHexString(hexMac));
					Connection.getOrCreateConnection(currentMac, getCurrentIPAddress());
					Node macVendor = KnowledgeBase.getInstance().getOrCreateNode(SoftwareHardware.VENDOR,
							attributes.getValue(NmapDtdStrings.VENDOR_ATTR));
					Connection.getOrCreateConnection(currentMac, macVendor);
				}
			}
			if (qName.equals(NmapDtdStrings.HOSTNAMES_TAG))
			{
			}
			if (qName.equals(NmapDtdStrings.HOSTNAME_TAG))
			{
				Node hostname = KnowledgeBase.getInstance().getOrCreateNode(Hostnames.HOSTNAME, attributes.getValue(NmapDtdStrings.NAME_ATTR));
				Connection.getOrCreateConnection(getCurrentIPAddress(), hostname);
			}
			if (qName.equals(NmapDtdStrings.PORTS_TAG))
			{
			}
			if (qName.equals(NmapDtdStrings.PORT_TAG))
			{
				currentPortType = Port.portTypeFromNmap(attributes.getValue(NmapDtdStrings.PROTOCOL_ATTR));
				currentPortNumber = Integer.valueOf(attributes.getValue(NmapDtdStrings.PORTID_ATTR));

			}
			if (qName.equals(NmapDtdStrings.STATE_TAG))
			{
				currentPortState = Port.portStateFromNmap(attributes.getValue(NmapDtdStrings.STATE_ATTR));
				if (currentPortState == Port.PORT_STATE_OPEN || trackAll)
				{

					Node stateReason = KnowledgeBase.getInstance().getOrCreateNode(Port.PORT_STATE, attributes.getValue(NmapDtdStrings.REASON_ATTR));

					getCurrentPort().addNode(stateReason);

					// If we've already parsed the service tag, handle it
					if (currentServiceName != null)
					{

						getCurrentPort().addNode(getCurrentServiceDescription());
					}
				}
			}
			if (qName.equals(NmapDtdStrings.SERVICE_TAG))
			{
				currentServiceName = attributes.getValue(NmapDtdStrings.NAME_ATTR);
				currentServiceReason = NetworkService.serviceReasonFromNmap(attributes.getValue(NmapDtdStrings.METHOD_ATTR));
				// If the port is open, or we're logging everything, put the service description in
				if (Port.PORT_STATE_OPEN.equals(currentPortState) || trackAll)
				{

					getCurrentPort().addNode(getCurrentServiceDescription());
				}
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
				Node deviceType = KnowledgeBase.getInstance().getOrCreateNode(SoftwareHardware.DEVICE_TYPE,
						attributes.getValue(NmapDtdStrings.TYPE_ATTR));
				Node vendor = KnowledgeBase.getInstance().getOrCreateNode(SoftwareHardware.VENDOR, attributes.getValue(NmapDtdStrings.VENDOR_ATTR));
				Node version = KnowledgeBase.getInstance().getOrCreateNode(SoftwareHardware.SOFTWARE_PRODUCT_VERSION,
						attributes.getValue(NmapDtdStrings.VERSION_ATTR));
				Node family = KnowledgeBase.getInstance().getOrCreateNode(SoftwareHardware.SOFTWARE_PRODUCT_FAMILY,
						attributes.getValue(NmapDtdStrings.OSFAMILY_ATTR));
				Node confidence = KnowledgeBase.getInstance().getOrCreateNode(SoftwareHardware.HOST_OS_GUESS_CONFIDENCE,
						attributes.getValue(NmapDtdStrings.ACCURACY_ATTR));

				Connection c = Connection.getOrCreateConnection(true, currentOSGuess, currentIPAddress);
				c.addNodes(deviceType, vendor, version, family, confidence);
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
				Node note = KnowledgeBase.getInstance().getOrCreateNode(General.NOTE,
						"Up since " + attributes.getValue(NmapDtdStrings.LASTBOOT_ATTR));
				Connection.getOrCreateConnection(getCurrentIPAddress(), note);
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
		catch (Exception e) // Xerxes swallows runtime exceptions, so we need to catch them here.
		{
			logger.error("Problem parsing nmap XML.", e);
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
