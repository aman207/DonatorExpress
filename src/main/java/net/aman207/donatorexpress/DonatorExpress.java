package net.aman207.donatorexpress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;
import net.aman207.donatorexpress.tools.*;
import net.aman207.donatorexpress.events.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class DonatorExpress extends JavaPlugin implements Listener {
	Connection con;
	
	public static boolean update = false;
	public static String name = "";
	public static Economy econ = null;
	
	public void onEnable()
	{
		initializeLanguageFile();
		Language lang = new Language(this);
		lang.languageInitialize();
		
		//Used to initialize constructor
		@SuppressWarnings("unused")
		LogIt inilog = new LogIt(this);
		//TODO Piwik
		//@SuppressWarnings("unused")
		//PiwikIt inipiwik = new PiwikIt(this);
		
		File forumConfig = new File(getDataFolder(), "forumConfig.yml");
		File inventoryConfig = new File(getDataFolder(), "inventoryConfig.yml");
		//File economyConfigFile = new File (getDataFolder(), "economy.yml"); mmmm not quite yet
		File configFile=new File(getDataFolder()+File.separator+"config.yml");
		File packagesFolder = new File(getDataFolder()+"/packages"+File.separator);
		File existingRanks=new File(this.getDataFolder()+File.separator,"packages.yml");
		
		if (!configFile.exists())
		{
			getDataFolder().mkdirs();
			
			getLogger().info("Deafult configuration not found. Generating...");
			LogIt.startup("Deafult configuration not found. Generating...");
			this.saveDefaultConfig();
		}
		if(!inventoryConfig.exists())
		{
			getDataFolder().mkdirs();
			
			getLogger().info("Inventory configuration not found. Generating....");
			LogIt.startup("Inventory configuration not found. Generating....");
			
			OutputStream out = null;
			InputStream defaultStream = this.getResource("configs/inventoryConfig.yml");
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
				LogIt.startup("IOerr2.1");
				LogIt.startup("https://aman207.net/wiki/Errors");
				LogIt.startup(e1.getMessage());
				
				LogIt.error("IOerr2.1");
				LogIt.error("https://aman207.net/wiki/Errors");
				LogIt.error("Stacktrace:");
				LogIt.error(" ");
				LogIt.error(LogIt.exceptionLog(e1));
			}
		}
		if(!packagesFolder.exists())
		{
			LogIt.startup("Packages folder not found. Making directory...");
			packagesFolder.mkdirs();
		}
		if(!existingRanks.exists())
		{
			try {
				LogIt.startup("packages.yml not found. Generating...");
				existingRanks.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				LogIt.startup("IOerr1");
				LogIt.startup("https://aman207.net/wiki/Errors");
				LogIt.startup(e.getMessage());
				
				LogIt.error("IOerr1");
				LogIt.error("https://aman207.net/wiki/Errors");
				LogIt.error("Stacktrace:");
				LogIt.error(" ");
				LogIt.error(LogIt.exceptionLog(e));
			}	
		}
		if(!forumConfig.exists())
		{
			getLogger().info("Forum Configuration not found. Generating...");
			LogIt.startup("Forum Configuration not found. Generating...");
			
			//Deprecated as of 1.9
			/**try {
				getDataFolder().mkdirs();
				ranks=this.getConfig().getStringList("ranks");
				
				if(existingRanks.exists())
				{
					FileConfiguration existingRanksConfig = null;
					existingRanksConfig=new YamlConfiguration();
					existingRanksConfig.load(existingRanks);
					existingRanksConfig.createSection("packages");
					existingRanksConfig.set("packages", ranks);
					existingRanksConfig.save(existingRanks);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				
				LogIt.startup("IOerr2");
				LogIt.startup("Stacktrace:");
				LogIt.startup(" ");
				LogIt.startup(LogIt.exceptionLog(e1));
				
				LogIt.error("IOerr2");
				LogIt.error("Stacktrace:");
				LogIt.error(" ");
				LogIt.error(LogIt.exceptionLog(e1));
				
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}*/
			
			OutputStream out = null;
			InputStream defaultStream = this.getResource("configs/forumConfig.yml");
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
				LogIt.startup("IOerr2");
				LogIt.startup("https://aman207.net/wiki/Errors");
				LogIt.startup(e1.getMessage());
				
				LogIt.error("IOerr2");
				LogIt.error("https://aman207.net/wiki/Errors");
				LogIt.error("Stacktrace:");
				LogIt.error(" ");
				LogIt.error(LogIt.exceptionLog(e1));
			}
			
			//Deprecated as of 1.9
			/**for(String s:ranks)
			{
				setForumConfig(s);
			}*/
		}
		
		//TODO
		//Not ready quite yet....
		/**
		if(!economyConfigFile.exists())
		{
			getLogger().info("Economy Configuration not found. Generating...");
			LogIt.startup("Economy Configuration not found. Generating...");
			
			OutputStream out = null;
			InputStream defaultStream = this.getResource("configs/economy.yml");
            try {
            	out = new FileOutputStream(economyConfigFile);
                int read = 0;
                byte[] bytes = new byte[1024];

				while((read = defaultStream.read(bytes)) != -1) {
				    out.write(bytes, 0, read);
				}
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				LogIt.startup("IOerr3");
				LogIt.startup("https://aman207.net/wiki/Errors");
				LogIt.startup(e1.getMessage());
				
				LogIt.error("IOerr3");
				LogIt.error("https://aman207.net/wiki/Errors");
				LogIt.error("Stacktrace:");
				LogIt.error(" ");
				LogIt.error(LogIt.exceptionLog(e1));
			}
		}*/
		getCommand("donate").setExecutor(new CommandListener(this));
		
		//Used to initialize the constructor
		@SuppressWarnings("unused")
		Database testConnect = new Database(this);
		
		Bukkit.getPluginManager().registerEvents(this, this);
		if(setupEconomy())
		{
			LogIt.startup("Vault found. Enabling sign feature");
			Bukkit.getPluginManager().registerEvents(new SignEvent(this), this);
		}
		else
		{
			LogIt.startup("Vault not found, disabling sign feature");
			Logger.getLogger("").warning("[DonatorExpress] Vault not found, disabling Sign feature");
		}
		
		Bukkit.getPluginManager().registerEvents(new CommandListener(this), this);
		
		if(this.getConfig().getString("metrics").equals("true"))
		{
			try {
				Metrics metrics = new Metrics(this);
				metrics.start();
				Logger.getLogger("").log(Level.INFO, "Metrics Enabled for DonatorExpress");
				LogIt.startup("Metrics enabled for DonatorExpress");
		} catch (IOException e) {
		    // Failed to submit the stats :-(
		}
			
		}
		
	}
	public void onDisable()
	{
		//Nothing really to do
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	public void initializeLanguageFile()
	{
		File languageFolder = new File(this.getDataFolder()+"/languages");
		if(!languageFolder.exists())
		{
			languageFolder.mkdirs();
		}
		
		if(this.getConfig().getString("language").equals("en"))
		{
			OutputStream out = null;
			File en=new File(this.getDataFolder()+"/languages"+File.separator, "en.yml");
			if(!en.exists())
			{
				InputStream defaultStream = this.getResource("languages/en.yml");
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
					LogIt.error("IOerr4");
					LogIt.error("https://aman207.net/wiki/Errors");
					LogIt.error("Stacktrace:");
					LogIt.error(" ");
					LogIt.error(LogIt.exceptionLog(e1));
				}
			}
		}
		else if(this.getConfig().getString("language").equals("fr"))
		{
			OutputStream out = null;
			File fr=new File(this.getDataFolder()+"/languages"+File.separator, "fr.yml");
			if(!fr.exists())
			{
				InputStream defaultStream = this.getResource("languages/fr.yml");
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
					LogIt.error("IOerr5");
					LogIt.error("https://aman207.net/wiki/Errors");
					LogIt.error("Stacktrace:");
					LogIt.error(" ");
					LogIt.error(LogIt.exceptionLog(e1));
				}
			}
		}
		else if(this.getConfig().getString("language").equals("es"))
		{
			OutputStream out = null;
			File es=new File(this.getDataFolder()+"/languages"+File.separator, "es.yml");
			if(!es.exists())
			{
				InputStream defaultStream = this.getResource("languages/es.yml");
	            try {
	            	out = new FileOutputStream(es);
	                int read = 0;
	                byte[] bytes = new byte[1024];

					while((read = defaultStream.read(bytes)) != -1) {
					    out.write(bytes, 0, read);
					}
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					LogIt.error("IOerr6");
					LogIt.error("https://aman207.net/wiki/Errors");
					LogIt.error("Stacktrace:");
					LogIt.error(" ");
					LogIt.error(LogIt.exceptionLog(e1));
				}
			}
		}
		
		else if(this.getConfig().getString("language").equals("nl"))
		{
			OutputStream out = null;
			File nl=new File(this.getDataFolder()+"/languages"+File.separator, "nl.yml");
			if(!nl.exists())
			{
				InputStream defaultStream = this.getResource("languages/nl.yml");
	            try {
	            	out = new FileOutputStream(nl);
	                int read = 0;
	                byte[] bytes = new byte[1024];

					while((read = defaultStream.read(bytes)) != -1) {
					    out.write(bytes, 0, read);
					}
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					LogIt.error("IOerr7");
					LogIt.error("https://aman207.net/wiki/Errors");
					LogIt.error("Stacktrace:");
					LogIt.error(" ");
					LogIt.error(LogIt.exceptionLog(e1));
				}
			}
		}
		else if(this.getConfig().getString("language").equals("de"))
		{
			OutputStream out = null;
			File de=new File(this.getDataFolder()+"/languages"+File.separator, "de.yml");
			if(!de.exists())
			{
				InputStream defaultStream = this.getResource("languages/de.yml");
	            try {
	            	out = new FileOutputStream(de);
	                int read = 0;
	                byte[] bytes = new byte[1024];

					while((read = defaultStream.read(bytes)) != -1) {
					    out.write(bytes, 0, read);
					}
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					LogIt.error("IOerr8");
					LogIt.error("https://aman207.net/wiki/Errors");
					LogIt.error("Stacktrace:");
					LogIt.error(" ");
					LogIt.error(LogIt.exceptionLog(e1));
				}
			}
		}
	}
	
	public static String colourize(String message) 
	{
		return message.replaceAll("&([l-o0-9a-f])", "\u00A7$1");
	}
	
	//Deprecated as of 1.9
	/**
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
	         InputStream in2 = this.getResource("configs/#####READ_ME_FIRST#####.yml");
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
		}*/
	
	
	@SuppressWarnings("unused")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		//Database.connect();
		//Database.execute("CREATE TABLE IF NOT EXISTS `users` (`id` int NOT NULL AUTO_INCREMENT, `username` varchar(36) NOT NULL, `tokens` varchar(16) DEFAULT '0', `date_reg` varchar(64) NOT NULL, `is_admin` varchar(5) NOT NULL, `online` varchar(5) NOT NULL, PRIMARY KEY (id))");
		//Database.executeUpdate("UPDATE `users` SET online='true' where username="e.getPlayer().getUniqueId());
		
		BukkitTask task = new Expire(e, this).runTaskLater(this, 20);
		
		if(this.getConfig().getBoolean("update-check")||this.getConfig().getString("update-check").equals("true"))
		{
			if(e.getPlayer().hasPermission("donexpress.admin.update"))
			{
				String thisVersion=getDescription().getVersion();
				Updater updater = new Updater(this, 59496, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false); // Start Updater but just do a version check
				update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE; // Determine if there is an update ready for us
				name = updater.getLatestName(); // Get the latest version
				if(update)
				{
					e.getPlayer().sendMessage(ChatColor.YELLOW+"An update for DonatorExpress is available");
					e.getPlayer().sendMessage(ChatColor.YELLOW+"New version: "+name);
					e.getPlayer().sendMessage(ChatColor.YELLOW+"Your version: "+thisVersion);
					e.getPlayer().sendMessage(ChatColor.YELLOW+"Download it here: ");
					e.getPlayer().sendMessage(ChatColor.YELLOW+updater.getLatestFileLink());
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent e)
	{
		
	}
}
