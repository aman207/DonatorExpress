package net.aman207.donatorexpress.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.json.JSONObject;

import net.aman207.donatorexpress.DonatorExpress;

public class Support implements Runnable {
	
	//At some point in the near future...............................................................
	
	HashMap<String, String> stringValues = new HashMap<String, String>();
	HashMap<String, String> configValues = new HashMap<String, String>();
	HashMap<String, String> forumValues = new HashMap<String, String>();
	HashMap<String, String> ticketDetails = new HashMap<String, String>();
	static HashMap<String, String> messageDetails= new HashMap<String, String>();
	
	static DonatorExpress plugin;
	
	public Support(DonatorExpress config)
	{
		plugin=config;
	}
	
	@Override
	public void run() {
		getSysInfo();
		
	}
	
	public static void putDetails(String details)
	{
		int count = 0;
		for (String key:messageDetails.keySet()) {
			count++;
		}
		messageDetails.put("message"+count, details);
	}
	
	public static void removeDetails(int lineNumber)
	{
		messageDetails.remove("message"+lineNumber);
	}
	
	private void detailsToTxt() throws IOException
	{
		File tmpTxt = new File (plugin.getDataFolder()+File.separator+"logs"+File.separator+"tmpDetails.txt");
		tmpTxt.createNewFile();
		
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpTxt, true), "UTF-8"));
		
		for (String key:messageDetails.keySet()) {
			writer.append(key);
			writer.newLine();
		}
		writer.flush();
		writer.close();
	}
	
	public void getSysInfo()
	{
		//Bukkit, DE, and java versions
		stringValues.put("bukkitVer", Bukkit.getBukkitVersion());
		stringValues.put("deVer", Bukkit.getVersion());
		stringValues.put("javaVer", System.getProperty("java.version"));
		
		//Copy config files to hashmap
		
		File configYamlFile = new File(plugin.getDataFolder()+"config.yml");
		FileConfiguration configYaml=null;
		configYaml=new YamlConfiguration();
		
		//File economyYamlFile = new File(plugin.getDataFolder()+"economy.yml");
		//FileConfiguration economyYaml=null;
		//economyYaml=new YamlConfiguration();
		
		File forumYamlFile = new File(plugin.getDataFolder()+"forumConfig.yml");
		FileConfiguration forumYaml=null;
		forumYaml=new YamlConfiguration();
		
		try {
			configYaml.load(configYamlFile);
			//economyYaml.load(economyYamlFile);
			forumYaml.load(forumYamlFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		configValues.put("metrics", configYaml.getString("metrics"));
		configValues.put("update-check", configYaml.getString("update-check"));
		configValues.put("disable-on-database-error", configYaml.getString("disable-on-database-error"));
		configValues.put("language", configYaml.getString("language"));
		configValues.put("portal-location", configYaml.getString("portal-location"));
		configValues.put("log-startup", configYaml.getString("log-startup"));
		configValues.put("log-errors", configYaml.getString("log-errors"));
		configValues.put("log-user-actions", configYaml.getString("log-user-actions"));
		configValues.put("log-admin-commands", configYaml.getString("log-admin-commands"));
		configValues.put("version", configYaml.getString("version"));
		
		//economyYamlValues[0]=economyYaml.getString("");
		
		//if forum problem, do this
		forumValues.put("enabled", forumYaml.getString("enabled"));
		forumValues.put("mybb", forumYaml.getString("mybb"));
		forumValues.put("xenforo", forumYaml.getString("xenforo"));
		forumValues.put("ipboard", forumYaml.getString("ipboard"));
		forumValues.put("simplemachines", forumYaml.getString("simplemachines"));
		forumValues.put("vbulletin", forumYaml.getString("vbulletin"));
		forumValues.put("username-mode", forumYaml.getString("username-mode"));
		forumValues.put("email-mode", forumYaml.getString("email-mode"));
		forumValues.put("version", forumYaml.getString("version"));
	}
	
	public String sendLog()
	{
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
        HttpPost post = new HttpPost("https://aman207.net/scripts/sendLog.php");
        
		String content = null;
		try {
			content = new Scanner(new File(plugin.getDataFolder()+File.separator+"logs"+File.separator+"error.log")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("text", content));
        params.add(new BasicNameValuePair("title", "Error report for "+ticketDetails.get("name")));
        params.add(new BasicNameValuePair("name", ticketDetails.get("name")));
        params.add(new BasicNameValuePair("lang", "text"));
        
        Scanner in = null;
        String check = null;
        
        try
        {
            post.setEntity(new UrlEncodedFormEntity(params));
            response = client.execute(post);
            HttpEntity entity = response.getEntity();
            in = new Scanner(entity.getContent());
            
            if(in.hasNext())
            {
                check = in.next();
            }
            else
            {
            	check = "Error. Nothing was returned";
            }
            if(check.contains(("Error")))
            {
            	check = "Error";
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
			e.printStackTrace();
		} finally
        {
            in.close();
            try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return check;
	}
	
	public void sendTicket()
	{
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
        HttpPost post = new HttpPost("https://aman207.net/scripts/sendTicket.php");
        JSONObject ticketSend = new JSONObject();
        
        try {
			ticketSend.put("source", "API");
			ticketSend.put("name", ticketDetails.get("name"));
			ticketSend.put("email", ticketDetails.get("emails"));
			ticketSend.put("subject", ticketDetails.get("subject"));
			ticketSend.put("ip", "192.99.43.43"); 
			//aman207's VPS IP address, used so that osticket
			//doesn't yell at me
			//No other IP information is collected
			
			String content = "<b>Begin support ticket</b><br><br>"
					//Begin System Information
					+ "<b>System Information:</br><br>"
					+ "Bukkit Version: "+stringValues.get("bukkitVer")+"<br>"
					+ "Java Version: "+stringValues.get("javaVer")+"<br>"
					+ "DonatorExpress Version: "+stringValues.get("deVer")+"<br><br>"
					//Begin config values
					+ "<b>config.yml Values</b><br><br>"
					+ "Metrics: "+configValues.get("metrics")+"<br>"
					+ "Update check: "+configValues.get("update-check")+"<br>"
					+ "Disable on Database Error"+configValues.get("disable-on-database-error")+"<br>"
					+ "Language: "+configValues.get("language")+"<br>"
					+ "Portal Location: "+configValues.get("portal-location")+"<br>"
					+ "Log Startup: "+configValues.get("log-startup")+"<br>"
					+ "Log Errors: "+configValues.get("log-errors")+"<br>"
					+ "Log User Actions: "+configValues.get("log-user-actions")+"<br>"
					+ "Log Admin Commands: "+configValues.get("log-admin-commands")+"<br>"
					+ "Version: "+configValues.get("version")+"<br><br>";
			
			int count = 0;
			for (String key:ticketDetails.keySet()) {
				count++;
			}
			
			ticketSend.put("message", "data:text/html,"+content);
			
			List<NameValuePair> params = new ArrayList<>();
	        params.add(new BasicNameValuePair("ticketDetails", ticketSend.toString()));
	        
	        Scanner in = null;
	        try
	        {
	            post.setEntity(new UrlEncodedFormEntity(params));
	            response = client.execute(post);
	            HttpEntity entity = response.getEntity();
	            in = new Scanner(entity.getContent());
	            while (in.hasNext())
	            {
	                System.out.println(in.next());
	            }
	            EntityUtils.consume(entity);
	        } finally
	        {
	            in.close();
	            response.close();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        finally{
        	try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	
	public String returnTicketNumber()
	{
		//Broken 
		//TODO
		return null;
	}

}
