package net.targetcraft.donatorexpress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
	HashMap<String, String> confirmDel = new HashMap<String, String>();
	HashMap<String, List<String>> confirmDel2 = new HashMap<String, List<String>>();
	HashMap<String, Boolean> confirmDelBoolean = new HashMap<String, Boolean>();
	
	//File language = new File(plugin.getDataFolder()+"/languages", plugin.getConfig().getString("language"));
	//File english = new File(plugin.getDataFolder()+"/languages", "en.yml");
	//FileConfiguration languageConfig=null;
	
	String ADDATTEMPTING=null;
	String ALREADYEXISTS=null;
	String CANCEL1= null;
	String CANCEL2 = null;
	String CANCEL3=null;
    String CANCEL4=null;
    String CANCEL5=null;
    String CANCEL6=null;
    String CANCEL7=null;
    String CANCEL8=null;
    String COMMANDUSAGE=null;
    String CURRENTLISTPACKAGES1=null;
    String CURRENTLISTPACKAGES2=null;
    String DBERROR=null;
    

	public void connectToDatabase() {		
		/**
		languageConfig=new YamlConfiguration();
		try {
			languageConfig.load(language);
		} catch (FileNotFoundException e1) {
			try {
				Logger.getLogger("").warning("[DonatorExpress] Could not find the language specified in the config. Using English");
				languageConfig.load(english);
			} catch (IOException
					| InvalidConfigurationException e) {
				e.printStackTrace();
			}
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InvalidConfigurationException e1) {
			e1.printStackTrace();
		}
		*/
		String dbUsername = plugin.getConfig().getString("db-username");
		String dbPassword = plugin.getConfig().getString("db-password");
		String dbHost = plugin.getConfig().getString("db-host");
		String dbName = plugin.getConfig().getString("db-name");
		String dbURL = "jdbc:mysql://" + dbHost + "/" + dbName;
		try {
			con = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
		} catch (SQLException e) {
			Logger.getLogger(plugin.getDataFolder()+"log.log").log(Level.SEVERE, "Error while trying to connect to the database");			
			e.printStackTrace();
			Logger.getLogger(plugin.getDataFolder()+"log.log").log(Level.SEVERE, "Error while trying to connect to the database.");
		    
			if(plugin.getConfig().getBoolean("disable-on-database-error"))
			{
				plugin.getPluginLoader().disablePlugin(plugin);
				Logger.getLogger(plugin.getDataFolder()+"log.log").log(Level.SEVERE, "DonatorExpress disabled. Reload server to re enable");
			}

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
					File packages = new File(plugin.getDataFolder()+File.separator, "packages.yml");
					FileConfiguration packagesConfig=null;
					packagesConfig=new YamlConfiguration();
					packagesConfig.load(packages);
					
				    boolean configAdd=false;
				    boolean rename=false;
				    
				    try{
					sender.sendMessage(prefix()+ChatColor.YELLOW+"Attempting to add "+args[1]+"...");
					
					List<String>ranks = packagesConfig.getStringList("packages");
					List<String>ranks2=new ArrayList<String>(ranks);
					 
					 int count = 0;
				     if(ranks2.contains(args[1]))
				     {
				         ranks2.remove(args[1]);
				         count++;
				     }
				     if(count==1)
				     {
				    	 sender.sendMessage(prefix()+ChatColor.DARK_RED+"Error. Package already exists");
				     }
				     else
				     {
				    	 ranks.add(args[1].toLowerCase());
				    	 packagesConfig.set("packages", ranks);
				    	 
				    	 File newFile = new File(plugin.getDataFolder()+"/packages"+File.separator ,args[1].toLowerCase()+".yml");
				    	 File newFileLocation = new File(plugin.getDataFolder()+"/packages"+File.separator);
				    	 
				    	 if(!newFileLocation.exists())
				    	 {
				    		 newFileLocation.mkdirs();
				    	 }
				    	 if(!newFile.exists())
				    	 {
				    		 
						     OutputStream out = null;
							 InputStream defaultStream = plugin.getResource("[defaultPackageConfiguration].yml");
							 File defaultPackage = new File(plugin.getDataFolder()+"/packages"+File.separator, "[defaultPackageConfiguration].yml");
							 

					         try {
					            	out = new FileOutputStream(defaultPackage);
					                int read = 0;
					                byte[] bytes = new byte[1024];

									while((read = defaultStream.read(bytes)) != -1) {
									    out.write(bytes, 0, read);
									}
								    out.close();
								    
									if(defaultPackage.renameTo(newFile))
									{
										rename=true;
									}
									else
									{
										rename=false;
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
						     
						     configAdd=true;
				    	 }
				    	 else
				    	 {
				    		 sender.sendMessage(prefix()+"Error. File already exists");
				    	 }  
				     }
				     if(configAdd||rename)
				     {
				    	 
						 packagesConfig.save(packages);
						 
				    	 sender.sendMessage(prefix()+ChatColor.GREEN+"Successfully added "+args[1]);
				     }
				     else
				     {
				    	 sender.sendMessage(prefix()+ChatColor.DARK_RED+"Major error. If the plugin created a file called, [defaultPackageConfiguration].yml NOTIFY THE DEVELOPER RIGHT AWAY");
				    	 sender.sendMessage(prefix()+ChatColor.RED+"Otherwise, delete "+args[1]+" in packages.yml, and try again");
				     }
				     				
				} catch (ArrayIndexOutOfBoundsException e)
				{
					configAdd=false;
					sender.sendMessage(prefix()+"Error. Invalid syntax. Type "+ChatColor.GREEN+"/donate help for commands");
				} catch (IOException e) {
					sender.sendMessage(prefix()+ChatColor.DARK_RED+"Error. forumConfig doesn't exist o.O Try to reload the server and try again");
					e.printStackTrace();
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
						File packages = new File(plugin.getDataFolder()+File.separator, "packages.yml");
						FileConfiguration packagesConfig=null;
						packagesConfig=new YamlConfiguration();
						packagesConfig.load(packages);
						
						File newFile = new File(plugin.getDataFolder()+"/packages"+File.separator ,args[1].toLowerCase()+".yml");				    	
						FileConfiguration newFileConfig=null;
			    		newFileConfig=new YamlConfiguration();
			    		newFileConfig.load(newFile);
						
						sender.sendMessage(prefix()+ChatColor.YELLOW+"Attempting to remove "+args[1]+"...");
						List<String>ranks = packagesConfig.getStringList("packages".toLowerCase());
						List<String>ranks2=new ArrayList<String>(ranks);
					 
						int count = 0;
						if(!ranks2.contains(args[1]))
					     {
					         count++;
					     }
						if(count==1)
						{
							sender.sendMessage(prefix()+ChatColor.DARK_RED+"Error. Package is not currently in the packages.yml file.");
				    	}
						else
							{
							ranks.remove(args[1].toLowerCase());
							confirmDel.put("confirm", args[1]);
							confirmDel2.put("confirm2", ranks);
							confirmDelBoolean.put("confirm", true);
						 
							sender.sendMessage(prefix()+"To confirm the deletion of this package, type "+ChatColor.GREEN+"/donate confirmdel");
							sender.sendMessage(ChatColor.RED+"This can not be undone");
							sender.sendMessage(ChatColor.RED+"Or type "+ChatColor.GREEN+"/donate cancel"+ChatColor.RED+" to cancel");
							}	
				
				}catch (ArrayIndexOutOfBoundsException e)
				{
					sender.sendMessage(prefix()+"Error. Invalid syntax. Type "+ChatColor.GREEN+"/donate help "+ChatColor.RED+"for commands");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InvalidConfigurationException e) {
					e.printStackTrace();
				}
				} 
				else
				{
					noPermission(sender);
				}
				}
			}
			else if(args[0].equalsIgnoreCase("confirmdel"))
			{
				if(sender.hasPermission("donexpress.admin.del"))
				{
					String packageName=confirmDel.get("confirm");
					List<String> stuffToRemove=confirmDel2.get("confirm2");
					
					File packages = new File(plugin.getDataFolder()+File.separator, "packages.yml");
					FileConfiguration packagesConfig=null;
					packagesConfig=new YamlConfiguration();
					packagesConfig.load(packages);
					
					File newFile = new File(plugin.getDataFolder()+"/packages"+File.separator ,packageName.toLowerCase()+".yml");
			    	FileConfiguration newFileConfig=null;
		    		newFileConfig=new YamlConfiguration();
		    		newFileConfig.load(newFile);
		    		
		    		if(confirmDelBoolean.get("confirm"))
		    		{
		    			packagesConfig.set("packages", stuffToRemove);
			    		newFile.delete();
			    		
			    		packagesConfig.save(packages);
			    		
			    		sender.sendMessage(prefix()+"Successfully removed "+packageName);
			    		confirmDel.clear();
			    		confirmDel2.clear();
			    		confirmDelBoolean.clear();
		    		}
		    		else
		    		{
		    			sender.sendMessage(prefix()+"Error. You have not requested for a package to be deleted");
		    		}
		    		
				}
			}
			else if(args[0].equalsIgnoreCase("list"))
			{
				if(sender.hasPermission("donexpress.user"))
				{
					File packages = new File(plugin.getDataFolder()+File.separator, "packages.yml");
					FileConfiguration packagesConfig=null;
					packagesConfig=new YamlConfiguration();
					packagesConfig.load(packages);
					
					List<String>ranks = packagesConfig.getStringList("packages".toLowerCase());
					sender.sendMessage(prefix()+ChatColor.AQUA+"Current list of packages:");
					sender.sendMessage(prefix()+ChatColor.AQUA+"To view a description of each package, type /donate info [packageName]");
					for(String s:ranks)
					{
						sender.sendMessage(ChatColor.AQUA+s);
					}
				}
				else
				{
					noPermission(sender);
				}
			}
			else if(args[0].equalsIgnoreCase("info"))
			{
				if(sender.hasPermission("donexpress.user"))
				{
					File packages = new File(plugin.getDataFolder()+File.separator, "packages.yml");
					FileConfiguration packagesConfig=null;
					packagesConfig=new YamlConfiguration();
					packagesConfig.load(packages);
					
					File newFile = new File(plugin.getDataFolder()+"/packages"+File.separator ,args[1].toLowerCase()+".yml");
					FileConfiguration newFileConfig=null;
		    		newFileConfig=new YamlConfiguration();
		    		newFileConfig.load(newFile);
		    		
					List<String>rank = packagesConfig.getStringList("packages");
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
				    	List<String> description=newFileConfig.getStringList("description");
				    	for(String s:description)
				    	{
				    		sender.sendMessage(colourize(s));
				    	}
				    }
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
								sender.sendMessage(prefix()+ChatColor.AQUA+"You currently have: "+result.getString(1)+" "+VCName);
							}
							else
							{
								sender.sendMessage(prefix()+ChatColor.YELLOW+"You do not have a DonatorExpress account. Please visit "+website+" to register.");
							}
							statement.close();
						} catch (SQLException e) {
							connectToDatabase();
							sender.sendMessage(prefix()+ChatColor.YELLOW+"The database returned an error. Please tell an Admin or Owner about this problem so they can investigate further");
							e.printStackTrace();
						} catch (ArrayIndexOutOfBoundsException e)
						{
							sender.sendMessage(prefix()+"[DonatorExpress] Error. Invalid syntax. Type "+ChatColor.GREEN+"/donate help "+ChatColor.RED+"for commands");
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
							sender.sendMessage(prefix()+ChatColor.AQUA+args[1]+" currently has "+result.getString(1)+" "+VCName);
						}
						else
						{
							sender.sendMessage(prefix()+ChatColor.YELLOW+"I could not find that username in the database. Please tell the player to register at "+website);
						}
					} catch (SQLException e) {
						connectToDatabase();
						sender.sendMessage(prefix()+ChatColor.YELLOW+"The database returned an error. Please tell an Admin or Owner about this problem so they can investigate further");
						e.printStackTrace();
					} catch (ArrayIndexOutOfBoundsException e)
					{
						sender.sendMessage(prefix()+"Error. Invalid syntax. Type "+ChatColor.GREEN+"/donate help "+ChatColor.RED+"for commands");
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
			else if(args[0].equalsIgnoreCase("checkp"))
			{
				if(sender.hasPermission("donexpress.admin.checkp"))
				{
					Statement statement=null;
					try {
						statement=con.createStatement();
						String username="'"+args[1]+"'";
						ResultSet result=statement.executeQuery("SELECT `rank`, `date` FROM packages_purchased  WHERE username = '"+username+"'");
						sender.sendMessage(prefix()+args[1]+"'s package buys:");
						while(result.next())
						{
							sender.sendMessage(ChatColor.RED+"Package: "+result.getString(1));
							sender.sendMessage(ChatColor.RED+"Date: "+result.getString(2));
						}
					} catch (SQLException e) {
						sender.sendMessage(prefix()+ChatColor.YELLOW+"The database returned an error. Please tell an Admin or Owner about this problem so they can investigate further");						e.printStackTrace();
					}
					
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
						sender.sendMessage(prefix()+ChatColor.YELLOW+"I could not find that username in the database. Please tell the player to register at "+website);
					}
					int tokensToAdd=Integer.parseInt(args[2]);
					int tokensFinal=currentTokens+tokensToAdd;
					sender.sendMessage(prefix()+ChatColor.YELLOW+"Attempting to give "+args[1]+" "+args[2]+" "+VCName);
					String tokens="'"+tokensFinal+"'";
					statement.executeUpdate("UPDATE dep SET tokens="+tokens+"where username="+username);
					
					ResultSet result=statement.executeQuery("SELECT `tokens`, `username` FROM dep  WHERE username = "+username);
					if(result.next())
					{
						sender.sendMessage(prefix()+ChatColor.GREEN+"Success!");
						sender.sendMessage(prefix()+ChatColor.AQUA+args[1]+" now has "+result.getString(1)+" "+VCName);
					}
					statement.close();
				} catch (SQLException e) {
					connectToDatabase();
					sender.sendMessage(prefix()+ChatColor.YELLOW+"The database returned an error. Please tell an Admin or Owner about this problem so they can investigate further");				} catch (ArrayIndexOutOfBoundsException e)
				{
					sender.sendMessage(prefix()+"Error. Invalid syntax. Type "+ChatColor.GREEN+"/donate help "+ChatColor.RED+"for commands");
				} catch (NullPointerException e)
				{
					sender.sendMessage(prefix()+"Error. config.yml is invalid. Check the database configuration and try again.");
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
							sender.sendMessage(prefix()+ChatColor.YELLOW+"Attempting to set "+args[1]+"'s "+VCName+" with "+args[2]+" "+VCName);
							String tokens="'"+args[2]+"'";
							statement.executeUpdate("UPDATE dep SET tokens="+tokens+"where username="+username);
							
							ResultSet result=statement.executeQuery("SELECT `tokens`, `username` FROM dep  WHERE username = "+username);
							if(result.next())
							{
								sender.sendMessage(prefix()+ChatColor.GREEN+"Success!");
								sender.sendMessage(prefix()+ChatColor.AQUA+args[1]+" now has "+result.getString(1)+" "+VCName);
							}
						}
						else
						{
							sender.sendMessage(prefix()+ChatColor.YELLOW+"I could not find that username in the database. Please tell the player to register at "+website);
						}
						statement.close();
					} catch (SQLException e)
					{
						connectToDatabase();
						sender.sendMessage(prefix()+ChatColor.YELLOW+"The database returned an error. Please tell an Admin or Owner about this problem so they can investigate further");					} catch (ArrayIndexOutOfBoundsException e)
					{
						sender.sendMessage(prefix()+"Error. Correct command usage is "+ChatColor.GREEN+"/donate editvc [username] [amount]");
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
						boolean continuePurchase=true;
						File packages = new File(plugin.getDataFolder()+File.separator, "packages.yml");
						FileConfiguration packagesConfig=null;
						packagesConfig=new YamlConfiguration();
						packagesConfig.load(packages);
						
						File newFile = new File(plugin.getDataFolder()+"/packages"+File.separator ,args[1].toLowerCase()+".yml");				    	
						FileConfiguration newFileConfig=null;
			    		newFileConfig=new YamlConfiguration();
			    		try
			    		{
			    			newFileConfig.load(newFile);
			    			
			    		}catch(FileNotFoundException e)
			    		{
			    			//Do nothing as it is handled down below. 
			    		}
			    		
			    		File userDataFolder = new File(plugin.getDataFolder()+"/userdata"+File.separator);
			    		File userData = new File(plugin.getDataFolder()+"/userdata"+File.separator, sender.getName().toString().toLowerCase()+".yml");
			    		FileConfiguration userDataConfig = null;
			    		userDataConfig = new YamlConfiguration();
			    		
			    		if(!userDataFolder.exists())
			    		{
			    			userDataFolder.mkdir();
			    		}
			    		if(newFileConfig.getBoolean("one-time-purchase"))
			    		{
			    			if(userData.exists())
			    			{
			    				userDataConfig.load(userData);
			    				List<String>upgradeCheck = userDataConfig.getStringList("one-time-purchases");
			    				for(String s:upgradeCheck)
			    				{
			    					if(s.equalsIgnoreCase(args[1].toLowerCase()))
			    					{
			    						continuePurchase=false;
			    					}
			    				}
			    				
			    			}
			    			else
			    			{
			    				continuePurchase = true;
			    			}
			    		}
			    		else
			    		{
			    			continuePurchase=true;
			    		}
			    		if(continuePurchase)
			    		{
			    			List<String>rank = packagesConfig.getStringList("packages");
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
										int rankInt=Integer.parseInt(newFileConfig.getString("price"));//Gets the amount of tokens needed for the specific rank
										int tokensInt=Integer.parseInt(tokens);//Gets the amount of tokens that a user currently has
										
										if(!(rankInt>=tokensInt+1))
										{
											String rankSend=args[1];
											sender.sendMessage(prefix()+ChatColor.YELLOW+"This item costs "+rankInt+" "+VCName+". Type "+ChatColor.GREEN+"/donate confirm "+ChatColor.YELLOW+"if you wish to buy this item. To cancel this purchase, type "+ChatColor.GREEN+"/donate cancel");
											
											confirm.put("sender", true);
											rankIntMap.put("rankInt", rankInt);
											tokensIntMap.put("tokensInt", tokensInt);
											rankString.put("rankName", rankSend);
										}
										else
										{
											sender.sendMessage(prefix()+ChatColor.YELLOW+"You do not have enough "+VCName+" to buy that rank!");
										}
										
									}
									else
									{
										sender.sendMessage(prefix()+ChatColor.RED+"Error. You have not registered at "+VCName+", or the Server Owner/Administrator has not correctly set up DonatorExpress");
									}
									statement.close();
								} catch (SQLException e) {
									connectToDatabase();
									sender.sendMessage(prefix()+ChatColor.YELLOW+"The database returned an error. Please tell an Admin or Owner about this problem so they can investigate further");								
									e.printStackTrace();
								} catch (NullPointerException e)
								{
									connectToDatabase();
									sender.sendMessage(prefix()+"Hm. I can't connect to the database. Your server owner may have incorrectly setup the config. Please let him/her know right now!");
									e.printStackTrace();
								} catch (ArrayIndexOutOfBoundsException e)
								{
									sender.sendMessage(prefix()+ChatColor.RED+"Error. Invalid syntax. Type "+ChatColor.GREEN+"/donate help "+ChatColor.RED+"for commands");
								}
							}
						    else
						    {
						    	 sender.sendMessage(prefix()+ChatColor.YELLOW+"I could not find that package. Please try again.");
						    }
			    		}
			    		else
			    		{
			    			sender.sendMessage(prefix()+ChatColor.RED+"Error. You have already purchased this package!");
			    		}
										    	 
					}
				}
				else
				{
					sender.sendMessage(prefix()+ChatColor.DARK_RED+"Error. This command can only be preformed by a player");
				}
				}
				else
				{
					noPermission(sender);
				}
				
			}
			else if(args[0].equalsIgnoreCase("upgrade"))
			{
				if(sender.hasPermission("donexpress.user"))
				{
					if(sender instanceof Player)
					{
						String VCName=plugin.getConfig().getString("currency-name");
						File packages = new File(plugin.getDataFolder()+File.separator, "packages.yml");
						FileConfiguration packagesConfig=null;
						packagesConfig=new YamlConfiguration();
						packagesConfig.load(packages);
			    		
			    		File userData = new File(plugin.getDataFolder()+"/userdata"+File.separator, sender.getName().toString()+".yml");
			    		FileConfiguration userDataConfig=null;
			    		userDataConfig=new YamlConfiguration();
			    		if(userData.exists())
			    		{
				    		userDataConfig.load(userData);
				    		String currentRank = userDataConfig.getString("current-package");
							
							File oldFile = new File(plugin.getDataFolder()+"/packages"+File.separator ,currentRank+".yml");				    	
							FileConfiguration oldFileConfig=null;
				    		oldFileConfig=new YamlConfiguration();
				    		oldFileConfig.load(oldFile);
				    		
				    		String nextRank = oldFileConfig.getString("next-package".toLowerCase());
				    		if(nextRank.equalsIgnoreCase("null"))
				    		{
				    			sender.sendMessage(prefix()+ChatColor.RED+"Error. You have not previously purchased an upgradable package or there is no more packages to upgrade to");
				    		}
				    		else
				    		{
				    			File newFile = new File(plugin.getDataFolder()+"/packages"+File.separator ,nextRank+".yml");				    	
								FileConfiguration newFileConfig=null;
					    		newFileConfig=new YamlConfiguration();
					    		newFileConfig.load(newFile);
					    		
					    		List<String>rank = packagesConfig.getStringList("packages");
						        List<String>rankCopy=new ArrayList<String>(rank);
						        
								int count = 0;
							    if(rankCopy.contains(nextRank))
							    {
							        rankCopy.remove(nextRank);
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
											int rankInt=Integer.parseInt(oldFileConfig.getString("next-package-price"));//Gets the amount of tokens needed for the specific rank
											int tokensInt=Integer.parseInt(tokens);//Gets the amount of tokens that a user currently has
											
											if(!(rankInt>=tokensInt+1))
											{
												String rankSend=oldFileConfig.getString("next-package");
												sender.sendMessage(prefix()+ChatColor.YELLOW+"You are upgrading to: "+rankSend+" which costs: "+rankInt+" "+VCName+". Type "+ChatColor.GREEN+"/donate confirm "+ChatColor.YELLOW+"if you wish to buy this item. To cancel this purchase, type "+ChatColor.GREEN+"/donate cancel");
												
												confirm.put("sender", true);
												rankIntMap.put("rankInt", rankInt);
												tokensIntMap.put("tokensInt", tokensInt);
												rankString.put("rankName", rankSend);
											}
											else
											{
												sender.sendMessage(prefix()+ChatColor.YELLOW+"You do not have enough "+VCName);
											}
											
										}
										else
										{
											sender.sendMessage(prefix()+ChatColor.RED+"Error. You have not registered at "+VCName+", or the Server Owner/Administrator has not correctly set up DonatorExpress");
										}
										statement.close();
									} catch (SQLException e) {
										connectToDatabase();
										sender.sendMessage(prefix()+ChatColor.YELLOW+"The database returned an error. Please tell an Admin or Owner about this problem so they can investigate further");								
										e.printStackTrace();
									} catch (NullPointerException e)
									{
										connectToDatabase();
										sender.sendMessage(prefix()+"Hm. I can't connect to the database. Your server owner may have incorrectly setup the config, or the database is having problems. Please let him/her know right now!");
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
			    			sender.sendMessage(prefix()+ChatColor.RED+"Error. You have not purchased a one time package yet! Ask an Admin/Owner about this problem");
			    		}

					}
					else
					{
						sender.sendMessage(prefix()+ChatColor.RED+"Error. You need to be a player to do this command");
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
				//TODO Add 10 seconds in next update. Or second one. Whichever I like xD
				if(sender instanceof Player)
				{
				try {
					
				Statement statement=null;
				statement=con.createStatement();
				if(confirm.get("sender"))
				{
					String rank=rankString.get("rankName");
					File newFile = new File(plugin.getDataFolder()+"/packages"+File.separator ,rank.toLowerCase()+".yml");
			    	FileConfiguration newFileConfig=null;
		    		newFileConfig=new YamlConfiguration();
		    		newFileConfig.load(newFile);
		    		
		    		File forum = new File(plugin.getDataFolder()+File.separator, "forumConfig.yml");
		    		FileConfiguration forumConfig=null;
		    		forumConfig=new YamlConfiguration();
		    		forumConfig.load(forum);

					List<String> rankCommand=newFileConfig.getStringList(("commands").replace("%player", sender.getName()));
					boolean sendMessage=false;
					
					statement.execute("CREATE TABLE IF NOT EXISTS `packages_purchased` (`id` int NOT NULL AUTO_INCREMENT, `username` varchar(24) NOT NULL, `tokens` varchar(16) NOT NULL, `rank` varchar(16) NOT NULL, `date` varchar(64) NOT NULL, PRIMARY KEY (id))");
					statement.execute("CREATE TABLE IF NOT EXISTS `expire_packages` (`id` int NOT NULL AUTO_INCREMENT, `username` varchar(24) NOT NULL, `package` varchar(50) NOT NULL, `date` varchar(64) NOT NULL, PRIMARY KEY (id))");
					
					int rankInt=0;
					rankInt=rankIntMap.get("rankInt");
					
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					Calendar cal = Calendar.getInstance();
					String date=dateFormat.format(cal.getTime());
					
					for(String s:rankCommand)
					{
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), s.replace("%player", sender.getName()));
						
						statement.execute("INSERT INTO packages_purchased (username, tokens, rank, date) VALUES ('"+sender.getName()+"', '"+rankInt+"', '"+rank+"', '"+date+"')");
						
						if(newFileConfig.getBoolean("expire")||newFileConfig.getString("expire").equals("true"))
						{
							statement.execute("INSERT INTO expire_packages (username, package, date) VALUES ('"+sender.getName()+"', '"+rank+"', '"+date+"')");
						}
						sendMessage=true;
					}
					if(sendMessage==true)
					{
						String username="'"+sender.getName()+"'";						
						int tokensInt=0;
						int rankInt2=0;
						tokensInt=tokensIntMap.get("tokensInt");
						rankInt2=rankIntMap.get("rankInt");
						String rankIntString=rankIntMap.get("rankInt").toString();
						int finalTokens=tokensInt-rankInt2;
						statement.executeUpdate("UPDATE dep SET tokens='"+finalTokens+"' where username='"+sender.getName()+"'");
						
						ResultSet result=statement.executeQuery("SELECT `tokens`, `username` FROM dep  WHERE username = "+username);
						if(result.next())
						{
							sender.sendMessage(prefix()+ChatColor.AQUA+"You now have: "+result.getString(1)+" "+VCName);
						}
						
						String donateMessage=plugin.getConfig().getString("donate-message");
						donateMessage=donateMessage.replace("%player", sender.getName());
						donateMessage=donateMessage.replace("%package", rank);
						donateMessage=donateMessage.replace("%currency", VCName);
						donateMessage=donateMessage.replace("%amount", rankIntString);
						Bukkit.broadcastMessage(colourize(donateMessage));
						
						if(forumConfig.getBoolean("enabled")||forumConfig.getString("enabled").equals("true"))
						{
							syncForum(sender, rank);
						}
						
						File userDataFolder = new File(plugin.getDataFolder()+"/userdata"+File.separator);
			    		File userData = new File(plugin.getDataFolder()+"/userdata"+File.separator, sender.getName().toString()+".yml");
			    		FileConfiguration userDataConfig = null;
			    		userDataConfig = new YamlConfiguration();
			    		
			    		if(!userDataFolder.exists())
			    		{
			    			userDataFolder.mkdir();
			    		}
			    		if(newFileConfig.getBoolean("one-time-purchase"))
			    		{
			    			if(!userData.exists())
			    			{
			    				userData.createNewFile();
			    				userDataConfig.load(userData);
			    				
			    				userDataConfig.createSection("current-package");
			    				userDataConfig.createSection("one-time-purchases");
			    				
			    				List<String>oneTimePurchase = userDataConfig.getStringList("one-time-purchase");
			    				oneTimePurchase.add(rank);
			    				
			    				userDataConfig.set("current-package", rank);
			    				userDataConfig.set("one-time-purchases", oneTimePurchase);
			    				
			    				userDataConfig.save(userData);
			    			}
			    			else
			    			{
			    				userDataConfig.load(userData);
			    				List<String>oneTimePurchase = userDataConfig.getStringList("one-time-purchases");
			    				oneTimePurchase.add(rank);
			    				
			    				userDataConfig.set("one-time-purchases", oneTimePurchase);
			    				userDataConfig.save(userData);
			    			}
			    		}
						
						tokensIntMap.clear();
						rankIntMap.clear();
						rankString.clear();
						confirm.clear();
					}
				}
				
				}catch (SQLException e)
				{
					connectToDatabase();
					sender.sendMessage(prefix()+ChatColor.YELLOW+"The database returned an error. Please tell an Admin or Owner about this problem so they can investigate further");					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e)
				{
					sender.sendMessage(prefix()+ChatColor.RED+"Error. Invalid syntax. Type "+ChatColor.GREEN+"/donate help "+ChatColor.RED+"for commands");
				} catch (NullPointerException e)
				{
					e.printStackTrace();
					sender.sendMessage(prefix()+ChatColor.RED+"You have to purchase a package first.");
				}
				}
			}
			}
			
			/**
			else if(args[0].equalsIgnoreCase("edit"))
			{
				String VCName=plugin.getConfig().getString("currency-name");
				if(sender.hasPermission("donexpress.admin.edit"))
				{
					try {
						File packages = new File(plugin.getDataFolder()+File.separator, "packages.yml");
						FileConfiguration packagesConfig=null;
						packagesConfig=new YamlConfiguration();
						packagesConfig.load(packages);
						
						File newFile = new File(plugin.getDataFolder()+"/packages"+File.separator ,args[1].toLowerCase()+".yml");			    	FileConfiguration newFileConfig=null;
			    		newFileConfig=new YamlConfiguration();
			    		newFileConfig.load(newFile);
			    		
					 sender.sendMessage(prefix()+ChatColor.YELLOW+"Attempting to replace "+args[1]+"'s amount of "+VCName+" with "+args[2]);
					 List<String>ranks = packagesConfig.getStringList("packages");			
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
				    	 int price=Integer.parseInt(args[2]);
				    	 newFileConfig.set("price", price);
				    	 boolean allClear=true;
				    	 try{
				    		 
				    	 @SuppressWarnings("unused")
						 int checkForWords=Integer.parseInt(newFileConfig.getString("price"));
				    	 }catch(NumberFormatException e)
				    	 {
				    		allClear=false; 
				    	 }
				    	 if(allClear==true)
				    	 {
				    		 newFileConfig.save(newFile);
				    		 
							 sender.sendMessage(prefix()+ChatColor.GREEN+"Successful");
				    	 }
				    	 else
				    	 {
				    		 sender.sendMessage(prefix()+ChatColor.YELLOW+"You can't have decimals or letters as a price. You fool. You almost broke the plugin.");
				    	 }
						 
				     }
				     else
				     {
				    	 sender.sendMessage(prefix()+ChatColor.DARK_RED+"Error. Existing package could not be found");
				     }	
				}catch (ArrayIndexOutOfBoundsException e)
				{
					sender.sendMessage(prefix()+ChatColor.RED+"Error. Invalid syntax. Type "+ChatColor.GREEN+"/donate help "+ChatColor.RED+"for commands");
				}
				} 
				else
				{
					noPermission(sender);
				}
			}
			*/
			else if(args[0].equalsIgnoreCase("cancel"))
			{
				if(sender.hasPermission("donexpress.user"))
				{
					sender.sendMessage(ChatColor.RED+prefix()+ChatColor.YELLOW+"Cancelling...");
					if(sender instanceof Player)
					{
						if(confirm.get("sender"))
						{
							confirm.clear();
						    rankIntMap.clear();
						    tokensIntMap.clear();
						    rankString.clear();
						}
						else
						{
							boolean no=true;
							if(confirmDelBoolean.get("confirm"))
							{
								confirmDel.clear();
							    confirmDel2.clear();
							    confirmDelBoolean.clear();
							    
							    sender.sendMessage(ChatColor.RED+prefix()+" Cancled the deletion of the package");
							    no=false;
							}
							else
							{
								sender.sendMessage(ChatColor.RED+prefix()+"Error. You have not requested for a package to be deleted");
							}
							if(no)
							{
								sender.sendMessage(ChatColor.RED+prefix()+"Error. You have not started a purchase a package");
							}
						}
					}
					else
					{
						boolean no=true;
						try
						{
							
						if(!confirmDelBoolean.get("confirm"))
						{
							sender.sendMessage(ChatColor.RED+prefix()+"Error. You have not requested for a package to be deleted");
							no=false;
						}
						else
						{
							confirmDel.clear();
						    confirmDel2.clear();
						    confirmDelBoolean.clear();
						    
						    sender.sendMessage(ChatColor.RED+prefix()+"Canceled the deletion of the package");
						    no=false;
						}
						
						}catch(NullPointerException e)
						{
							e.printStackTrace();
							no=false;
							sender.sendMessage(ChatColor.RED+prefix()+"Error. You have not requested for a package to be deleted");
						}

						if(no)
						{
							sender.sendMessage(ChatColor.RED+prefix()+"Error. You have not started a purchase a package");
						}
						
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
						ResultSet result=statement.executeQuery("SELECT * FROM packages_purchased ORDER BY `id` DESC LIMIT 5");
						sender.sendMessage(prefix()+ChatColor.YELLOW+"Last 5 transactions");
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
						sender.sendMessage(prefix()+ChatColor.RED+"Error. Invalid syntax. Type /donate help for commands");
					}
				}
			}
			else if(args[0].equalsIgnoreCase("reload"))
			{
				if(sender.hasPermission("donexpress.admin.reload"))
				{
				plugin.reloadConfig();
				sender.sendMessage(prefix()+ChatColor.GREEN+"Reload successful!");
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
				sender.sendMessage(ChatColor.AQUA+"DonatorExpress Version 1.5");
				sender.sendMessage(ChatColor.AQUA+"Plugin developed by: aman207");
				sender.sendMessage(ChatColor.AQUA+"Webportal developed by: AzroWear");
				sender.sendMessage(ChatColor.AQUA+"http://bit.ly/DonExp");
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
				else
				{
					sender.sendMessage(ChatColor.GOLD+"Lol do you want this plugin to implode on its self? How about you don't do that ;)");
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
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InvalidConfigurationException e1) {
			e1.printStackTrace();
		}
		}
		return true;
	}
	public void syncForum(CommandSender sender, String group)
	{
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
						Statement forumStatement=null;
						forumStatement=con.createStatement();
						ResultSet result=forumStatement.executeQuery("SELECT `email` FROM dep  WHERE username = '"+username+"'");
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
						Statement forumStatement=null;
						forumStatement=con.createStatement();
						ResultSet result=forumStatement.executeQuery("SELECT `email` FROM dep  WHERE username = '"+username+"'");
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
						Statement forumStatement=null;
						forumStatement=con.createStatement();
						ResultSet result=forumStatement.executeQuery("SELECT `email` FROM dep  WHERE username = '"+username+"'");
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
						Statement forumStatement=null;
						forumStatement=con.createStatement();
						ResultSet result=forumStatement.executeQuery("SELECT `email` FROM dep  WHERE username = '"+username+"'");
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
						Statement forumStatement=null;
						forumStatement=con.createStatement();
						ResultSet result=forumStatement.executeQuery("SELECT `email` FROM dep  WHERE username = '"+username+"'");
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
			sender.sendMessage(prefix()+ChatColor.RED+"Uh oh. I could not add you to a forum group. " +
					"This could be because the server owner has improperly configured DonatorExpress or because you have not signed up on the forums");
			sender.sendMessage(ChatColor.RED+"Please contact the server owner!");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		
	}

	public String colourize(String message) {
		return message.replaceAll("&([l-o0-9a-f])", "\u00A7$1");
	}
	
	public String prefix()
	{
		String prefix=plugin.getConfig().getString("prefix");
		return colourize(prefix);
	}

	public void commandUsage(CommandSender sender) {
		sender.sendMessage(ChatColor.GOLD + "Correct command usage");
		sender.sendMessage(ChatColor.GOLD + "/donate buy [package]");
		sender.sendMessage(ChatColor.GOLD + "/donate confirm");
		sender.sendMessage(ChatColor.GOLD + "/donate cancel");
		sender.sendMessage(ChatColor.GOLD + "/donate check");
		sender.sendMessage(ChatColor.GOLD + "/donate list");
		sender.sendMessage(ChatColor.GOLD + "/donate info [package]");
		sender.sendMessage(ChatColor.GOLD + "/donate upgrade");
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
			sender.sendMessage(ChatColor.GOLD + "/donate confirmdel");
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
		sender.sendMessage(prefix()+ChatColor.YELLOW
				+ "Error. You do not have permission to do that!");
	}
}
