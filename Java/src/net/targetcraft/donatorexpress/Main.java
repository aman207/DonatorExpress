package net.targetcraft.donatorexpress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin implements Listener {
	Connection con;
	private static HashMap<String, String> captions;
	
	public void onEnable()
	{
		File forumConfig = new File(getDataFolder(), "forumConfig.yml");		
		List<String> ranks = new ArrayList<String>();
		File file=new File(getDataFolder()+File.separator+"config.yml");
		File packagesFolder = new File(getDataFolder()+"/packages"+File.separator);
		File existingRanks=new File(this.getDataFolder()+File.separator,"packages.yml");
		
		if (!file.exists())
		{
			getDataFolder().mkdirs();
			
			getLogger().info("Configuration not found. Generating...");
			this.saveDefaultConfig();
		}
		if(!packagesFolder.exists())
		{
			packagesFolder.mkdirs();
		}
		if(!existingRanks.exists())
		{
			try {
				existingRanks.createNewFile();
			} catch (IOException e) {

				e.printStackTrace();
			}
			
		}
		if(!forumConfig.exists())
		{
			getLogger().info("Forum Configuration not found. Generating...");
			
			try {
				getDataFolder().mkdirs();
				ranks=this.getConfig().getStringList("ranks");
				
				if(!existingRanks.exists())
				{
					existingRanks.createNewFile();
					FileConfiguration existingRanksConfig = null;
					existingRanksConfig=new YamlConfiguration();
					existingRanksConfig.load(existingRanks);
					existingRanksConfig.createSection("packages");
					existingRanksConfig.set("packages", ranks);
					existingRanksConfig.save(existingRanks);
				}
			} catch (IOException | InvalidConfigurationException e1) {
				e1.printStackTrace();
			}
			
			OutputStream out = null;
			InputStream defaultStream = this.getResource("forumConfig.yml");
            try {
            	out = new FileOutputStream(forumConfig);
                int read = 0;
                byte[] bytes = new byte[1024];

				while((read = defaultStream.read(bytes)) != -1) {
				    out.write(bytes, 0, read);
				}
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
			for(String s:ranks)
			{
				setForumConfig(s);
			}
		}
		getCommand("donate").setExecutor(new CommandListener(this));
		Bukkit.getPluginManager().registerEvents(this, this);
		
		getServer().dispatchCommand(getServer().getConsoleSender(), "donate dbconnect");
		//getServer().dispatchCommand(getServer().getConsoleSender(), "donate update");
		
		if(this.getConfig().getString("metrics").equals("true"))
		{
			try {
				Metrics metrics = new Metrics(this);
				metrics.start();
				Logger.getLogger("").log(Level.INFO, "Metrics Enabled for DonatorExpress");
		} catch (IOException e) {
		    // Failed to submit the stats :-(
		}
			
		}
		else
		{
			
		}
		
	}
	public void onDisable()
	{
		getServer().dispatchCommand(getServer().getConsoleSender(), "donate dbcloseNEVERRUNTHIS");
	}
	
	public void initializePhrase()
	{
		if(this.getConfig().getString("language").equals("en"))
		{
			OutputStream out = null;
			File en=new File(this.getDataFolder()+"/languages"+File.separator, "en.yml");
			en.mkdirs();
			InputStream defaultStream = this.getResource("en.yml");
            try {
            	out = new FileOutputStream(en);
                int read = 0;
                byte[] bytes = new byte[1024];

				while((read = defaultStream.read(bytes)) != -1) {
				    out.write(bytes, 0, read);
				}
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(this.getConfig().getString("language").equals("fr"))
		{
			OutputStream out = null;
			File fr=new File(this.getDataFolder()+"/languages"+File.separator, "fr.yml");
			fr.mkdirs();
			InputStream defaultStream = this.getResource("fr.yml");
            try {
            	out = new FileOutputStream(fr);
                int read = 0;
                byte[] bytes = new byte[1024];

				while((read = defaultStream.read(bytes)) != -1) {
				    out.write(bytes, 0, read);
				}
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static String getPhrase(String message)
	{
		if(captions.containsKey(message)) 
		{
			return colourize(captions.get(message));
		}
		else
		{
			return "Error. Missing translation for: "+message;
		}
		
	}
	
	public static String colourize(String message) 
	{
		return message.replaceAll("&([l-o0-9a-f])", "\u00A7$1");
	}
	public void setForumConfig(String s)
	{
		// This is only used for upgrading from 0.6 > 1.5
		File packages = new File(this.getDataFolder()+"/packages"+File.separator, s+".yml");		
		YamlConfiguration forum = null;
		forum=new YamlConfiguration();

			List<String>commands=this.getConfig().getStringList(s+"-commands");		
			
			String price = this.getConfig().getString(s);
			int priceInt=Integer.parseInt(price);
			
			OutputStream out = null;
			InputStream defaultStream = this.getResource("[defaultPackageConfiguration].yml");
			File defaultPackage = new File(this.getDataFolder()+"/packages"+File.separator, "[defaultPackageConfiguration].yml");
			File defaultPackageFolder = new File(this.getDataFolder()+"/packages"+File.separator);
			if(!defaultPackageFolder.exists())
			{
				defaultPackageFolder.mkdir();
			}

	         try {
	            	out = new FileOutputStream(defaultPackage);
	                int read = 0;
	                byte[] bytes = new byte[1024];

					while((read = defaultStream.read(bytes)) != -1) {
					    out.write(bytes, 0, read);
					}
				    out.close();
					if(defaultPackage.renameTo(packages))
					{
						
					}
					else
					{
						Logger.getLogger("").severe("[DonatorExpress] MAJOR ERROR. COULD NOT UPGRADE TO NEW CONFIG SYSTEM");
						Logger.getLogger("").severe("[DonatorExpress] MAJOR ERROR. COULD NOT UPGRADE TO NEW CONFIG SYSTEM");
						Logger.getLogger("").severe("[DonatorExpress] MAJOR ERROR. COULD NOT UPGRADE TO NEW CONFIG SYSTEM");
						Logger.getLogger("").severe("[DonatorExpress] MAJOR ERROR. COULD NOT UPGRADE TO NEW CONFIG SYSTEM");
						Logger.getLogger("").severe("[DonatorExpress] NOTIFY DEVELOPER ABOUT THIS RIGHT AWAY");
						Logger.getLogger("").severe("[DonatorExpress] ERROR OCCURED WHEN TRYING TO ADD "+s);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (NullPointerException e1)
				{
					e1.printStackTrace();
				} catch (SecurityException e1)
				{
					e1.printStackTrace();
				}
	         
	         OutputStream out2 = null;
	         InputStream in2 = this.getResource("#####READ_ME_FIRST#####.yml");
	         File readmefirst = new File(this.getDataFolder()+File.separator, "#####READ_ME_FIRST#####.yml");
	         
	         try {
				out2 = new FileOutputStream(readmefirst);				
	             int read = 0;
	             byte[] bytes = new byte[1024];

					while((read = in2.read(bytes)) != -1) {
					    out2.write(bytes, 0, read);
					}					
				    out2.close();
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
				try {
					
					forum.load(packages);
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InvalidConfigurationException e1) {
					e1.printStackTrace();
				}
			forum.set("price", priceInt);
			forum.set("commands", commands);			
			try {
				forum.save(packages);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	
	@SuppressWarnings("unused")
	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent e)
	{
		BukkitTask task = new Expire(e, this).runTaskLater(this, 20);
		if(e.getPlayer().hasPermission("donexpress.admin.update"))
		{
			BukkitTask update = new Update(e, this).runTaskLater(this, 20);
		}
	}
}
