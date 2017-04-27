package mcp.shell.commands;

import java.util.HashMap;
import java.util.Map;

import org.crsh.cli.descriptor.ParameterDescriptor;
import org.crsh.cli.spi.Completer;
import org.crsh.cli.spi.Completion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dacce.commons.general.MapOfLists;

public class MappedListCompleter implements Completer
{
	private final static Logger logger = LoggerFactory.getLogger(MappedListCompleter.class);

	private final MapOfLists<String, String> values;


	public MappedListCompleter(MapOfLists<String, String> values)
	{
		this.values = values;
	}


	@Override
	public Completion complete(ParameterDescriptor parameter, String prefix) throws Exception
	{
		Map<String, Boolean> m = new HashMap<String, Boolean>();
		for (String value : values.keySet())
		{
			if (value.startsWith(prefix))
			{
				m.put(value.substring(prefix.length()), true);
			}
		}
		return Completion.create(prefix, m);
	}
}
