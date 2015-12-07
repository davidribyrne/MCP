package mcp.knowledgebase.attributes;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;


public interface ScoredAttributeHistory<Attribute extends ScoredNodeAttribute> extends AttributeHistory<Attribute>
{

	public void addValue(Attribute attribute);


	public Attribute getAggregate();

}
