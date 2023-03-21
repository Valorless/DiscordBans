package valorless.discordbans;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import valorless.valorlessutils.ValorlessUtils.*;
import valorless.valorlessutils.config.Config;


public final class DiscordBans extends JavaPlugin implements Listener {
	public static JavaPlugin plugin;
	public enum BanType { ban, unban, tempban, ipban, ipunban }
	String Name = "§7[§4DiscordBans§7]§r";
	public static Boolean enabled = true;
	public static Config config;
	
	public void onLoad() {
		plugin = this;
		BanListener.plugin = this;
		CommandListener.plugin = this;
		config = new Config(this, "config.yml");
		CommandListener.plugin = this;
		Lang.messages = new Config(this, "messages.yml");
	}
	
	@Override
    public void onEnable() {
		//Config
		config.AddValidationEntry("webhook-url", "");
		config.AddValidationEntry("bot-name", "George");
		config.AddValidationEntry("bot-picture", "https://i.pinimg.com/originals/bf/23/ca/bf23ca87c2a867e2b3b991e76d982abd.jpg");
		config.AddValidationEntry("ban-color", "#ff2b2b");
		config.AddValidationEntry("tempban-color", "#ff992b");
		config.AddValidationEntry("unban-color", "#2afa4d");
		config.AddValidationEntry("banip-color", "#5b09ad");
		config.AddValidationEntry("unbanip-color", "#0ce6fa");
		config.AddValidationEntry("bans", true);
		config.AddValidationEntry("tempbans", true);
		config.AddValidationEntry("unbans", true);
		config.AddValidationEntry("banips", true);
		config.AddValidationEntry("unbanips", true);
		config.AddValidationEntry("debug", false);

		//Lang
		Lang.messages.AddValidationEntry("bot-message", "");
		Lang.messages.AddValidationEntry("banned-title", "%target% has been banned!");
		Lang.messages.AddValidationEntry("tempbanned-title", "%target% has been temp banned!");
		Lang.messages.AddValidationEntry("unbanned-title", "%target% has been unbanned!");
		Lang.messages.AddValidationEntry("ip-banned-title", "IP: %target% has been banned!");
		Lang.messages.AddValidationEntry("ip-unbanned-title", "IP: %target% has been unbanned!");
		Lang.messages.AddValidationEntry("description", "");
		Lang.messages.AddValidationEntry("reason-line1", "Reason: ");
		Lang.messages.AddValidationEntry("reason-line2", "%reason%");
		Lang.messages.AddValidationEntry("banned-by-line1", "Banned by: ");
		Lang.messages.AddValidationEntry("banned-by-line2", "%sender%");
		Lang.messages.AddValidationEntry("unbanned-by-line1", "Unbanned by: ");
		Lang.messages.AddValidationEntry("unbanned-by-line2", "%sender%");
		Lang.messages.AddValidationEntry("duration-line1", "Duration: ");
		Lang.messages.AddValidationEntry("duration-line2", "%duration%");
		Lang.messages.AddValidationEntry("banned-on", "Banned on %date%");
				
		getServer().getPluginManager().registerEvents(new BanListener(), this);
		getServer().getPluginManager().registerEvents(new CommandListener(), this);
		
		Log.Info(plugin, "Bans: " + config.GetBool("bans"));
		Log.Info(plugin, "Tempbans: " + config.GetBool("tempbans"));
		Log.Info(plugin, "Unbans: " + config.GetBool("unbans"));
		Log.Info(plugin, "IP-Bans: " + config.GetBool("banips"));
		Log.Info(plugin, "IP-Unbans: " + config.GetBool("unbanips"));
		
		if(config.GetBool("debug")) {
		Log.Info(plugin, "Debugging enabled.");
		}
		if(config.GetString("webhook-url") == "") {
			Log.Warning(plugin, "Please change my config.yml before using me.\nYou can reload me when needed with /db reload.");
			Log.Info(plugin, "§cDisabled!");
			enabled = false;
		}
		else {
			if(config.GetBool("debug")) {
				Log.Info(plugin, "§aEnabled!");
			}
		}
		getCommand("db").setExecutor(this);
		getCommand("db reload").setExecutor(this);
		getCommand("db disable").setExecutor(this);
		getCommand("db debug").setExecutor(this);
		
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
    	if(config.GetBool("debug")) {
    		Log.Info(plugin, "§cDisabled!");
    	}
    }

    
}

