package net.targetcraft.donatorexpress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
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
	public void onEnable()
	{
		File forumConfig = new File(getDataFolder(), "forumConfig.yml");		
		List<String> ranks = new ArrayList<String>();
		File file=new File(getDataFolder()+File.separator+"config.yml");
		
		if (!file.exists())
		{
			getDataFolder().mkdirs();
			
			getLogger().info("Configuration not found. Generating...");
			this.saveDefaultConfig();
		}
		
		if(!forumConfig.exists())
		{
			getLogger().info("Forum Configuration not found. Generating...");
			
			try {
				getDataFolder().mkdirs();
				ranks=this.getConfig().getStringList("ranks");
				File existingRanks=new File(this.getDataFolder()+file.separator,"packages.yml");
				
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
			//All that junk down there copies the forumConfig.yml from the jar to the folder
			//I don't even know how it works, it just does. 
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
	public void setForumConfig(String s)
	{
		File packages = new File(Bukkit.getServer().getPluginManager().getPlugin("DonatorExpress").getDataFolder(), s+".yml");		
		YamlConfiguration forum = null;
		forum=new YamlConfiguration();
		
		if(!packages.exists())
		{
			try {
				packages.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			List<String>commands=this.getConfig().getStringList(s+"-commands");
			List<String>expireCommands = forum.getStringList("packages");
			expireCommands.add("putCommandsHere");
			String price = this.getConfig().getString(s);
			int priceInt=Integer.parseInt(price);
			
			forum.createSection("price");
			forum.createSection("commands");
			forum.createSection("forum-group");
			forum.createSection("forum-expire");
			forum.createSection("forum-expire-group");
			forum.createSection("expire");
			forum.createSection("expire-time");
			forum.createSection("expire-commands");
			forum.createSection("expire-message");
			forum.set("price", priceInt);
			forum.set("forum-group", 0);
			forum.set("forum-expire", false);
			forum.set("forum-expire-group", 0);
			forum.set("commands", commands);
			forum.set("expire", false);
			forum.set("expire-time", 0);
			forum.set("expire-commands", expireCommands);
			forum.set("expire-message", "&cYour package has expired. You have been deranked");
			
			try {
				forum.load(packages);
				forum.save(packages);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent e)
	{
		BukkitTask task = new Expire(e, this).runTaskLater(this, 20);
	}
}
