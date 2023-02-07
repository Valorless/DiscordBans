package valorless.discordbans;
import valorless.discordbans.DiscordBans.BanType;

import org.bukkit.plugin.java.JavaPlugin;

import java.awt.Color;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
			Logger.getLogger("Minecraft").log(Level.WARNING, "Please change my config.yml before using me.\nYou can reload me when needed with /db reload.");
		} 
		else {
			if(args[0].equalsIgnoreCase("/ban") && args.length >= 2 && Config.GetBool("bans") == true) {
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
			if(args[0].equalsIgnoreCase("/tempban") && args.length >= 3 && Config.GetBool("tempbans") == true) {
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
			if(args[0].equalsIgnoreCase("/unban") && args.length >= 2 && Config.GetBool("unbans") == true || 
			args[0].equalsIgnoreCase("/pardon") && args.length >= 2 && Config.GetBool("unbans") == true) {
				if(sender.hasPermission("minecraft.command.pardon") || sender.hasPermission("essentials.unban")) {
					Date now = new Date();
					String target = args[1];
					if (target != "") {
						SendWebhook(BanType.unban, target, sender.getName(), "", now, "");
					}
				}
			}
			if(args[0].equalsIgnoreCase("/banip") && args.length >= 2 && Config.GetBool("banips") == true) {
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
			if(args[0].equalsIgnoreCase("/unbanip") && args.length >= 2 && Config.GetBool("unbanips") == true ||
			args[0].equalsIgnoreCase("/pardon-ip") && args.length >= 2 && Config.GetBool("unbanips") == true) {
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
	
	public void Log(String msg) {
		if(plugin != null) {
			if(Config.GetBool("debug") == true) {
				Logger.getLogger("Minecraft").info(msg);
    		}
		}
	}
	
	
	public void SendWebhook(BanType type, String target, String sender, String reason, Date date, String duration) {
		FormatText.Format.target = target;
		FormatText.Format.sender = sender;
		FormatText.Format.reason = reason;
		FormatText.Format.date = date.toString();
		FormatText.Format.duration = duration;
		
    	Log("Attempting to send embed to discord!");
    	Log("Type: " + type.name());
    	Log("Target: " + target);
    	Log("Sender: " + sender);
    	Log("Reason: " + reason);
    	Log("Date: " + date.toString());
    	Log("Duration: " + duration);
    	DiscordWebhook webhook = new DiscordWebhook(Config.GetString("webhook-url"));
        webhook.setContent(FormatText.Entry(Config.GetString("bot-message")));
        webhook.setAvatarUrl(Config.GetString("bot-picture"));
        webhook.setUsername(Config.GetString("bot-name"));
        webhook.setTts(false);
        if(type == BanType.ban) {
        	webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(FormatText.Entry(Config.GetString("banned-title")))
                .setDescription(FormatText.Entry(Config.GetString("description")))
                .setColor(Color.decode(Config.GetString("ban-color")))
                .addField(FormatText.Entry(Config.GetString("reason-line1")), FormatText.Entry(Config.GetString("reason-line2")), false)
                .addField(FormatText.Entry(Config.GetString("banned-by-line1")), FormatText.Entry(Config.GetString("banned-by-line2")), false)
                .setThumbnail("https://minotar.net/armor/bust/" + target + "/100.png")
                .setFooter(FormatText.Entry(Config.GetString("banned-on")), "")
                .setUrl("https://mcnames.net/username/" + target)
        	);
        }
        if(type == BanType.tempban)
        {
        	webhook.addEmbed(new DiscordWebhook.EmbedObject()
                 .setTitle(FormatText.Entry(Config.GetString("tempbanned-title")))
                .setDescription(FormatText.Entry(Config.GetString("description")))
                .setColor(Color.decode(Config.GetString("tempban-color")))
                .addField(FormatText.Entry(Config.GetString("reason-line1")), FormatText.Entry(Config.GetString("reason-line2")), false)
                .addField(FormatText.Entry(Config.GetString("banned-by-line1")), FormatText.Entry(Config.GetString("banned-by-line2")), false)
                .addField(FormatText.Entry(Config.GetString("duration-line1")), FormatText.Entry(Config.GetString("duration-line2")), false)
                .setThumbnail("https://minotar.net/armor/bust/" + target + "/100.png")
                .setFooter(FormatText.Entry(Config.GetString("banned-on")), "")
                .setUrl("https://mcnames.net/username/" + target)
            );
        }
        if(type == BanType.unban)
        {
        	webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(FormatText.Entry(Config.GetString("unbanned-title")))
                .setDescription(FormatText.Entry(Config.GetString("description")))
                .setColor(Color.decode(Config.GetString("unban-color")))
                .addField(FormatText.Entry(Config.GetString("unbanned-by-line1")), FormatText.Entry(Config.GetString("unbanned-by-line2")), false)
                .setThumbnail("https://minotar.net/armor/bust/" + target + "/100.png")
                .setFooter(FormatText.Entry(Config.GetString("unbanned-on")), "")
                .setUrl("https://mcnames.net/username/" + target)
            );
        }
        if(type == BanType.ipban) {
        	webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(FormatText.Entry(Config.GetString("ip-banned-title")))
                .setDescription(FormatText.Entry(Config.GetString("description")))
                .setColor(Color.decode(Config.GetString("banip-color")))
                .addField(FormatText.Entry(Config.GetString("reason-line1")), FormatText.Entry(Config.GetString("reason-line2")), false)
                .addField(FormatText.Entry(Config.GetString("banned-by-line1")), FormatText.Entry(Config.GetString("banned-by-line2")), false)
                .setFooter(FormatText.Entry(Config.GetString("banned-on")), "")
        	);
        }
        if(type == BanType.ipunban)
        {
        	webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(FormatText.Entry(Config.GetString("ip-unbanned-title")))
                .setDescription(FormatText.Entry(Config.GetString("description")))
                .setColor(Color.decode(Config.GetString("unbanip-color")))
                .addField(FormatText.Entry(Config.GetString("unbanned-by-line1")), FormatText.Entry(Config.GetString("unbanned-by-line2")), false)
                .setFooter(FormatText.Entry(Config.GetString("unbanned-on")), "")
            );
        }
        try {
        	Log("Executing webhook.");
			webhook.execute();
		} catch (IOException e) {
			e.printStackTrace();
			Log("&c Connection failed.");
		}
    }
}