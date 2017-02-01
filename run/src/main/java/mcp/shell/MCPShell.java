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


import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.crsh.cli.Command;
import org.crsh.cli.Named;
import org.crsh.cli.descriptor.CommandDescriptor;
import org.crsh.cli.impl.SyntaxException;
import org.crsh.cli.impl.bootstrap.CommandProvider;
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
import org.crsh.console.jline.Terminal;
import org.crsh.console.jline.TerminalFactory;
import org.crsh.console.jline.console.ConsoleReader;
import org.crsh.console.jline.internal.Configuration;
import org.crsh.shell.Shell;
import org.crsh.shell.ShellFactory;
import org.crsh.shell.impl.command.ExternalResolver;
import org.crsh.util.InterruptHandler;
import org.crsh.util.Utils;
import org.crsh.vfs.FS;
import org.crsh.vfs.spi.file.FileMountFactory;
import org.crsh.vfs.spi.url.ClassPathMountFactory;
import org.fusesource.jansi.AnsiConsole;

import mcp.shell.commands.*;


@Named("crash")
public class MCPShell
{
	private static Logger log = Logger.getLogger(MCPShell.class.getName());
	private final MCPBootstrap2 bootstrap;
	private JLineProcessor processor;
	private Thread shellThread;
	
	public MCPShell()
	{
		bootstrap = new MCPBootstrap2();
	}

	private void loadCommands()
	{
		ExternalResolver.INSTANCE.addCommand("date", "show the date", date.class);
		ExternalResolver.INSTANCE.addCommand("ps", "show jobs", ps.class);

	}
	
	public void start() throws InvocationException, SyntaxException, InstantiationException, IllegalAccessException, IntrospectionException
	{
		ObjectCommandDescriptor<MCPShell> descriptor = CommandFactory.DEFAULT.create(MCPShell.class);
		HelpDescriptor<Instance<MCPShell>> helpDescriptor = HelpDescriptor.create(descriptor);
		InvocationMatcher<Instance<MCPShell>> matcher = helpDescriptor.matcher();
		InvocationMatch<Instance<MCPShell>> match = matcher.parse("");
//		final MCPShell instance = MCPShell.class.newInstance();
		Object o = match.invoke(Util.wrap(this));
//		if (o != null)
//		{
//			System.out.println(o);
//		}
	}


	private FS.Builder createBuilder() throws IOException
	{
		FileMountFactory fileDriver = new FileMountFactory(Utils.getCurrentDirectory());
		ClassPathMountFactory classpathDriver = new ClassPathMountFactory(Thread.currentThread().getContextClassLoader());
		return new FS.Builder().register("file", fileDriver).register("classpath", classpathDriver);
	}


	@Command
	public void main() throws Exception
	{
		loadCommands();
		String conf = "classpath:/crash/";
		FS.Builder confBuilder = createBuilder().mount(conf);

		//
		String cmd = "classpath:/crash/commands/";
		FS.Builder cmdBuilder = createBuilder().mount(cmd);

		//
		log.log(Level.INFO, "conf mounts: " + confBuilder.toString());
		log.log(Level.INFO, "cmd mounts: " + cmdBuilder.toString());


		// Do bootstrap
		bootstrap.bootstrap();
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				bootstrap.stop();
			}
		});

		//
		ShellFactory factory = bootstrap.getContext().getPlugin(ShellFactory.class);
		Shell shell = factory.create(null);

		//
		final Terminal term = TerminalFactory.create();

		//
		String encoding = Configuration.getEncoding();

		// Use AnsiConsole only if term doesn't support Ansi
		PrintStream out;
		boolean ansi;
		if (term.isAnsiSupported())
		{
			out = new PrintStream(new BufferedOutputStream(term.wrapOutIfNeeded(new FileOutputStream(FileDescriptor.out)), 16384), false,
					encoding);
			ansi = true;
		}
		else
		{
			out = AnsiConsole.out;
			ansi = false;
		}

		FileInputStream in = new FileInputStream(FileDescriptor.in);
		ConsoleReader reader = new ConsoleReader(in, out);

		processor = new JLineProcessor(ansi, shell, reader, out);

		InterruptHandler interruptHandler = new InterruptHandler(new Runnable()
		{
			@Override
			public void run()
			{
				processor.interrupt();
			}
		});
		interruptHandler.install();

		//
		shellThread = new Thread(processor);
		shellThread.setName("MCP shell");
		shellThread.setDaemon(true);
		shellThread.start();

		//
//		try
//		{
//			// This is where the shell thread blocks
//			processor.closed();
//		}
//		catch (Throwable t)
//		{
//			t.printStackTrace();
//		}
	}

	public MCPBootstrap2 getBootstrap()
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

}
