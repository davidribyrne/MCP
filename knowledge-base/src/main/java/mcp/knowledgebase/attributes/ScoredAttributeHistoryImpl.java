package mcp.knowledgebase.attributes;


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
		addValue(attribute);
		if(aggregate == null || attribute.getConfidence() > aggregate.getConfidence())
			this.aggregate = attribute;
	}
	

	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.ScoredAttributeHistory#getAggregate()
	 */
	@Override
	public Attribute getAggregate()
	{
		return aggregate;
	}

}
