package net.targetcraft.donatorexpress;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class CommandListener implements Listener, CommandExecutor{
	
    static Main plugin;
	
	public CommandListener(Main config) 
	{
		plugin = config;
	}
    Connection con;
    //String VCName=plugin.getConfig().getString("currency-name");
    //String website=plugin.getConfig().getString("portal-location");
	public void connectToDatabase()
	{
		String dbUsername=plugin.getConfig().getString("db-username");
		String dbPassword=plugin.getConfig().getString("db-password");
		String dbHost=plugin.getConfig().getString("db-host");
		String dbName=plugin.getConfig().getString("db-name");
		String dbURL="jdbc:mysql://"+dbHost+"/"+dbName;
		try {
			con = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
		} catch (SQLException e) {
			Logger.getLogger(ChatColor.RED+"[DonatorExpress] Error. All has failed. The database is most likely dead. Or it is having problems. Give it a moment, and try again");
		}
	}
	public void closeDatabase()
	{
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("donate"))
		{
			if(args[0].equalsIgnoreCase("addrank"))
			{
				if(sender.hasPermission("donexpress.admin.addrank"))
				{
				if(!(args[1]==null))
				{
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Attempting to add "+args[1]+"...");
					List<String>ranks = plugin.getConfig().getStringList("ranks");			
					List<String>ranks2=new ArrayList<String>(ranks);
					 
					 int count = 0;
				     if(ranks2.contains(args[1]))
				     {
				         ranks2.remove(args[1]);
				         count++;
				     }
				     if(count==1)
				     {
				    	 sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.DARK_RED+"Error. Rank already exists");
				     }
				     else
				     {
				    	 ranks.add(args[1]);
					     plugin.getConfig().set("ranks",ranks);
					     plugin.getConfig().createSection(args[1]);
					     plugin.getConfig().createSection(args[1]+"-commands");
					     List<String>commands=plugin.getConfig().getStringList(args[1]+"-commands");
					     commands.add("putCommandsHere");
					     plugin.getConfig().set(args[1]+"-commands",commands);
					     plugin.getConfig().set(args[1], 0);
						 plugin.saveConfig(); 
						 sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.GREEN+"Successfully added "+args[1]);
				     }	
				}
				}
				else
				{
					noPermission(sender);
				}
			}
			else if (args[0].equalsIgnoreCase("delrank"))
			{
				if(sender.hasPermission("donexpress.admin.delrank"))
				{
				if(!(args[1]==null))
				{
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Attempting to remove "+args[1]+"...");
					List<String>ranks = plugin.getConfig().getStringList("ranks");			
					List<String>ranks2=new ArrayList<String>(ranks);
					 
					 int count = 0;
				     if(!ranks2.contains(args[1]))
				     {
				         count++;
				     }
				     if(count==1)
				     {
				    	 sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.DARK_RED+"Error. Rank doesn't even exist.");
				     }
				     else
				     {
				    	 ranks.remove(args[1]);
					     plugin.getConfig().set("ranks",ranks);
						 plugin.saveConfig(); 
						 sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.GREEN+"Successfully removed "+args[1]);
				     }	
				}
				}
				else
				{
					noPermission(sender);
				}
			}
			else if(args[0].equalsIgnoreCase("ranklist"))
			{
				if(sender.hasPermission(""))
				{
				List<String>ranks = plugin.getConfig().getStringList("ranks");
				sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.AQUA+"Current list of ranks:");
				sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.AQUA+ranks);
				}
				else
				{
					noPermission(sender);
				}
			}
			else if (args[0].equalsIgnoreCase("check"))
			{
				String VCName=plugin.getConfig().getString("currency-name");
			    String website=plugin.getConfig().getString("portal-location");
				if(sender.hasPermission("donexpress.user"))
				{
				Statement statement=null;
				String username="'"+sender.getName()+"'";
				try {

				statement=con.createStatement();
						
				ResultSet result=statement.executeQuery("SELECT `tokens`, `username` FROM dep  WHERE username = "+username);
				if(result.next())
				{
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.AQUA+"You currently have: "+result.getString(1)+" "+VCName);
				}
				else
				{
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"You do not have a Donator Express account. Please visit "+website+" to register.");
				}
			} catch (SQLException e) {
				connectToDatabase();
				sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Error. Could not connect to database. Attempting to reconnect. Try your command again");
				e.printStackTrace();
			}

				}
				else
				{
					noPermission(sender);
				}
			}
			
			else if (args[0].equalsIgnoreCase("checkvc"))
			{
				String VCName=plugin.getConfig().getString("currency-name");
			    String website=plugin.getConfig().getString("portal-location");
				if(sender.hasPermission("donexpress.admin.checkvc"))
				{
				Statement statement = null;				
				if(!(args[1]==null))
				{
					String username="'"+args[1]+"'";
					try {			
						statement=con.createStatement();
						ResultSet result=statement.executeQuery("SELECT `tokens`, `username` FROM dep  WHERE username = "+username);
						if(result.next())
						{
							sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.AQUA+args[1]+" currently has "+result.getString(1)+" "+VCName);
						}
						else
						{
							sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"I could not find that username in the database. Please tell the player to register at "+website);
						}
						statement.close();
					} catch (SQLException e) {
						connectToDatabase();
						sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Error. Could not connect to database. Attempting to reconnect. Try your command again");
						e.printStackTrace();
					}
				}
				else
				{
					
				}
				}
				else
				{
					noPermission(sender);
				}
			}
			else if(args[0].equalsIgnoreCase("addvc"))
			{
				String website=plugin.getConfig().getString("portal-location");
				String VCName=plugin.getConfig().getString("currency-name");
				Statement statement=null;
				try {
					statement=con.createStatement();
					if(sender.hasPermission("donexpress.admin.addvc"))
					{
					String username="'"+args[1]+"'";
					int currentTokens = 0;
					ResultSet result1=statement.executeQuery("SELECT `tokens`, `username` FROM dep  WHERE username = "+username);
					if(result1.next())
					{
						String tokens=result1.getString(1);
						currentTokens=Integer.parseInt(tokens);
					}
					else
					{
						sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"I could not find that username in the database. Please tell the player to register at "+website);
					}
					int tokensToAdd=Integer.parseInt(args[2]);
					int tokensFinal=currentTokens+tokensToAdd;
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Attempting to give "+args[1]+" "+args[2]+" "+VCName);
					String tokens="'"+tokensFinal+"'";
					statement.executeUpdate("UPDATE dep SET tokens="+tokens+"where username="+username);
					
					ResultSet result=statement.executeQuery("SELECT `tokens`, `username` FROM dep  WHERE username = "+username);
					if(result.next())
					{
						sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.GREEN+"Success!");
						sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.AQUA+args[1]+" now has "+result.getString(1)+" "+VCName);
					}
					}
					else
					{
						noPermission(sender);
					}
					statement.close();
				} catch (SQLException e) {
					connectToDatabase();
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Error. Could not connect to database. Attempting to reconnect. Try your command again");
					e.printStackTrace();
				}
			}
			else if(args[0].equalsIgnoreCase("ranks"))
			{
				if(sender instanceof Player)
				{
				sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"You can afford these ranks:");
				String website=plugin.getConfig().getString("portal-location");
				if(sender.hasPermission("donexpress.user"))
				{
					Statement statement = null;				
					String username="'"+sender.getName()+"'";
					String tokens;
					int tokensInt = 0;
					try {			
						statement=con.createStatement();
						ResultSet result=statement.executeQuery("SELECT `tokens`, `username` FROM dep  WHERE username = "+username);
						if(result.next())
						{
							tokens=result.getString(1);
							tokensInt=Integer.parseInt(tokens);
						}
						else
						{
							sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"I could not find your username in the database. Please register at "+website);
						}
						List<String>ranks = plugin.getConfig().getStringList("ranks");
						for(String s:ranks)
						{
							plugin.getConfig().getInt(s);
							int rankInt=Integer.parseInt(s);
							if(rankInt>=tokensInt)
							{
								sender.sendMessage(ChatColor.AQUA+s);
							}
							else
							{
								
							}
						}
						
						statement.close();
					} catch (SQLException e) {
						connectToDatabase();
						sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Error. Could not connect to database. Attempting to reconnect. Try your command again");
						e.printStackTrace();
					}
					}
				}
				else
				{
					sender.sendMessage(ChatColor.RED+"Error. This can only be preformed by a player");
				}
			}
			else if(args[0].equalsIgnoreCase("buy"))
			{
				String VCName=plugin.getConfig().getString("currency-name");
				if(sender.hasPermission("donexpress.user"))
				{
				if(sender instanceof Player==true)
				{
					if(!(args[1]==null))
					{
						List<String>rank = plugin.getConfig().getStringList("ranks".toLowerCase());
				        List<String>rankCopy=new ArrayList<String>(rank);
						 
						int count = 0;
					    if(rankCopy.contains(args[1].toLowerCase()))
					    {
					        rankCopy.remove(args[1].toLowerCase());
					        count++;
					    }
					    if(count==1)
					    {
							Statement statement=null;
							String username="'"+sender.getName()+"'";
							try {
								statement=con.createStatement();
									
								ResultSet result=statement.executeQuery("SELECT `tokens`, `username` FROM dep  WHERE username = "+username);
								if(result.next())
								{
									String tokens=result.getString(1);
									//String rankLookup=plugin.getConfig().getString(args[1]);
									int rankInt=Integer.parseInt(plugin.getConfig().getString(args[1].toLowerCase()));//Gets the amount of tokens needed for the specific rank
									int tokensInt=Integer.parseInt(tokens);//Gets the amount of tokens that a user currently has
									if(!(rankInt>=tokensInt))
									{
										//Move this stuff to /buy confirm
										List<String> rankCommand=plugin.getConfig().getStringList((args[1].toLowerCase()+"-commands").replace("%player", sender.getName()));
										boolean sendMessage=false;
										for(String s:rankCommand)
										{
											plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), s.replace("%player", sender.getName()));
											sendMessage=true;
										}
										if(sendMessage==true)
										{
											int finalTokens=tokensInt-rankInt;
											statement.executeUpdate("UPDATE dep SET tokens='"+finalTokens+"' where username='"+sender.getName()+"'");
											Bukkit.broadcastMessage(colourize(plugin.getConfig().getString("donate-message").replace("%player", sender.getName())));
										}
									}
									else
									{
										sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"You do not have enough "+VCName+" to buy that rank!");
									}
									
								}
								else
								{
									sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.RED+"Error. You have not registered at "+VCName+", or the Server Owner/Administrator has not correctly set up DonatorExpress");
								}
								statement.close();
							} catch (SQLException e) {
								connectToDatabase();
								sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Error. Could not connect to database. Attempting to reconnect. Try your command again");
								e.printStackTrace();
							} catch (NullPointerException e)
							{
								connectToDatabase();
								sender.sendMessage(ChatColor.RED+"[DonatorExpress] Hm. I can't connect to the database. Please resend your command. It should work this time.");
							}
						}
					    else
					    {
					    	 sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"I could not find that rank. Please try again.");
					    }				    	 
					}
				}
				else
				{
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.DARK_RED+"Error. This command can only be preformed by a player");
				}
				}
				else
				{
					noPermission(sender);
				}
				
			}
			else if(args[0].equalsIgnoreCase("confirm"))
			{
				//TODO
				//Command to confirm that you wish to buy that rank. Make it expire after after 10 seconds
			}
			else if(args[0].equalsIgnoreCase("about"))
			{
				sender.sendMessage(ChatColor.YELLOW+"***************************************");
				sender.sendMessage(ChatColor.RED+"");
				sender.sendMessage(ChatColor.AQUA+"DonatorExpress Version 0.4");
				sender.sendMessage(ChatColor.AQUA+"Plugin developed by: aman207");
				sender.sendMessage(ChatColor.AQUA+"Webportal developed by: AzroWear");
				sender.sendMessage(ChatColor.RED+"");
				sender.sendMessage(ChatColor.YELLOW+"***************************************");
			}
			else if(args[0].equalsIgnoreCase("help"))
			{
				sender.sendMessage(ChatColor.RED+"Correct command usage");
				sender.sendMessage(ChatColor.RED+"/donator buy [rank]");
				sender.sendMessage(ChatColor.RED+"/donator confirm");
				sender.sendMessage(ChatColor.RED+"/donator check");
				if(sender.hasPermission("donexpress.admin.basic"))
				{
					sender.sendMessage(ChatColor.RED+"/donator checkvc [username]");
					sender.sendMessage(ChatColor.RED+"/donator ranklist");
					sender.sendMessage(ChatColor.RED+"/donator dbconnect    WARNING. ONLY USE THIS IF YOU ARE HAVING TROUBLES CONNECTING TO THE DATABASE. THIS COMMAND SHOULD NEVER BE NEEDED");
				}
				if(sender.hasPermission("donexpress.admin.*"))
				{
					sender.sendMessage(ChatColor.RED+"/donator addrank [rankName]");
					sender.sendMessage(ChatColor.RED+"/donator delrank [rankName]");
					sender.sendMessage(ChatColor.RED+"/donator addvc [userName]");
				}
			}
			else if(args[0].equalsIgnoreCase("dbconnect"))
			{
				if(sender.hasPermission("donexpress.admin.dbconnect"))
				{
					connectToDatabase();
				}

			}
			else if(args[0].equals("dbcloseNEVERRUNTHIS"))
			{
				if(sender instanceof ConsoleCommandSender)
				{
					closeDatabase();
				}
			}
			else
			{
				sender.sendMessage(ChatColor.RED+"Correct command usage");
				sender.sendMessage(ChatColor.RED+"/donator buy [rank]");
				sender.sendMessage(ChatColor.RED+"/donator confirm");
				sender.sendMessage(ChatColor.RED+"/donator check");
				if(sender.hasPermission("donexpress.admin.basic"))
				{
					sender.sendMessage(ChatColor.RED+"/donator checkvc [username]");
					sender.sendMessage(ChatColor.RED+"/donator ranklist");
					sender.sendMessage(ChatColor.RED+"/donator dbconnect    WARNING. ONLY USE THIS IF YOU ARE HAVING TROUBLES CONNECTING TO THE DATABASE. THIS COMMAND SHOULD NEVER BE NEEDED");
				}
				if(sender.hasPermission("donexpress.admin.*"))
				{
					sender.sendMessage(ChatColor.RED+"/donator addrank [rankName]");
					sender.sendMessage(ChatColor.RED+"/donator delrank [rankName]");
					sender.sendMessage(ChatColor.RED+"/donator addvc [userName]");
				}
				return true;
			}
			return false;
		}
		if(cmd.getName().equalsIgnoreCase("editrank"))
		{
			if(sender.hasPermission("donexpress.admin.editrank"))
			{
			if(args[0].equalsIgnoreCase("tokens"))
			{
				sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Attempting to replace "+args[1]+"'s amount of tokens with "+args[2]);
				List<String>ranks = plugin.getConfig().getStringList("ranks");			
				List<String>ranks2=new ArrayList<String>(ranks);
				 
				 int count = 0;
			     if(ranks2.contains(args[1]))
			     {
			         ranks2.remove(args[1]);
			         count++;
			     }
			     if(count==1)
			     {
			    	 plugin.getConfig().set(args[1], args[2]);
					 plugin.saveConfig();
					 sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.GREEN+"Successful");
			     }
			     else
			     {
			    	 sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.DARK_RED+"Error. Existing rank could not be found");
			     }	

				
			}
			}
			else
			{
				noPermission(sender);
			}
		}
		return false;
	}
	public String colourize(String message)
	{
		return message.replaceAll("&([l-o0-9a-f])", "\u00A7$1");
	}
	public void error(CommandSender sender)
	{
		sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"I did not recognize the command you entered or you did not enter in the correct arguments for the command");
		sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Type /donate help for all commands");
	}
	public void noPermission(CommandSender sender)
	{
		sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Error. You do not have permission to do that!");
	}

}
