<<<<<<< Updated upstream
package mcp.knowledgebase.nodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.attributes.URL;

import java.util.*;

public interface Website extends Node
{
	public Iterable<Port> getServices();
	public Iterable<URL> getURLs();
}
=======
package mcp.knowledgebase.nodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.attributes.URL;

import java.util.*;

public interface Website extends Node
{
	public Iterable<Port> getServices();
	public Iterable<URL> getURLs();
}
>>>>>>> Stashed changes
