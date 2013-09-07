package net.targetcraft.donatorexpress;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class UpdateCommand {
	
	static Main plugin;

	public UpdateCommand(Main config) {
		plugin = config;
	}
	
	public static void updateCommand(CommandSender sender)
	{
		if(sender.hasPermission("donexpress.admin.update"))
		{
			File newFile = new File(plugin.getDataFolder()+File.separator, "config.yml");
			FileConfiguration newConfig = null;
			newConfig = new YamlConfiguration();
			try {
				newConfig.load(newFile);
			} catch (IOException
					| InvalidConfigurationException e1) {
				e1.printStackTrace();
			}
			
			
			String test = newConfig.getString("db-username");
			sender.sendMessage(ChatColor.RED+test);
			
			if(plugin.getConfig().getString("update-check").equals("true"))
			{
				String updateURL="https://dl.dropboxusercontent.com/s/2aljd6wda7yxmoc/DEVersion.yml?token_hash=AAHfmxCP0N1sJEAHhyE1yRtqBFT-yOvX38R87itFg_MBww&dl=1";
				String version=plugin.getDescription().getVersion();
				File updateFile = new File(plugin.getDataFolder()+File.separator, "updateCheck.yml");
				
				FileConfiguration updateCheck=null;
				updateCheck=new YamlConfiguration();
				
				BufferedInputStream in = null;
		        FileOutputStream fout = null;
		        try
		        {
		            in = new BufferedInputStream(new URL(updateURL).openStream());
		            fout = new FileOutputStream(updateFile);

		            byte data[] = new byte[1024];
		            int count;
		            while ((count = in.read(data, 0, 1024)) != -1)
		            {
		                fout.write(data, 0, count);
		            }
		        }
		        catch(IOException e)
		        {
		        	e.printStackTrace();
		        	updateCommandURLGone(sender);
		        	sender.sendMessage(ChatColor.DARK_RED+"Error. Could not write update check file. "
		        			+ "Either the update URL can not be found or the file could not be written. Trying an alternative...");
		        }
		           if (in != null)
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
		           if (fout != null)
					try {
						fout.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				try {
					updateCheck.load(updateFile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InvalidConfigurationException e) {
					e.printStackTrace();
				}
				
				if(plugin.getConfig().getBoolean("update-check"))
				{
					if(!updateCheck.getString("version").equals(version))
					{
						sender.sendMessage(ChatColor.YELLOW+"An update for DonatorExpress is available.");
						sender.sendMessage(ChatColor.YELLOW+"Your version: "+version);
						sender.sendMessage(ChatColor.YELLOW+"New version: "+updateCheck.getString("version"));
						sender.sendMessage(ChatColor.YELLOW+"Download it here: http://bit.ly/DonExp");
					}
					else
					{
						sender.sendMessage(ChatColor.YELLOW+"No new update is available");
					}
				}
				else if(plugin.getConfig().getBoolean("dev-update-check"))
				{
					if(!updateCheck.getString("version").equals(version))
					{
						sender.sendMessage(ChatColor.YELLOW+"A dev build update is available for DonatorExpress");
						sender.sendMessage(ChatColor.YELLOW+"Download is here: http://bit.ly/DonExp");
					}
				}
			}
		}
		else
		{
			sender.sendMessage(ChatColor.YELLOW
					+ "Error. You do not have permission to do that!");
		}
	}
	
	public static void updateCommandURLGone(CommandSender sender)
	{
		if(sender.hasPermission("donexpress.admin.update"))
		{
			if(plugin.getConfig().getBoolean("update-check")||plugin.getConfig().getBoolean("dev-update-check"))
			{
				String updateURL="https://docs.google.com/uc?export=download&id=0B06YPIpKUj4URDZDUnBjb3c1Mzg";
				String version=plugin.getDescription().getVersion();
				File updateFile = new File(plugin.getDataFolder()+File.separator, "updateCheck.yml");
				
				FileConfiguration updateCheck=null;
				updateCheck=new YamlConfiguration();
				
				BufferedInputStream in = null;
		        FileOutputStream fout = null;
		        try
		        {
		            in = new BufferedInputStream(new URL(updateURL).openStream());
		            fout = new FileOutputStream(updateFile);

		            byte data[] = new byte[1024];
		            int count;
		            while ((count = in.read(data, 0, 1024)) != -1)
		            {
		                fout.write(data, 0, count);
		            }
		        }
		        catch(IOException e)
		        {
		        	e.printStackTrace();
		        	sender.sendMessage(ChatColor.DARK_RED+"Error. Could not write update check file. "
		        			+ "Either the update URL can not be found or the file could not be written");
		        }
		        finally
		        {
		            if (in != null)
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
		            if (fout != null)
						try {
							fout.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
		        }
				try {
					updateCheck.load(updateFile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InvalidConfigurationException e) {
					e.printStackTrace();
				}
				
				if(plugin.getConfig().getBoolean("update-check"))
				{
					if(!updateCheck.getString("version").equals(version))
					{
						sender.sendMessage(ChatColor.YELLOW+"An update for DonatorExpress is available.");
						sender.sendMessage(ChatColor.YELLOW+"Your version: "+version);
						sender.sendMessage(ChatColor.YELLOW+"New version: "+updateCheck.getString("version"));
						sender.sendMessage(ChatColor.YELLOW+"Download it here: http://bit.ly/DonExp");
					}
					else
					{
						sender.sendMessage(ChatColor.YELLOW+"No new update is available");
					}
				}
				else if(plugin.getConfig().getBoolean("dev-update-check"))
				{
					if(!updateCheck.getString("version").equals(version))
					{
						sender.sendMessage(ChatColor.YELLOW+"A dev build update is available for DonatorExpress");
						sender.sendMessage(ChatColor.YELLOW+"Download is here: http://bit.ly/DonExp");
					}
				}
			}
		}
		else
		{
			sender.sendMessage(ChatColor.YELLOW
					+ "Error. You do not have permission to do that!");
		}
	}

}
