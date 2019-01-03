package mcp.shell;
/*
 * Copyright (C) 2012 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */


import java.io.IOException;

import org.crsh.cli.Named;
import org.crsh.cli.impl.SyntaxException;
import org.crsh.cli.impl.descriptor.HelpDescriptor;
import org.crsh.cli.impl.descriptor.IntrospectionException;
import org.crsh.cli.impl.invocation.InvocationException;
import org.crsh.cli.impl.invocation.InvocationMatch;
import org.crsh.cli.impl.invocation.InvocationMatcher;
import org.crsh.cli.impl.lang.CommandFactory;
import org.crsh.cli.impl.lang.Instance;
import org.crsh.cli.impl.lang.ObjectCommandDescriptor;
import org.crsh.cli.impl.lang.Util;
import org.crsh.console.jline.JLineProcessor;
import org.crsh.util.Utils;
import org.crsh.vfs.FS;
import org.crsh.vfs.spi.file.FileMountFactory;
import org.crsh.vfs.spi.url.ClassPathMountFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.modules.GeneralOptions;


@Named("crash")
public class MCPShell
{
	private final static Logger logger = LoggerFactory.getLogger(MCPShell.class);
	private final MCPBootstrap bootstrap;
	private JLineProcessor processor;
	private Thread shellThread;
	private static boolean interactiveConsole;
	private static boolean interactiveTelnet;
	private static MCPShell instance;


	private MCPShell()
	{
		bootstrap = new MCPBootstrap();


	}


	public static void setupShell()
	{
		interactiveConsole = GeneralOptions.getInstance().isInteractiveConsole();
		interactiveTelnet = GeneralOptions.getInstance().getTelnetOption().isEnabled();
		if (interactiveConsole || interactiveTelnet)
		{
			instance = new MCPShell();
			try
			{
				instance.start();
			}
			catch (IntrospectionException | InvocationException | SyntaxException | InstantiationException | IllegalAccessException e)
			{
				logger.error("Problem with shell", e);
			}
		}

	}




	public void start() throws InvocationException, SyntaxException, InstantiationException, IllegalAccessException, IntrospectionException
	{
		ObjectCommandDescriptor<MCPShell> descriptor = CommandFactory.DEFAULT.create(MCPShell.class);
		HelpDescriptor<Instance<MCPShell>> helpDescriptor = HelpDescriptor.create(descriptor);
		InvocationMatcher<Instance<MCPShell>> matcher = helpDescriptor.matcher();
		InvocationMatch<Instance<MCPShell>> match = matcher.parse("");
		match.invoke(Util.wrap(this));
	}


	private FS.Builder createBuilder() throws IOException
	{
		FileMountFactory fileDriver = new FileMountFactory(Utils.getCurrentDirectory());
		ClassPathMountFactory classpathDriver = new ClassPathMountFactory(Thread.currentThread().getContextClassLoader());
		return new FS.Builder().register("file", fileDriver).register("classpath", classpathDriver);
	}



//	@Command
//	public void main() throws Exception
//	{
//		Commands.loadCommands();
//		boolean interactive = GeneralOptions.getInstance().isInteractiveConsole();
//
//		// Do bootstrap
//		bootstrap.bootstrap();
//		if (interactive)
//		{
//			Runtime.getRuntime().addShutdownHook(new Thread()
//			{
//				@Override
//				public void run()
//				{
//					bootstrap.stop();
//				}
//			});
//
//			ShellFactory factory = bootstrap.getContext().getPlugin(ShellFactory.class);
//			Shell shell = factory.create(null);
//
//			//
//			final Terminal term = TerminalFactory.create();
//
//			//
//			String encoding = Configuration.getEncoding();
//
//			// Use AnsiConsole only if term doesn't support Ansi
//			PrintStream out;
//			boolean ansi;
//			if (term.isAnsiSupported())
//			{
//				out = new PrintStream(new BufferedOutputStream(term.wrapOutIfNeeded(new FileOutputStream(FileDescriptor.out)), 16384), false,
//						encoding);
//				ansi = true;
//			}
//			else
//			{
//				out = AnsiConsole.out;
//				ansi = false;
//			}
//			FileInputStream in = new FileInputStream(FileDescriptor.in);
//			ConsoleReader reader = new ConsoleReader(in, out);
//
//			processor = new JLineProcessor(ansi, shell, reader, out);
//			InterruptHandler interruptHandler = new InterruptHandler(new Runnable()
//			{
//				@Override
//				public void run()
//				{
//					processor.interrupt();
//				}
//			});
//			interruptHandler.install();
//
//
//			//
//			shellThread = new Thread(processor);
//			shellThread.setName("MCP shell");
//			shellThread.setDaemon(true);
//			shellThread.start();
//		}
//	}


	public MCPBootstrap getBootstrap()
	{
		return bootstrap;
	}


	public JLineProcessor getProcessor()
	{
		return processor;
	}


	public void stop()
	{
		bootstrap.getExecutor().shutdown();
	}


	public Thread getShellThread()
	{
		return shellThread;
	}


	public static boolean isActive()
	{
		// TODO: Fix this, especially telnet
		return (interactiveConsole && instance.getShellThread().isAlive())
				|| (interactiveTelnet) ;
	}

}
