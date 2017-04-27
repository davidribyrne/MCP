package mcp.modules.nmap;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.modules.Module;
import net.dacce.commons.cli.Option;
import net.dacce.commons.cli.OptionGroup;
import net.dacce.commons.general.BooleanFormatException;
import net.dacce.commons.general.BooleanUtils;
import net.dacce.commons.general.FileUtils;
import net.dacce.commons.validators.BooleanValidator;
import net.dacce.commons.validators.NumericValidator;
import net.dacce.commons.validators.PathState;
import net.dacce.commons.validators.PathValidator;
import net.dacce.commons.validators.Requirement;


public class NmapGeneralOptions extends Module
{
	final static Logger logger = LoggerFactory.getLogger(NmapGeneralOptions.class);
	private static final NmapGeneralOptions instance = new NmapGeneralOptions();

	private OptionGroup group;
	private Option nmapPath;
	private Option nmapSpeed;
	private Option nmapResume;

	
	private String nmapPathValue;
	private byte speed;
	private boolean resume;


	private NmapGeneralOptions()
	{
		super("Nmap general options");
		nmapPath = new Option(null, "nmapPath", "Path to nmap binary. Defaults to environment path.", true, true, null, "filepath");
		nmapSpeed = new Option(null, "nmapSpeed", "Nmap speed.", true, true, "4", "0-5");
		nmapResume = new Option(null, "nmapResume", "Attempt to resume previously aborted nmap scans.", true, true, "true", "true/false");

		nmapSpeed.addValidator(new NumericValidator(false, 0, 5));
		nmapResume.addValidator(new BooleanValidator(false));
		PathState nmapPathState = new PathState();
		nmapPathState.directory = Requirement.MUST_NOT;
		nmapPathState.executable = Requirement.MUST;
		nmapPathState.exists = Requirement.MUST;
		nmapPath.addValidator(new PathValidator(nmapPathState));

		group = new OptionGroup("nmap", "Nmap options");
		group.addChild(nmapPath);
		group.addChild(nmapSpeed);
		group.addChild(nmapResume);

	}



	@Override
	public void initialize()
	{

		nmapPathValue = nmapPath.getValue();
		File nmapexecutable;
		if (nmapPathValue == null)
		{
			String name = System.getProperty("os.name").toLowerCase().contains("windows") ? "nmap.exe" : "nmap";
			nmapPathValue = FileUtils.which(name);
			if (nmapPathValue == null)
			{
				logger.warn("Nmap executable not found in system path. This will cause an error if an nmap scan is attempted.");
				return;
			}
			nmapexecutable = new File(nmapPathValue);
		}
		else
		{
			nmapexecutable = new File(nmapPathValue);
		}

		if (!nmapexecutable.canExecute())
		{
			logger.warn("Nmap executable found at " + nmapPathValue
					+ ", but it isn't executable. This will cause an error if an nmap scan is attempted.");
		}


		try
		{
			resume = BooleanUtils.friendlyParse(nmapResume.getValue());
		}
		catch (BooleanFormatException e)
		{
			IllegalArgumentException e2 = new IllegalArgumentException("Invalid nmapResume argument, which should have been caught earlier: " + e.getLocalizedMessage(), e);
			logger.error(e2.getLocalizedMessage(), e2);
			throw e2;
		}

		boolean passed = false;
		try
		{
			speed = Byte.parseByte(nmapSpeed.getValue());
			if ((speed >= 0) && (speed <= 5))
			{
				passed = true;
			}
		}
		catch (NumberFormatException e)
		{
		}
		if (!passed)
		{
			throw new IllegalArgumentException("Nmap speed is a value from 0-5");
		}
	}


	public byte getSpeed()
	{
		return speed;
	}


	public String getNmapPath()
	{
		return nmapPathValue;
	}


	public static NmapGeneralOptions getInstance()
	{
		return instance;
	}


	public boolean isResume()
	{
		return resume;
	}

	@Override
	public OptionGroup getOptions()
	{
		return group;
	}
}
