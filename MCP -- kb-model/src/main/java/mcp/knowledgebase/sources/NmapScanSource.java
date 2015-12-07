package mcp.knowledgebase.sources;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;


public interface NmapScanSource extends Source
{

	public String getOutputPath();


	public String getDescription();


	public String getCommandLine();

}
