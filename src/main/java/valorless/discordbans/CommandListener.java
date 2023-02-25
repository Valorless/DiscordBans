package valorless.discordbans;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.java.JavaPlugin;

import valorless.valorlessutils.ValorlessUtils.*;

public class CommandListener implements Listener { // Primary objective of CommandListener is to listen for DiscordBans commands.
	public static JavaPlugin plugin;
	String Name = "§7[§4DiscordBans§7]§r";
	
	public void onEnable() {
	}
	
	@EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		String[] args = event.getMessage().split("\\s+");
		//Player sender = event.getPlayer();
		CommandSender sender = event.getPlayer();
		ProcessCommand(args, sender, false);
	}
	
	@EventHandler
    public void onServerCommand(ServerCommandEvent event) {
		String[] args = event.getCommand().split("\\s+");
		CommandSender console = event.getSender();
		args[0] = "/" + args[0];
		ProcessCommand(args, console, true);
	}
	
	@EventHandler
	public void ProcessCommand(String[] args, CommandSender sender, Boolean console) {
		
		if(args[0].equalsIgnoreCase("/db")) {			
			if(args.length == 1) {
			sender.sendMessage(Name + " DiscordBans by Valorless. Send bans, tempbans, and unbans to Discord.");
			}
			else 
			if (args.length >= 2){
				if(args[1].equalsIgnoreCase("reload") && sender.hasPermission("discordbans.reload")) {
					Config.Reload(plugin);
					sender.sendMessage(Name +" §aReloaded.");
					if(!console) { Log.Info(plugin, "§aReloaded!"); }
					if(Config.GetString(plugin, "webhook-url") == "") {
						Log.Info(plugin, "Disabled!");
						DiscordBans.enabled = false;
					}
				}
				if(args[1].equalsIgnoreCase("disable") && sender.hasPermission("discordbans.disable")) {
					sender.sendMessage(Name +" §cDisabled!");
					Log.Info(plugin, "Disabled!");
					plugin.getServer().getPluginManager().disablePlugin(plugin);
				}
				if(args[1].equalsIgnoreCase("debug") && sender.hasPermission("discordbans.debug")) {
					if(Config.GetBool(plugin, "Debug") == false) {
						Config.SetBool(plugin, "Debug", true);
						sender.sendMessage(Name + " §eDebugging enabled.");
						if(!console) { Log.Info(plugin, "§eDebugging Enabled!"); }
					} else {
						Config.SetBool(plugin, "Debug", false);
						sender.sendMessage(Name + " §eDebugging disabled.");
						if(!console) { Log.Info(plugin, "§eDebugging Disabled!"); }
					}
				}
			}
		}
	}

}
