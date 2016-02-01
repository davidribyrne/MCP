package mcp.knowledgebase.attributes;

import java.util.List;


public interface AttributeHistory<Attribute extends NodeAttribute> extends Iterable<Attribute>
{

	public List<Attribute> getHistory();


	public void addValue(Attribute attribute);


	public Attribute getLastAttribute();
	
	public boolean isEmpty();

}
