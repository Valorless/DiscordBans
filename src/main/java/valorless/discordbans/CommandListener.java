package valorless.discordbans;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandListener implements Listener { // Primary objective of CommandListener is to listen for DiscordBans commands.
	public static JavaPlugin plugin;
	String Name = "§7[§4DiscordBans§7]§r";
	
	public void onEnable() {
	}
	
	@EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		String[] args = event.getMessage().split("\\s+");
		Player sender = event.getPlayer();
		
		if(args[0].equalsIgnoreCase("/db")) {
			if(args.length == 1) {
			sender.sendMessage(Name + " DiscordBans by Valorless. Send bans, tempbans, and unbans to Discord.");
			}
			else 
			if (args.length >= 2){
				if(args[1].equalsIgnoreCase("reload") && sender.hasPermission("discordbans.reload")) {
					Config.Reload();
					sender.sendMessage(Name +" §aReloaded.");
					Log.Info(Name + " §aReloaded!");
					if(Config.GetString("webhook-url") == "") {
						Log.Info("§cDisabled!");
						DiscordBans.enabled = false;
					}
				}
				if(args[1].equalsIgnoreCase("disable") && sender.hasPermission("discordbans.disable")) {
					sender.sendMessage(Name +" §cDisabled!");
					Log.Info("§cDisabled!");
					plugin.getServer().getPluginManager().disablePlugin(plugin);
				}
				if(args[1].equalsIgnoreCase("debug") && sender.hasPermission("discordbans.debug")) {
					if(Config.GetBool("Debug") == false) {
						Config.SetBool("Debug", true);
						sender.sendMessage(Name +" §eDebugging enabled.");
						Log.Info("§eDebugging Enabled!");
					} else {
						Config.SetBool("Debug", false);
						sender.sendMessage(Name +" §eDebugging disabled.");
						Log.Info("§eDebugging Disabled!");
					}
				}
			}
		}
	}

}
