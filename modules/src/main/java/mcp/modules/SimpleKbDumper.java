package mcp.modules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.knowledgebase.KnowledgeBase;
import space.dcce.commons.cli.OptionContainer;


public class SimpleKbDumper extends Module
{
	final static Logger logger = LoggerFactory.getLogger(SimpleKbDumper.class);
	
	public SimpleKbDumper()
	{
		super("Simple KB dumper");
	}

	@Override
	public void initialize()
	{
	}


	public String getKBDump()
	{
//		KnowledgeBase kb = KnowledgeBase.instance;
		StringBuilder sb = new StringBuilder();
//		sb.append("Simple KB dumper output:\n");
//		sb.append("Scope:\n");
//		sb.append(StringUtils.indentText(1, true, Scope.instance.toString()));
//
//
//		sb.append("\n\nKB classes:\n");
//		sb.append(StringUtils.indentText(1, true, CollectionUtils.joinObjects(", ", kb.getClasses())));
//		
//		ToStringBuilder tsb = new ToStringBuilder(this, ToStringStyle.JSON_STYLE);
//
//		sb.append("\n\nIP Addresses:\n");
//		List<IPAddress> addresses = kb.getIPAddressNodes().getAllMembers();
//		IPAddress[] a = addresses.toArray(new IPAddress[0]);
//		tsb.append("addresses", a);
////		for (IPAddress address: a)
////		{
////			tsb.append(address);
////		}
////		sb.append(StringUtils.indentText(1, true, CollectionUtils.joinObjects(", \n", kb.getIPAddressNodes())));
//		sb.append(StringUtils.indentText(1, true, tsb.toString()));
//		
//		sb.append("\n\nHosts:\n");
//		sb.append(StringUtils.indentText(1, true, CollectionUtils.joinObjects(", ", kb.getHosts())));
//		
//		sb.append("\n\nHostnames:\n");
//		sb.append(StringUtils.indentText(1, true, CollectionUtils.joinObjects(", ", kb.getHostnames())));
//		
//		sb.append("\n\nDomains:\n");
//		sb.append(StringUtils.indentText(1, true, CollectionUtils.joinObjects(", ", kb.getHostnames())));
//		
//		sb.append("\n\nMAC addresses:\n");
//		sb.append(StringUtils.indentText(1, true, CollectionUtils.joinObjects(", ", kb.getMacAddressNodes())));
//		
		return sb.toString();
	}
	
	public static OptionContainer getOptions()
	{
		return null;
	}
}
