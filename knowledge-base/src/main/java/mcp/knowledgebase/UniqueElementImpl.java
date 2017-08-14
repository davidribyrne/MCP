package mcp.knowledgebase;

import java.util.*;


public abstract class UniqueElementImpl implements UniqueElement
{
	private UUID uuid;


	protected UniqueElementImpl()
	{
		this.uuid = UUID.randomUUID();
	}


	protected UniqueElementImpl(UUID uuid)
	{
		if (uuid == null)
		{
			this.uuid = UUID.randomUUID();
		}
		else
		{
			this.uuid = uuid;
		}
	}


	@Override
	public UUID getID()
	{
		return uuid;
	}
}
