package net.aman207.donatorexpress.events;

import java.io.File;

import net.aman207.donatorexpress.DonatorExpress;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class Inventory {
	//With the aid of
	//https://forums.bukkit.org/threads/tutorial-create-a-inventory-menu.173571/
	//and
	//https://forums.bukkit.org/threads/how-to-convert-item-ids-to-item-name.82319/
	
	static DonatorExpress plugin;
	public static org.bukkit.inventory.Inventory shopInventory;
	
	public Inventory (DonatorExpress config, int slotNumber, String inventoryName)
	{
		plugin=config;
		
		shopInventory = Bukkit.createInventory(null, slotNumber, inventoryName);
		initializeInventory();
	}
	
	public static void initializeInventory()
	{
		//Lets get this thing rolling
		File inventoryConfigFile = new File(plugin.getDataFolder(), "inventoryConfig.yml");
	}
	
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
