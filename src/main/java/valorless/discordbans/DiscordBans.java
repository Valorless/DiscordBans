package valorless.discordbans;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;


public final class DiscordBans extends JavaPlugin implements Listener {
	public static Plugin instance;
	public enum BanType { ban, unban, tempban, ipban, ipunban }
	String Name = "§7[§4DiscordBans§7]§r";
	public static Boolean enabled = true;
	
	public void onLoad() {
		instance = this;
	}
	
	@Override
    public void onEnable() {
		LoadConfig();
		
		getServer().getPluginManager().registerEvents(new BanListener(), this);
		getServer().getPluginManager().registerEvents(new CommandListener(), this);
		
		getLogger().info("Bans: " + this.getConfig().getBoolean("bans"));
		getLogger().info("Tempbans: " + this.getConfig().getBoolean("tempbans"));
		getLogger().info("Unbans: " + this.getConfig().getBoolean("unbans"));
		getLogger().info("IP-Bans: " + this.getConfig().getBoolean("banips"));
		getLogger().info("IP-Unbans: " + this.getConfig().getBoolean("unbanips"));
		if(this.getConfig().getBoolean("debug")) {
		Log("Debugging enabled.");
		}
		if(this.getConfig().getString("webhook-url") == "") {
			getLogger().log(Level.WARNING, "Please change my config.yml before using me.\nYou can reload me when needed with /db reload.");
			getLogger().info("§cDisabled!");
			enabled = false;
		}
		else {
			getLogger().info("§aEnabled!");
		}
		getCommand("db").setExecutor(this);
		getCommand("db reload").setExecutor(this);
		
		if(!enabled) {
			for(Player player:Bukkit.getServer().getOnlinePlayers()) //Message OPs on reload or start.
			{
			    if(player.isOp()){
			            player.sendMessage(Name + " Please set me up before use, I have disabled myself.");
			    }
			}
    	}
    }
    
    @Override
    public void onDisable() {
		getLogger().info("§cDisabled!");
    }
    
    public void LoadConfig() {
    	File configFile = new File(this.getDataFolder(), "config.yml");
    	if(!configFile.exists()) {
    		Log("No config exists, creating new.");
    		saveDefaultConfig();
    	}
    	else {
    		Log("Loading config.");
    		this.getConfig();
    	}
    }
    
    public void Log(String msg) {
    	if(this.getConfig().getBoolean("debug") == true) {
    		getLogger().info(msg);
    	}
    }
    
}

