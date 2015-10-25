package mcp.knowledgebase.nodes;

import java.lang.reflect.Field;

import net.dacce.commons.general.UnexpectedException;


public class Port
{
	private final PortType type;
	private final int number;
	private PortState state;
	private String appProtocolName;
	private PortStateReason stateReason;
	private ServiceReason serviceReason;


	public Port(PortType type, int number)
	{
		this.type = type;
		this.number = number;
		state = PortState.UNKNOWN;
	}


	public void setAppProtocolName(String name, ServiceReason reason)
	{
		if ((appProtocolName == null) || (reason.getScore() > this.serviceReason.getScore()))
		{
			appProtocolName = name;
			this.serviceReason = reason;
		}
	}


	public PortType getType()
	{
		return type;
	}


	public int getNumber()
	{
		return number;
	}

	private static Field numberField;


	private static void initializeFields()
	{
		try
		{
			numberField = Port.class.getDeclaredField("number");
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


	public PortState getState()
	{
		return state;
	}


	public void setState(PortState state)
	{
		this.state = state;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(number);
		sb.append('/');
		sb.append(type.toString());
		sb.append(" - ");
		sb.append(state.toString());
		sb.append(" (");
		sb.append(stateReason.toString());
		sb.append(") - ");
		sb.append(appProtocolName);
		sb.append(" (");
		sb.append(serviceReason.toString());
		sb.append(")");
		return sb.toString();
	}


	public PortStateReason getStateReason()
	{
		return stateReason;
	}


	public void setStateReason(PortStateReason stateReason)
	{
		this.stateReason = stateReason;
	}


	public ServiceReason getServiceReason()
	{
		return serviceReason;
	}


	public void setServiceReason(ServiceReason serviceReason)
	{
		this.serviceReason = serviceReason;
	}
}
