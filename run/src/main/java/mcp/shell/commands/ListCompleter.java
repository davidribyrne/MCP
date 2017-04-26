<<<<<<< Updated upstream
package mcp.shell.commands;

import org.crsh.cli.descriptor.ParameterDescriptor;
import org.crsh.cli.spi.Completer;
import org.crsh.cli.spi.Completion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;


public abstract class ListCompleter implements Completer
{
	private final String[] values;


	protected ListCompleter(String[] values)
	{
		this.values = values;
	}


	@Override
	public Completion complete(ParameterDescriptor parameter, String prefix) throws Exception
	{
		Map<String, Boolean> m = new HashMap<String, Boolean>();
		for (String value : values)
		{
			if (value.startsWith(prefix))
			{
				m.put(value.substring(prefix.length()), true);
			}
		}
		return Completion.create(prefix, m);
	}
}
=======
package mcp.shell.commands;

import org.crsh.cli.descriptor.ParameterDescriptor;
import org.crsh.cli.spi.Completer;
import org.crsh.cli.spi.Completion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;


public abstract class ListCompleter implements Completer
{
	private final String[] values;


	protected ListCompleter(String[] values)
	{
		this.values = values;
	}


	@Override
	public Completion complete(ParameterDescriptor parameter, String prefix) throws Exception
	{
		Map<String, Boolean> m = new HashMap<String, Boolean>();
		for (String value : values)
		{
			if (value.startsWith(prefix))
			{
				m.put(value.substring(prefix.length()), true);
			}
		}
		return Completion.create(prefix, m);
	}
}
>>>>>>> Stashed changes
