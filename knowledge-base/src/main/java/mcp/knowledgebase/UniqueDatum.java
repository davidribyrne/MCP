package mcp.knowledgebase;

import java.util.UUID;

import org.apache.commons.lang3.builder.HashCodeBuilder;


public abstract class UniqueDatum
{
	private UUID uuid;


	protected UniqueDatum()
	{
		this.uuid = UUID.randomUUID();
	}


	protected UniqueDatum(UUID uuid)
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
		if (obj instanceof UniqueDatum)
		{
			UniqueDatum o = (UniqueDatum) obj;
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
