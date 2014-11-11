package net.aman207.donatorexpress.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.aman207.donatorexpress.DonatorExpress;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Language {
	
	static DonatorExpress plugin;
	static HashMap<String, String> lang = new HashMap<String, String>();

	public Language(DonatorExpress config)
	{
		plugin=config;
	}
	
	public static String getPhrase(String phrase)
	{
		return lang.get(phrase);
	}
	
	public void languageInitialize()
	{
		//if(plugin.getConfig().getString("language").equals("en"))
		//{
			File english = new File(plugin.getDataFolder()+"/languages", plugin.getConfig().getString("language")+".yml");
			FileConfiguration languageConfig = null;
			languageConfig = new YamlConfiguration();
			try {
				languageConfig.load(english);
			} catch (FileNotFoundException e) {
				LogIt.error("IOerr11");
				LogIt.error("https://aman207.net/wiki/Errors");
				LogIt.error("Couldn't find correct language code in config.yml Plugin cannot start");
				LogIt.error("Stacktrace:");
				LogIt.error(" ");
				LogIt.error(LogIt.exceptionLog(e));
				e.printStackTrace();
				Logger.getLogger("").log(Level.SEVERE, "DonatorExpress disabled. Unable to find language code in config.yml "
						+ "Reload server to re enable after changes to config have been made");

				plugin.getPluginLoader().disablePlugin(plugin);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
			
			lang.put("ADDATTEMPTING", languageConfig.getString("ADDATTEMPTING"));
			lang.put("ATTEMPTINGTOGIVE", languageConfig.getString("ATTEMPTINGTOGIVE"));
			lang.put("ATTEMPTINGTOSET1", languageConfig.getString("ATTEMPTINGTOSET1"));
			lang.put("ATTEMPTINGTOSET2", languageConfig.getString("ATTEMPTINGTOSET2"));
			lang.put("ALREADYEXISTS", languageConfig.getString("ALREADYEXISTS"));
			lang.put("CANCEL1", languageConfig.getString("CANCEL1"));
			lang.put("CANCEL2", languageConfig.getString("CANCEL2"));
			lang.put("CANCEL3", languageConfig.getString("CANCEL3"));
			lang.put("CANCEL4", languageConfig.getString("CANCEL4"));
			lang.put("CANCEL5", languageConfig.getString("CANCEL5"));
			lang.put("CANCEL6", languageConfig.getString("CANCEL6"));
			lang.put("CANCEL7", languageConfig.getString("CANCEL7"));
			lang.put("CANCEL8", languageConfig.getString("CANCEL8"));
			lang.put("COMMANDUSAGE", languageConfig.getString("COMMANDUSAGE"));
			lang.put("CURRENTLISTPACKAGES1", languageConfig.getString("CURRENTLISTPACKAGES1"));
			lang.put("CURRENTLISTPACKAGES2", languageConfig.getString("CURRENTLISTPACKAGES2"));
			lang.put("DBERROR", languageConfig.getString("DBERROR"));
			lang.put("HOWMUCH1", languageConfig.getString("HOWMUCH1"));
			lang.put("HOWMUCH2", languageConfig.getString("HOWMUCH2"));
			lang.put("HOWMUCH3", languageConfig.getString("HOWMUCH3"));
			lang.put("INVALIDSYNTAX1", languageConfig.getString("INVALIDSYNTAX1"));
			lang.put("INVALIDSYNTAX2", languageConfig.getString("INVALIDSYNTAX2"));
			lang.put("NOACCOUNT1", languageConfig.getString("NOACCOUNT1"));
			lang.put("NOACCOUNT2", languageConfig.getString("NOACCOUNT2"));
			lang.put("NOTENOUGH", languageConfig.getString("NOTENOUGH"));
			lang.put("NOWHAVE", languageConfig.getString("NOWHAVE"));
			lang.put("NOWHAS", languageConfig.getString("NOWHAS"));
			lang.put("PACKAGENOTPURCHASED", languageConfig.getString("PACKAGENOTPURCHASED"));
			lang.put("PACKAGEALREADYPURCHASED", languageConfig.getString("PACKAGEALREADYPURCHASED"));
			lang.put("PACKAGENOTFOUND", languageConfig.getString("PACKAGENOTFOUND"));
			lang.put("PACKAGENOTTHERE", languageConfig.getString("PACKAGENOTTHERE"));
			lang.put("PLUGINDISABLED", languageConfig.getString("PLUGINDISABLED"));
			lang.put("REMOVE1", languageConfig.getString("REMOVE1"));
			lang.put("REMOVE2", languageConfig.getString("REMOVE2"));
			lang.put("REMOVE3", languageConfig.getString("REMOVE3"));
			lang.put("REMOVE4", languageConfig.getString("REMOVE4"));
			lang.put("REMOVEATTEMPTING", languageConfig.getString("REMOVEATTEMPTING"));
			lang.put("REQUESTREMOVENOTDONE", languageConfig.getString("REQUESTREMOVENOTDONE"));
			lang.put("SUCCESS", languageConfig.getString("SUCCESS"));
			lang.put("SUCCESSREMOVE", languageConfig.getString("SUCCESSREMOVE"));
			lang.put("TOKENSRETURN", languageConfig.getString("TOKENSRETURN"));
			lang.put("TOKENSRETURN2", languageConfig.getString("TOKENSRETURN2"));
			lang.put("UPGRADETO1", languageConfig.getString("UPGRADETO1"));
			lang.put("UPGRADETO2", languageConfig.getString("UPGRADETO2"));
			lang.put("USERNOTFOUND", languageConfig.getString("USERNOTFOUND"));
			lang.put("FROM", languageConfig.getString("FROM"));
			
			lang.put("SIGN_PACKAGENOTEXIST1", languageConfig.getString("SIGN_PACKAGENOTEXIST1"));
			lang.put("SIGN_PACKAGENOTEXIST2", languageConfig.getString("SIGN_PACKAGENOTEXIST2"));
			lang.put("SIGN_PACKAGENOTEXIST3", languageConfig.getString("SIGN_PACKAGENOTEXIST3"));
			lang.put("SIGN_WHOLENUMBER1", languageConfig.getString("SIGN_WHOLENUMBER1"));
			lang.put("SIGN_WHOLENUMBER2", languageConfig.getString("SIGN_WHOLENUMBER2"));
			lang.put("SIGN_EXCHANGEATTEMPTING1", languageConfig.getString("SIGN_EXCHANGEATTEMPTING2"));
			lang.put("SIGN_SUCCESS", languageConfig.getString("SIGN_SUCCESS"));
			lang.put("SIGN_NOWHAVE", languageConfig.getString("SIGN_NOWHAVE"));
			lang.put("SIGN_EXCHANGE", languageConfig.getString("SIGN_EXCHANGE"));
			lang.put("SIGN_NAMENOTFOUND", languageConfig.getString("SIGN_NAMENOTFOUND"));
			lang.put("SIGN_NOMONEY", languageConfig.getString("SIGN_NOMONEY"));
			lang.put("SIGN_NOTOKENS", languageConfig.getString("SIGN_NOTOKENS"));
			lang.put("SIGN_UHOHERROR1", languageConfig.getString("SIGN_UHOHERROR1"));
			lang.put("SIGN_UHOHERROR2", languageConfig.getString("SIGN_UHOHERROR2"));
			lang.put("SIGN_SELLATTEMPTING1", languageConfig.getString("SIGN_SELLATTEMPTING2"));
			lang.put("SIGN_SELLDBERROR", languageConfig.getString("SIGN_SELLDBERROR"));
			lang.put("SIGN_TRANSACTIONERROR", languageConfig.getString("SIGN_TRANSACTIONERROR"));
			lang.put("SIGN_FOR", languageConfig.getString("SIGN_FOR"));
			
			lang.put("SUPPORT_", languageConfig.getString("SUPPORT_"));
		//}
		/**
		else if(plugin.getConfig().getString("language").equals("fr"))
		{
			File french = new File(plugin.getDataFolder()+"/languages", "fr.yml");
			FileConfiguration languageConfig = null;
			languageConfig = new YamlConfiguration();
			try {
				languageConfig.load(french);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
			
			lang.put("ADDATTEMPTING", languageConfig.getString("ADDATTEMPTING"));
			lang.put("ALREADYEXISTS", languageConfig.getString("ALREADYEXISTS"));
			lang.put("ATTEMPTINGTOGIVE", languageConfig.getString("ATTEMPTINGTOGIVE"));
			lang.put("ATTEMPTINGTOSET1", languageConfig.getString("ATTEMPTINGTOSET1"));
			lang.put("ATTEMPTINGTOSET2", languageConfig.getString("ATTEMPTINGTOSET2"));
			lang.put("CANCEL1", languageConfig.getString("CANCEL1"));
			lang.put("CANCEL2", languageConfig.getString("CANCEL2"));
			lang.put("CANCEL3", languageConfig.getString("CANCEL3"));
			lang.put("CANCEL4", languageConfig.getString("CANCEL4"));
			lang.put("CANCEL5", languageConfig.getString("CANCEL5"));
			lang.put("CANCEL6", languageConfig.getString("CANCEL6"));
			lang.put("CANCEL7", languageConfig.getString("CANCEL7"));
			lang.put("CANCEL8", languageConfig.getString("CANCEL8"));
			lang.put("COMMANDUSAGE", languageConfig.getString("COMMANDUSAGE"));
			lang.put("CURRENTLISTPACKAGES1", languageConfig.getString("CURRENTLISTPACKAGES1"));
			lang.put("CURRENTLISTPACKAGES2", languageConfig.getString("CURRENTLISTPACKAGES2"));
			lang.put("DBERROR", languageConfig.getString("DBERROR"));
			lang.put("HOWMUCH1", languageConfig.getString("HOWMUCH1"));
			lang.put("HOWMUCH2", languageConfig.getString("HOWMUCH2"));
			lang.put("HOWMUCH3", languageConfig.getString("HOWMUCH3"));
			lang.put("INVALIDSYNTAX1", languageConfig.getString("INVALIDSYNTAX1"));
			lang.put("INVALIDSYNTAX2", languageConfig.getString("INVALIDSYNTAX2"));
			lang.put("NOACCOUNT1", languageConfig.getString("NOACCOUNT1"));
			lang.put("NOACCOUNT2", languageConfig.getString("NOACCOUNT2"));
			lang.put("NOTENOUGH", languageConfig.getString("NOTENOUGH"));
			lang.put("NOWHAVE", languageConfig.getString("NOWHAVE"));
			lang.put("NOWHAS", languageConfig.getString("NOWHAS"));
			lang.put("PACKAGENOTPURCHASED", languageConfig.getString("PACKAGENOTPURCHASED"));
			lang.put("PACKAGEALREADYPURCHASED", languageConfig.getString("PACKAGEALREADYPURCHASED"));
			lang.put("PACKAGENOTFOUND", languageConfig.getString("PACKAGENOTFOUND"));
			lang.put("PACKAGENOTTHERE", languageConfig.getString("PACKAGENOTTHERE"));
			lang.put("PLUGINDISABLED", languageConfig.getString("PLUGINDISABLED"));
			lang.put("REMOVE1", languageConfig.getString("REMOVE1"));
			lang.put("REMOVE2", languageConfig.getString("REMOVE2"));
			lang.put("REMOVE3", languageConfig.getString("REMOVE3"));
			lang.put("REMOVE4", languageConfig.getString("REMOVE4"));
			lang.put("REMOVEATTEMPTING", languageConfig.getString("REMOVEATTEMPTING"));
			lang.put("REQUESTREMOVENOTDONE", languageConfig.getString("REQUESTREMOVENOTDONE"));
			lang.put("SUCCESS", languageConfig.getString("SUCCESS"));
			lang.put("SUCCESSREMOVE", languageConfig.getString("SUCCESSREMOVE"));
			lang.put("TOKENSRETURN", languageConfig.getString("TOKENSRETURN"));
			lang.put("TOKENSRETURN2", languageConfig.getString("TOKENSRETURN2"));
			lang.put("UPGRADETO1", languageConfig.getString("UPGRADETO1"));
			lang.put("UPGRADETO2", languageConfig.getString("UPGRADETO2"));
			lang.put("USERNOTFOUND", languageConfig.getString("USERNOTFOUND"));
			lang.put("FROM", languageConfig.getString("FROM"));
			
			lang.put("SIGN_PACKAGENOTEXIST1", languageConfig.getString("SIGN_PACKAGENOTEXIST1"));
			lang.put("SIGN_PACKAGENOTEXIST2", languageConfig.getString("SIGN_PACKAGENOTEXIST2"));
			lang.put("SIGN_PACKAGENOTEXIST3", languageConfig.getString("SIGN_PACKAGENOTEXIST3"));
			lang.put("SIGN_WHOLENUMBER1", languageConfig.getString("SIGN_WHOLENUMBER1"));
			lang.put("SIGN_WHOLENUMBER2", languageConfig.getString("SIGN_WHOLENUMBER2"));
			lang.put("SIGN_EXCHANGEATTEMPTING1", languageConfig.getString("SIGN_EXCHANGEATTEMPTING2"));
			lang.put("SIGN_SUCCESS", languageConfig.getString("SIGN_SUCCESS"));
			lang.put("SIGN_NOWHAVE", languageConfig.getString("SIGN_NOWHAVE"));
			lang.put("SIGN_EXCHANGE", languageConfig.getString("SIGN_EXCHANGE"));
			lang.put("SIGN_NAMENOTFOUND", languageConfig.getString("SIGN_NAMENOTFOUND"));
			lang.put("SIGN_NOMONEY", languageConfig.getString("SIGN_NOMONEY"));
			lang.put("SIGN_NOTOKENS", languageConfig.getString("SIGN_NOTOKENS"));
			lang.put("SIGN_UHOHERROR1", languageConfig.getString("SIGN_UHOHERROR1"));
			lang.put("SIGN_UHOHERROR2", languageConfig.getString("SIGN_UHOHERROR2"));
			lang.put("SIGN_SELLATTEMPTING1", languageConfig.getString("SIGN_SELLATTEMPTING2"));
			lang.put("SIGN_SELLDBERROR", languageConfig.getString("SIGN_SELLDBERROR"));
			lang.put("SIGN_TRANSACTIONERROR", languageConfig.getString("SIGN_TRANSACTIONERROR"));
			lang.put("SIGN_FOR", languageConfig.getString("SIGN_FOR"));
		}
		else if(plugin.getConfig().getString("language").equals("es"))
		{
			File spanish = new File(plugin.getDataFolder()+"/languages", "es.yml");
			FileConfiguration languageConfig = null;
			languageConfig = new YamlConfiguration();
			try {
				languageConfig.load(spanish);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
			
			lang.put("ADDATTEMPTING", languageConfig.getString("ADDATTEMPTING"));
			lang.put("ALREADYEXISTS", languageConfig.getString("ALREADYEXISTS"));
			lang.put("ATTEMPTINGTOGIVE", languageConfig.getString("ATTEMPTINGTOGIVE"));
			lang.put("ATTEMPTINGTOSET1", languageConfig.getString("ATTEMPTINGTOSET1"));
			lang.put("ATTEMPTINGTOSET2", languageConfig.getString("ATTEMPTINGTOSET2"));
			lang.put("CANCEL1", languageConfig.getString("CANCEL1"));
			lang.put("CANCEL2", languageConfig.getString("CANCEL2"));
			lang.put("CANCEL3", languageConfig.getString("CANCEL3"));
			lang.put("CANCEL4", languageConfig.getString("CANCEL4"));
			lang.put("CANCEL5", languageConfig.getString("CANCEL5"));
			lang.put("CANCEL6", languageConfig.getString("CANCEL6"));
			lang.put("CANCEL7", languageConfig.getString("CANCEL7"));
			lang.put("CANCEL8", languageConfig.getString("CANCEL8"));
			lang.put("COMMANDUSAGE", languageConfig.getString("COMMANDUSAGE"));
			lang.put("CURRENTLISTPACKAGES1", languageConfig.getString("CURRENTLISTPACKAGES1"));
			lang.put("CURRENTLISTPACKAGES2", languageConfig.getString("CURRENTLISTPACKAGES2"));
			lang.put("DBERROR", languageConfig.getString("DBERROR"));
			lang.put("HOWMUCH1", languageConfig.getString("HOWMUCH1"));
			lang.put("HOWMUCH2", languageConfig.getString("HOWMUCH2"));
			lang.put("HOWMUCH3", languageConfig.getString("HOWMUCH3"));
			lang.put("INVALIDSYNTAX1", languageConfig.getString("INVALIDSYNTAX1"));
			lang.put("INVALIDSYNTAX2", languageConfig.getString("INVALIDSYNTAX2"));
			lang.put("NOACCOUNT1", languageConfig.getString("NOACCOUNT1"));
			lang.put("NOACCOUNT2", languageConfig.getString("NOACCOUNT2"));
			lang.put("NOTENOUGH", languageConfig.getString("NOTENOUGH"));
			lang.put("NOWHAVE", languageConfig.getString("NOWHAVE"));
			lang.put("NOWHAS", languageConfig.getString("NOWHAS"));
			lang.put("PACKAGENOTPURCHASED", languageConfig.getString("PACKAGENOTPURCHASED"));
			lang.put("PACKAGEALREADYPURCHASED", languageConfig.getString("PACKAGEALREADYPURCHASED"));
			lang.put("PACKAGENOTFOUND", languageConfig.getString("PACKAGENOTFOUND"));
			lang.put("PACKAGENOTTHERE", languageConfig.getString("PACKAGENOTTHERE"));
			lang.put("PLUGINDISABLED", languageConfig.getString("PLUGINDISABLED"));
			lang.put("REMOVE1", languageConfig.getString("REMOVE1"));
			lang.put("REMOVE2", languageConfig.getString("REMOVE2"));
			lang.put("REMOVE3", languageConfig.getString("REMOVE3"));
			lang.put("REMOVE4", languageConfig.getString("REMOVE4"));
			lang.put("REMOVEATTEMPTING", languageConfig.getString("REMOVEATTEMPTING"));
			lang.put("REQUESTREMOVENOTDONE", languageConfig.getString("REQUESTREMOVENOTDONE"));
			lang.put("SUCCESS", languageConfig.getString("SUCCESS"));
			lang.put("SUCCESSREMOVE", languageConfig.getString("SUCCESSREMOVE"));
			lang.put("TOKENSRETURN", languageConfig.getString("TOKENSRETURN"));
			lang.put("TOKENSRETURN2", languageConfig.getString("TOKENSRETURN2"));
			lang.put("UPGRADETO1", languageConfig.getString("UPGRADETO1"));
			lang.put("UPGRADETO2", languageConfig.getString("UPGRADETO2"));
			lang.put("USERNOTFOUND", languageConfig.getString("USERNOTFOUND"));
			lang.put("FROM", languageConfig.getString("FROM"));
			
			lang.put("SIGN_PACKAGENOTEXIST1", languageConfig.getString("SIGN_PACKAGENOTEXIST1"));
			lang.put("SIGN_PACKAGENOTEXIST2", languageConfig.getString("SIGN_PACKAGENOTEXIST2"));
			lang.put("SIGN_PACKAGENOTEXIST3", languageConfig.getString("SIGN_PACKAGENOTEXIST3"));
			lang.put("SIGN_WHOLENUMBER1", languageConfig.getString("SIGN_WHOLENUMBER1"));
			lang.put("SIGN_WHOLENUMBER2", languageConfig.getString("SIGN_WHOLENUMBER2"));
			lang.put("SIGN_EXCHANGEATTEMPTING1", languageConfig.getString("SIGN_EXCHANGEATTEMPTING2"));
			lang.put("SIGN_SUCCESS", languageConfig.getString("SIGN_SUCCESS"));
			lang.put("SIGN_NOWHAVE", languageConfig.getString("SIGN_NOWHAVE"));
			lang.put("SIGN_EXCHANGE", languageConfig.getString("SIGN_EXCHANGE"));
			lang.put("SIGN_NAMENOTFOUND", languageConfig.getString("SIGN_NAMENOTFOUND"));
			lang.put("SIGN_NOMONEY", languageConfig.getString("SIGN_NOMONEY"));
			lang.put("SIGN_NOTOKENS", languageConfig.getString("SIGN_NOTOKENS"));
			lang.put("SIGN_UHOHERROR1", languageConfig.getString("SIGN_UHOHERROR1"));
			lang.put("SIGN_UHOHERROR2", languageConfig.getString("SIGN_UHOHERROR2"));
			lang.put("SIGN_SELLATTEMPTING1", languageConfig.getString("SIGN_SELLATTEMPTING2"));
			lang.put("SIGN_SELLDBERROR", languageConfig.getString("SIGN_SELLDBERROR"));
			lang.put("SIGN_TRANSACTIONERROR", languageConfig.getString("SIGN_TRANSACTIONERROR"));
			lang.put("SIGN_FOR", languageConfig.getString("SIGN_FOR"));
		}
		else if(plugin.getConfig().getString("language").equals("nl"))
		{
			File dutch = new File(plugin.getDataFolder()+"/languages", "nl.yml");
			FileConfiguration languageConfig = null;
			languageConfig = new YamlConfiguration();
			try {
				languageConfig.load(dutch);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
			
			lang.put("ADDATTEMPTING", languageConfig.getString("ADDATTEMPTING"));
			lang.put("ALREADYEXISTS", languageConfig.getString("ALREADYEXISTS"));
			lang.put("ATTEMPTINGTOGIVE", languageConfig.getString("ATTEMPTINGTOGIVE"));
			lang.put("ATTEMPTINGTOSET1", languageConfig.getString("ATTEMPTINGTOSET1"));
			lang.put("ATTEMPTINGTOSET2", languageConfig.getString("ATTEMPTINGTOSET2"));
			lang.put("CANCEL1", languageConfig.getString("CANCEL1"));
			lang.put("CANCEL2", languageConfig.getString("CANCEL2"));
			lang.put("CANCEL3", languageConfig.getString("CANCEL3"));
			lang.put("CANCEL4", languageConfig.getString("CANCEL4"));
			lang.put("CANCEL5", languageConfig.getString("CANCEL5"));
			lang.put("CANCEL6", languageConfig.getString("CANCEL6"));
			lang.put("CANCEL7", languageConfig.getString("CANCEL7"));
			lang.put("CANCEL8", languageConfig.getString("CANCEL8"));
			lang.put("COMMANDUSAGE", languageConfig.getString("COMMANDUSAGE"));
			lang.put("CURRENTLISTPACKAGES1", languageConfig.getString("CURRENTLISTPACKAGES1"));
			lang.put("CURRENTLISTPACKAGES2", languageConfig.getString("CURRENTLISTPACKAGES2"));
			lang.put("DBERROR", languageConfig.getString("DBERROR"));
			lang.put("HOWMUCH1", languageConfig.getString("HOWMUCH1"));
			lang.put("HOWMUCH2", languageConfig.getString("HOWMUCH2"));
			lang.put("HOWMUCH3", languageConfig.getString("HOWMUCH3"));
			lang.put("INVALIDSYNTAX1", languageConfig.getString("INVALIDSYNTAX1"));
			lang.put("INVALIDSYNTAX2", languageConfig.getString("INVALIDSYNTAX2"));
			lang.put("NOACCOUNT1", languageConfig.getString("NOACCOUNT1"));
			lang.put("NOACCOUNT2", languageConfig.getString("NOACCOUNT2"));
			lang.put("NOTENOUGH", languageConfig.getString("NOTENOUGH"));
			lang.put("NOWHAVE", languageConfig.getString("NOWHAVE"));
			lang.put("NOWHAS", languageConfig.getString("NOWHAS"));
			lang.put("PACKAGENOTPURCHASED", languageConfig.getString("PACKAGENOTPURCHASED"));
			lang.put("PACKAGEALREADYPURCHASED", languageConfig.getString("PACKAGEALREADYPURCHASED"));
			lang.put("PACKAGENOTFOUND", languageConfig.getString("PACKAGENOTFOUND"));
			lang.put("PACKAGENOTTHERE", languageConfig.getString("PACKAGENOTTHERE"));
			lang.put("PLUGINDISABLED", languageConfig.getString("PLUGINDISABLED"));
			lang.put("REMOVE1", languageConfig.getString("REMOVE1"));
			lang.put("REMOVE2", languageConfig.getString("REMOVE2"));
			lang.put("REMOVE3", languageConfig.getString("REMOVE3"));
			lang.put("REMOVE4", languageConfig.getString("REMOVE4"));
			lang.put("REMOVEATTEMPTING", languageConfig.getString("REMOVEATTEMPTING"));
			lang.put("REQUESTREMOVENOTDONE", languageConfig.getString("REQUESTREMOVENOTDONE"));
			lang.put("SUCCESS", languageConfig.getString("SUCCESS"));
			lang.put("SUCCESSREMOVE", languageConfig.getString("SUCCESSREMOVE"));
			lang.put("TOKENSRETURN", languageConfig.getString("TOKENSRETURN"));
			lang.put("TOKENSRETURN2", languageConfig.getString("TOKENSRETURN2"));
			lang.put("UPGRADETO1", languageConfig.getString("UPGRADETO1"));
			lang.put("UPGRADETO2", languageConfig.getString("UPGRADETO2"));
			lang.put("USERNOTFOUND", languageConfig.getString("USERNOTFOUND"));
			lang.put("FROM", languageConfig.getString("FROM"));
			
			lang.put("SIGN_PACKAGENOTEXIST1", languageConfig.getString("SIGN_PACKAGENOTEXIST1"));
			lang.put("SIGN_PACKAGENOTEXIST2", languageConfig.getString("SIGN_PACKAGENOTEXIST2"));
			lang.put("SIGN_PACKAGENOTEXIST3", languageConfig.getString("SIGN_PACKAGENOTEXIST3"));
			lang.put("SIGN_WHOLENUMBER1", languageConfig.getString("SIGN_WHOLENUMBER1"));
			lang.put("SIGN_WHOLENUMBER2", languageConfig.getString("SIGN_WHOLENUMBER2"));
			lang.put("SIGN_EXCHANGEATTEMPTING1", languageConfig.getString("SIGN_EXCHANGEATTEMPTING2"));
			lang.put("SIGN_SUCCESS", languageConfig.getString("SIGN_SUCCESS"));
			lang.put("SIGN_NOWHAVE", languageConfig.getString("SIGN_NOWHAVE"));
			lang.put("SIGN_EXCHANGE", languageConfig.getString("SIGN_EXCHANGE"));
			lang.put("SIGN_NAMENOTFOUND", languageConfig.getString("SIGN_NAMENOTFOUND"));
			lang.put("SIGN_NOMONEY", languageConfig.getString("SIGN_NOMONEY"));
			lang.put("SIGN_NOTOKENS", languageConfig.getString("SIGN_NOTOKENS"));
			lang.put("SIGN_UHOHERROR1", languageConfig.getString("SIGN_UHOHERROR1"));
			lang.put("SIGN_UHOHERROR2", languageConfig.getString("SIGN_UHOHERROR2"));
			lang.put("SIGN_SELLATTEMPTING1", languageConfig.getString("SIGN_SELLATTEMPTING2"));
			lang.put("SIGN_SELLDBERROR", languageConfig.getString("SIGN_SELLDBERROR"));
			lang.put("SIGN_TRANSACTIONERROR", languageConfig.getString("SIGN_TRANSACTIONERROR"));
			lang.put("SIGN_FOR", languageConfig.getString("SIGN_FOR"));
		}
		else if(plugin.getConfig().getString("language").equals("it"))
		{
			File dutch = new File(plugin.getDataFolder()+"/languages", "it.yml");
			FileConfiguration languageConfig = null;
			languageConfig = new YamlConfiguration();
			try {
				languageConfig.load(italian);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
			
			lang.put("ADDATTEMPTING", languageConfig.getString("ADDATTEMPTING"));
			lang.put("ALREADYEXISTS", languageConfig.getString("ALREADYEXISTS"));
			lang.put("ATTEMPTINGTOGIVE", languageConfig.getString("ATTEMPTINGTOGIVE"));
			lang.put("ATTEMPTINGTOSET1", languageConfig.getString("ATTEMPTINGTOSET1"));
			lang.put("ATTEMPTINGTOSET2", languageConfig.getString("ATTEMPTINGTOSET2"));
			lang.put("CANCEL1", languageConfig.getString("CANCEL1"));
			lang.put("CANCEL2", languageConfig.getString("CANCEL2"));
			lang.put("CANCEL3", languageConfig.getString("CANCEL3"));
			lang.put("CANCEL4", languageConfig.getString("CANCEL4"));
			lang.put("CANCEL5", languageConfig.getString("CANCEL5"));
			lang.put("CANCEL6", languageConfig.getString("CANCEL6"));
			lang.put("CANCEL7", languageConfig.getString("CANCEL7"));
			lang.put("CANCEL8", languageConfig.getString("CANCEL8"));
			lang.put("COMMANDUSAGE", languageConfig.getString("COMMANDUSAGE"));
			lang.put("CURRENTLISTPACKAGES1", languageConfig.getString("CURRENTLISTPACKAGES1"));
			lang.put("CURRENTLISTPACKAGES2", languageConfig.getString("CURRENTLISTPACKAGES2"));
			lang.put("DBERROR", languageConfig.getString("DBERROR"));
			lang.put("HOWMUCH1", languageConfig.getString("HOWMUCH1"));
			lang.put("HOWMUCH2", languageConfig.getString("HOWMUCH2"));
			lang.put("HOWMUCH3", languageConfig.getString("HOWMUCH3"));
			lang.put("INVALIDSYNTAX1", languageConfig.getString("INVALIDSYNTAX1"));
			lang.put("INVALIDSYNTAX2", languageConfig.getString("INVALIDSYNTAX2"));
			lang.put("NOACCOUNT1", languageConfig.getString("NOACCOUNT1"));
			lang.put("NOACCOUNT2", languageConfig.getString("NOACCOUNT2"));
			lang.put("NOTENOUGH", languageConfig.getString("NOTENOUGH"));
			lang.put("NOWHAVE", languageConfig.getString("NOWHAVE"));
			lang.put("NOWHAS", languageConfig.getString("NOWHAS"));
			lang.put("PACKAGENOTPURCHASED", languageConfig.getString("PACKAGENOTPURCHASED"));
			lang.put("PACKAGEALREADYPURCHASED", languageConfig.getString("PACKAGEALREADYPURCHASED"));
			lang.put("PACKAGENOTFOUND", languageConfig.getString("PACKAGENOTFOUND"));
			lang.put("PACKAGENOTTHERE", languageConfig.getString("PACKAGENOTTHERE"));
			lang.put("PLUGINDISABLED", languageConfig.getString("PLUGINDISABLED"));
			lang.put("REMOVE1", languageConfig.getString("REMOVE1"));
			lang.put("REMOVE2", languageConfig.getString("REMOVE2"));
			lang.put("REMOVE3", languageConfig.getString("REMOVE3"));
			lang.put("REMOVE4", languageConfig.getString("REMOVE4"));
			lang.put("REMOVEATTEMPTING", languageConfig.getString("REMOVEATTEMPTING"));
			lang.put("REQUESTREMOVENOTDONE", languageConfig.getString("REQUESTREMOVENOTDONE"));
			lang.put("SUCCESS", languageConfig.getString("SUCCESS"));
			lang.put("SUCCESSREMOVE", languageConfig.getString("SUCCESSREMOVE"));
			lang.put("TOKENSRETURN", languageConfig.getString("TOKENSRETURN"));
			lang.put("TOKENSRETURN2", languageConfig.getString("TOKENSRETURN2"));
			lang.put("UPGRADETO1", languageConfig.getString("UPGRADETO1"));
			lang.put("UPGRADETO2", languageConfig.getString("UPGRADETO2"));
			lang.put("USERNOTFOUND", languageConfig.getString("USERNOTFOUND"));
			lang.put("FROM", languageConfig.getString("FROM"));
			
			lang.put("SIGN_PACKAGENOTEXIST1", languageConfig.getString("SIGN_PACKAGENOTEXIST1"));
			lang.put("SIGN_PACKAGENOTEXIST2", languageConfig.getString("SIGN_PACKAGENOTEXIST2"));
			lang.put("SIGN_PACKAGENOTEXIST3", languageConfig.getString("SIGN_PACKAGENOTEXIST3"));
			lang.put("SIGN_WHOLENUMBER1", languageConfig.getString("SIGN_WHOLENUMBER1"));
			lang.put("SIGN_WHOLENUMBER2", languageConfig.getString("SIGN_WHOLENUMBER2"));
			lang.put("SIGN_EXCHANGEATTEMPTING1", languageConfig.getString("SIGN_EXCHANGEATTEMPTING2"));
			lang.put("SIGN_SUCCESS", languageConfig.getString("SIGN_SUCCESS"));
			lang.put("SIGN_NOWHAVE", languageConfig.getString("SIGN_NOWHAVE"));
			lang.put("SIGN_EXCHANGE", languageConfig.getString("SIGN_EXCHANGE"));
			lang.put("SIGN_NAMENOTFOUND", languageConfig.getString("SIGN_NAMENOTFOUND"));
			lang.put("SIGN_NOMONEY", languageConfig.getString("SIGN_NOMONEY"));
			lang.put("SIGN_NOTOKENS", languageConfig.getString("SIGN_NOTOKENS"));
			lang.put("SIGN_UHOHERROR1", languageConfig.getString("SIGN_UHOHERROR1"));
			lang.put("SIGN_UHOHERROR2", languageConfig.getString("SIGN_UHOHERROR2"));
			lang.put("SIGN_SELLATTEMPTING1", languageConfig.getString("SIGN_SELLATTEMPTING2"));
			lang.put("SIGN_SELLDBERROR", languageConfig.getString("SIGN_SELLDBERROR"));
			lang.put("SIGN_TRANSACTIONERROR", languageConfig.getString("SIGN_TRANSACTIONERROR"));
			lang.put("SIGN_FOR", languageConfig.getString("SIGN_FOR"));
		}*/
	}

}
