package net.aman207.donatorexpress.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.aman207.donatorexpress.DonatorExpress;

public class Support {
	
	HashMap<String, String> stringValues = new HashMap<String, String>();
	HashMap<String, String> configValues = new HashMap<String, String>();
	HashMap<String, String> forumValues = new HashMap<String, String>();
	
	static DonatorExpress plugin;
	
	public Support(DonatorExpress config)
	{
		plugin=config;
	}
	
	public void getSysInfo()
	{
		//Bukkit, plugin and java versions
		stringValues.put("bukkitVer", Bukkit.getBukkitVersion());
		stringValues.put("deVer", Bukkit.getVersion());
		stringValues.put("javaVer", System.getProperty("java.version"));
		
		//error.log
		String content = null;
		try {
			content = new Scanner(new File(plugin.getDataFolder()+File.separator+"logs/error.log")).useDelimiter("\\Z").next();
			stringValues.put("errorLog", content);
		} catch (FileNotFoundException e) {
			//notify that error.log couldn't be found
			//continue without error.log
			e.printStackTrace();
		}
		
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
	
	public void getMessage()
	{
		
	}
	
	public void sendTicket()
	{
		
	}
	
	public String returnTicketNumber()
	{
		return null;
	}

}
