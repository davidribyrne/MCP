package mcp.knowledgebase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dacce.commons.general.SlimList;

import java.util.*;

public abstract class ComponentWithAttributes extends UniqueDatum
{
	private SlimList attributes;

	public ComponentWithAttributes()
	{
	}


	public ComponentWithAttributes(UUID uuid)
	{
		super(uuid);
	}
}
