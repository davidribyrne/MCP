package mcp.knowledgebase;

import java.util.UUID;

import org.apache.commons.lang3.builder.HashCodeBuilder;


public abstract class UniqueElement
{
	private UUID uuid;


	protected UniqueElement()
	{
		this.uuid = UUID.randomUUID();
	}


	protected UniqueElement(UUID uuid)
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


	public UUID getID()
	{
		return uuid;
	}
	


	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof UniqueElement)
		{
			UniqueElement o = (UniqueElement) obj;
			return o.uuid.equals(uuid);
		}
		else
		{
			return false;
		}
	}


	@Override
	public int hashCode()
	{
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(uuid);
		return hcb.toHashCode();
	}

}
