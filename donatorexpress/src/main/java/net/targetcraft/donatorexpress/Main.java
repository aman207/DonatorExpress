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

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin implements Listener {
	Connection con;
	private static HashMap<String, String> captions;
	
	public static boolean update = false;
	public static String name = "";
	public static Economy econ = null;
	
	public void onEnable()
	{
		initializeLanguageFile();
		Language lang = new Language(this);
		lang.languageInitialize();
		
		File forumConfig = new File(getDataFolder(), "forumConfig.yml");
		File economyConfigFile = new File (getDataFolder(), "economy.yml");
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
				
				if(existingRanks.exists())
				{
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
			}
			
			
			for(String s:ranks)
			{
				setForumConfig(s);
			}
		}
		if(!economyConfigFile.exists())
		{
			getLogger().info("Economy Configuration not found. Generating...");
			
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
			}
		}
		getCommand("donate").setExecutor(new CommandListener(this));
		
		//Used to initialize the constructor
		@SuppressWarnings("unused")
		Database testConnect = new Database(this);
		
		Bukkit.getPluginManager().registerEvents(this, this);
		if(setupEconomy())
		{
			Bukkit.getPluginManager().registerEvents(new SignEvent(this), this);
		}
		else
		{
			Logger.getLogger("").warning("[DonatorExpress] Vault not found, disabling Sign feature");
		}
		Bukkit.getPluginManager().registerEvents(new CommandListener(this), this);
		
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
				}
			}
		}
		
		else if(this.getConfig().getString("language").equals("du"))
		{
			OutputStream out = null;
			File du=new File(this.getDataFolder()+"/languages"+File.separator, "du.yml");
			if(!du.exists())
			{
				InputStream defaultStream = this.getResource("languages/du.yml");
	            try {
	            	out = new FileOutputStream(du);
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
		}
	
	
	@SuppressWarnings("unused")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
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
					e.getPlayer().sendMessage(ChatColor.YELLOW+"Download it here: http://dev.bukkit.org/bukkit-plugins/donator-express");
				}
			}
		}
	}
}
