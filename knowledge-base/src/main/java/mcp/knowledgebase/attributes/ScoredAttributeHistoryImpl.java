package mcp.knowledgebase.attributes;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ScoredAttributeHistoryImpl<Attribute extends ScoredNodeAttribute> extends AttributeHistoryImpl<Attribute> implements ScoredAttributeHistory<Attribute>
{
	private Attribute aggregate;

	public ScoredAttributeHistoryImpl()
	{
	}
	
	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.ScoredAttributeHistory#addValue(Attribute)
	 */
	@Override
	public void addValue(Attribute attribute)
	{
		super.addValue(attribute);
		if(aggregate == null || attribute.getConfidence() > aggregate.getConfidence())
			this.aggregate = attribute;
	}
	

	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.ScoredAttributeHistory#getAggregate()
	 */
	@Override
	public Attribute getAggregate()
	{
//		if (aggregate == null)
//		{
//			throw new IllegalStateException("No value has been added, so there is no aggregate.");
//		}
		return aggregate;
	}

	
	@Override
	public String toString()
	{
		return new ToStringBuilder(this).appendSuper(super.toString()).append("aggregate", aggregate).build();
	}

}
