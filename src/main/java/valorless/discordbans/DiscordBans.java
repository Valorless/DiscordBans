package valorless.discordbans;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import valorless.valorlessutils.ValorlessUtils.*;


public final class DiscordBans extends JavaPlugin implements Listener {
	public static JavaPlugin plugin;
	public enum BanType { ban, unban, tempban, ipban, ipunban }
	String Name = "§7[§4DiscordBans§7]§r";
	public static Boolean enabled = true;
	
	public void onLoad() {
		plugin = this;
		BanListener.plugin = this;
		CommandListener.plugin = this;
	}
	
	@Override
    public void onEnable() {
		Config.Load(plugin);
		
		//Config
		Config.AddValidationEntry(plugin, "webhook-url", "");
		Config.AddValidationEntry(plugin, "bot-name", "George");
		Config.AddValidationEntry(plugin, "bot-picture", "https://i.pinimg.com/originals/bf/23/ca/bf23ca87c2a867e2b3b991e76d982abd.jpg");
		Config.AddValidationEntry(plugin, "ban-color", "#ff2b2b");
		Config.AddValidationEntry(plugin, "tempban-color", "#ff992b");
		Config.AddValidationEntry(plugin, "unban-color", "#2afa4d");
		Config.AddValidationEntry(plugin, "banip-color", "#5b09ad");
		Config.AddValidationEntry(plugin, "unbanip-color", "#0ce6fa");
		Config.AddValidationEntry(plugin, "bans", true);
		Config.AddValidationEntry(plugin, "tempbans", true);
		Config.AddValidationEntry(plugin, "unbans", true);
		Config.AddValidationEntry(plugin, "banips", true);
		Config.AddValidationEntry(plugin, "unbanips", true);
		Config.AddValidationEntry(plugin, "debug", false);

		//Lang
		Config.AddValidationEntry(plugin, "bot-message", "");
		Config.AddValidationEntry(plugin, "banned-title", "%target% has been banned!");
		Config.AddValidationEntry(plugin, "tempbanned-title", "%target% has been temp banned!");
		Config.AddValidationEntry(plugin, "unbanned-title", "%target% has been unbanned!");
		Config.AddValidationEntry(plugin, "ip-banned-title", "IP: %target% has been banned!");
		Config.AddValidationEntry(plugin, "ip-unbanned-title", "IP: %target% has been unbanned!");
		Config.AddValidationEntry(plugin, "description", "");
		Config.AddValidationEntry(plugin, "reason-line1", "Reason: ");
		Config.AddValidationEntry(plugin, "reason-line2", "%reason%");
		Config.AddValidationEntry(plugin, "banned-by-line1", "Banned by: ");
		Config.AddValidationEntry(plugin, "banned-by-line2", "%sender%");
		Config.AddValidationEntry(plugin, "unbanned-by-line1", "Unbanned by: ");
		Config.AddValidationEntry(plugin, "unbanned-by-line2", "%sender%");
		Config.AddValidationEntry(plugin, "duration-line1", "Duration: ");
		Config.AddValidationEntry(plugin, "duration-line2", "%duration%");
		Config.AddValidationEntry(plugin, "banned-on", "Banned on %date%");
				
		getServer().getPluginManager().registerEvents(new BanListener(), this);
		getServer().getPluginManager().registerEvents(new CommandListener(), this);
		
		Log.Info(plugin, "Bans: " + Config.GetBool(plugin, "bans"));
		Log.Info(plugin, "Tempbans: " + Config.GetBool(plugin, "tempbans"));
		Log.Info(plugin, "Unbans: " + Config.GetBool(plugin, "unbans"));
		Log.Info(plugin, "IP-Bans: " + Config.GetBool(plugin, "banips"));
		Log.Info(plugin, "IP-Unbans: " + Config.GetBool(plugin, "unbanips"));
		
		if(Config.GetBool(plugin, "debug")) {
		Log.Info(plugin, "Debugging enabled.");
		}
		if(Config.GetString(plugin, "webhook-url") == "") {
			Log.Warning(plugin, "Please change my config.yml before using me.\nYou can reload me when needed with /db reload.");
			Log.Info(plugin, "§cDisabled!");
			enabled = false;
		}
		else {
			if(Config.GetBool(plugin, "debug")) {
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
    	if(Config.GetBool(plugin, "debug")) {
    		Log.Info(plugin, "§cDisabled!");
    	}
    }

    
}

