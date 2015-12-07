package mcp.events.listeners.nodeUpdate;

import mcp.knowledgebase.nodes.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public interface NodeUpdateMatcher
{
	public boolean matches(Node node, UpdateAction action);
}
