package net.aman207.donatorexpress.tools;

import net.aman207.donatorexpress.DonatorExpress;

import org.piwik.PiwikException;
import org.piwik.SimplePiwikTracker;

public class PiwikIt {
	
	static DonatorExpress plugin;
	
	public PiwikIt(DonatorExpress config)
	{
		plugin = config;
	}

	public void uniqueStartup()
	{
		try {
			SimplePiwikTracker tracker = new SimplePiwikTracker("http://aman207.net/piwik");
			
			tracker.setIdSite(2);
			tracker.setPageUrl("http://aman207.net/donatorexpress/uniquestartup");
			tracker.setPageCustomVariable("uniquestartup", "1");
			//tracker.setVisitorId("aea245d3-8560-449b-85b7-c372401e4911");
			tracker.sendRequest(tracker.getLinkTrackURL("http://aman207.net/donatorexpress/uniquestartup"));
		} catch (PiwikException e) {
			e.printStackTrace();
		}
	}
	
	public void startup()
	{
		try {
			SimplePiwikTracker tracker = new SimplePiwikTracker("http://aman207.net/piwik");
			
			tracker.setIdSite(2);
			tracker.setPageUrl("http://aman207.net/donatorexpress/startup");
			tracker.setPageCustomVariable("startup", "1");
			//tracker.setVisitorId("aea245d3-8560-449b-85b7-c372401e4911");
			tracker.sendRequest(tracker.getLinkTrackURL("http://aman207.net/donatorexpress/startup"));
		} catch (PiwikException e) {
			e.printStackTrace();
		}
	}
}
