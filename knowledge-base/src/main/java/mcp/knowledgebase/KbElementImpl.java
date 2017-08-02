package mcp.knowledgebase;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import mcp.knowledgebase.nodes.Node;
import net.dacce.commons.general.SlimList;

@Entity(name="Elements")
public class KbElementImpl implements KbElement
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @ElementCollection(targetClass=String.class)
	private List<String> notes;

    @ElementCollection(targetClass=Node.class)
    private List<Node> secondaryParents;
	
    private Node parent;
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private long id;

	public KbElementImpl(Node parent)
	{
		this.parent = parent;
		notes = new SlimList<String>();
		secondaryParents = new SlimList<Node>();
	}


	protected KbElementImpl()
	{
	}


	@Override
	public List<String> getNotes()
	{
		return notes;
	}


	@Override
	public void addNote(String note)
	{
		notes.add(note);
	}

	@Override
	public Node getParent()
	{
		return parent;
	}

	@Override
	public void addSecondaryParent(Node secondaryParent)
	{
		secondaryParents.add(secondaryParent);		
	}

	@Override
	public List<Node> getSecondaryParents()
	{
		return secondaryParents;
	}


	public long getId()
	{
		return id;
	}


	private void setId(long id)
	{
		this.id = id;
	}

}
