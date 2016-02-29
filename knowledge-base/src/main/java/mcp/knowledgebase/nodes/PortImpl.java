package mcp.knowledgebase.nodes;

import java.lang.reflect.Field;
import org.apache.commons.lang3.builder.ToStringBuilder;
import mcp.knowledgebase.attributes.AttributeHistory;
import mcp.knowledgebase.attributes.AttributeHistoryImpl;
import mcp.knowledgebase.attributes.ScoredAttributeHistory;
import mcp.knowledgebase.attributes.ScoredAttributeHistoryImpl;
import mcp.knowledgebase.attributes.port.PortState;
import mcp.knowledgebase.attributes.port.ServiceDescription;
import mcp.knowledgebase.attributes.port.SoftwareGuess;
import net.dacce.commons.general.UnexpectedException;


public class PortImpl extends NodeImpl implements Port
{
	private final PortType type;
	private final int number;
	private final Address address;

	private final AttributeHistory<PortState> stateHistory;
	private final ScoredAttributeHistory<ServiceDescription> serviceDescriptionHistory;
	private final ScoredAttributeHistory<SoftwareGuess> softwareGuessHistory;
 
	public PortImpl(Address address, PortType type, int number)
	{
		super(address);
		this.address = address;
		this.type = type;
		this.number = number;
		stateHistory = new AttributeHistoryImpl<PortState>();
		serviceDescriptionHistory = new ScoredAttributeHistoryImpl<ServiceDescription>();
		softwareGuessHistory = new ScoredAttributeHistoryImpl<SoftwareGuess>();
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Port#setServiceDescription(mcp.knowledgebase.nodes.attributes.port.ServiceDescription)
	 */
	@Override
	public void setServiceDescription(ServiceDescription description)
	{
		serviceDescriptionHistory.addValue(description);
	}

	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Port#setSoftwareGuess(mcp.knowledgebase.nodes.attributes.port.SoftwareGuess)
	 */
	@Override
	public void setSoftwareGuess(SoftwareGuess guess)
	{
		softwareGuessHistory.addValue(guess);
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Port#getType()
	 */
	@Override
	public PortType getType()
	{
		return type;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Port#getNumber()
	 */
	@Override
	public int getNumber()
	{
		return number;
	}

	private static Field numberField;


	private static void initializeFields()
	{
		try
		{
			numberField = PortImpl.class.getDeclaredField("number");
			numberField.setAccessible(true);
		}
		catch (NoSuchFieldException e)
		{
			throw new UnexpectedException("This shouldn't have happened: " + e.getMessage(), e);
		}
		catch (SecurityException e)
		{
			throw new UnexpectedException("This shouldn't have happened: " + e.getMessage(), e);
		}
	}


	public static Field NUMBER_FIELD()
	{
		if (numberField == null)
		{
			initializeFields();
		}
		return numberField;
	}


	
	@Override
	public String toString()
	{
		ToStringBuilder tsb = new ToStringBuilder(this);
		tsb.append("number", number);
		tsb.append("type", type);
		tsb.append("stateHistory", stateHistory.getLastAttribute());
		tsb.append("serviceDescriptionHistory", serviceDescriptionHistory.getAggregate());
		return tsb.toString();
	}



	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Port#getState()
	 */
	@Override
	public AttributeHistory<PortState> getStateHistory()
	{
		return stateHistory;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Port#getServiceDescription()
	 */
	@Override
	public ScoredAttributeHistory<ServiceDescription> getServiceDescription()
	{
		return serviceDescriptionHistory;
	}


	/* (non-Javadoc)
	 * @see mcp.knowledgebase.nodes.impl.Port#getSoftwareGuess()
	 */
	@Override
	public ScoredAttributeHistory<SoftwareGuess> getSoftwareGuess()
	{
		return softwareGuessHistory;
	}


	@Override
	public void addState(PortState state)
	{
		this.stateHistory.addValue(state);
	}


	@Override
	public Address getAddressNode()
	{
		return address;
	}


//	@Override
//	public int hashCode()
//	{
//		return new HashCodeBuilder()
//				.append(type.hashCode())
//				.append(number)
//				.append(address.hashCode())
//				.append(stateHistory.hashCode())
//				.append(serviceDescriptionHistory.hashCode())
//				.append(softwareGuessHistory.hashCode())
//				.appendSuper(super.hashCode())
//				.toHashCode();
//	}
//
//
//	@Override
//	public boolean equals(Object obj)
//	{
//		PortImpl o = (PortImpl) obj; 
//		return new EqualsBuilder()
//				.append(type, o.type)
//				.append(number, o.number)
//				.append(address, o.address)
//				.append(stateHistory, o.stateHistory)
//				.append(serviceDescriptionHistory, o.serviceDescriptionHistory)
//				.append(softwareGuessHistory, o.softwareGuessHistory)
//				.isEquals();
//	}


}
