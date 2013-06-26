package net.targetcraft.donatorexpress;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
    HashMap<String, Boolean> confirm = new HashMap<String, Boolean>();
    HashMap<String, Integer> rankIntMap = new HashMap<String, Integer>();
    HashMap<String, Integer> tokensIntMap = new HashMap<String, Integer>();
    HashMap<String, String> rankString = new HashMap<String, String>();
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
			try{
			if(args[0].equalsIgnoreCase("addrank"))
			{
				if(sender.hasPermission("donexpress.admin.addrank"))
				{
				if(!(args[1]==null))
				{
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Attempting to add "+args[1]+"...");
					List<String>ranks = plugin.getConfig().getStringList("ranks".toLowerCase());			
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
				    	 ranks.add(args[1].toLowerCase());
					     plugin.getConfig().set("ranks",ranks);
					     plugin.getConfig().createSection(args[1].toLowerCase());
					     plugin.getConfig().createSection(args[1].toLowerCase()+"-commands");
					     List<String>commands=plugin.getConfig().getStringList(args[1].toLowerCase()+"-commands");
					     commands.add("putCommandsHere");
					     plugin.getConfig().set(args[1].toLowerCase()+"-commands",commands);
					     plugin.getConfig().set(args[1].toLowerCase(), 0);
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
					List<String>ranks = plugin.getConfig().getStringList("ranks".toLowerCase());			
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
				    	 ranks.remove(args[1].toLowerCase());
					     plugin.getConfig().set("ranks".toLowerCase(),ranks);
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
				if(sender.hasPermission("donexpress.user"))
				{
				List<String>ranks = plugin.getConfig().getStringList("ranks".toLowerCase());
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
				if(sender.hasPermission("donexpress.user"))
				{
				if(sender instanceof Player)
				{
				String VCName=plugin.getConfig().getString("currency-name");
			    String website=plugin.getConfig().getString("portal-location");
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
				}
				else
				{
					noPermission(sender);
				}
			}
			
			else if (args[0].equalsIgnoreCase("checkvc"))
			{
				if(sender.hasPermission("donexpress.admin.checkvc"))
				{
				String VCName=plugin.getConfig().getString("currency-name");
			    String website=plugin.getConfig().getString("portal-location");
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
				if(sender.hasPermission("donexpress.admin.addvc"))
				{
				String website=plugin.getConfig().getString("portal-location");
				String VCName=plugin.getConfig().getString("currency-name");
				Statement statement=null;
				try {
					statement=con.createStatement();
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
			//TODO
			/**
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
			*/
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
									int rankInt=Integer.parseInt(plugin.getConfig().getString(args[1]));//Gets the amount of tokens needed for the specific rank
									int tokensInt=Integer.parseInt(tokens);//Gets the amount of tokens that a user currently has
									if(!(rankInt>=tokensInt))
									{
										String rankSend=args[1];
										sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"This item costs "+rankInt+" "+VCName+". Type /donate confirm if you wish to buy this item");
										
										confirm.put("sender", true);
										rankIntMap.put("rankInt", rankInt);
										tokensIntMap.put("tokensInt", tokensInt);
										rankString.put("rankName", rankSend);
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
								e.printStackTrace();
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
			else if(args[0].equalsIgnoreCase("upgrade"))
			{
				if(sender.hasPermission("donexpress.user.upgrade"))
				{
				try{
					String VCName=plugin.getConfig().getString("currency-name");
					Statement statement=null;
					statement=con.createStatement();
					String rankAlready = null;
				
					if(sender instanceof Player)
					{
						boolean hasPurchased=false;
						String username="'"+sender.getName()+"'";
						ResultSet result=statement.executeQuery("SELECT `tokens`, `username` FROM purchases  WHERE username = "+username);
						if(result.next())
						{
							//String tokens=result.getString(1);
							//int tokensInt=Integer.parseInt(tokens);
					        hasPurchased=true;
					        
					        ResultSet result1=statement.executeQuery("SELECT `rank`, `username` FROM purchases  WHERE username = "+username);
							if(result1.next())
							{
								rankAlready=result1.getString(1);
							}
						}
						if(hasPurchased==true)
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
								ResultSet result2=statement.executeQuery("SELECT `tokens`, `username` FROM dep  WHERE username = "+username);
								if(result2.next())
								{
									String tokens=result2.getString(1);
									//String rankLookup=plugin.getConfig().getString(args[1]);
									int rankAlreadyInt=Integer.parseInt(plugin.getConfig().getString(rankAlready));
									int rankInt1=Integer.parseInt(plugin.getConfig().getString(args[1]));//Gets the amount of tokens needed for the specific rank
									int tokensInt=Integer.parseInt(tokens);//Gets the amount of tokens that a user currently has
									int rankAlreadyCheck=rankInt1-rankAlreadyInt;
									if(rankAlreadyCheck>0)
									{
									if(!(rankInt1>=tokensInt))
									{
										String rankSend=args[1];
										sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Type /donate confirm to confirm that you wish to buy this rank");
									    
										confirm.clear();
									    rankIntMap.clear();
									    tokensIntMap.clear();
									    rankString.clear();
									    
										confirm.put("sender", true);
										rankIntMap.put("rankInt", rankInt1);
										tokensIntMap.put("tokensInt", tokensInt);
										rankString.put("rankName", rankSend);
									}
									else
									{
										sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"You do not have enough "+VCName+" to buy that rank!");
									}
									}
									else
									{
										sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"You are trying to get a rank lower than the one that you have already. Or you are tying to get one that you already have. Try again");
									}
								}
						      }
						else
						{
							sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"You need to buy a rank before you can upgrade!");
						}
				
					}

					}
					else
					{
						sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"This command can only be run by a player");
					}
				    }catch(SQLException e)
				{
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
			else if(args[0].equalsIgnoreCase("confirm"))
			{
				if(sender.hasPermission("donexpress.user"))
				{
				//TODO Add 10 seconds in next update.
				if(sender instanceof Player)
				{
				try {
					
				Statement statement=null;
				statement=con.createStatement();
				if(confirm.get("sender"))
				{
					String rank=rankString.get("rankName");
					List<String> rankCommand=plugin.getConfig().getStringList((rank+"-commands").replace("%player", sender.getName()));
					boolean sendMessage=false;
					for(String s:rankCommand)
					{
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), s.replace("%player", sender.getName()));
						statement.execute("CREATE TABLE IF NOT EXISTS `purchases` (`id` int NOT NULL AUTO_INCREMENT, `username` varchar(16) NOT NULL, `tokens` varchar(16) NOT NULL, `rank` varchar(16) NOT NULL, `date` varchar(64) NOT NULL, PRIMARY KEY (id))");
						
						int rankInt=0;
						rankInt=rankIntMap.get("rankInt");
						
						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
						Calendar cal = Calendar.getInstance();
						String date=dateFormat.format(cal.getTime());
						
						String username="'"+sender.getName()+"'";
						ResultSet result=statement.executeQuery("SELECT `tokens`, `username` FROM purchases  WHERE username = "+username);
						if(result.next())
						{
							String tokens=result.getString(1);
							int tokensInt=Integer.parseInt(tokens);
							rankInt=rankInt+tokensInt;
				     	}
						statement.execute("INSERT INTO purchases (username, tokens, rank, date) VALUES ('"+sender.getName()+"', '"+rankInt+"', '"+rank+"', '"+date+"')");
						statement.executeUpdate("UPDATE purchases SET tokens='"+rankInt+"' where username='"+sender.getName()+"'");
						statement.executeUpdate("UPDATE purchases SET rank='"+rank+"' where username='"+sender.getName()+"'");
						
						sendMessage=true;
					}
					if(sendMessage==true)
					{
						int tokensInt=0;
						int rankInt=0;
						tokensInt=tokensIntMap.get("tokensInt");
						rankInt=rankIntMap.get("rankInt");
						int finalTokens=tokensInt-rankInt;
						statement.executeUpdate("UPDATE dep SET tokens='"+finalTokens+"' where username='"+sender.getName()+"'");
						Bukkit.broadcastMessage(colourize(plugin.getConfig().getString("donate-message").replace("%player", sender.getName())));
						tokensIntMap.clear();
						rankIntMap.clear();
						rankString.clear();
						confirm.clear();
					}
				}
				else
				{
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Please type /donate buy [rank] first");
				}
				}catch (SQLException e) {
					connectToDatabase();
					e.printStackTrace();
				}catch (NullPointerException e)
				{
					sender.sendMessage(ChatColor.GOLD+"[DonatorExpress] "+ChatColor.DARK_RED+"Error. You need to select a rank/item to purchase first! Type /donate ranklist to view the available ");
				}
				}
				else
				{
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"This command can only be ran by a player");
				}
				}
				else
				{
					noPermission(sender);
				}
			}
			else if(args[0].equalsIgnoreCase("editrank"))
			{
				if(sender.hasPermission("donexpress.admin.editrank"))
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
				else
				{
					noPermission(sender);
				}
			}
			else if(args[0].equalsIgnoreCase("reload"))
			{
				plugin.saveConfig();
				plugin.reloadConfig();
				sender.sendMessage(ChatColor.GREEN+"[DonatorExpress] Reload successful!");
			}
			else if(args[0].equalsIgnoreCase("about"))
			{
				sender.sendMessage(ChatColor.YELLOW+"***************************************");
				sender.sendMessage(ChatColor.RED+"");
				sender.sendMessage(ChatColor.AQUA+"DonatorExpress Version 0.5");
				sender.sendMessage(ChatColor.AQUA+"Plugin developed by: aman207");
				sender.sendMessage(ChatColor.AQUA+"Webportal developed by: AzroWear");
				sender.sendMessage(ChatColor.RED+"");
				sender.sendMessage(ChatColor.YELLOW+"***************************************");
			}
			else if(args[0].equalsIgnoreCase("help"))
			{
				sender.sendMessage(ChatColor.RED+"Correct command usage");
				sender.sendMessage(ChatColor.RED+"/donate buy [rank]");
				sender.sendMessage(ChatColor.RED+"/donate confirm");
				sender.sendMessage(ChatColor.RED+"/donate check");
				if(sender.hasPermission("donexpress.admin.basic"))
				{
					sender.sendMessage(ChatColor.RED+"/donate checkvc [username]");
					sender.sendMessage(ChatColor.RED+"/donate ranklist");
					sender.sendMessage(ChatColor.RED+"/donate dbconnect    WARNING. ONLY USE THIS IF YOU ARE HAVING TROUBLES CONNECTING TO THE DATABASE. THIS COMMAND SHOULD NEVER BE NEEDED");
				}
				if(sender.hasPermission("donexpress.admin.*"))
				{
					sender.sendMessage(ChatColor.RED+"/donate addrank [rankName]");
					sender.sendMessage(ChatColor.RED+"/donate delrank [rankName]");
					sender.sendMessage(ChatColor.RED+"/donate addvc [userName]");
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
				sender.sendMessage(ChatColor.RED+"/donate buy [rank]");
				sender.sendMessage(ChatColor.RED+"/donate confirm");
				sender.sendMessage(ChatColor.RED+"/donate check");
				if(sender.hasPermission("donexpress.admin.basic"))
				{
					sender.sendMessage(ChatColor.RED+"/donate checkvc [username]");
					sender.sendMessage(ChatColor.RED+"/donate ranklist");
					sender.sendMessage(ChatColor.RED+"/donate dbconnect    WARNING. ONLY USE THIS IF YOU ARE HAVING TROUBLES CONNECTING TO THE DATABASE. THIS COMMAND SHOULD NEVER BE NEEDED");
				}
				if(sender.hasPermission("donexpress.admin.*"))
				{
					sender.sendMessage(ChatColor.RED+"/donate addrank [rankName]");
					sender.sendMessage(ChatColor.RED+"/donate delrank [rankName]");
					sender.sendMessage(ChatColor.RED+"/donate addvc [userName]");
				}
				return true;
			}
			return false;
			}catch(ArrayIndexOutOfBoundsException e)
			{
				sender.sendMessage(ChatColor.RED+"Correct command usage");
				sender.sendMessage(ChatColor.RED+"/donate buy [rank]");
				sender.sendMessage(ChatColor.RED+"/donate confirm");
				sender.sendMessage(ChatColor.RED+"/donate check");
				if(sender.hasPermission("donexpress.admin.basic"))
				{
					sender.sendMessage(ChatColor.RED+"/donate checkvc [username]");
					sender.sendMessage(ChatColor.RED+"/donate ranklist");
					sender.sendMessage(ChatColor.RED+"/donate dbconnect    WARNING. ONLY USE THIS IF YOU ARE HAVING TROUBLES CONNECTING TO THE DATABASE. THIS COMMAND SHOULD NEVER BE NEEDED");
				}
				if(sender.hasPermission("donexpress.admin.*"))
				{
					sender.sendMessage(ChatColor.RED+"/donate addrank [rankName]");
					sender.sendMessage(ChatColor.RED+"/donate delrank [rankName]");
					sender.sendMessage(ChatColor.RED+"/donate addvc [userName]");
				}
			}
		}
		else
		{
			sender.sendMessage(ChatColor.RED+"Correct command usage");
			sender.sendMessage(ChatColor.RED+"/donate buy [rank]");
			sender.sendMessage(ChatColor.RED+"/donate confirm");
			sender.sendMessage(ChatColor.RED+"/donate check");
			if(sender.hasPermission("donexpress.admin.basic"))
			{
				sender.sendMessage(ChatColor.RED+"/donate checkvc [username]");
				sender.sendMessage(ChatColor.RED+"/donate ranklist");
				sender.sendMessage(ChatColor.RED+"/donate dbconnect    WARNING. ONLY USE THIS IF YOU ARE HAVING TROUBLES CONNECTING TO THE DATABASE. THIS COMMAND SHOULD NEVER BE NEEDED");
			}
			if(sender.hasPermission("donexpress.admin.*"))
			{
				sender.sendMessage(ChatColor.RED+"/donate addrank [rankName]");
				sender.sendMessage(ChatColor.RED+"/donate delrank [rankName]");
				sender.sendMessage(ChatColor.RED+"/donate addvc [userName]");
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
