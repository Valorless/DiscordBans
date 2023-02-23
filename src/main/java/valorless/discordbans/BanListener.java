package valorless.discordbans;
import valorless.discordbans.DiscordBans.BanType;
import valorless.valorlessutils.ValorlessUtils.*;

import org.bukkit.plugin.java.JavaPlugin;

import java.awt.Color;
import java.io.IOException;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BanListener implements Listener { // Primary objective of BanListener is to listen for Ban commands.
	public static JavaPlugin plugin;
	String Name = "§7[§4DiscordBans§7]§r";
	
	public void onEnable() {
	}
	
	@EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		//Log("command1! " + event.getMessage());
		String[] args = event.getMessage().split("\\s+");
		Player sender = event.getPlayer();
		
		if(DiscordBans.enabled == false) {
			sender.sendMessage(Name + " Please set me up before use, I have disabled myself.");
			Log.Warning(plugin, "Please change my config.yml before using me.\nYou can reload me when needed with /db reload.");
		} 
		else {
			if(args[0].equalsIgnoreCase("/ban") && args.length >= 2 && Config.GetBool(plugin, "bans") == true) {
				if(sender.hasPermission("minecraft.command.ban") || sender.hasPermission("essentials.ban")) {
					Date now = new Date();
					String target = args[1];
					if (target != "") {
						if(args.length >= 3) {
							SendWebhook(BanType.ban, target, sender.getName(), args[2], now, "");
						}
						else {
							SendWebhook(BanType.ban, target, sender.getName(), "No reason given.", now, "");
						}
					}
				}
			}
			if(args[0].equalsIgnoreCase("/tempban") && args.length >= 3 && Config.GetBool(plugin, "tempbans") == true) {
				if(sender.hasPermission("essentials.tempban")) {
					for(Player entry:Bukkit.getServer().getOnlinePlayers())
					{
					    if(entry.getName().equalsIgnoreCase(args[1])) {  //Check if player is online
					    	Date now = new Date();
							String target = args[1];
							if (target != "") {
								if(args.length >= 4) {
									SendWebhook(BanType.tempban, target, sender.getName(), args[3], now, args[2]);
								}
								else {
									SendWebhook(BanType.tempban, target, sender.getName(), "No reason given.", now, args[2]);
								}
							}
					    }
					}
				}
			}
			if(args[0].equalsIgnoreCase("/unban") && args.length >= 2 && Config.GetBool(plugin, "unbans") == true || 
			args[0].equalsIgnoreCase("/pardon") && args.length >= 2 && Config.GetBool(plugin, "unbans") == true) {
				if(sender.hasPermission("minecraft.command.pardon") || sender.hasPermission("essentials.unban")) {
					Date now = new Date();
					String target = args[1];
					if (target != "") {
						SendWebhook(BanType.unban, target, sender.getName(), "", now, "");
					}
				}
			}
			if(args[0].equalsIgnoreCase("/banip") && args.length >= 2 && Config.GetBool(plugin, "banips") == true) {
				if(sender.hasPermission("essentials.banip")) {
					Date now = new Date();
					String target = args[1];
					if (target != "") {
						if(args.length >= 3) {
							SendWebhook(BanType.ipban, target, sender.getName(), args[2], now, "");
						}
						else {
							SendWebhook(BanType.ipban, target, sender.getName(), "No reason given.", now, "");
						}
					}
				}
			}
			if(args[0].equalsIgnoreCase("/unbanip") && args.length >= 2 && Config.GetBool(plugin, "unbanips") == true ||
			args[0].equalsIgnoreCase("/pardon-ip") && args.length >= 2 && Config.GetBool(plugin, "unbanips") == true) {
				if(sender.hasPermission("minecraft.command.pardon-ip") || sender.hasPermission("essentials.unbanip")) {
					Date now = new Date();
					String target = args[1];
					if (target != "") {
						SendWebhook(BanType.ipunban, target, sender.getName(), "", now, "");
					}
				}
			}
		}
    }
	
	public void SendWebhook(BanType type, String target, String sender, String reason, Date date, String duration) {
		Lang.AddPlaceholder(plugin, "%target%", target);
		Lang.AddPlaceholder(plugin, "%sender%", sender);
		Lang.AddPlaceholder(plugin, "%reason%", reason);
		Lang.AddPlaceholder(plugin, "%date%", date.toString());
		Lang.AddPlaceholder(plugin, "%duration%", duration);
		
    	Log.Info(plugin, "Attempting to send embed to discord!");
    	Log.Info(plugin, "Type: " + type.name());
    	Log.Info(plugin, "Target: " + target);
    	Log.Info(plugin, "Sender: " + sender);
    	Log.Info(plugin, "Reason: " + reason);
    	Log.Info(plugin, "Date: " + date.toString());
    	Log.Info(plugin, "Duration: " + duration);
    	DiscordWebhook webhook = new DiscordWebhook(Config.GetString(plugin, "webhook-url"));
        webhook.setContent(Lang.Get(plugin, Config.GetString(plugin, "bot-message")));
        webhook.setAvatarUrl(Config.GetString(plugin, "bot-picture"));
        webhook.setUsername(Config.GetString(plugin, "bot-name"));
        webhook.setTts(false);
        if(type == BanType.ban) {
        	webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(Lang.Get(plugin, Config.GetString(plugin, "banned-title")))
                .setDescription(Lang.Get(plugin, Config.GetString(plugin, "description")))
                .setColor(Color.decode(Config.GetString(plugin, "ban-color")))
                .addField(Lang.Get(plugin, Config.GetString(plugin, "reason-line1")), Lang.Get(plugin, Config.GetString(plugin, "reason-line2")), false)
                .addField(Lang.Get(plugin, Config.GetString(plugin, "banned-by-line1")), Lang.Get(plugin, Config.GetString(plugin, "banned-by-line2")), false)
                .setThumbnail("https://minotar.net/armor/bust/" + target + "/100.png")
                .setFooter(Lang.Get(plugin, Config.GetString(plugin, "banned-on")), "")
                .setUrl("https://mcnames.net/username/" + target)
        	);
        }
        if(type == BanType.tempban)
        {
        	webhook.addEmbed(new DiscordWebhook.EmbedObject()
                 .setTitle(Lang.Get(plugin, Config.GetString(plugin, "tempbanned-title")))
                .setDescription(Lang.Get(plugin, Config.GetString(plugin, "description")))
                .setColor(Color.decode(Config.GetString(plugin, "tempban-color")))
                .addField(Lang.Get(plugin, Config.GetString(plugin, "reason-line1")), Lang.Get(plugin, Config.GetString(plugin, "reason-line2")), false)
                .addField(Lang.Get(plugin, Config.GetString(plugin, "banned-by-line1")), Lang.Get(plugin, Config.GetString(plugin, "banned-by-line2")), false)
                .addField(Lang.Get(plugin, Config.GetString(plugin, "duration-line1")), Lang.Get(plugin, Config.GetString(plugin, "duration-line2")), false)
                .setThumbnail("https://minotar.net/armor/bust/" + target + "/100.png")
                .setFooter(Lang.Get(plugin, Config.GetString(plugin, "banned-on")), "")
                .setUrl("https://mcnames.net/username/" + target)
            );
        }
        if(type == BanType.unban)
        {
        	webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(Lang.Get(plugin, Config.GetString(plugin, "unbanned-title")))
                .setDescription(Lang.Get(plugin, Config.GetString(plugin, "description")))
                .setColor(Color.decode(Config.GetString(plugin, "unban-color")))
                .addField(Lang.Get(plugin, Config.GetString(plugin, "unbanned-by-line1")), Lang.Get(plugin, Config.GetString(plugin, "unbanned-by-line2")), false)
                .setThumbnail("https://minotar.net/armor/bust/" + target + "/100.png")
                .setFooter(Lang.Get(plugin, Config.GetString(plugin, "unbanned-on")), "")
                .setUrl("https://mcnames.net/username/" + target)
            );
        }
        if(type == BanType.ipban) {
        	webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(Lang.Get(plugin, Config.GetString(plugin, "ip-banned-title")))
                .setDescription(Lang.Get(plugin, Config.GetString(plugin, "description")))
                .setColor(Color.decode(Config.GetString(plugin, "banip-color")))
                .addField(Lang.Get(plugin, Config.GetString(plugin, "reason-line1")), Lang.Get(plugin, Config.GetString(plugin, "reason-line2")), false)
                .addField(Lang.Get(plugin, Config.GetString(plugin, "banned-by-line1")), Lang.Get(plugin, Config.GetString(plugin, "banned-by-line2")), false)
                .setFooter(Lang.Get(plugin, Config.GetString(plugin, "banned-on")), "")
        	);
        }
        if(type == BanType.ipunban)
        {
        	webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(Lang.Get(plugin, Config.GetString(plugin, "ip-unbanned-title")))
                .setDescription(Lang.Get(plugin, Config.GetString(plugin, "description")))
                .setColor(Color.decode(Config.GetString(plugin, "unbanip-color")))
                .addField(Lang.Get(plugin, Config.GetString(plugin, "unbanned-by-line1")), Lang.Get(plugin, Config.GetString(plugin, "unbanned-by-line2")), false)
                .setFooter(Lang.Get(plugin, Config.GetString(plugin, "unbanned-on")), "")
            );
        }
        try {
        	Log.Info(plugin, "Executing webhook.");
			webhook.execute();
		} catch (IOException e) {
			e.printStackTrace();
			Log.Error(plugin, "&cConnection failed.");
		}
    }
}