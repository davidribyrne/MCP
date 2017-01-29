package mcp.modules.parsers;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.dacce.commons.cli.OptionContainer;
import java.io.File;
import java.util.*;

public interface Parser
{
	public String getDir();
	public String getName();
	public OptionContainer getOptions();
	public void parse(File file);
}
