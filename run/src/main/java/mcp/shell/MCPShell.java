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


import com.sun.tools.attach.VirtualMachine;
import jline.AnsiWindowsTerminal;
import jline.Terminal;
import jline.TerminalFactory;
// import jline.console.ConsoleReader;
import jline.internal.Configuration;
import org.crsh.cli.Argument;
import org.crsh.cli.Command;
import org.crsh.cli.Named;
import org.crsh.cli.Option;
import org.crsh.cli.Usage;
import org.crsh.cli.descriptor.CommandDescriptor;
import org.crsh.cli.impl.Delimiter;
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
import org.crsh.console.jline.console.ConsoleReader;
import org.crsh.plugin.ResourceManager;
import org.crsh.shell.Shell;
import org.crsh.shell.ShellFactory;
import org.crsh.shell.impl.remoting.RemoteServer;
import org.crsh.standalone.Agent;
import org.crsh.standalone.Bootstrap;
import org.crsh.util.CloseableList;
import org.crsh.util.InterruptHandler;
import org.crsh.util.Utils;
import org.crsh.vfs.FS;
import org.crsh.vfs.Path;
import org.crsh.vfs.Resource;
import org.crsh.vfs.spi.Mount;
import org.crsh.vfs.spi.file.FileMountFactory;
import org.crsh.vfs.spi.url.ClassPathMountFactory;
import org.fusesource.jansi.AnsiConsole;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


@Named("crash")
public class MCPShell
{

	private CommandProvider commandProvider;

	/** . */
	private static Logger log = Logger.getLogger(MCPShell.class.getName());

	/** . */
	private final CommandDescriptor<Instance<MCPShell>> descriptor;


	public MCPShell() throws IntrospectionException
	{
		this.descriptor = CommandFactory.DEFAULT.create(MCPShell.class);
		ServiceLoader<CommandProvider> loader = ServiceLoader.load(CommandProvider.class);
		Iterator<CommandProvider> iterator = loader.iterator();
		if (iterator.hasNext())
		{
			commandProvider = iterator.next();
		}

	}

	public void start() throws InvocationException, SyntaxException, InstantiationException, IllegalAccessException, IntrospectionException 
	{
//		Class<?> commandClass = commandProvider.getCommandClass();
//		CRaSHProvider provider = new CRaSHProvider();
		ObjectCommandDescriptor<MCPShell> descriptor = CommandFactory.DEFAULT.create(MCPShell.class);
		HelpDescriptor<Instance<MCPShell>> helpDescriptor = HelpDescriptor.create(descriptor);
		InvocationMatcher<Instance<MCPShell>> matcher = helpDescriptor.matcher();
		InvocationMatch<Instance<MCPShell>> match = matcher.parse("");
		final MCPShell instance = MCPShell.class.newInstance();
		Object o = match.invoke(Util.wrap(instance));
		if (o != null)
		{
			System.out.println(o);
		}
	}


//	private <T> void handle(Class<T> commandClass, String line) throws InvocationException, SyntaxException, InstantiationException, IllegalAccessException, IntrospectionException 
//	{
//		ObjectCommandDescriptor<T> descriptor = CommandFactory.DEFAULT.create(commandClass);
//		HelpDescriptor<Instance<T>> helpDescriptor = HelpDescriptor.create(descriptor);
//		InvocationMatcher<Instance<T>> matcher = helpDescriptor.matcher();
//		InvocationMatch<Instance<T>> match = matcher.parse(line);
//		final T instance = commandClass.newInstance();
//		Object o = match.invoke(Util.wrap(instance));
//		if (o != null)
//		{
//			System.out.println(o);
//		}
//	}







	private FS.Builder createBuilder() throws IOException
	{
		FileMountFactory fileDriver = new FileMountFactory(Utils.getCurrentDirectory());
		ClassPathMountFactory classpathDriver = new ClassPathMountFactory(Thread.currentThread().getContextClassLoader());
		return new FS.Builder().register("file", fileDriver).register("classpath", classpathDriver);
	}


	@Command
	public void main() throws Exception
	{


		// @Option(names= {"non-interactive"})
		// @Usage("non interactive mode, the JVM io will not be used")
		// Boolean nonInteractive,
		// @Option(names={"c","cmd"})
		// @Usage("the command mounts")
		// String cmd,
		// @Option(names={"conf"})
		// @Usage("the conf mounts")
		// String conf,
		// @Option(names={"p","property"})
		// @Usage("set a property of the form a=b")
		// List<String> properties,
		// @Option(names = {"cmd-folder"})
		// @Usage("a folder in which commands should be extracted")
		// String cmdFolder,
		// @Option(names = {"conf-folder"})
		// @Usage("a folder in which configuration should be extracted")
		// String confFolder,
		// @Argument(name = "pid")
		// @Usage("the optional list of JVM process id to attach to")
		// List<Integer> pids
		//
		boolean interactive = true;
		String conf = "classpath:/crash/";
		FS.Builder confBuilder = createBuilder().mount(conf);

		//
		String cmd = "classpath:/crash/commands/";
		FS.Builder cmdBuilder = createBuilder().mount(cmd);

		//
		log.log(Level.INFO, "conf mounts: " + confBuilder.toString());
		log.log(Level.INFO, "cmd mounts: " + cmdBuilder.toString());


		//
		CloseableList closeable = new CloseableList();
		Shell shell;
//		final MCPBootstrap bootstrap = new MCPBootstrap(
//				Thread.currentThread().getContextClassLoader(),
//				confBuilder.build(),
//				cmdBuilder.build());

		final MCPBootstrap2 bootstrap = new MCPBootstrap2();
		//

		// Register shutdown hook
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				// Should trigger some kind of run interruption
			}
		});

		// Do bootstrap
		bootstrap.bootstrap();
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
//				bootstrap.shutdown();
			}
		});

		//
		ShellFactory factory = bootstrap.getContext().getPlugin(ShellFactory.class);
		shell = factory.create(null);
		closeable = null;

		//
		if (shell != null)
		{

			//
			final Terminal term = TerminalFactory.create();

			//
			Runtime.getRuntime().addShutdownHook(new Thread()
			{
				@Override
				public void run()
				{
					try
					{
						term.restore();
					}
					catch (Exception ignore)
					{
					}
				}
			});

			//
			String encoding = Configuration.getEncoding();

			// Use AnsiConsole only if term doesn't support Ansi
			PrintStream out;
			PrintStream err;
			boolean ansi;
			if (term.isAnsiSupported())
			{
				out = new PrintStream(new BufferedOutputStream(term.wrapOutIfNeeded(new FileOutputStream(FileDescriptor.out)), 16384), false,
						encoding);
				err = new PrintStream(new BufferedOutputStream(term.wrapOutIfNeeded(new FileOutputStream(FileDescriptor.err)), 16384), false,
						encoding);
				ansi = true;
			}
			else
			{
				out = AnsiConsole.out;
				err = AnsiConsole.err;
				ansi = false;
			}

			//
			FileInputStream in = new FileInputStream(FileDescriptor.in);
			// ConsoleReader reader = new ConsoleReader(null, in, out, term);
			ConsoleReader reader = new ConsoleReader(in, out);

			final JLineProcessor processor = new JLineProcessor(ansi, shell, reader, out);

			//
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
			Thread thread = new Thread(processor);
			thread.setDaemon(true);
			thread.start();

			//
			try
			{
				processor.closed();
			}
			catch (Throwable t)
			{
				t.printStackTrace();
			}
			finally
			{

				//
				if (closeable != null)
				{
					Utils.close(closeable);
				}

				// Force exit
				System.exit(0);
			}
		}
	}

}
