package net.aman207.donatorexpress.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.aman207.donatorexpress.DonatorExpress;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class Forum {
	
	static DonatorExpress plugin;
	
	public Forum(DonatorExpress config)
	{
		plugin=config;
	}
	
	public void syncForum(CommandSender sender, String group)
	{
		Database.connect();
		
		File forumGroup = new File(plugin.getDataFolder()+"/packages"+File.separator, group.toLowerCase()+".yml");
		File forumConfig = new File(plugin.getDataFolder()+File.separator, "forumConfig.yml");
		
		YamlConfiguration forumGroupYaml = null;
		forumGroupYaml=new YamlConfiguration();
		YamlConfiguration forumConfigYaml = null;
		forumConfigYaml=new YamlConfiguration();
		
		try {
				Statement statement=null;
				Connection forumdb;
				forumConfigYaml.load(forumConfig);
				forumGroupYaml.load(forumGroup);
			
				String dbUsername = forumConfigYaml.getString("db-username");
				String dbPassword = forumConfigYaml.getString("db-password");
				String dbHost = forumConfigYaml.getString("db-host");
				String dbName = forumConfigYaml.getString("db-name");
				String dbURL = "jdbc:mysql://" + dbHost + "/" + dbName;
				forumdb = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
				
				statement=forumdb.createStatement();				
				if(forumConfigYaml.getString("mybb").equals("true")||forumConfigYaml.getBoolean("mybb"))
				{
					String groupName=forumGroupYaml.getString("forum-group");
					
					String prefix=forumConfigYaml.getString("db-prefix");
					String username=sender.getName().toString();
					if(forumConfigYaml.getString("username-mode").equals("true")||forumConfigYaml.getBoolean("username-mode"))
					{
						statement.executeUpdate("UPDATE "+prefix+"users SET usergroup='"+groupName+"' WHERE username='"+username+"'");
					}
					else if(forumConfigYaml.getString("email-mode").equals("true")||forumConfigYaml.getBoolean("email-mode"))
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
				else if(forumConfigYaml.getString("xenforo").equals("true")||forumConfigYaml.getBoolean("xenforo"))
				{
					String groupName=forumGroupYaml.getString("forum-group");
					String prefix=forumConfigYaml.getString("db-prefix");
					String username=sender.getName().toString();
					if(forumConfigYaml.getString("username-mode").equals("true")||forumConfigYaml.getBoolean("username-mode"))
					{
						statement.executeUpdate("UPDATE "+prefix+"user SET user_group_id='"+groupName+"' WHERE username='"+username+"'");
					}
					else if(forumConfigYaml.getString("email-mode").equals("true")||forumConfigYaml.getBoolean("email-mode"))
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
				else if(forumConfigYaml.getString("ipboard").equals("true")||forumConfigYaml.getBoolean("ipboard"))
				{
					String groupName=forumGroupYaml.getString("forum-group");
					String prefix=forumConfigYaml.getString("db-prefix");
					String username=sender.getName().toString();
					if(forumConfigYaml.getString("username-mode").equals("true")||forumConfigYaml.getBoolean("username-mode"))
					{
						statement.executeUpdate("UPDATE "+prefix+"members SET member_group_id='"+groupName+"' WHERE name='"+username+"'");
					}
					else if(forumConfigYaml.getString("email-mode").equals("true")||forumConfigYaml.getBoolean("email-mode"))
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
				else if(forumConfigYaml.getString("simplemachines").equals("true")||forumConfigYaml.getBoolean("simplemachines"))
				{
					String groupName=forumGroupYaml.getString("forum-group");
					String prefix=forumConfigYaml.getString("db-prefix");
					String username=sender.getName().toString();
					if(forumConfigYaml.getString("username-mode").equals("true")||forumConfigYaml.getBoolean("username-mode"))
					{
						statement.executeUpdate("UPDATE "+prefix+"members SET id_group='"+groupName+"' WHERE member_name='"+username+"'");
					}
					else if(forumConfigYaml.getString("email-mode").equals("true")||forumConfigYaml.getBoolean("email-mode"))
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
				else if(forumConfigYaml.getString("vbulletin").equals("true")||forumConfigYaml.getBoolean("vbulletin"))
				{
					String groupName=forumGroupYaml.getString("forum-group");
					String prefix=forumConfigYaml.getString("db-prefix");
					String username=sender.getName().toString();
					if(forumConfigYaml.getString("username-mode").equals("true")||forumConfigYaml.getBoolean("username-mode"))
					{
						statement.executeUpdate("UPDATE "+prefix+"user SET usergroupid='"+groupName+"' WHERE username='"+username+"'");
					}
					else if(forumConfigYaml.getString("email-mode").equals("true")||forumConfigYaml.getBoolean("email-mode"))
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
				Database.close();
				forumdb.close();
		} catch (SQLException e) {
			sender.sendMessage(ChatColor.RED+"Uh oh. I could not add you to a forum group. " +
					"This could be because the server owner has improperly configured DonatorExpress or because you have not signed up on the forums");
			sender.sendMessage(ChatColor.RED+"Please contact the server owner!");
			e.printStackTrace();
			LogIt.error("SQLerr7");
			LogIt.error("https://aman207.net/wiki/Errors");
			LogIt.error("A database occred when trying to add a user to a forum group");
			LogIt.error("Stacktrace:");
			LogIt.error(" ");
			LogIt.error(LogIt.exceptionLog(e));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

}
