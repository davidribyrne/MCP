package mcp.knowledgebase.attributes.host;


import mcp.knowledgebase.attributes.ScoredNodeAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public interface OSGuess extends ScoredNodeAttribute
{

	public String getVendor();


	public String getOsFamily();


	public String getVersion();


	public String getDeviceType();


	public String getLongName();


	public void setVendor(String vendor);

	public void setConfidence(int confidence);



	public void setOsFamily(String osFamily);


	public void setVersion(String version);


	public void setDeviceType(String deviceType);


	public void setLongName(String longName);

}
