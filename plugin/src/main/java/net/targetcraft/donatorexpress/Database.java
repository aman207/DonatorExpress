package net.targetcraft.donatorexpress;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.CommandSender;

public class Database {
	
	static Main plugin;
	
	public Database(Main config) {		
		plugin = config;
	}

	static Connection con;
	
	public static boolean connect()
	{
		String dbUsername = plugin.getConfig().getString("db-username");
		String dbPassword = plugin.getConfig().getString("db-password");
		String dbHost = plugin.getConfig().getString("db-host");
		String dbName = plugin.getConfig().getString("db-name");
		String dbURL = "jdbc:mysql://" + dbHost + "/" + dbName;
		try {
			con = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
			return true;
		} catch (SQLException e) {
			Logger.getLogger("").log(Level.SEVERE, "Error while trying to connect to the database");			
			e.printStackTrace();
			Logger.getLogger("").log(Level.SEVERE, "Error while trying to connect to the database.");
		    
			if(plugin.getConfig().getBoolean("disable-on-database-error"))
			{
				plugin.getPluginLoader().disablePlugin(plugin);
				Logger.getLogger("").log(Level.SEVERE, "DonatorExpress disabled. Reload server to re enable");
			}
			return false;

		}
	}
	
	public static void close()
	{
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet executeStatement(String statementGet)
	{
		Statement statement=null;
		ResultSet result = null;
		try {
			statement=con.createStatement();
			result=statement.executeQuery(statementGet);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	public static ResultSet executeStatement(String statementGet, CommandSender sender)
	{
		Statement statement=null;
		ResultSet result = null;
		try {
			statement=con.createStatement();
			result=statement.executeQuery(statementGet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	public static void executeUpdate(String statementGet)
	{
		Statement statement=null;
		try {
			statement=con.createStatement();
			statement.executeUpdate(statementGet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void execute(String statementGet)
	{
		Statement statement=null;
		try {
			statement=con.createStatement();
			statement.execute(statementGet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
