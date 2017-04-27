package mcp.modules.listeners;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.commons.HttpTransaction;
import mcp.modules.Module;
import net.dacce.commons.cli.Option;
import net.dacce.commons.cli.OptionContainer;
import net.dacce.commons.cli.OptionGroup;
import net.dacce.commons.simpleServer.Server;
import net.dacce.commons.simpleServer.ThreadedWorker;
import net.dacce.commons.simpleServer.Worker;
import net.dacce.commons.validators.IPAddressValidator;
import net.dacce.commons.validators.NumericValidator;


public class HttpTransactionListener extends Module
{
	private final static Logger logger = LoggerFactory.getLogger(HttpTransactionListener.class);

	private OptionGroup group;
	private Option enabledOption;
	private Option portNumberOption;
	private Option listenAddressOption;
	private TransactionServer server;
	private InetAddress hostAddress;
	private int port;


	public HttpTransactionListener()
	{
		super("HTTP Transaction Listener");

		enabledOption = new Option(null, "transaction-listener", "Enable transaction listener", false, false, "", "");
		listenAddressOption = new Option(null, "transaction-listener-address", "IP address to listen on. THIS SERVICE MAY NOT BE SECURE. BE CAREFUL.", true, true,
				"127.0.0.1", "address|hostname");
		listenAddressOption.addValidator(new IPAddressValidator(false, false));

		portNumberOption = new Option(null, "transaction-listener-port", "TCP port to listen on.", true, true, "2063", "number");
		portNumberOption.addValidator(new NumericValidator(false, 0, 65535));

		group = new OptionGroup("HTTP Transaction Listener",
				"Listens on a network port for serialized HTTP transactions to analyze");

		group.addChild(enabledOption);
		group.addChild(listenAddressOption);
		group.addChild(portNumberOption);
	}


	@Override
	public void initialize()
	{
		if (enabledOption.isEnabled())
		{
			try
			{
				hostAddress = InetAddress.getByName(listenAddressOption.getValue());
			}
			catch (UnknownHostException e)
			{
				logger.error("The listen address for the HTTP Transaction Listener has a problem: " + e.getLocalizedMessage(), e);
				return;
			}
			port = Integer.valueOf(portNumberOption.getValue());


			try
			{
				server = new TransactionServer(hostAddress, port);
			}
			catch (IOException e)
			{
				logger.error("Problem creating HTTP Transaction Listener service: " + e.getLocalizedMessage(), e);
				return;
			}

		}
	}


	@Override
	public OptionContainer getOptions()
	{
		return group;
	}

	private class TransactionServer extends Server
	{
		public TransactionServer(InetAddress hostAddress, int port) throws IOException
		{
			super(hostAddress, port);
		}


		@Override
		protected Worker createWorker()
		{
			return new TransactionWorker();
		}
	}


	private class TransactionWorker extends ThreadedWorker
	{
		private void attemptClose()
		{
			if (clientSocket != null)
			{
				try
				{
					clientSocket.close();
				}
				catch (IOException e)
				{
				}
			}
		}


		@Override
		public void run()
		{
			ObjectInputStream ois;
			try
			{
				ois = new ObjectInputStream(clientSocket.getInputStream());
			}
			catch (IOException e)
			{
				logger.error("Problem creating input stream for HTTP transaction server: " + e.getMessage(), e);
				attemptClose();
				return;
			}


			while (true)
			{
				Object o = null;
				try
				{
					o = ois.readObject();
				}
				catch (ClassNotFoundException e)
				{
					logger.error("Very strange problem with HTTP transaction server: " + e.getLocalizedMessage(), e);
					attemptClose();
					return;
				}
				catch (IOException e)
				{
					logger.warn("Failed to read from HTTP tranaction server socket. Closing connection: " + e.getLocalizedMessage(), e);
					attemptClose();
					return;
				}
				
				if (o instanceof HttpTransaction)
				{
					HttpTransaction t = (HttpTransaction) o;
				}
				else
				{
					logger.warn("Unexpected object type in HTTP transaction server: " + o.getClass().toString());
				}
			}
		}
	}
}
