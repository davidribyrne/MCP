package burp;

import java.util.*;

import com.ibm.xfr.burpExtractor.ExtractorScanner;
import com.ibm.xfr.burpExtractor.ExtractorPanel;


public class BurpExtender implements IBurpExtender
{

	private IBurpExtenderCallbacks callbacks;
	private ExtractorScanner es;
	private ExtractorPanel ui;
	
	public BurpExtender()
	{
	}

	@Override
	public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks)
	{
		es = new ExtractorScanner(callbacks);
		ui = new ExtractorPanel(es);
		this.callbacks = callbacks;
		callbacks.setExtensionName("Extractor");
		callbacks.registerScannerCheck(es);
		callbacks.addSuiteTab(ui);
	}

	public IBurpExtenderCallbacks getCallbacks()
	{
		return callbacks;
	}
}
