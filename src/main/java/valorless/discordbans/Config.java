package valorless.discordbans;

import java.io.File;
import org.bukkit.Color;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
	public static JavaPlugin plugin;

	public void OnEnable() {
	}
	
	public static void Initiate() {
		//plugin = DiscordBans.instance;
		Load();
		
	}
	
	public static void Load() {
    	File configFile = new File(plugin.getDataFolder(), "config.yml");
    	if(!configFile.exists()) {
    		Log.Info("No config exists, creating new.");
    		plugin.saveDefaultConfig();
    	}
    	else {
    		Log.Info("Loading config.");
    		plugin.getConfig();
    	}
    }
	
	public static void Reload() {
		plugin.reloadConfig();
	}
	
	
	// GET
	
	public static String GetString(String key) {
		String i = "";
		i = plugin.getConfig().getString(key);
		return i;
	}
	
	public static Boolean GetBool(String key) {
		Boolean i = false;
		i = plugin.getConfig().getBoolean(key);
		return i;
	}
	
	public static Integer GetInt(String key) {
		Integer i = 0;
		i = plugin.getConfig().getInt(key);
		return i;
	}
	
	public static Double GetFloat(String key) {
		Double i = 0.0;
		i = plugin.getConfig().getDouble(key);
		return i;
	}
	
	public static Color GetColor(String key) {
		Color i = Color.WHITE;
		i = plugin.getConfig().getColor(key);
		return i;
	}
	
	// SET
	
	public static void SetString(String key, String value) {
		plugin.getConfig().set(key, value);
	}
	
	public static void SetBool(String key, Boolean value) {
		plugin.getConfig().set(key, value);
	}
	
	public static void SetInt(String key, String value) {
		plugin.getConfig().set(key, value);
	}
	
	public static void SetFloat(String key, String value) {
		plugin.getConfig().set(key, value);
	}
	
	public static void SetColor(String key, Color value) {
		plugin.getConfig().set(key, value);
	}
	
}
