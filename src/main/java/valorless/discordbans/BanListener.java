package valorless.discordbans;
import valorless.discordbans.DiscordBans.BanType;

import org.bukkit.plugin.*;

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

public class BanListener implements Listener {
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
			else if (args.length >= 2){
				if(args[1].equalsIgnoreCase("reload")) {
					instance.reloadConfig();
					sender.sendMessage("§aDiscordBans reloaded.");
					Logger.getLogger("Minecraft").info(Name + "§aReloaded!");
					if(instance.getConfig().getString("webhook-url") == "") {
						Logger.getLogger("Minecraft").info("§cDisabled!");
						DiscordBans.enabled = false;
					}
				}
			}
		}
		
		if(DiscordBans.enabled == false) {
			sender.sendMessage(Name + " Please set me up before use, I have disabled myself.");
			Logger.getLogger("Minecraft").log(Level.WARNING, "Please change my config.yml before using me.\nYou can reload me when needed with /db reload.");
		} 
		else {
			if(args[0].equalsIgnoreCase("/ban") && args.length >= 2 && instance.getConfig().getBoolean("bans") == true) {
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
			if(args[0].equalsIgnoreCase("/tempban") && args.length >= 3 && instance.getConfig().getBoolean("tempbans") == true) {
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
			if(args[0].equalsIgnoreCase("/unban") && args.length >= 2 && instance.getConfig().getBoolean("unbans") == true || 
			args[0].equalsIgnoreCase("/pardon") && args.length >= 2 && instance.getConfig().getBoolean("unbans") == true) {
				if(sender.hasPermission("minecraft.command.pardon") || sender.hasPermission("essentials.unban")) {
					Date now = new Date();
					String target = args[1];
					if (target != "") {
						SendWebhook(BanType.unban, target, sender.getName(), "", now, "");
					}
				}
			}
			if(args[0].equalsIgnoreCase("/banip") && args.length >= 2 && instance.getConfig().getBoolean("banips") == true) {
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
			if(args[0].equalsIgnoreCase("/unbanip") && args.length >= 2 && instance.getConfig().getBoolean("unbanips") == true ||
			args[0].equalsIgnoreCase("/pardon-ip") && args.length >= 2 && instance.getConfig().getBoolean("unbanips") == true) {
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
		if(instance != null) {
			if(instance.getConfig().getBoolean("debug") == true) {
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
    	DiscordWebhook webhook = new DiscordWebhook(instance.getConfig().getString("webhook-url"));
        webhook.setContent(FormatText.Entry(instance.getConfig().getString("bot-message")));
        webhook.setAvatarUrl(instance.getConfig().getString("bot-picture"));
        webhook.setUsername(instance.getConfig().getString("bot-name"));
        webhook.setTts(false);
        if(type == BanType.ban) {
        	webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(FormatText.Entry(instance.getConfig().getString("banned-title")))
                .setDescription(FormatText.Entry(instance.getConfig().getString("description")))
                .setColor(Color.decode(instance.getConfig().getString("ban-color")))
                .addField(FormatText.Entry(instance.getConfig().getString("reason-line1")), FormatText.Entry(instance.getConfig().getString("reason-line2")), false)
                .addField(FormatText.Entry(instance.getConfig().getString("banned-by-line1")), FormatText.Entry(instance.getConfig().getString("banned-by-line2")), false)
                .setThumbnail("https://minotar.net/armor/bust/" + target + "/100.png")
                .setFooter(FormatText.Entry(instance.getConfig().getString("banned-on")), "")
                .setUrl("https://mcnames.net/username/" + target)
        	);
        }
        if(type == BanType.tempban)
        {
        	webhook.addEmbed(new DiscordWebhook.EmbedObject()
                 .setTitle(FormatText.Entry(instance.getConfig().getString("tempbanned-title")))
                .setDescription(FormatText.Entry(instance.getConfig().getString("description")))
                .setColor(Color.decode(instance.getConfig().getString("tempban-color")))
                .addField(FormatText.Entry(instance.getConfig().getString("reason-line1")), FormatText.Entry(instance.getConfig().getString("reason-line2")), false)
                .addField(FormatText.Entry(instance.getConfig().getString("banned-by-line1")), FormatText.Entry(instance.getConfig().getString("banned-by-line2")), false)
                .addField(FormatText.Entry(instance.getConfig().getString("duration-line1")), FormatText.Entry(instance.getConfig().getString("duration-line2")), false)
                .setThumbnail("https://minotar.net/armor/bust/" + target + "/100.png")
                .setFooter(FormatText.Entry(instance.getConfig().getString("banned-on")), "")
                .setUrl("https://mcnames.net/username/" + target)
            );
        }
        if(type == BanType.unban)
        {
        	webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(FormatText.Entry(instance.getConfig().getString("unbanned-title")))
                .setDescription(FormatText.Entry(instance.getConfig().getString("description")))
                .setColor(Color.decode(instance.getConfig().getString("unban-color")))
                .addField(FormatText.Entry(instance.getConfig().getString("unbanned-by-line1")), FormatText.Entry(instance.getConfig().getString("unbanned-by-line2")), false)
                .setThumbnail("https://minotar.net/armor/bust/" + target + "/100.png")
                .setFooter(FormatText.Entry(instance.getConfig().getString("unbanned-on")), "")
                .setUrl("https://mcnames.net/username/" + target)
            );
        }
        if(type == BanType.ipban) {
        	webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(FormatText.Entry(instance.getConfig().getString("ip-banned-title")))
                .setDescription(FormatText.Entry(instance.getConfig().getString("description")))
                .setColor(Color.decode(instance.getConfig().getString("banip-color")))
                .addField(FormatText.Entry(instance.getConfig().getString("reason-line1")), FormatText.Entry(instance.getConfig().getString("reason-line2")), false)
                .addField(FormatText.Entry(instance.getConfig().getString("banned-by-line1")), FormatText.Entry(instance.getConfig().getString("banned-by-line2")), false)
                .setFooter(FormatText.Entry(instance.getConfig().getString("banned-on")), "")
        	);
        }
        if(type == BanType.ipunban)
        {
        	webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(FormatText.Entry(instance.getConfig().getString("ip-unbanned-title")))
                .setDescription(FormatText.Entry(instance.getConfig().getString("description")))
                .setColor(Color.decode(instance.getConfig().getString("unbanip-color")))
                .addField(FormatText.Entry(instance.getConfig().getString("unbanned-by-line1")), FormatText.Entry(instance.getConfig().getString("unbanned-by-line2")), false)
                .setFooter(FormatText.Entry(instance.getConfig().getString("unbanned-on")), "")
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