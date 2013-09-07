package net.targetcraft.donatorexpress;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Update extends BukkitRunnable implements Listener {
	
	static Main plugin;
	static PlayerLoginEvent e;

	static Connection con;

	public Update(PlayerLoginEvent e, Main config) 
	{
		plugin = config;
		Update.e = e;
	}
	
	public void run()
	{
		//This one is ran after someone logs in
		if(e.getPlayer().hasPermission("donexpress.admin.update"))
		{
			if(plugin.getConfig().getBoolean("update-check")||plugin.getConfig().getBoolean("dev-update-check"))
			{
				String updateURL="https://dl.dropboxusercontent.com/s/2aljd6wda7yxmoc/DEVersion.yml?token_hash=AAHfmxCP0N1sJEAHhyE1yRtqBFT-yOvX38R87itFg_MBww&dl=1";
				String version=plugin.getDescription().getVersion();
				File updateFile = new File(plugin.getDataFolder()+File.separator, "updateCheck.yml");
				
				FileConfiguration updateCheck=null;
				updateCheck=new YamlConfiguration();
				
				BufferedInputStream in = null;
		        FileOutputStream out = null;
		        try
		        {
		            in = new BufferedInputStream(new URL(updateURL).openStream());
		            out = new FileOutputStream(updateFile);

		            byte data[] = new byte[1024];
		            int count;
		            while ((count = in.read(data, 0, 1024)) != -1)
		            {
		                out.write(data, 0, count);
		            }
		        }
		        catch(IOException e1)
		        {
		        	e1.printStackTrace();
		        	e.getPlayer().sendMessage(ChatColor.DARK_RED+"Error. Could not write update check file for DonatorExpress. Encountered an IOException");
		        }
		        finally
		        {
		            if (in != null)
						try {
							in.close();
							
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		            if (out != null)
						try {
							out.close();
							
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		        }
				try {
					updateCheck.load(updateFile);
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InvalidConfigurationException e1) {
					e1.printStackTrace();
				}
				
				if(plugin.getConfig().getBoolean("update-check"))
				{
					if(!updateCheck.getString("version").equals(version))
					{
						e.getPlayer().sendMessage(ChatColor.YELLOW+"An update for DonatorExpress is available.");
						e.getPlayer().sendMessage(ChatColor.YELLOW+"Your version: "+version);
						e.getPlayer().sendMessage(ChatColor.YELLOW+"New version: "+updateCheck.getString("version"));
						e.getPlayer().sendMessage(ChatColor.YELLOW+"Download it here: http://bit.ly/DonExp");
					}
					else
					{
						e.getPlayer().sendMessage(ChatColor.YELLOW+"No new update is available for DonatorExpress");
					}
				}
				else if(plugin.getConfig().getBoolean("dev-update-check"))
				{
					if(!updateCheck.getString("version").equals(version))
					{
						e.getPlayer().sendMessage(ChatColor.YELLOW+"A dev build is available for DonatorExpress");
						e.getPlayer().sendMessage(ChatColor.YELLOW+"Download is here: http://bit.ly/DonExp");
					}
				}
			}
		}
		else
		{
			//Do nothing
		}
	}
	public void URLGone()
	{
		String updateURL="https://docs.google.com/uc?export=download&id=0B06YPIpKUj4URDZDUnBjb3c1Mzg";
		
		if(e.getPlayer().hasPermission("donexpress.admin.update"))
		{
			if(plugin.getConfig().getBoolean("update-check")||plugin.getConfig().getBoolean("dev-update-check"))
			{
				String version=plugin.getDescription().getVersion();
				File updateFile = new File(plugin.getDataFolder()+File.separator, "updateCheck.yml");
				
				FileConfiguration updateCheck=null;
				updateCheck=new YamlConfiguration();
				
				BufferedInputStream in = null;
		        FileOutputStream out = null;
		        try
		        {
		            in = new BufferedInputStream(new URL(updateURL).openStream());
		            out = new FileOutputStream(updateFile);

		            byte data[] = new byte[1024];
		            int count;
		            while ((count = in.read(data, 0, 1024)) != -1)
		            {
		                out.write(data, 0, count);
		            }
		        }
		        catch(IOException e1)
		        {
		        	e1.printStackTrace();
		        	e.getPlayer().sendMessage(ChatColor.DARK_RED+"Error. Could not write update check file for DonatorExpress. Encountered an IOException");
		        }
		        finally
		        {
		            if (in != null)
						try {
							in.close();
							
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		            if (out != null)
						try {
							out.close();
							
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		        }
				try {
					updateCheck.load(updateFile);
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InvalidConfigurationException e1) {
					e1.printStackTrace();
				}
				
				if(plugin.getConfig().getBoolean("update-check"))
				{
					if(!updateCheck.getString("version").equals(version))
					{
						e.getPlayer().sendMessage(ChatColor.YELLOW+"An update for DonatorExpress is available.");
						e.getPlayer().sendMessage(ChatColor.YELLOW+"Your version: "+version);
						e.getPlayer().sendMessage(ChatColor.YELLOW+"New version: "+updateCheck.getString("version"));
						e.getPlayer().sendMessage(ChatColor.YELLOW+"Download it here: http://bit.ly/DonExp");
					}
					else
					{
						e.getPlayer().sendMessage(ChatColor.YELLOW+"No new update is available for DonatorExpress");
					}
				}
				else if(plugin.getConfig().getBoolean("dev-update-check"))
				{
					if(!updateCheck.getString("version").equals(version))
					{
						e.getPlayer().sendMessage(ChatColor.YELLOW+"A dev build is available for DonatorExpress");
						e.getPlayer().sendMessage(ChatColor.YELLOW+"Download is here: http://bit.ly/DonExp");
					}
				}
			}
		}
		else
		{
			//Do nothing
		}
	}

}
