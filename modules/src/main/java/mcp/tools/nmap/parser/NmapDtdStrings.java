package mcp.tools.nmap.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class NmapDtdStrings
{
	public final static String NMAPRUN_TAG = "nmaprun";

	public final static String SCANNER_ATTR = "scanner";
	public final static String ARGS_ATTR = "args";
	public final static String START_ATTR = "start";
	public final static String STARTSTR_ATTR = "startstr";
	public final static String VERSION_ATTR = "version";
	public final static String XML_OUTPUT_VERSION_ATTR = "xmloutputversion";
	public final static String SCANINFO_TAG = "scaninfo";

	public final static String TYPE_ATTR = "type";
	public final static String PROTOCOL_ATTR = "protocol";
	public final static String NUM_SERVICES_ATTR = "numservices";
	public final static String SERVICES_ATTR = "services";
	public static final String DEBUGGING_TAG = "debugging";
	public static final String LEVEL_TAG = "level";
	public final static String VERBOSE_TAG = "verbose";
	public final static String LEVEL_ATTR = "level";
	public final static String HOST_TAG = "host";
	public final static String STARTTIME_ATTR = "starttime";
	public final static String ENDTIME_ATTR = "endtime";
	public final static String STATUS_TAG = "status";
	public final static String STATE_ATTR = "state";
	public final static String REASON_ATTR = "reason";
	public final static String ADDRESS_TAG = "address";

	public final static String ADDR_ATTR = "addr";
	public final static String ADDRTYPE_ATTR = "addrtype";
	public final static String HOSTNAMES_TAG = "hostnames";
	public final static String HOSTNAME_TAG = "hostname";
	public final static String NAME_ATTR = "name";
	public final static String PORTS_TAG = "ports";
	public final static String PORT_TAG = "port";

	public final static String STATE_TAG = "state";

	public final static String REASON_TTL_ATTR = "reason_ttl";
	public final static String SERVICE_TAG = "service";

	public final static String PRODUCT_ATTR = "product";
	public final static String METHOD_ATTR = "method";
	public final static String CONF_ATTR = "conf";
	public final static String OS_TYPE_ATTR = "ostype";
	public final static String EXTRA_INFO_ATTR = "extrainfo";
	public final static String OS_TAG = "os";
	public final static String PORT_USED_TAG = "portused";

	public final static String PROTO_ATTR = "proto";
	public final static String PORTID_ATTR = "portid";
	public final static String OSCLASS_TAG = "osclass";

	public final static String VENDOR_ATTR = "vendor";
	public final static String OSFAMILY_ATTR = "osfamily";
	public final static String OSGEN_ATTR = "osgen";
	public final static String ACCURACY_ATTR = "accuracy";
	public final static String OS_MATCH_TAG = "osmatch";

	public final static String LINE_ATTR = "line";
	public final static String DISTANCE_TAG = "distance";

	public final static String VALUE_ATTR = "value";
	public final static String TCP_SEQUENCE_TAG = "tcpsequence";

	public final static String INDEX_ATTR = "index";
	public final static String DIFFICULTY_ATTR = "difficulty";
	public final static String VALUES_ATTR = "values";
	public final static String TCP_TS_SEQUENCE_TAG = "tcptssequence";

	public final static String CLASS_ATTR = "class";
	public final static String TIMES_TAG = "times";

	public final static String SRTT_ATTR = "srtt";
	public final static String RTTVAR_ATTR = "rttvar";
	public final static String TO_ATTR = "to";
	public final static String UPTIME_TAG = "uptime";

	public final static String SECONDS_ATTR = "seconds";
	public final static String LASTBOOT_ATTR = "lastboot";
	public final static String RUNSTATS_TAG = "runstats";
	public final static String FINISHED_TAG = "finished";

	public final static String TIME_ATTR = "time";
	public final static String TIMESTR_ATTR = "timestr";
	public final static String ELAPSED_ATTR = "elapsed";
	public final static String HOSTS_TAG = "hosts";

	public final static String UP_ATTR = "up";
	public final static String DOWN_ATTR = "down";
	public final static String TOTAL_ATTR = "total";
	public final static String CPE_ATTR = "cpe";



	private NmapDtdStrings()
	{
	}
}
