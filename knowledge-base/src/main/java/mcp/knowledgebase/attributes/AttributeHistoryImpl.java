package mcp.knowledgebase.attributes;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Consumer;


public class AttributeHistoryImpl<Attribute extends NodeAttribute> implements AttributeHistory<Attribute>
{

	private final List<Attribute> history;

	public AttributeHistoryImpl()
	{
		history = new ArrayList<Attribute>(1);
	}

	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.AttributeHistory#getHistory()
	 */
	@Override
	public List<Attribute> getHistory()
	{
		return history;
	}

	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.AttributeHistory#addValue(Attribute)
	 */
	@Override
	public void addValue(Attribute attribute)
	{
		history.add(attribute);
	}

	public int size()
	{
		return history.size();
	}

	public boolean isEmpty()
	{
		return history.isEmpty();
	}

	public void forEach(Consumer<? super Attribute> action)
	{
		history.forEach(action);
	}

	public Iterator<Attribute> iterator()
	{
		return history.iterator();
	}

	public Spliterator<Attribute> spliterator()
	{
		return history.spliterator();
	}
	
	/* (non-Javadoc)
	 * @see mcp.knowledgebase.attributes.AttributeHistory#getLastAttribute()
	 */
	@Override
	public Attribute getLastAttribute()
	{
		return history.get(history.size() - 1);
	}
}
