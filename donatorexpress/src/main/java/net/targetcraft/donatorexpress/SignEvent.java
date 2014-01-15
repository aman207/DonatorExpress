package net.targetcraft.donatorexpress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

public class SignEvent implements Listener {
	
	static Main plugin;
	
	boolean economySetup;

	public SignEvent(Main config) {
		plugin = config;
		if(setupEconomy()==false)
		{
			economySetup = false;
		}
		else
		{
			economySetup = true;
		}
	}
	
	public static Economy econ = null;
	
	private boolean setupEconomy()
	{
		if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent e)
	{
		String tokensName = plugin.getConfig().getString("currency-name");
		if(e.getPlayer().hasPermission("donexpress.admin.signplace"))
		{
			File packages = new File(plugin.getDataFolder()+File.separator, "packages.yml");
			FileConfiguration packagesConfig=null;
			packagesConfig=new YamlConfiguration();
			try {
				packagesConfig.load(packages);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InvalidConfigurationException e1) {
				e1.printStackTrace();
			}
			
			List<String>ranks = packagesConfig.getStringList("packages");
			List<String>ranks2=new ArrayList<String>(ranks);
			
			if(e.getLine(0).equals("[DE]"))
			{	
				if(e.getLine(1).equalsIgnoreCase("buy"))
				{
					if(!e.getLine(2).equalsIgnoreCase(tokensName))
					{
						int count = 0;
					    if(ranks2.contains(e.getLine(2)))
					    {
					        ranks2.remove(e.getLine(2));
					        count++;
					    }
					    if(count==1)
					    {
							e.setLine(1, "Buy");
					    }
					    else
					    {
					    	e.setLine(1, ChatColor.RED+"Buy");
					    	e.getPlayer().sendMessage(prefix()+ChatColor.RED+Language.getPhrase("SIGN_PACKAGENOTEXIST1")+e.getLine(2)+Language.getPhrase("SIGN_PACKAGENOTEXIST2"));
					    }
					}
					else if(e.getLine(2).equalsIgnoreCase(tokensName))
					{
						String numberOfTokensString = e.getLine(3);
						try
						{
							@SuppressWarnings("unused")
							int numberOfTokens = Integer.parseInt(numberOfTokensString);
						}catch(NumberFormatException e1)
						{
							e.setCancelled(true);
							e.getPlayer().sendMessage(prefix()+ChatColor.RED+Language.getPhrase("SIGN_WHOLENUMBER1")+tokensName+Language.getPhrase("SIGN_WHOLENUMBER2"));
						}

					}
				}
				else if(e.getLine(1).equalsIgnoreCase("sell"))
				{
					if(e.getLine(2).equalsIgnoreCase(tokensName))
					{
						String numberOfTokensString = e.getLine(3);
						try
						{
							@SuppressWarnings("unused")
							int numberOfTokens = Integer.parseInt(numberOfTokensString);
						}catch(NumberFormatException e1)
						{
							e.setCancelled(true);
							e.getPlayer().sendMessage(prefix()+ChatColor.RED+Language.getPhrase("SIGN_WHOLENUMBER1")+tokensName+Language.getPhrase("SIGN_WHOLENUMBER1"));
						}
					}
				}
				else if(e.getLine(1).equalsIgnoreCase("info"))
				{
					int count = 0;
				    if(ranks2.contains(e.getLine(2)))
				    {
				        ranks2.remove(e.getLine(2));
				        count++;
				    }
				    if(count==1)
				    {
						e.setLine(1, "Info");
				    }
				    else
				    {
				    	e.setLine(1, ChatColor.RED+"Info");
				    	e.getPlayer().sendMessage(prefix()+ChatColor.RED+Language.getPhrase("SIGN_PACKAGENOTEXIST1")+e.getLine(2)+Language.getPhrase("SIGN_PACKAGENOTEXIST2"));
				    }
				}
			}
		}
		else
		{
			if(e.getLine(0).equals("[DE]"))
			{
				e.setCancelled(true);
				e.getPlayer().sendMessage(prefix()+ChatColor.YELLOW+Language.getPhrase("NOPERMISSION"));
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		Player player = e.getPlayer();
		Block block = e.getBlock();
		
		if(block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN)
		{
			Sign sign = (Sign) block.getState();
			String[] lines = sign.getLines();
			
			if(lines[0].equals("[DE]"))
			{
				if(player.hasPermission("donexpress.admin.signremove"))
				{
					//Do Nothing
				}
				else
				{
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		Block block = e.getClickedBlock();
		String tokensName = plugin.getConfig().getString("currency-name");
		
		File packages = new File(plugin.getDataFolder()+File.separator, "packages.yml");
		FileConfiguration packagesConfig=null;
		packagesConfig=new YamlConfiguration();
		try {
			packagesConfig.load(packages);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InvalidConfigurationException e1) {
			e1.printStackTrace();
		}
		
		List<String>ranks = packagesConfig.getStringList("packages");
		List<String>ranks2=new ArrayList<String>(ranks);
		
		if(e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType()  == Material.WALL_SIGN)
		{
			Sign sign = (Sign) block.getState();
			String[] lines = sign.getLines();
			
			
			if(lines[0].equals("[DE]"))
			{
				if(lines[1].equalsIgnoreCase("buy"))
				{
					if(!lines[2].equals(tokensName))
					{
						int count = 0;
					    if(ranks2.contains(lines[2]))
					    {
					        ranks2.remove(lines[2]);
					        count++;
					    }
					    if(count==1)
					    {
					    	String getPackageName = lines[2];
					    	
					    	plugin.getServer().dispatchCommand(player, "donate buy "+getPackageName);
					    }
					    else
					    {
					    	player.sendMessage(prefix()+ChatColor.RED+Language.getPhrase("SIGN_PACKAGENOTEXIST3"));
					    }
					}
					else if(lines[2].equals(tokensName))
					{
						String tokensAmountString = lines[3];
						String currencyName=plugin.getConfig().getString("currency-name");
						String website = plugin.getConfig().getString("portal-location");
						int tokensAmount = 0;
						boolean allGood=true;
						boolean transactionSucessful=false;
						
						double getTokensAmount = plugin.getConfig().getDouble("costToBuyToken");
						e.getPlayer().sendMessage(Double.toString(getTokensAmount));
						e.getPlayer().sendMessage(Double.toString(econ.getBalance(e.getPlayer().getName())));
						
						try
						{
							tokensAmount = Integer.parseInt(tokensAmountString);
							getTokensAmount = getTokensAmount * tokensAmount;
						}catch(NumberFormatException e1)
						{
							allGood=false;
							e1.printStackTrace();
						}
						
						if(allGood)
						{
							if(econ.getBalance(e.getPlayer().getName()) >= getTokensAmount)
							{
								Database.connect();
								
								String username="'"+e.getPlayer().getName()+"'";
								ResultSet result1=Database.executeStatement("SELECT `tokens`, `username` FROM dep  WHERE username = "+username);
								try {
									if(result1.next())
									{
										int currentTokens = result1.getInt(1);
										
										e.getPlayer().sendMessage(prefix()+ChatColor.YELLOW+Language.getPhrase("SIGN_EXCHANGE")+tokensAmount+" "+currencyName+Language.getPhrase("SIGN_FOR") + getTokensAmount);
										int tokensInt = tokensAmount + currentTokens;
										String tokens="'"+tokensInt+"'";
										
										EconomyResponse r = econ.withdrawPlayer(e.getPlayer().getName(), getTokensAmount);
										if(r.transactionSuccess())
										{
											transactionSucessful=true;
											Database.executeUpdate("UPDATE dep SET tokens="+tokens+"where username="+username);
											ResultSet result=Database.executeStatement("SELECT `tokens`, `username` FROM dep  WHERE username = "+username);
											if(result.next())
											{
												e.getPlayer().sendMessage(prefix()+ChatColor.GREEN+Language.getPhrase("SIGN_SUCCESS"));
												e.getPlayer().sendMessage(prefix()+ChatColor.GREEN+Language.getPhrase("SIGN_NOWHAVE")+result.getInt(1)+" "+currencyName);
											}
											else
											{
												e.getPlayer().sendMessage(prefix()+ChatColor.RED+Language.getPhrase("SIGN_TRANSACTIONERROR"));
												e.getPlayer().sendMessage(ChatColor.RED+r.errorMessage);
											}
										}
									}
									else
									{
										e.getPlayer().sendMessage(prefix()+ChatColor.YELLOW+Language.getPhrase("SIGN_NAMENOTFOUND")+website);
									}
								} catch (NumberFormatException e1) {
									e1.printStackTrace();
								} catch (SQLException e1) {
									if(transactionSucessful)
									{
										econ.depositPlayer(e.getPlayer().getName(), getTokensAmount);
									}
									e.getPlayer().sendMessage(prefix()+ChatColor.RED+Language.getPhrase("SIGN_SELLDBERROR"));
									
									e1.printStackTrace();
								}
								Database.close();
							}
							else
							{
								e.getPlayer().sendMessage(prefix()+ChatColor.YELLOW+Language.getPhrase("SIGN_NOMONEY"));
							}
						}
						else
						{
							e.getPlayer().sendMessage(prefix()+ChatColor.RED+Language.getPhrase("SIGN_UHOHERROR1"));
							e.getPlayer().sendMessage(ChatColor.RED+Language.getPhrase("SIGN_UHOHERROR2"));
						}
					}
				}
				else if(lines[1].equalsIgnoreCase("info"))
				{
					int count = 0;
				    if(ranks2.contains(lines[2]))
				    {
				        ranks2.remove(lines[2]);
				        count++;
				    }
				    if(count==1)
				    {
				    	String getPackageName = lines[2];
				    	
				    	plugin.getServer().dispatchCommand(player, "donate info "+getPackageName);
				    }
				    else
				    {
				    	player.sendMessage(prefix()+ChatColor.RED+Language.getPhrase("SIGN_PACKAGENOTEXIST3"));
				    }
				}
				else if(lines[1].equalsIgnoreCase("sell"))
				{
					if(lines[2].equalsIgnoreCase(tokensName))
					{
						String tokensAmountString = lines[3];
						String currencyName=plugin.getConfig().getString("currency-name");
						String website = plugin.getConfig().getString("portal-location");
						int tokensAmount = 0;
						boolean allGood=true;
						boolean transactionSucessful=false;
						
						
						double getTokensAmount = plugin.getConfig().getDouble("costToSellToken");
						
						try
						{
							tokensAmount = Integer.parseInt(tokensAmountString);
							getTokensAmount = getTokensAmount * tokensAmount;
						}catch(NumberFormatException e1)
						{
							allGood=false;
							e1.printStackTrace();
						}
						
						if(allGood)
						{
							Database.connect();
							
							String username="'"+e.getPlayer().getName()+"'";
							ResultSet result1=Database.executeStatement("SELECT `tokens`, `username` FROM dep  WHERE username = "+username);
							try {
								if(result1.next())
								{
									int amountOfTokensUser=result1.getInt(1);
									if(amountOfTokensUser >= tokensAmount)
									{
										e.getPlayer().sendMessage(prefix()+ChatColor.YELLOW+Language.getPhrase("SIGN_EXCHANGE")+tokensAmount+" "+currencyName+Language.getPhrase("SIGN_FOR") + getTokensAmount);
										tokensAmount=amountOfTokensUser-tokensAmount;
										String tokens="'"+tokensAmount+"'";
										
										EconomyResponse r = econ.depositPlayer(e.getPlayer().getName(), getTokensAmount);
										if(r.transactionSuccess())
										{
											transactionSucessful=true;
											Database.executeUpdate("UPDATE dep SET tokens="+tokens+"where username="+username);
											ResultSet result=Database.executeStatement("SELECT `tokens`, `username` FROM dep  WHERE username = "+username);
											if(result.next())
											{
												e.getPlayer().sendMessage(prefix()+ChatColor.GREEN+Language.getPhrase("SIGN_SUCCESS"));
												e.getPlayer().sendMessage(prefix()+ChatColor.GREEN+Language.getPhrase("SIGN_NOWHAVE")+result.getInt(1)+" "+currencyName);
											}
											else
											{
												e.getPlayer().sendMessage(prefix()+ChatColor.RED+Language.getPhrase("SIGN_SELLDBERROR"));
												econ.withdrawPlayer(e.getPlayer().getName(), getTokensAmount);
											}
										}
										else
										{
											e.getPlayer().sendMessage(prefix()+ChatColor.RED+Language.getPhrase("SIGN_TRANSACTIONERROR"));
											e.getPlayer().sendMessage(ChatColor.RED+r.errorMessage);
										}
									}
									else
									{
										e.getPlayer().sendMessage(prefix()+ChatColor.YELLOW+Language.getPhrase("SIGN_NOTOKENS"));
									}
								}
								else
								{
									e.getPlayer().sendMessage(prefix()+ChatColor.YELLOW+Language.getPhrase("SIGN_NAMENOTFOUND")+website);
								}
							} catch (NumberFormatException e1) {
								e1.printStackTrace();
							} catch (SQLException e1) {
								if(transactionSucessful)
								{
									econ.withdrawPlayer(e.getPlayer().getName(), getTokensAmount);
								}
								e.getPlayer().sendMessage(prefix()+ChatColor.RED+Language.getPhrase("SIGN_SELLDBERROR"));
								
								e1.printStackTrace();
							}
							Database.close();
						}
						else
						{
							e.getPlayer().sendMessage(prefix()+ChatColor.RED+Language.getPhrase("SIGN_UHOHERROR1"));
							e.getPlayer().sendMessage(ChatColor.RED+Language.getPhrase("SIGN_UHOHERROR2"));
						}
					}
				}
			}
		}		
	}
	
	public String colourize(String message) 
	{
		return message.replaceAll("&([l-o0-9a-f])", "\u00A7$1");
	}
	
	public String prefix()
	{
		String prefix=plugin.getConfig().getString("prefix");
		return colourize(prefix);
	}
}
