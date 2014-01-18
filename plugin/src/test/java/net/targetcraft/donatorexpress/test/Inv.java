package net.targetcraft.donatorexpress.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Inv implements Listener {
	
	static Main plugin;
	
	private Inventory inv;
	
	public Inv(Main config)
	{
		plugin = config;
		
		File inventory = new File(plugin.getDataFolder()+File.separator, "inventory.yml");
		FileConfiguration inventoryConfig=null;
		inventoryConfig=new YamlConfiguration();
		try {
			inventoryConfig.load(inventory);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		
		if(inventoryConfig.getInt("inventorySize") == 9)
		{
			inv = Bukkit.getServer().createInventory(null, 9, inventoryConfig.getString("inventoryName"));
		}
		else if(inventoryConfig.getInt("inventorySize") == 18)
		{
			inv = Bukkit.getServer().createInventory(null, 18, inventoryConfig.getString("inventoryName"));
		}
		else if(inventoryConfig.getInt("inventorySize") == 27)
		{
			inv = Bukkit.getServer().createInventory(null, 27, inventoryConfig.getString("inventoryName"));
		}
		else
		{
			Logger.getLogger("").warning("Your inventory size is not 9, 18 or 27. Please change this");
		}
		
	}
	
	public void openInventory(CommandSender sender)
	{
		File inventory = new File(plugin.getDataFolder()+File.separator, "inventory.yml");
		FileConfiguration inventoryConfig=null;
		inventoryConfig=new YamlConfiguration();
		try {
			inventoryConfig.load(inventory);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		if(inventoryConfig.getInt("inventorySize") == 9)
		{
			String blockStack1 = inventoryConfig.getString("invSlot1.block");
			String blockStack2 = inventoryConfig.getString("invSlot2.block");
			String blockStack3 = inventoryConfig.getString("invSlot3.block");
			String blockStack4 = inventoryConfig.getString("invSlot4.block");
			String blockStack5 = inventoryConfig.getString("invSlot5.block");
			String blockStack6 = inventoryConfig.getString("invSlot6.block");
			String blockStack7 = inventoryConfig.getString("invSlot7.block");
			String blockStack8 = inventoryConfig.getString("invSlot8.block");
			String blockStack9 = inventoryConfig.getString("invSlot9.block");
			
			ItemStack stack1 = new ItemStack(Material.getMaterial(blockStack1));
			ItemStack stack2 = new ItemStack(Material.getMaterial(blockStack2));
			ItemStack stack3 = new ItemStack(Material.getMaterial(blockStack3));
			ItemStack stack4 = new ItemStack(Material.getMaterial(blockStack4));
			ItemStack stack5 = new ItemStack(Material.getMaterial(blockStack5));
			ItemStack stack6 = new ItemStack(Material.getMaterial(blockStack6));
			ItemStack stack7 = new ItemStack(Material.getMaterial(blockStack7));
		    ItemStack stack8 = new ItemStack(Material.getMaterial(blockStack8));
			ItemStack stack9 = new ItemStack(Material.getMaterial(blockStack9));
			
			ItemMeta meta1 = stack1.getItemMeta();
			ItemMeta meta2 = stack2.getItemMeta();
			ItemMeta meta3 = stack3.getItemMeta();
			ItemMeta meta4 = stack4.getItemMeta();
			ItemMeta meta5 = stack5.getItemMeta();
			ItemMeta meta6 = stack6.getItemMeta();
			ItemMeta meta7 = stack7.getItemMeta();
			ItemMeta meta8 = stack8.getItemMeta();
			ItemMeta meta9 = stack9.getItemMeta();
			
			//if(!inventoryConfig.getString("invSlot1.title").equalsIgnoreCase("null")) 
			meta1.setDisplayName(inventoryConfig.getString("invSlot1.title"));
			meta2.setDisplayName(inventoryConfig.getString("invSlot2.title"));
			meta3.setDisplayName(inventoryConfig.getString("invSlot3.title"));
			meta4.setDisplayName(inventoryConfig.getString("invSlot4.title"));
			meta5.setDisplayName(inventoryConfig.getString("invSlot5.title"));
			meta6.setDisplayName(inventoryConfig.getString("invSlot6.title"));
			meta7.setDisplayName(inventoryConfig.getString("invSlot7.title"));
			meta8.setDisplayName(inventoryConfig.getString("invSlot8.title"));
			meta9.setDisplayName(inventoryConfig.getString("invSlot9.title"));
		}
		else if(inventoryConfig.getInt("inventorySize") == 18)
		{
			
		}
		else if(inventoryConfig.getInt("inventorySize") == 27)
		{
			
		}
		else
		{
			sender.sendMessage(prefix()+ChatColor.RED+"Error. Your server owner has incorrecly setup the inventory config. Let him/her know about this error right away");
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

}
