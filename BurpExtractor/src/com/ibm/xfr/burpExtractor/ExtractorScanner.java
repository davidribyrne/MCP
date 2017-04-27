package com.ibm.xfr.burpExtractor;


import burp.IBurpExtenderCallbacks;
import burp.IHttpRequestResponse;
import burp.IHttpService;
import burp.IScanIssue;
import burp.IScannerCheck;
import burp.IScannerInsertionPoint;
import mcp.commons.HttpTransaction;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExtractorScanner implements IScannerCheck
{

	private Socket clientSocket;
	private ObjectOutputStream oos;
	private final Object socketLock = new Object();
	private IBurpExtenderCallbacks callbacks;
	private InetAddress address;
	private int port;
	private boolean disconnected;


	public ExtractorScanner(IBurpExtenderCallbacks callbacks)
	{
		this.callbacks = callbacks;
	}


	public void updateSettings(InetAddress address, int port) throws IOException
	{
		synchronized (socketLock)
		{
			if (clientSocket != null)
			{
				try
				{
					clientSocket.close();
				}
				catch (IOException e)
				{
					callbacks.printError("Problem closing socket: " + e.getLocalizedMessage());
				}
			}
			try
			{
				createSocket(address, port);
			}
			catch (IOException e)
			{
				disconnected = true;
				throw e;
			}
		}
		disconnected = false;
	}


	private void checkSocket()
	{
		if (clientSocket == null)
		{
			callbacks.printOutput("null");
		}
		else
		{
			callbacks.printOutput(clientSocket.toString());
			callbacks.printOutput(String.valueOf(clientSocket.isBound()));
			callbacks.printOutput(String.valueOf(clientSocket.isClosed()));
			callbacks.printOutput(String.valueOf(clientSocket.isConnected()));
			callbacks.printOutput(String.valueOf(clientSocket.isOutputShutdown()));
			callbacks.printOutput(String.valueOf(clientSocket.isInputShutdown()));
		}
	}


	private void createSocket(InetAddress address, int port) throws IOException
	{
		try
		{
			clientSocket = new Socket(address, port);
		}
		catch (IOException e)
		{
			callbacks.printError("Problem creating BurpExtractor connection to server: " + e.getLocalizedMessage());
			throw e;
		}
		try
		{
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
		}
		catch (IOException e)
		{
			callbacks.printError("Problem creating ObjectOutputStream for BurpExtractor: " + e.getLocalizedMessage());
			throw e;
		}

		this.address = address;
		this.port = port;

	}


	@Override
	public int consolidateDuplicateIssues(IScanIssue arg0, IScanIssue arg1)
	{
		callbacks.printOutput("consolidate");
		return 0;
	}


	@Override
	public List<IScanIssue> doActiveScan(IHttpRequestResponse arg0, IScannerInsertionPoint arg1)
	{
		callbacks.printOutput("Active scan");
		return null;
	}


	@Override
	public List<IScanIssue> doPassiveScan(IHttpRequestResponse transaction)
	{
		callbacks.printOutput("passive scan");
		if (disconnected)
			return null;

		HttpTransaction t = new HttpTransaction(transaction.getRequest(), transaction.getResponse());

		synchronized (socketLock)
		{
			try
			{
				oos.writeObject(t);
			}
			catch (IOException e)
			{
				callbacks.printError("Problem sending HTTP transaction to server with BurpExtractor: " + e.toString());
				callbacks.printError("Trying to reconnect.");
				try
				{
					createSocket(address, port);
				}
				catch (IOException e1)
				{
					callbacks.printError("Failed to reconnect. Going into disconnected mode.");
					disconnected = true;
					return null;
				}
				try
				{
					oos.writeObject(t);
				}
				catch (IOException e2)
				{
					callbacks.printError("Second problem sending. Going into disconnected mode.");
					disconnected = true;
				}

			}
		}


		callbacks.printOutput("passive scan complete");
		return null;
	}
}
