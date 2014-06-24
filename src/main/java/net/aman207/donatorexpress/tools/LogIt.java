package net.aman207.donatorexpress.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.aman207.donatorexpress.DonatorExpress;

public class LogIt {
	
	static DonatorExpress plugin;
	
	public LogIt(DonatorExpress config) {
		plugin = config;
		
		File logFolder = new File(plugin.getDataFolder()+"/logs");
		if(!logFolder.exists())
		{
			logFolder.mkdirs();
		}
	}
	
	public static void info(String message)
	{
		if(plugin.getConfig().getBoolean("log-user-actions"))
		{
			try
			{
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    Date date = new Date();
			    
	            File log = new File(plugin.getDataFolder()+"/logs", "info.log");
	            if (!log.exists())
	            {
	                log.createNewFile();
	            }

	            FileWriter fw = new FileWriter(log, true);
	            PrintWriter pw = new PrintWriter(fw);
	 
	            pw.println(dateFormat.format(date)+" [INFO]: "+message);
	            pw.flush();
	            pw.close();
	 
	        } catch (IOException e)
	        {
	            e.printStackTrace();
	        }
		}
	}
	
	public static void error(String message)
    {
		if(plugin.getConfig().getBoolean("log-errors"))
		{
			try
	        {
	        	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    Date date = new Date();
			    
	            File log = new File(plugin.getDataFolder()+"/logs"+File.separator, "error.log");
	            if (!log.exists())
	            {
	            	//log.mkdirs();
	                log.createNewFile();
	            }

	            FileWriter fw = new FileWriter(log, true);
	            PrintWriter pw = new PrintWriter(fw);
	 
	            pw.println(dateFormat.format(date)+" [ERROR]: "+message);
	            pw.flush();
	            pw.close();
	 
	        } catch (IOException e)
	        {
	            e.printStackTrace();
	        }
		}
    }
	public static void admin(String message)
    {
		if(plugin.getConfig().getBoolean("log-admin-commands"))
		{
			try
	        {
	        	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    Date date = new Date();
			    
	            File log = new File(plugin.getDataFolder()+"/logs", "admin.log");
	            if (!log.exists())
	            {
	            	//log.mkdirs();
	                log.createNewFile();
	            }

	            FileWriter fw = new FileWriter(log, true);
	            PrintWriter pw = new PrintWriter(fw);
	 
	            pw.println(dateFormat.format(date)+" [INFO]: "+message);
	            pw.flush();
	            pw.close();
	 
	        } catch (IOException e)
	        {
	            e.printStackTrace();
	        }
		}
    }
	public static void startup(String message)
    {
		if(plugin.getConfig().getBoolean("log-startup"))
		{
			try
	        { 
	        	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    Date date = new Date();
			    
	            File log = new File(plugin.getDataFolder()+"/logs", "startup.log");
	            if (!log.exists())
	            {
	            	//log.mkdirs();
	                log.createNewFile();
	            }

	            FileWriter fw = new FileWriter(log, true);
	            PrintWriter pw = new PrintWriter(fw);
	 
	            pw.println(dateFormat.format(date)+" [INFO]: "+message);
	            pw.flush();
	            pw.close();
	 
	        } catch (IOException e)
	        {
	            e.printStackTrace();
	        }
		}
    }
	
	public static String exceptionLog(Exception e)
	{
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(baos);
	    e.printStackTrace(ps);
	    ps.close();
	    return baos.toString();
	}
}
