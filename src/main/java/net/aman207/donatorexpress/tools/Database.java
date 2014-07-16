package net.aman207.donatorexpress.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.aman207.donatorexpress.DonatorExpress;

import org.bukkit.command.CommandSender;

public class Database {
	
	static DonatorExpress plugin;
	
	public Database(DonatorExpress config) {		
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
			
			LogIt.error("SQLException occured when trying to connect to Database");
			LogIt.error("SQLerr1");
			LogIt.error("Message:");
			LogIt.error(e.getMessage());
			LogIt.error(LogIt.exceptionLog(e));
		    
			if(plugin.getConfig().getBoolean("disable-on-database-error"))
			{
				plugin.getPluginLoader().disablePlugin(plugin);
				Logger.getLogger("").log(Level.SEVERE, "DonatorExpress disabled. Reload server to re enable");
				LogIt.error("Plugin disabled due to Database error and because the option disable-on-database-error was set to true");
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
			LogIt.error("SQLException occured when trying to connect to Database");
			LogIt.error("SQLerr2");
			LogIt.error("Message:");
			LogIt.error(e.getMessage());
			LogIt.error(LogIt.exceptionLog(e));
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
			LogIt.error("SQLerr3");
			LogIt.error("https://aman207.net/wiki/Errors");
			LogIt.error("Message:");
			LogIt.error(e.getMessage());
			LogIt.error(LogIt.exceptionLog(e));
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
			LogIt.error("SQLerr4");
			LogIt.error("https://aman207.net/wiki/Errors");
			LogIt.error("Message:");
			LogIt.error(e.getMessage());
			LogIt.error(LogIt.exceptionLog(e));
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
			LogIt.error("SQLerr5");
			LogIt.error("https://aman207.net/wiki/Errors");
			LogIt.error("Message:");
			LogIt.error(e.getMessage());
			LogIt.error(LogIt.exceptionLog(e));
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
			LogIt.error("SQLerr6");
			LogIt.error("https://aman207.net/wiki/Errors");
			LogIt.error("Message:");
			LogIt.error(e.getMessage());
			LogIt.error(LogIt.exceptionLog(e));
		}
	}

}
