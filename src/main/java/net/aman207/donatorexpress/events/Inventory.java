package net.aman207.donatorexpress.events;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import net.aman207.donatorexpress.DonatorExpress;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class Inventory implements Listener {
	//With the aid of
	//https://forums.bukkit.org/threads/tutorial-create-a-inventory-menu.173571/
	//and
	//https://forums.bukkit.org/threads/how-to-convert-item-ids-to-item-name.82319/
	
	static DonatorExpress plugin;
	public static org.bukkit.inventory.Inventory shopInventory;
	private static HashMap<String, String> inventoryItems = new HashMap<String, String>();
	
	public Inventory(DonatorExpress config1)
	{
		
	}
	
	public Inventory (DonatorExpress config, int slotNumber, String inventoryName)
	{
		plugin=config;
		
		shopInventory = Bukkit.createInventory(null, slotNumber, inventoryName);
		initializeInventory();
	}
	
	public static void initializeInventory()
	{
		//Lets get this thing rolling
		
		File packages = new File(plugin.getDataFolder()+File.separator, "packages.yml");
		FileConfiguration packagesConfig=null;
		packagesConfig=new YamlConfiguration();
		try {
			packagesConfig.load(packages);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		List<String>ranks = packagesConfig.getStringList("packages".toLowerCase());
		for(String s:ranks)
		{
			File specificPackages = new File(plugin.getDataFolder()+File.separator+"packages"+File.separator, s);
			FileConfiguration packagesSpecific=null;
			packagesSpecific=new YamlConfiguration();
			try {
				packagesSpecific.load(specificPackages);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
			
			int position = packagesSpecific.getInt(""+-1);
			
		}
	}
	
	private static void addItems(String item)
	{
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) 
	{
		Player player = (Player) event.getWhoClicked(); 
		ItemStack clicked = event.getCurrentItem(); 
		org.bukkit.inventory.Inventory inventory = event.getInventory();
		
		if(inventory.getName().equals(shopInventory.getName()))
		{
			
		}
	}

}
