package burp;

import com.ibm.xfr.burpExtractor.ExtractorPanel;
import com.ibm.xfr.burpExtractor.ExtractorScanner;


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
