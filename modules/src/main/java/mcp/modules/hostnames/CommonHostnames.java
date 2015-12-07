package mcp.modules.hostnames;

import mcp.events.EventDispatcher;
import mcp.events.events.NodeUpdateEvent;
import mcp.events.listeners.NodeUpdateListener;
import mcp.events.listeners.NodeUpdateType;
import mcp.modules.Module;
import net.dacce.commons.cli.OptionContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;

public class CommonHostnames extends Module implements NodeUpdateListener
{
	private final static Logger logger = LoggerFactory.getLogger(CommonHostnames.class);


	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		return sb.toString();
	}


	@Override
	public int hashCode()
	{
		return super.hashCode();
	}


	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj);
	}


	public CommonHostnames()
	{
	}


	@Override
	public void initialize()
	{
		if (HostnameDiscoveryGeneralOptions.getInstance().getTestCommonHostnamesOption().isEnabled())
		{
			EventDispatcher.getInstance().registerListener(NodeUpdateEvent.class, this);
		}
		URL url = HostnameDiscoveryGeneralOptions.class.getResource("/common-host-names.txt");
		
	}

	private void loadHostnames()
	{
		
	}

	@Override
	public OptionContainer getOptions()
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void handleEvent(NodeUpdateEvent reconEvent)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public Collection<NodeUpdateType> getNodeUpdateEventTypes()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
