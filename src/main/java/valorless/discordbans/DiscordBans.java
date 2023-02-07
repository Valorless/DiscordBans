package valorless.discordbans;

import java.util.logging.Level;

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
		Config.OnInitiate();
		
		getServer().getPluginManager().registerEvents(new BanListener(), this);
		getServer().getPluginManager().registerEvents(new CommandListener(), this);
		
		getLogger().info("Bans: " + Config.GetBool("bans"));
		getLogger().info("Tempbans: " + Config.GetBool("tempbans"));
		getLogger().info("Unbans: " + Config.GetBool("unbans"));
		getLogger().info("IP-Bans: " + Config.GetBool("banips"));
		getLogger().info("IP-Unbans: " + Config.GetBool("unbanips"));
		if(Config.GetBool("debug")) {
		Log("Debugging enabled.");
		}
		if(Config.GetString("webhook-url") == "") {
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
    
    
    
    public void Log(String msg) {
    	if(Config.GetBool("debug") == true) {
    		getLogger().info(msg);
    	}
    }
    
}

