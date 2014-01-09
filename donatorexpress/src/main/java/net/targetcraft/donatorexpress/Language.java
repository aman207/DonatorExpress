package net.targetcraft.donatorexpress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Language {
	
	static Main plugin;
	static HashMap<String, String> lang = new HashMap<String, String>();

	public Language(Main config)
	{
		plugin=config;
	}
	
	public static String getPhrase(String phrase)
	{
		return lang.get(phrase);
	}
	
	public void languageInitialize()
	{
		if(plugin.getConfig().getString("language").equals("en"))
		{
			File english = new File(plugin.getDataFolder()+"/languages", "en.yml");
			FileConfiguration englishConfig = null;
			englishConfig = new YamlConfiguration();
			try {
				englishConfig.load(english);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
			
			lang.put("ADDATTEMPTING", englishConfig.getString("ADDATTEMPTING"));
			lang.put("ATTEMPTINGTOGIVE", englishConfig.getString("ATTEMPTINGTOGIVE"));
			lang.put("ATTEMPTINGTOSET1", englishConfig.getString("ATTEMPTINGTOSET1"));
			lang.put("ATTEMPTINGTOSET2", englishConfig.getString("ATTEMPTINGTOSET2"));
			lang.put("ALREADYEXISTS", englishConfig.getString("ALREADYEXISTS"));
			lang.put("CANCEL1", englishConfig.getString("CANCEL1"));
			lang.put("CANCEL2", englishConfig.getString("CANCEL2"));
			lang.put("CANCEL3", englishConfig.getString("CANCEL3"));
			lang.put("CANCEL4", englishConfig.getString("CANCEL4"));
			lang.put("CANCEL5", englishConfig.getString("CANCEL5"));
			lang.put("CANCEL6", englishConfig.getString("CANCEL6"));
			lang.put("CANCEL7", englishConfig.getString("CANCEL7"));
			lang.put("CANCEL8", englishConfig.getString("CANCEL8"));
			lang.put("COMMANDUSAGE", englishConfig.getString("COMMANDUSAGE"));
			lang.put("CURRENTLISTPACKAGES1", englishConfig.getString("CURRENTLISTPACKAGES1"));
			lang.put("CURRENTLISTPACKAGES2", englishConfig.getString("CURRENTLISTPACKAGES2"));
			lang.put("DBERROR", englishConfig.getString("DBERROR"));
			lang.put("HOWMUCH1", englishConfig.getString("HOWMUCH1"));
			lang.put("HOWMUCH2", englishConfig.getString("HOWMUCH2"));
			lang.put("HOWMUCH3", englishConfig.getString("HOWMUCH3"));
			lang.put("INVALIDSYNTAX1", englishConfig.getString("INVALIDSYNTAX1"));
			lang.put("INVALIDSYNTAX2", englishConfig.getString("INVALIDSYNTAX2"));
			lang.put("NOACCOUNT1", englishConfig.getString("NOACCOUNT1"));
			lang.put("NOACCOUNT2", englishConfig.getString("NOACCOUNT2"));
			lang.put("NOTENOUGH", englishConfig.getString("NOTENOUGH"));
			lang.put("NOWHAVE", englishConfig.getString("NOWHAVE"));
			lang.put("NOWHAS", englishConfig.getString("NOWHAS"));
			lang.put("PACKAGENOTPURCHASED", englishConfig.getString("PACKAGENOTPURCHASED"));
			lang.put("PACKAGEALREADYPURCHASED", englishConfig.getString("PACKAGEALREADYPURCHASED"));
			lang.put("PACKAGENOTFOUND", englishConfig.getString("PACKAGENOTFOUND"));
			lang.put("PACKAGENOTTHERE", englishConfig.getString("PACKAGENOTTHERE"));
			lang.put("PLUGINDISABLED", englishConfig.getString("PLUGINDISABLED"));
			lang.put("REMOVE1", englishConfig.getString("REMOVE1"));
			lang.put("REMOVE2", englishConfig.getString("REMOVE2"));
			lang.put("REMOVE3", englishConfig.getString("REMOVE3"));
			lang.put("REMOVE4", englishConfig.getString("REMOVE4"));
			lang.put("REMOVEATTEMPTING", englishConfig.getString("REMOVEATTEMPTING"));
			lang.put("REQUESTREMOVENOTDONE", englishConfig.getString("REQUESTREMOVENOTDONE"));
			lang.put("SUCCESS", englishConfig.getString("SUCCESS"));
			lang.put("SUCCESSREMOVE", englishConfig.getString("SUCCESSREMOVE"));
			lang.put("TOKENSRETURN", englishConfig.getString("TOKENSRETURN"));
			lang.put("UPGRADETO1", englishConfig.getString("UPGRADETO1"));
			lang.put("UPGRADETO2", englishConfig.getString("UPGRADETO2"));
			lang.put("USERNOTFOUND", englishConfig.getString("USERNOTFOUND"));
			lang.put("FROM", englishConfig.getString("FROM"));
			
			lang.put("SIGN_PACKAGENOTEXIST1", englishConfig.getString("SIGN_PACKAGENOTEXIST1"));
			lang.put("SIGN_PACKAGENOTEXIST2", englishConfig.getString("SIGN_PACKAGENOTEXIST2"));
			lang.put("SIGN_PACKAGENOTEXIST3", englishConfig.getString("SIGN_PACKAGENOTEXIST3"));
			lang.put("SIGN_WHOLENUMBER1", englishConfig.getString("SIGN_WHOLENUMBER1"));
			lang.put("SIGN_WHOLENUMBER2", englishConfig.getString("SIGN_WHOLENUMBER2"));
			lang.put("SIGN_EXCHANGEATTEMPTING1", englishConfig.getString("SIGN_EXCHANGEATTEMPTING2"));
			lang.put("SIGN_SUCCESS", englishConfig.getString("SIGN_SUCCESS"));
			lang.put("SIGN_NOWHAVE", englishConfig.getString("SIGN_NOWHAVE"));
			lang.put("SIGN_EXCHANGE", englishConfig.getString("SIGN_EXCHANGE"));
			lang.put("SIGN_NAMENOTFOUND", englishConfig.getString("SIGN_NAMENOTFOUND"));
			lang.put("SIGN_NOMONEY", englishConfig.getString("SIGN_NOMONEY"));
			lang.put("SIGN_NOTOKENS", englishConfig.getString("SIGN_NOTOKENS"));
			lang.put("SIGN_UHOHERROR1", englishConfig.getString("SIGN_UHOHERROR1"));
			lang.put("SIGN_UHOHERROR2", englishConfig.getString("SIGN_UHOHERROR2"));
			lang.put("SIGN_SELLATTEMPTING1", englishConfig.getString("SIGN_SELLATTEMPTING2"));
			lang.put("SIGN_SELLDBERROR", englishConfig.getString("SIGN_SELLDBERROR"));
			lang.put("SIGN_TRANSACTIONERROR", englishConfig.getString("SIGN_TRANSACTIONERROR"));
			lang.put("SIGN_FOR", englishConfig.getString("SIGN_FOR"));
		}
		else if(plugin.getConfig().getString("language").equals("fr"))
		{
			File french = new File(plugin.getDataFolder()+"/languages", "fr.yml");
			FileConfiguration frenchConfig = null;
			frenchConfig = new YamlConfiguration();
			try {
				frenchConfig.load(french);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
			
			lang.put("ADDATTEMPTING", frenchConfig.getString("ADDATTEMPTING"));
			lang.put("ALREADYEXISTS", frenchConfig.getString("ALREADYEXISTS"));
			lang.put("ATTEMPTINGTOGIVE", frenchConfig.getString("ATTEMPTINGTOGIVE"));
			lang.put("ATTEMPTINGTOSET1", frenchConfig.getString("ATTEMPTINGTOSET1"));
			lang.put("ATTEMPTINGTOSET2", frenchConfig.getString("ATTEMPTINGTOSET2"));
			lang.put("CANCEL1", frenchConfig.getString("CANCEL1"));
			lang.put("CANCEL2", frenchConfig.getString("CANCEL2"));
			lang.put("CANCEL3", frenchConfig.getString("CANCEL3"));
			lang.put("CANCEL4", frenchConfig.getString("CANCEL4"));
			lang.put("CANCEL5", frenchConfig.getString("CANCEL5"));
			lang.put("CANCEL6", frenchConfig.getString("CANCEL6"));
			lang.put("CANCEL7", frenchConfig.getString("CANCEL7"));
			lang.put("CANCEL8", frenchConfig.getString("CANCEL8"));
			lang.put("COMMANDUSAGE", frenchConfig.getString("COMMANDUSAGE"));
			lang.put("CURRENTLISTPACKAGES1", frenchConfig.getString("CURRENTLISTPACKAGES1"));
			lang.put("CURRENTLISTPACKAGES2", frenchConfig.getString("CURRENTLISTPACKAGES2"));
			lang.put("DBERROR", frenchConfig.getString("DBERROR"));
			lang.put("HOWMUCH1", frenchConfig.getString("HOWMUCH1"));
			lang.put("HOWMUCH2", frenchConfig.getString("HOWMUCH2"));
			lang.put("HOWMUCH3", frenchConfig.getString("HOWMUCH3"));
			lang.put("INVALIDSYNTAX1", frenchConfig.getString("INVALIDSYNTAX1"));
			lang.put("INVALIDSYNTAX2", frenchConfig.getString("INVALIDSYNTAX2"));
			lang.put("NOACCOUNT1", frenchConfig.getString("NOACCOUNT1"));
			lang.put("NOACCOUNT2", frenchConfig.getString("NOACCOUNT2"));
			lang.put("NOTENOUGH", frenchConfig.getString("NOTENOUGH"));
			lang.put("NOWHAVE", frenchConfig.getString("NOWHAVE"));
			lang.put("NOWHAS", frenchConfig.getString("NOWHAS"));
			lang.put("PACKAGENOTPURCHASED", frenchConfig.getString("PACKAGENOTPURCHASED"));
			lang.put("PACKAGEALREADYPURCHASED", frenchConfig.getString("PACKAGEALREADYPURCHASED"));
			lang.put("PACKAGENOTFOUND", frenchConfig.getString("PACKAGENOTFOUND"));
			lang.put("PACKAGENOTTHERE", frenchConfig.getString("PACKAGENOTTHERE"));
			lang.put("PLUGINDISABLED", frenchConfig.getString("PLUGINDISABLED"));
			lang.put("REMOVE1", frenchConfig.getString("REMOVE1"));
			lang.put("REMOVE2", frenchConfig.getString("REMOVE2"));
			lang.put("REMOVE3", frenchConfig.getString("REMOVE3"));
			lang.put("REMOVE4", frenchConfig.getString("REMOVE4"));
			lang.put("REMOVEATTEMPTING", frenchConfig.getString("REMOVEATTEMPTING"));
			lang.put("REQUESTREMOVENOTDONE", frenchConfig.getString("REQUESTREMOVENOTDONE"));
			lang.put("SUCCESS", frenchConfig.getString("SUCCESS"));
			lang.put("SUCCESSREMOVE", frenchConfig.getString("SUCCESSREMOVE"));
			lang.put("TOKENSRETURN", frenchConfig.getString("TOKENSRETURN"));
			lang.put("UPGRADETO1", frenchConfig.getString("UPGRADETO1"));
			lang.put("UPGRADETO2", frenchConfig.getString("UPGRADETO2"));
			lang.put("USERNOTFOUND", frenchConfig.getString("USERNOTFOUND"));
			lang.put("FROM", frenchConfig.getString("FROM"));
			
			lang.put("SIGN_PACKAGENOTEXIST1", frenchConfig.getString("SIGN_PACKAGENOTEXIST1"));
			lang.put("SIGN_PACKAGENOTEXIST2", frenchConfig.getString("SIGN_PACKAGENOTEXIST2"));
			lang.put("SIGN_PACKAGENOTEXIST3", frenchConfig.getString("SIGN_PACKAGENOTEXIST3"));
			lang.put("SIGN_WHOLENUMBER1", frenchConfig.getString("SIGN_WHOLENUMBER1"));
			lang.put("SIGN_WHOLENUMBER2", frenchConfig.getString("SIGN_WHOLENUMBER2"));
			lang.put("SIGN_EXCHANGEATTEMPTING1", frenchConfig.getString("SIGN_EXCHANGEATTEMPTING2"));
			lang.put("SIGN_SUCCESS", frenchConfig.getString("SIGN_SUCCESS"));
			lang.put("SIGN_NOWHAVE", frenchConfig.getString("SIGN_NOWHAVE"));
			lang.put("SIGN_EXCHANGE", frenchConfig.getString("SIGN_EXCHANGE"));
			lang.put("SIGN_NAMENOTFOUND", frenchConfig.getString("SIGN_NAMENOTFOUND"));
			lang.put("SIGN_NOMONEY", frenchConfig.getString("SIGN_NOMONEY"));
			lang.put("SIGN_NOTOKENS", frenchConfig.getString("SIGN_NOTOKENS"));
			lang.put("SIGN_UHOHERROR1", frenchConfig.getString("SIGN_UHOHERROR1"));
			lang.put("SIGN_UHOHERROR2", frenchConfig.getString("SIGN_UHOHERROR2"));
			lang.put("SIGN_SELLATTEMPTING1", frenchConfig.getString("SIGN_SELLATTEMPTING2"));
			lang.put("SIGN_SELLDBERROR", frenchConfig.getString("SIGN_SELLDBERROR"));
			lang.put("SIGN_TRANSACTIONERROR", frenchConfig.getString("SIGN_TRANSACTIONERROR"));
			lang.put("SIGN_FOR", frenchConfig.getString("SIGN_FOR"));
		}
		else if(plugin.getConfig().getString("language").equals("es"))
		{
			File spanish = new File(plugin.getDataFolder()+"/languages", "es.yml");
			FileConfiguration spanishConfig = null;
			spanishConfig = new YamlConfiguration();
			try {
				spanishConfig.load(spanish);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
			
			lang.put("ADDATTEMPTING", spanishConfig.getString("ADDATTEMPTING"));
			lang.put("ALREADYEXISTS", spanishConfig.getString("ALREADYEXISTS"));
			lang.put("ATTEMPTINGTOGIVE", spanishConfig.getString("ATTEMPTINGTOGIVE"));
			lang.put("ATTEMPTINGTOSET1", spanishConfig.getString("ATTEMPTINGTOSET1"));
			lang.put("ATTEMPTINGTOSET2", spanishConfig.getString("ATTEMPTINGTOSET2"));
			lang.put("CANCEL1", spanishConfig.getString("CANCEL1"));
			lang.put("CANCEL2", spanishConfig.getString("CANCEL2"));
			lang.put("CANCEL3", spanishConfig.getString("CANCEL3"));
			lang.put("CANCEL4", spanishConfig.getString("CANCEL4"));
			lang.put("CANCEL5", spanishConfig.getString("CANCEL5"));
			lang.put("CANCEL6", spanishConfig.getString("CANCEL6"));
			lang.put("CANCEL7", spanishConfig.getString("CANCEL7"));
			lang.put("CANCEL8", spanishConfig.getString("CANCEL8"));
			lang.put("COMMANDUSAGE", spanishConfig.getString("COMMANDUSAGE"));
			lang.put("CURRENTLISTPACKAGES1", spanishConfig.getString("CURRENTLISTPACKAGES1"));
			lang.put("CURRENTLISTPACKAGES2", spanishConfig.getString("CURRENTLISTPACKAGES2"));
			lang.put("DBERROR", spanishConfig.getString("DBERROR"));
			lang.put("HOWMUCH1", spanishConfig.getString("HOWMUCH1"));
			lang.put("HOWMUCH2", spanishConfig.getString("HOWMUCH2"));
			lang.put("HOWMUCH3", spanishConfig.getString("HOWMUCH3"));
			lang.put("INVALIDSYNTAX1", spanishConfig.getString("INVALIDSYNTAX1"));
			lang.put("INVALIDSYNTAX2", spanishConfig.getString("INVALIDSYNTAX2"));
			lang.put("NOACCOUNT1", spanishConfig.getString("NOACCOUNT1"));
			lang.put("NOACCOUNT2", spanishConfig.getString("NOACCOUNT2"));
			lang.put("NOTENOUGH", spanishConfig.getString("NOTENOUGH"));
			lang.put("NOWHAVE", spanishConfig.getString("NOWHAVE"));
			lang.put("NOWHAS", spanishConfig.getString("NOWHAS"));
			lang.put("PACKAGENOTPURCHASED", spanishConfig.getString("PACKAGENOTPURCHASED"));
			lang.put("PACKAGEALREADYPURCHASED", spanishConfig.getString("PACKAGEALREADYPURCHASED"));
			lang.put("PACKAGENOTFOUND", spanishConfig.getString("PACKAGENOTFOUND"));
			lang.put("PACKAGENOTTHERE", spanishConfig.getString("PACKAGENOTTHERE"));
			lang.put("PLUGINDISABLED", spanishConfig.getString("PLUGINDISABLED"));
			lang.put("REMOVE1", spanishConfig.getString("REMOVE1"));
			lang.put("REMOVE2", spanishConfig.getString("REMOVE2"));
			lang.put("REMOVE3", spanishConfig.getString("REMOVE3"));
			lang.put("REMOVE4", spanishConfig.getString("REMOVE4"));
			lang.put("REMOVEATTEMPTING", spanishConfig.getString("REMOVEATTEMPTING"));
			lang.put("REQUESTREMOVENOTDONE", spanishConfig.getString("REQUESTREMOVENOTDONE"));
			lang.put("SUCCESS", spanishConfig.getString("SUCCESS"));
			lang.put("SUCCESSREMOVE", spanishConfig.getString("SUCCESSREMOVE"));
			lang.put("TOKENSRETURN", spanishConfig.getString("TOKENSRETURN"));
			lang.put("UPGRADETO1", spanishConfig.getString("UPGRADETO1"));
			lang.put("UPGRADETO2", spanishConfig.getString("UPGRADETO2"));
			lang.put("USERNOTFOUND", spanishConfig.getString("USERNOTFOUND"));
			lang.put("FROM", spanishConfig.getString("FROM"));
			
			lang.put("SIGN_PACKAGENOTEXIST1", spanishConfig.getString("SIGN_PACKAGENOTEXIST1"));
			lang.put("SIGN_PACKAGENOTEXIST2", spanishConfig.getString("SIGN_PACKAGENOTEXIST2"));
			lang.put("SIGN_PACKAGENOTEXIST3", spanishConfig.getString("SIGN_PACKAGENOTEXIST3"));
			lang.put("SIGN_WHOLENUMBER1", spanishConfig.getString("SIGN_WHOLENUMBER1"));
			lang.put("SIGN_WHOLENUMBER2", spanishConfig.getString("SIGN_WHOLENUMBER2"));
			lang.put("SIGN_EXCHANGEATTEMPTING1", spanishConfig.getString("SIGN_EXCHANGEATTEMPTING2"));
			lang.put("SIGN_SUCCESS", spanishConfig.getString("SIGN_SUCCESS"));
			lang.put("SIGN_NOWHAVE", spanishConfig.getString("SIGN_NOWHAVE"));
			lang.put("SIGN_EXCHANGE", spanishConfig.getString("SIGN_EXCHANGE"));
			lang.put("SIGN_NAMENOTFOUND", spanishConfig.getString("SIGN_NAMENOTFOUND"));
			lang.put("SIGN_NOMONEY", spanishConfig.getString("SIGN_NOMONEY"));
			lang.put("SIGN_NOTOKENS", spanishConfig.getString("SIGN_NOTOKENS"));
			lang.put("SIGN_UHOHERROR1", spanishConfig.getString("SIGN_UHOHERROR1"));
			lang.put("SIGN_UHOHERROR2", spanishConfig.getString("SIGN_UHOHERROR2"));
			lang.put("SIGN_SELLATTEMPTING1", spanishConfig.getString("SIGN_SELLATTEMPTING2"));
			lang.put("SIGN_SELLDBERROR", spanishConfig.getString("SIGN_SELLDBERROR"));
			lang.put("SIGN_TRANSACTIONERROR", spanishConfig.getString("SIGN_TRANSACTIONERROR"));
			lang.put("SIGN_FOR", spanishConfig.getString("SIGN_FOR"));
		}
		else if(plugin.getConfig().getString("lanauge").equals("du"))
		{
			File dutch = new File(plugin.getDataFolder()+"/languages", "du.yml");
			FileConfiguration dutchConfig = null;
			dutchConfig = new YamlConfiguration();
			try {
				dutchConfig.load(dutch);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
			
			lang.put("ADDATTEMPTING", dutchConfig.getString("ADDATTEMPTING"));
			lang.put("ALREADYEXISTS", dutchConfig.getString("ALREADYEXISTS"));
			lang.put("ATTEMPTINGTOGIVE", dutchConfig.getString("ATTEMPTINGTOGIVE"));
			lang.put("ATTEMPTINGTOSET1", dutchConfig.getString("ATTEMPTINGTOSET1"));
			lang.put("ATTEMPTINGTOSET2", dutchConfig.getString("ATTEMPTINGTOSET2"));
			lang.put("CANCEL1", dutchConfig.getString("CANCEL1"));
			lang.put("CANCEL2", dutchConfig.getString("CANCEL2"));
			lang.put("CANCEL3", dutchConfig.getString("CANCEL3"));
			lang.put("CANCEL4", dutchConfig.getString("CANCEL4"));
			lang.put("CANCEL5", dutchConfig.getString("CANCEL5"));
			lang.put("CANCEL6", dutchConfig.getString("CANCEL6"));
			lang.put("CANCEL7", dutchConfig.getString("CANCEL7"));
			lang.put("CANCEL8", dutchConfig.getString("CANCEL8"));
			lang.put("COMMANDUSAGE", dutchConfig.getString("COMMANDUSAGE"));
			lang.put("CURRENTLISTPACKAGES1", dutchConfig.getString("CURRENTLISTPACKAGES1"));
			lang.put("CURRENTLISTPACKAGES2", dutchConfig.getString("CURRENTLISTPACKAGES2"));
			lang.put("DBERROR", dutchConfig.getString("DBERROR"));
			lang.put("HOWMUCH1", dutchConfig.getString("HOWMUCH1"));
			lang.put("HOWMUCH2", dutchConfig.getString("HOWMUCH2"));
			lang.put("HOWMUCH3", dutchConfig.getString("HOWMUCH3"));
			lang.put("INVALIDSYNTAX1", dutchConfig.getString("INVALIDSYNTAX1"));
			lang.put("INVALIDSYNTAX2", dutchConfig.getString("INVALIDSYNTAX2"));
			lang.put("NOACCOUNT1", dutchConfig.getString("NOACCOUNT1"));
			lang.put("NOACCOUNT2", dutchConfig.getString("NOACCOUNT2"));
			lang.put("NOTENOUGH", dutchConfig.getString("NOTENOUGH"));
			lang.put("NOWHAVE", dutchConfig.getString("NOWHAVE"));
			lang.put("NOWHAS", dutchConfig.getString("NOWHAS"));
			lang.put("PACKAGENOTPURCHASED", dutchConfig.getString("PACKAGENOTPURCHASED"));
			lang.put("PACKAGEALREADYPURCHASED", dutchConfig.getString("PACKAGEALREADYPURCHASED"));
			lang.put("PACKAGENOTFOUND", dutchConfig.getString("PACKAGENOTFOUND"));
			lang.put("PACKAGENOTTHERE", dutchConfig.getString("PACKAGENOTTHERE"));
			lang.put("PLUGINDISABLED", dutchConfig.getString("PLUGINDISABLED"));
			lang.put("REMOVE1", dutchConfig.getString("REMOVE1"));
			lang.put("REMOVE2", dutchConfig.getString("REMOVE2"));
			lang.put("REMOVE3", dutchConfig.getString("REMOVE3"));
			lang.put("REMOVE4", dutchConfig.getString("REMOVE4"));
			lang.put("REMOVEATTEMPTING", dutchConfig.getString("REMOVEATTEMPTING"));
			lang.put("REQUESTREMOVENOTDONE", dutchConfig.getString("REQUESTREMOVENOTDONE"));
			lang.put("SUCCESS", dutchConfig.getString("SUCCESS"));
			lang.put("SUCCESSREMOVE", dutchConfig.getString("SUCCESSREMOVE"));
			lang.put("TOKENSRETURN", dutchConfig.getString("TOKENSRETURN"));
			lang.put("UPGRADETO1", dutchConfig.getString("UPGRADETO1"));
			lang.put("UPGRADETO2", dutchConfig.getString("UPGRADETO2"));
			lang.put("USERNOTFOUND", dutchConfig.getString("USERNOTFOUND"));
			lang.put("FROM", dutchConfig.getString("FROM"));
			
			lang.put("SIGN_PACKAGENOTEXIST1", dutchConfig.getString("SIGN_PACKAGENOTEXIST1"));
			lang.put("SIGN_PACKAGENOTEXIST2", dutchConfig.getString("SIGN_PACKAGENOTEXIST2"));
			lang.put("SIGN_PACKAGENOTEXIST3", dutchConfig.getString("SIGN_PACKAGENOTEXIST3"));
			lang.put("SIGN_WHOLENUMBER1", dutchConfig.getString("SIGN_WHOLENUMBER1"));
			lang.put("SIGN_WHOLENUMBER2", dutchConfig.getString("SIGN_WHOLENUMBER2"));
			lang.put("SIGN_EXCHANGEATTEMPTING1", dutchConfig.getString("SIGN_EXCHANGEATTEMPTING2"));
			lang.put("SIGN_SUCCESS", dutchConfig.getString("SIGN_SUCCESS"));
			lang.put("SIGN_NOWHAVE", dutchConfig.getString("SIGN_NOWHAVE"));
			lang.put("SIGN_EXCHANGE", dutchConfig.getString("SIGN_EXCHANGE"));
			lang.put("SIGN_NAMENOTFOUND", dutchConfig.getString("SIGN_NAMENOTFOUND"));
			lang.put("SIGN_NOMONEY", dutchConfig.getString("SIGN_NOMONEY"));
			lang.put("SIGN_NOTOKENS", dutchConfig.getString("SIGN_NOTOKENS"));
			lang.put("SIGN_UHOHERROR1", dutchConfig.getString("SIGN_UHOHERROR1"));
			lang.put("SIGN_UHOHERROR2", dutchConfig.getString("SIGN_UHOHERROR2"));
			lang.put("SIGN_SELLATTEMPTING1", dutchConfig.getString("SIGN_SELLATTEMPTING2"));
			lang.put("SIGN_SELLDBERROR", dutchConfig.getString("SIGN_SELLDBERROR"));
			lang.put("SIGN_TRANSACTIONERROR", dutchConfig.getString("SIGN_TRANSACTIONERROR"));
			lang.put("SIGN_FOR", dutchConfig.getString("SIGN_FOR"));
		}
	}

}
