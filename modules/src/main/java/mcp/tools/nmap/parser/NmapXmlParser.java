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

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import space.dcce.commons.node_database.Connection;
import mcp.knowledgebase.KnowledgeBase;
import space.dcce.commons.node_database.Node;
import mcp.knowledgebase.nodeLibrary.General;
import mcp.knowledgebase.nodeLibrary.Nmap;
import space.dcce.commons.general.UnexpectedException;


/**
 * The OnePassParser takes a document and parsers it all the way through and
 * returns a tree of Objects that represent the XML that was just parsed.
 * <p>
 * This type of parsing is best for smaller documents where the time to parse it is small and the amount of data is
 * less. If you have a large XML document, you may want to consider using the NotifyingParser which issues an event
 * listener model, allowing you to listen in for host objects as they are parsed.
 * <p>
 * To use this class, pass in a either a File object or an InputStream and call the parse() method. You will receive a
 * tree of objects that contain the contents of the XML.
 *
 * @author jsvede
 *
 */
public class NmapXmlParser
{
	final static Logger logger = LoggerFactory.getLogger(NmapXmlParser.class);

	private NmapXmlParser()
	{
	}

	public static void parse(File xmlResultsFile, String scanDescription)
	{
		Pattern endTimePattern = Pattern.compile("<runstats><finished time=\"(\\d+)" );
		String xmlResults;
		try
		{
			xmlResults = FileUtils.readFileToString(xmlResultsFile);
		}
		catch (IOException e)
		{
			logger.error("Failed to parse nmap results file '" + xmlResultsFile.toString() + "': " + e.getLocalizedMessage(), e);
			return;
		}
		
		Matcher m = endTimePattern.matcher(xmlResults);
		Instant scanTime = null;
		if (m.find())
		{
			int t;
			try
			{
				t = Integer.parseInt(m.group(1));
				scanTime = Instant.ofEpochSecond(t);
			}
			catch (NumberFormatException e)
			{
				logger.warn("Invalid number format", e);
			}
		}

		if (scanTime == null)
		{
			logger.debug("Failed to find scan completion time in nmap results. Using current time.");
			scanTime = Instant.now();
		}
		String path;
		try
		{
			path = xmlResultsFile.getCanonicalPath();
		}
		catch (IOException e1)
		{
			logger.debug("Why did the canonical path fail?", e1);
			path = xmlResultsFile.getAbsolutePath();
		}
		
		
		Node source = KnowledgeBase.INSTANCE.getOrCreateNode(Nmap.NMAP_SCAN_SOURCE, path);
		Node description = KnowledgeBase.INSTANCE.getOrCreateNode(General.NOTE, scanDescription);
		
		Connection scan = KnowledgeBase.INSTANCE.getOrCreateConnection(source, description);
		
		NMapXmlHandler nmxh = new NMapXmlHandler(scan);
		SAXParserFactory spf = SAXParserFactory.newInstance();

		SAXParser sp;
		try
		{
			sp = spf.newSAXParser();
		}
		catch (ParserConfigurationException e)
		{
			throw new UnexpectedException("This shouldn't have happened: " + e.getLocalizedMessage(), e);
		}
		catch (SAXException e)
		{
			throw new UnexpectedException("This shouldn't have happened: " + e.getLocalizedMessage(), e);
		}

		try
		{
			sp.parse(xmlResultsFile, nmxh);
		}
		catch (SAXException e)
		{
			logger.error("Failed to parse nmap results file '" + xmlResultsFile.toString() + "': " + e.getLocalizedMessage(), e);
			return;
		}
		catch (IOException e)
		{
			logger.error("Failed to parse nmap results file '" + xmlResultsFile.toString() + "': " + e.getLocalizedMessage(), e);
			return;
		}
	}
}
