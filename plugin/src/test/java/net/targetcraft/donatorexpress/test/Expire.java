package net.targetcraft.donatorexpress.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Expire extends BukkitRunnable {
	
	static Main plugin;
	static PlayerJoinEvent e;

	public Expire(PlayerJoinEvent e2, Main config) 
	{
		plugin = config;
		Expire.e = e2;
	}

	public void run()
	{
		Database.connect();
		
		File configYaml = new File(plugin.getDataFolder()+File.separator, "config.yml");
		FileConfiguration config=null;
		config=new YamlConfiguration();
		try {
			config.load(configYaml);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (InvalidConfigurationException e2) {
			e2.printStackTrace();
		}
		
		try {
			String username=e.getPlayer().getName().toString();
			Database.execute("CREATE TABLE IF NOT EXISTS `expire_packages` (`id` int NOT NULL AUTO_INCREMENT, `username` varchar(24) NOT NULL, `package` varchar(50) NOT NULL, `date` varchar(64) NOT NULL, PRIMARY KEY (id))");
			ResultSet result=Database.executeStatement("SELECT `package`, `date` FROM expire_packages  WHERE username = '"+username+"'");
			while(result.next())
			{
				String dateGet=result.getString(2);
				String rankGet=result.getString(1);
				
				File packages = new File(plugin.getDataFolder()+File.separator, "packages.yml");
				FileConfiguration packagesConfig=null;
				packagesConfig=new YamlConfiguration();
				packagesConfig.load(packages);
				
				File newFile = new File(plugin.getDataFolder()+"/packages"+File.separator ,rankGet+".yml");
		    	FileConfiguration newFileConfig=null;
	    		newFileConfig=new YamlConfiguration();
	    		newFileConfig.load(newFile);
	    		
	    		if(newFileConfig.getBoolean("expire")||newFileConfig.getString("expire").equals("true"))
	    		{	    			
	    			Date date = new SimpleDateFormat("yyyy/MM/dd").parse(dateGet);
					Calendar today = Calendar.getInstance();
					Calendar check = Calendar.getInstance();
					check.setTime(date);
					int time = newFileConfig.getInt("expire-time");
					
					check.add(Calendar.DAY_OF_MONTH, time);
					if(today.after(check))
					{
						if(newFileConfig.getBoolean("forum-expire")||newFileConfig.getString("forum-expire").equals("true"))
						{	
							forumDerank(rankGet);
						}
						List<String> expireCommands = newFileConfig.getStringList("expire-commands");
						for(String s:expireCommands)
						{
							plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), s.replace("%player", e.getPlayer().getName().toString()));
							Database.execute("DELETE FROM expire_packages WHERE username='"+username+"' AND date='"+dateGet+"'");
						}
						String prefix=plugin.getConfig().getString("prefix");
						prefix=prefix.replaceAll("&([l-o0-9a-f])", "\u00A7$1");
						String expireMessage=newFileConfig.getString("expire-message");
						expireMessage=expireMessage.replaceAll("&([l-o0-9a-f])", "\u00A7$1");
						
						e.getPlayer().sendMessage(prefix+expireMessage);						
					}
	    		}				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InvalidConfigurationException e1) {
			e1.printStackTrace();
		}
		Database.close();
	}
	
	public void forumDerank(String group) throws FileNotFoundException, IOException, InvalidConfigurationException
	{
		Database.connect();
		
		File forumGroup = new File(plugin.getDataFolder()+"/packages"+File.separator, group+".yml");
		File forumConfig = new File(plugin.getDataFolder()+File.separator, "forumConfig.yml");
		
		FileConfiguration forumGroupYaml = null;
		forumGroupYaml=new YamlConfiguration();
		
		FileConfiguration forumConfigYaml = null;
		forumConfigYaml=new YamlConfiguration();
		
		forumConfigYaml.load(forumConfig);
		forumGroupYaml.load(forumGroup);
		
		if(forumGroupYaml.getBoolean("forum-expire"))
		{
			try {
				Statement statement=null;
				Connection forumdb;
			
				String dbUsername = forumConfigYaml.getString("db-username");
				String dbPassword = forumConfigYaml.getString("db-password");
				String dbHost = forumConfigYaml.getString("db-host");
				String dbName = forumConfigYaml.getString("db-name");
				String dbURL = "jdbc:mysql://" + dbHost + "/" + dbName;
				forumdb = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
				
				statement=forumdb.createStatement();
				if(forumConfigYaml.getString("mybb").equals("true"))
				{
					String groupName=forumGroupYaml.getString("forum-expire-group");
					
					String prefix=forumConfigYaml.getString("db-prefix");
					String username=e.getPlayer().getName().toString();
					if(forumConfigYaml.getString("username-mode").equals("true"))
					{
						statement.executeUpdate("UPDATE "+prefix+"users SET usergroup='"+groupName+"' WHERE username='"+username+"'");
					}
					else if(forumConfigYaml.getString("email-mode").equals("true"))
					{
						ResultSet result=Database.executeStatement("SELECT `email` FROM dep  WHERE username = '"+username+"'");
						String email=null;
						while(result.next())
						{
							email=result.getString(1);
						}
						
						statement.executeUpdate("UPDATE "+prefix+"users SET usergroup='"+groupName+"' WHERE email='"+email+"'");
					}
				}
				else if(forumConfigYaml.getString("xenforo").equals("true"))
				{
					String groupName=forumGroupYaml.getString("forum-expire-group");
					String prefix=forumConfigYaml.getString("db-prefix");
					String username=e.getPlayer().getName().toString();
					if(forumConfigYaml.getString("username-mode").equals("true"))
					{
						statement.executeUpdate("UPDATE "+prefix+"user SET user_group_id='"+groupName+"' WHERE username='"+username+"'");
					}
					else if(forumConfigYaml.getString("email-mode").equals("true"))
					{
						ResultSet result=Database.executeStatement("SELECT `email` FROM dep  WHERE username = '"+username+"'");
						String email=null;
						while(result.next())
						{
							email=result.getString(1);
						}
						
						statement.executeUpdate("UPDATE "+prefix+"user SET user_group_id='"+groupName+"' WHERE email='"+email+"'");
					}
				}
				else if(forumConfigYaml.getString("ipboard").equals("true"))
				{
					String groupName=forumGroupYaml.getString("forum-expire-group");
					String prefix=forumConfigYaml.getString("db-prefix");
					String username=e.getPlayer().getName().toString();
					if(forumConfigYaml.getString("username-mode").equals("true"))
					{
						statement.executeUpdate("UPDATE "+prefix+"members SET member_group_id='"+groupName+"' WHERE name='"+username+"'");
					}
					else if(forumConfigYaml.getString("email-mode").equals("true"))
					{
						ResultSet result=Database.executeStatement("SELECT `email` FROM dep  WHERE username = '"+username+"'");
						String email=null;
						while(result.next())
						{
							email=result.getString(1);
						}
						
						statement.executeUpdate("UPDATE "+prefix+"members SET member_group_id='"+groupName+"' WHERE email='"+email+"'");
					}
				}
				/**
				//TODO
				else if(forumConfigYaml.getString("phpbb").equals("true"))
				{
					String groupName=forumGroupYaml.getString(group+"-group");
					String prefix=forumConfigYaml.getString("db-prefix");
					String username=sender.toString();
					if(forumConfigYaml.getString("username-mode").equals("true"))
					{
						statement.executeUpdate("UPDATE "+prefix+"users SET group_id='"+groupName+"' WHERE usernamename='"+username+"'");
					}
					else if(forumConfigYaml.getString("email-mode").equals("true"))
					{
						Statement forumStatement=null;
						forumStatement=con.createStatement();
						ResultSet result=forumStatement.executeQuery("SELECT `email` FROM dep  WHERE username = '"+username+"'");
						String email=null;
						while(result.next())
						{
							email=result.getString(1);
						}
						
						statement.executeUpdate("UPDATE "+prefix+"users SET group_id='"+groupName+"' WHERE user_email='"+email+"'");
					}
				}
				*/
				else if(forumConfigYaml.getString("simplemachines").equals("true"))
				{
					String groupName=forumGroupYaml.getString("forum-expire-group");
					String prefix=forumConfigYaml.getString("db-prefix");
					String username=e.getPlayer().getName().toString();
					if(forumConfigYaml.getString("username-mode").equals("true"))
					{
						statement.executeUpdate("UPDATE "+prefix+"members SET id_group='"+groupName+"' WHERE member_name='"+username+"'");
					}
					else if(forumConfigYaml.getString("email-mode").equals("true"))
					{
						ResultSet result=Database.executeStatement("SELECT `email` FROM dep  WHERE username = '"+username+"'");
						String email=null;
						while(result.next())
						{
							email=result.getString(1);
						}
						
						statement.executeUpdate("UPDATE "+prefix+"members SET id_group='"+groupName+"' WHERE email_address='"+email+"'");
					}
				}
				else if(forumConfigYaml.getString("vbulletin").equals("true"))
				{
					String groupName=forumGroupYaml.getString("forum-expire-group");
					String prefix=forumConfigYaml.getString("db-prefix");
					String username=e.getPlayer().getName().toString();
					if(forumConfigYaml.getString("username-mode").equals("true"))
					{
						statement.executeUpdate("UPDATE "+prefix+"user SET usergroupid='"+groupName+"' WHERE username='"+username+"'");
					}
					else if(forumConfigYaml.getString("email-mode").equals("true"))
					{
						ResultSet result=Database.executeStatement("SELECT `email` FROM dep  WHERE username = '"+username+"'");
						String email=null;
						while(result.next())
						{
							email=result.getString(1);
						}
						
						statement.executeUpdate("UPDATE "+prefix+"user SET usergroupid='"+groupName+"' WHERE email='"+email+"'");
					}
				}
				else
				{
					
				}
				statement.close();
				forumdb.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		}
		else
		{
			
		}

	}

}
