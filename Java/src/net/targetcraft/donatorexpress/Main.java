package net.targetcraft.donatorexpress;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	Connection con;
	public void onEnable()
	{
		List<String> ranks = new ArrayList<String>();
		File file=new File(getDataFolder()+File.separator+"config.yml");
		if (!file.exists())
		{
			getLogger().info("[DonatorExpress] Configuration not found. Generating...");
			this.getConfig().addDefault("metrics", true);
			this.getConfig().addDefault("db-username", "");
			this.getConfig().addDefault("db-password", "");
			this.getConfig().addDefault("db-host", "localhost:3306");
			this.getConfig().addDefault("db-name", "");
			this.getConfig().addDefault("ranks", ranks);
			this.getConfig().addDefault("donate-message", "&2Please thank %player for donating!");
			this.getConfig().addDefault("portal-location", "www.");
			this.getConfig().addDefault("currency-name", "tokens");
			this.getConfig().options().copyDefaults(true);
			this.saveConfig();
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
}
