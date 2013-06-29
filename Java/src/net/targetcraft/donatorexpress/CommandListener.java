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

public class CommandListener implements Listener, CommandExecutor {

	static Main plugin;

	public CommandListener(Main config) {
		plugin = config;
	}

	Connection con;
	HashMap<String, Boolean> confirm = new HashMap<String, Boolean>();
	HashMap<String, Integer> rankIntMap = new HashMap<String, Integer>();
	HashMap<String, Integer> tokensIntMap = new HashMap<String, Integer>();
	HashMap<String, String> rankString = new HashMap<String, String>();

	public void connectToDatabase() {
		String dbUsername = plugin.getConfig().getString("db-username");
		String dbPassword = plugin.getConfig().getString("db-password");
		String dbHost = plugin.getConfig().getString("db-host");
		String dbName = plugin.getConfig().getString("db-name");
		String dbURL = "jdbc:mysql://" + dbHost + "/" + dbName;
		try {
			con = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
		} catch (SQLException e) {
			Logger.getLogger(ChatColor.RED
					+ "[DonatorExpress] Error. All has failed. The database is most likely dead. Or it is having problems. Give it a moment, and try again");
		}
	}

	public void closeDatabase() {
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
			if(args[0].equalsIgnoreCase("add"))
			{
				if(sender.hasPermission("donexpress.admin.add"))
				{
				if(!(args[1]==null))
				{
					try{
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
				    	 sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.DARK_RED+"Error. Package already exists");
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
				
				} catch (ArrayIndexOutOfBoundsException e)
				{
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] Error. Invalid syntax. Type "+ChatColor.GREEN+"/donate help for commands");
				}
				}
				else
				{
					noPermission(sender);
				}
				}
			}
		
			else if (args[0].equalsIgnoreCase("delete"))
			{
				if(sender.hasPermission("donexpress.admin.delete"))
				{
				if(!(args[1]==null))
				{
					try {
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
				    	 sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.DARK_RED+"Error. Package is not currently in the config.");
				     }
				     else
				     {
				    	 ranks.remove(args[1].toLowerCase());
					     plugin.getConfig().set("ranks".toLowerCase(),ranks);
						 plugin.saveConfig(); 
						 sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.GREEN+"Successfully removed "+args[1]);
				     }	
				
				}catch (ArrayIndexOutOfBoundsException e)
				{
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] Error. Invalid syntax. Type "+ChatColor.GREEN+"/donate help "+ChatColor.RED+"for commands");
				}
				} 
				else
				{
					noPermission(sender);
				}
				}
			}
			else if(args[0].equalsIgnoreCase("list"))
			{
				if(sender.hasPermission("donexpress.user"))
				{
				List<String>ranks = plugin.getConfig().getStringList("ranks".toLowerCase());
				sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.AQUA+"Current list of packages:");
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
				statement.close();
			} catch (SQLException e) {
				connectToDatabase();
				sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Error. Could not connect to database. Attempting to reconnect. Try your command again");
				e.printStackTrace();
			} catch (ArrayIndexOutOfBoundsException e)
			{
				sender.sendMessage(ChatColor.RED+"[DonatorExpress] Error. Invalid syntax. Type "+ChatColor.GREEN+"/donate help "+ChatColor.RED+"for commands");
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
					} catch (SQLException e) {
						connectToDatabase();
						sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Error. Could not connect to database. Attempting to reconnect. Try your command again");
						e.printStackTrace();
					} catch (ArrayIndexOutOfBoundsException e)
					{
						sender.sendMessage(ChatColor.RED+"[DonatorExpress] Error. Invalid syntax. Type "+ChatColor.GREEN+"/donate help "+ChatColor.RED+"for commands");
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
				} catch (ArrayIndexOutOfBoundsException e)
				{
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] Error. Invalid syntax. Type "+ChatColor.GREEN+"/donate help "+ChatColor.RED+"for commands");
				}
				}
				else
				{
					
				}
			}
			else if(args[0].equalsIgnoreCase("setvc"))
			{
				if(sender.hasPermission("donexpress.admin.setvc"))
				{
					String website=plugin.getConfig().getString("portal-location");
					String VCName=plugin.getConfig().getString("currency-name");
					Statement statement=null;
					try {
						statement=con.createStatement();
						String username="'"+args[1]+"'";
						ResultSet result1=statement.executeQuery("SELECT `tokens`, `username` FROM dep  WHERE username = "+username);
						if(result1.next())
						{
							sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Attempting to set "+args[1]+"'s "+VCName+" with "+args[2]+" "+VCName);
							String tokens="'"+args[2]+"'";
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
							sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"I could not find that username in the database. Please tell the player to register at "+website);
						}
						statement.close();
					} catch (SQLException e)
					{
						connectToDatabase();
						sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Error. Could not connect to database. Attempting to reconnect. Try your command again");
						e.printStackTrace();					
					} catch (ArrayIndexOutOfBoundsException e)
					{
						sender.sendMessage(ChatColor.RED+"[DonatorExpress] Error. Correct command usage is "+ChatColor.GREEN+"/donate editvc [username] [amount]");
					}
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
				        
				        String datRankName=args[1].toLowerCase();
						int count = 0;
					    if(rankCopy.contains(datRankName))
					    {
					        rankCopy.remove(datRankName);
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
									int rankInt=Integer.parseInt(plugin.getConfig().getString(datRankName));//Gets the amount of tokens needed for the specific rank
									int tokensInt=Integer.parseInt(tokens);//Gets the amount of tokens that a user currently has
									if(!(rankInt>=tokensInt))
									{
										String rankSend=args[1];
										sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"This item costs "+rankInt+" "+VCName+". Type "+ChatColor.GREEN+"/donate confirm "+ChatColor.YELLOW+"if you wish to buy this item. To cancel this purchase, type "+ChatColor.GREEN+"/donate cancel");
										
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
							} catch (ArrayIndexOutOfBoundsException e)
							{
								sender.sendMessage(ChatColor.RED+"[DonatorExpress] Error. Invalid syntax. Type "+ChatColor.GREEN+"/donate help "+ChatColor.RED+"for commands");
							}
						}
					    else
					    {
					    	 sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"I could not find that package. Please try again.");
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
					        String datRankName=args[1].toLowerCase();
					        
							int count = 0;
						    if(rankCopy.contains(datRankName))
						    {
						        rankCopy.remove(datRankName);
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
									int rankInt1=Integer.parseInt(plugin.getConfig().getString(datRankName));//Gets the amount of tokens needed for the specific rank
									int tokensInt=Integer.parseInt(tokens);//Gets the amount of tokens that a user currently has
									int rankAlreadyCheck=rankInt1-rankAlreadyInt;
									if(rankAlreadyCheck>0)
									{
									if(!(rankInt1>=tokensInt))
									{
										String rankSend=args[1];
										sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Type "+ChatColor.GREEN+"/donate confirm "+ChatColor.YELLOW+"to confirm that you wish to buy this rank. Type "+ChatColor.GREEN+"/donate cancel "+ChatColor.YELLOW+"to cancel the purchase");
									    
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
										sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"You are trying to get a package lower than the one that you have already. Or you are tying to get one that you already have. Try again");
									}
								}
						      }
						else
						{
							sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"You need to buy a package before you can upgrade!");
						}
				
					}

					}
					else
					{
						sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"This command can only be run by a player");
					}
					statement.close();
				    }catch(SQLException e)
				{
					connectToDatabase();
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Error. Could not connect to database. Attempting to reconnect. Try your command again");
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e)
				{
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] Error. Invalid syntax. Type "+ChatColor.GREEN+"/donate help "+ChatColor.RED+"for commands");
				}
				}
				else
				{
					noPermission(sender);
				}
			}
			else if(args[0].equalsIgnoreCase("confirm"))
			{
				String VCName=plugin.getConfig().getString("currency-name");
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
						statement.execute("CREATE TABLE IF NOT EXISTS `packages_purchased` (`id` int NOT NULL AUTO_INCREMENT, `username` varchar(16) NOT NULL, `tokens` varchar(16) NOT NULL, `rank` varchar(16) NOT NULL, `date` varchar(64) NOT NULL, PRIMARY KEY (id))");
						
						int rankInt=0;
						rankInt=rankIntMap.get("rankInt");
						
						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
						Calendar cal = Calendar.getInstance();
						String date=dateFormat.format(cal.getTime());
						
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), s.replace("%player", sender.getName()));
						
						statement.execute("INSERT INTO packages_purchased (username, tokens, rank, date) VALUES ('"+sender.getName()+"', '"+rankInt+"', '"+rank+"', '"+date+"')");
						sendMessage=true;
					}
					if(sendMessage==true)
					{
						String username="'"+sender.getName()+"'";						
						int tokensInt=0;
						int rankInt=0;
						tokensInt=tokensIntMap.get("tokensInt");
						rankInt=rankIntMap.get("rankInt");
						String rankIntString=rankIntMap.get("rankInt").toString();
						int finalTokens=tokensInt-rankInt;
						statement.executeUpdate("UPDATE dep SET tokens='"+finalTokens+"' where username='"+sender.getName()+"'");
						
						ResultSet result=statement.executeQuery("SELECT `tokens`, `username` FROM dep  WHERE username = "+username);
						if(result.next())
						{
							sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.AQUA+"You now have: "+result.getString(1)+" "+VCName);
						}
						
						String donateMessage=plugin.getConfig().getString("donate-message");
						donateMessage=donateMessage.replace("%player", sender.getName());
						donateMessage=donateMessage.replace("%package", rank);
						donateMessage=donateMessage.replace("%currency", VCName);
						donateMessage=donateMessage.replace("%amount", rankIntString);
						//Bukkit.broadcastMessage(colourize(plugin.getConfig().getString("donate-message").replace("%player", sender.getName())));
						Bukkit.broadcastMessage(colourize(donateMessage));
						tokensIntMap.clear();
						rankIntMap.clear();
						rankString.clear();
						confirm.clear();
					}
				}
				
				}catch (SQLException e)
				{
					connectToDatabase();
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Error. Could not connect to database. Attempting to reconnect. Try your command again");
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e)
				{
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] Error. Invalid syntax. Type "+ChatColor.GREEN+"/donate help "+ChatColor.RED+"for commands");
				} catch (NullPointerException e)
				{
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] You have to purchase a package first.");
				}
				}
			}
			}
			else if(args[0].equalsIgnoreCase("edit"))
			{
				String VCName=plugin.getConfig().getString("currency-name");
				if(sender.hasPermission("donexpress.admin.edit"))
				{
					try {
					 sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Attempting to replace "+args[1]+"'s amount of "+VCName+" with "+args[2]);
					 List<String>ranks = plugin.getConfig().getStringList("ranks");			
					 List<String>ranks2=new ArrayList<String>(ranks);
					 
					 String datRankName=args[1].toLowerCase();
					 int count = 0;
				     if(ranks2.contains(datRankName))
				     {
				         ranks2.remove(datRankName);
				         count++;
				     }
				     if(count==1)
				     {
				    	 plugin.getConfig().set(datRankName, args[2]);
				    	 boolean allClear=true;
				    	 try{
				    	 @SuppressWarnings("unused")
						int checkForWords=Integer.parseInt(plugin.getConfig().getString(datRankName));
				    	 }catch(NumberFormatException e)
				    	 {
				    		allClear=false; 
				    	 }
				    	 if(allClear==true)
				    	 {
				    		 plugin.saveConfig();
							 sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.GREEN+"Successful");
				    	 }
				    	 else
				    	 {
				    		 sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"You can't have decimals or letters as virtual currency. You fool. You almost broke the plugin.");
				    	 }
						 
				     }
				     else
				     {
				    	 sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.DARK_RED+"Error. Existing rank could not be found");
				     }	
				}catch (ArrayIndexOutOfBoundsException e)
				{
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] Error. Invalid syntax. Type "+ChatColor.GREEN+"/donate help "+ChatColor.RED+"for commands");
				}
				} 
				else
				{
					noPermission(sender);
				}
			}
			else if(args[0].equalsIgnoreCase("cancel"))
			{
				if(sender.hasPermission("donexpress.user"))
				{
					if(sender instanceof Player)
					{
					sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Canceling the purchase...");
					if(confirm.get("sender"))
					{
						confirm.clear();
					    rankIntMap.clear();
					    tokensIntMap.clear();
					    rankString.clear();
					}
					else
					{
						sender.sendMessage(ChatColor.RED+"[DonatorExpress] Error. You have not started a purchase.");
					}
					}
					else
					{
						sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Error. This can only be ran by a player");
					}
				}
				else
				{
					noPermission(sender);
				}
				
			}
			else if(args[0].equalsIgnoreCase("recent"))
			{
				if(sender.hasPermission("donexpress.admin.recent"))
				{
					String VCName=plugin.getConfig().getString("currency-name");
					try {
						Statement statement=null;
						statement=con.createStatement();	
						ResultSet result=statement.executeQuery("SELECT * FROM transactions ORDER BY `id` DESC LIMIT 5");
						sender.sendMessage(ChatColor.RED+"[DonatorExpress] "+ChatColor.YELLOW+"Last 5 transactions");
						while(result.next())
						{
							sender.sendMessage(ChatColor.GOLD+"*********************************");
							sender.sendMessage(ChatColor.GOLD+"ID: "+result.getString(1));
							sender.sendMessage(ChatColor.GOLD+"Email: "+result.getString(2));
							sender.sendMessage(ChatColor.GOLD+VCName+"'s purchased: "+result.getString(3));
							sender.sendMessage(ChatColor.GOLD+"Amount paid: "+result.getString(4));
							sender.sendMessage(ChatColor.GOLD+"Name: "+result.getString(5)+" "+result.getString(6));
							sender.sendMessage(ChatColor.GOLD+"Email: "+result.getString(7));
							sender.sendMessage(ChatColor.GOLD+"Date: "+result.getString(8));
							sender.sendMessage(ChatColor.GOLD+"*********************************");
							sender.sendMessage(ChatColor.GOLD+"");
						}
						statement.close();
					} catch (SQLException e) {
						connectToDatabase();
						e.printStackTrace();
					} catch (ArrayIndexOutOfBoundsException e)
					{
						sender.sendMessage(ChatColor.RED+"[DonatorExpress] Error. Invalid syntax. Type /donate help for commands");
					}
				}
			}
			else if(args[0].equalsIgnoreCase("reload"))
			{
				if(sender.hasPermission("donexpress.admin.reload"))
				{
				plugin.saveConfig();
				plugin.reloadConfig();
				sender.sendMessage(ChatColor.GREEN+"[DonatorExpress] Reload successful!");
				}
				else
				{
					noPermission(sender);
				}
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
				commandUsage(sender);
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
				commandUsage(sender);
				return true;
			}
		}catch(ArrayIndexOutOfBoundsException e)
		{
			commandUsage(sender);
		}
		}
		return true;
	}

	public String colourize(String message) {
		return message.replaceAll("&([l-o0-9a-f])", "\u00A7$1");
	}

	public void error(CommandSender sender) {
		sender.sendMessage(ChatColor.RED
				+ "[DonatorExpress] "
				+ ChatColor.YELLOW
				+ "I did not recognize the command you entered or you did not enter in the correct arguments for the command");
		sender.sendMessage(ChatColor.RED+"[DonatorExpress] Error. Invalid syntax. Type "+ChatColor.GREEN+"/donate help "+ChatColor.RED+"for commands");
	}

	public void commandUsage(CommandSender sender) {
		sender.sendMessage(ChatColor.GOLD + "Correct command usage");
		sender.sendMessage(ChatColor.GOLD + "/donate buy [package]");
		sender.sendMessage(ChatColor.GOLD + "/donate confirm");
		sender.sendMessage(ChatColor.GOLD + "/donate cancel");
		sender.sendMessage(ChatColor.GOLD + "/donate check");
		sender.sendMessage(ChatColor.GOLD + "/donate list");
		if (sender.hasPermission("donexpress.user.upgrade")) {
			sender.sendMessage(ChatColor.GOLD + "/donate upgrade [package]");
		}
		if (sender.hasPermission("donexpress.admin.checkvc")) {
			sender.sendMessage(ChatColor.GOLD + "/donate checkvc [username]");
		}
		if (sender.hasPermission("donexpress.admin.edit")) {
			sender.sendMessage(ChatColor.GOLD
					+ "/donate edit [package] [amount]");
		}
		if (sender.hasPermission("donexpress.admin.add")) {
			sender.sendMessage(ChatColor.GOLD + "/donate add [package]");
		}
		if (sender.hasPermission("donexpress.admin.delete")) {
			sender.sendMessage(ChatColor.GOLD + "/donate delete [package]");
		}
		if (sender.hasPermission("donexpress.admin.addvc")) {
			sender.sendMessage(ChatColor.GOLD
					+ "/donate addvc [username] [amount]");
		}
		if (sender.hasPermission("donexpress.admin.editvc")) {
			sender.sendMessage(ChatColor.GOLD
					+ "/donate editvc [username] [amount]");
		}
		if (sender.hasPermission("donexpress.admin.reload")) {
			sender.sendMessage(ChatColor.GOLD + "/donate reload");
		}
	}

	public void noPermission(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "[DonatorExpress] "
				+ ChatColor.YELLOW
				+ "Error. You do not have permission to do that!");
	}

}
