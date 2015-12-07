package mcp.knowledgebase.attributes;

import java.time.Instant;



import mcp.knowledgebase.sources.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public interface NodeAttribute
{

	public Instant getTime();


	public Source getSource();



}
