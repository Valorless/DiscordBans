package valorless.discordbans;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

public class CommandListener implements Listener { // Primary objective of CommandListener is to listen for DiscordBans commands.
	Plugin instance;
	String Name = "§7[§4DiscordBans§7]§r";
	
	public void onEnable() {
		instance = DiscordBans.instance;
	}
	
	@EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		if(instance == null) { instance = DiscordBans.instance; }
		//Log("command1! " + event.getMessage());
		String[] args = event.getMessage().split("\\s+");
		Player sender = event.getPlayer();
		
		if(args[0].equalsIgnoreCase("/db") && sender.hasPermission("discordbans.reload")) {
			if(args.length == 1) {
			sender.sendMessage(Name + " DiscordBans by Valorless. Send bans, tempbans, and unbans to Discord.");
			}
			else 
			if (args.length >= 2){
				if(args[1].equalsIgnoreCase("reload")) {
					instance.reloadConfig();
					sender.sendMessage(Name +" §aReloaded.");
					Logger.getLogger("Minecraft").info(Name + " §aReloaded!");
					if(instance.getConfig().getString("webhook-url") == "") {
						Logger.getLogger("Minecraft").info("§cDisabled!");
						DiscordBans.enabled = false;
					}
				}
				if(args[1].equalsIgnoreCase("disable")) {
					sender.sendMessage(Name +" §cDisabled!");
					Logger.getLogger("Minecraft").info("§cDisabled!");
					instance.getServer().getPluginManager().disablePlugin(instance);
				}
				if(args[1].equalsIgnoreCase("debug")) {
					if(instance.getConfig().getBoolean("Debug") == false) {
						instance.getConfig().set("Debug", true);
						sender.sendMessage(Name +" §eDebugging enabled.");
						Logger.getLogger("Minecraft").info("§eDebugging Enabled!");
					} else {
						instance.getConfig().set("Debug", false);
						sender.sendMessage(Name +" §eDebugging disabled.");
						Logger.getLogger("Minecraft").info("§eDebugging Disabled!");
					}
				}
			}
		}
	}

}
