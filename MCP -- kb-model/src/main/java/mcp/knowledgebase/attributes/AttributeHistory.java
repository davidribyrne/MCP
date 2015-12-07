package mcp.knowledgebase.attributes;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public interface AttributeHistory<Attribute extends NodeAttribute> extends Iterable<Attribute>
{

	public List<Attribute> getHistory();


	public void addValue(Attribute attribute);


	public Attribute getLastAttribute();
	
	public boolean isEmpty();

}
