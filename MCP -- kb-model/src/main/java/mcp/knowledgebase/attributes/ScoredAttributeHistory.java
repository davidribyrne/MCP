package mcp.knowledgebase.attributes;




public interface ScoredAttributeHistory<Attribute extends ScoredNodeAttribute> extends AttributeHistory<Attribute>
{

	public void addValue(Attribute attribute);


	public Attribute getAggregate();

}
