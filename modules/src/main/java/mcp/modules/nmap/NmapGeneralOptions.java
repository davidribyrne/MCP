package mcp.modules.nmap;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcp.modules.Module;
import mcp.options.MCPOptions;
import space.dcce.commons.cli.Option;
import space.dcce.commons.cli.OptionGroup;
import space.dcce.commons.general.BooleanFormatException;
import space.dcce.commons.general.BooleanUtils;
import space.dcce.commons.general.FileUtils;
import space.dcce.commons.general.OSUtils;
import space.dcce.commons.validators.BooleanValidator;
import space.dcce.commons.validators.NumericValidator;
import space.dcce.commons.validators.PathState;
import space.dcce.commons.validators.PathValidator;
import space.dcce.commons.validators.Requirement;


public class NmapGeneralOptions extends Module
{
	final static Logger logger = LoggerFactory.getLogger(NmapGeneralOptions.class);

	private OptionGroup group;
	private Option nmapPath;
	private Option nmapSpeed;
	private Option nmapResume;

	
	private String nmapPathValue;
	private byte speed;
	private boolean resume;

	@Override
	protected void initializeOptions()
	{
		group = MCPOptions.instance.addOptionGroup("Nmap global options", "");

		nmapPath = group.addOption(null, "nmapPath", "Path to nmap binary. Defaults to environment path.", true, true, null, "filepath");
		nmapSpeed = group.addOption(null, "nmapSpeed", "Nmap speed.", true, true, "4", "0-5");
		nmapResume = group.addOption(null, "nmapResume", "Attempt to resume previously aborted nmap scans.", true, true, "true", "true/false");

		nmapSpeed.addValidator(new NumericValidator(false, 0, 5));
		nmapResume.addValidator(new BooleanValidator(false));
		PathState nmapPathState = new PathState();
		nmapPathState.directory = Requirement.MUST_NOT;
		nmapPathState.executable = Requirement.MUST;
		nmapPathState.exists = Requirement.MUST;
		nmapPath.addValidator(new PathValidator(nmapPathState));


	}

	private NmapGeneralOptions()
	{
		super("Nmap general options");

	}



	@Override
	public void initialize()
	{

		nmapPathValue = nmapPath.getValue();
		File nmapexecutable;
		if (nmapPathValue == null)
		{
			String name = OSUtils.isWindows() ? "nmap.exe" : "nmap";
			nmapPathValue = FileUtils.which(name);
			if (nmapPathValue == null)
			{
				logger.warn("Nmap executable not found in system path (\"" + System.getenv("PATH") + "\"). "
						+ "This will cause an error if an nmap scan is attempted.");
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
		catch (@SuppressWarnings("unused") NumberFormatException e)
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


	public boolean isResume()
	{
		return resume;
	}

	public OptionGroup getOptions()
	{
		return group;
	}
}
