package valorless.discordbans;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public final class DiscordBans extends JavaPlugin implements Listener {
	public static JavaPlugin plugin;
	public enum BanType { ban, unban, tempban, ipban, ipunban }
	String Name = "§7[§4DiscordBans§7]§r";
	public static Boolean enabled = true;
	
	public void onLoad() {
		plugin = this;
		Config.plugin = this;
		BanListener.plugin = this;
		CommandListener.plugin = this;
	}
	
	@Override
    public void onEnable() {
		Config.Initiate();
		
		getServer().getPluginManager().registerEvents(new BanListener(), this);
		getServer().getPluginManager().registerEvents(new CommandListener(), this);
		
		Log.Info("Bans: " + Config.GetBool("bans"));
		Log.Info("Tempbans: " + Config.GetBool("tempbans"));
		Log.Info("Unbans: " + Config.GetBool("unbans"));
		Log.Info("IP-Bans: " + Config.GetBool("banips"));
		Log.Info("IP-Unbans: " + Config.GetBool("unbanips"));
		if(Config.GetBool("debug")) {
		Log.Info("Debugging enabled.");
		}
		if(Config.GetString("webhook-url") == "") {
			Log.Warning("Please change my config.yml before using me.\nYou can reload me when needed with /db reload.");
			Log.Info("§cDisabled!");
			enabled = false;
		}
		else {
			Log.Info("§aEnabled!");
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
		Log.Info("§cDisabled!");
    }

    
}

