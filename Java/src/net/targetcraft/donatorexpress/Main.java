package net.targetcraft.donatorexpress;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	Connection con;
	public static boolean update = false;
	public static String name = "";
	public void onEnable()
	{
		List<String> ranks = new ArrayList<String>();
		File file=new File(getDataFolder()+File.separator+"config.yml");
		if (!file.exists())
		{
			getLogger().info("[DonatorExpress] Configuration not found. Generating...");
			this.getConfig().addDefault("metrics", "true");
			this.getConfig().addDefault("auto-update", "true");
			this.getConfig().addDefault("db-username", "");
			this.getConfig().addDefault("db-password", "");
			this.getConfig().addDefault("db-host", "localhost:3306");
			this.getConfig().addDefault("db-name", "");
			this.getConfig().addDefault("ranks", ranks);
			this.getConfig().addDefault("donate-message", "&2%player has just spent %amount %currency");
			this.getConfig().addDefault("portal-location", "www.");
			this.getConfig().addDefault("currency-name", "Tokens");
			this.getConfig().options().copyDefaults(true);
			this.saveConfig();
		}
		if(this.getConfig().getBoolean("auto-update")==true)
		{
			Updater updater = new Updater(this, "donator-express", this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
			update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;
			if(Main.update)
			{
				updater = new Updater(this, "donator-express", this.getFile(), Updater.UpdateType.DEFAULT, false);
			}
			name = updater.getLatestVersionString();
		}
		else
		{
			
		}
		getCommand("donate").setExecutor(new CommandListener(this));
		getServer().dispatchCommand(getServer().getConsoleSender(), "donate dbconnect");		
		
		if(this.getConfig().getBoolean("metrics")==true)
		{
			try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
		    // Failed to submit the stats :-(
		}
			
		}
		else
		{
			
		}
		
	}
	public void onDisable()
	{
		getServer().dispatchCommand(getServer().getConsoleSender(), "donate dbcloseNEVERRUNTHIS");
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void playerJoinEvent(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		if(player.hasPermission("donexpress.admin.update")&&Main.update)
		{
			player.sendMessage(ChatColor.GOLD+"[DonatorExpress] "+Main.name+" for Donator Express is available. Reload/restart the server to finish the update");
		}
	}
}
