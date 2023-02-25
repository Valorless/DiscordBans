package valorless.discordbans;

import org.bukkit.plugin.java.JavaPlugin;
import valorless.valorlessutils.ValorlessUtils.Config;

public class Lang {
	
	public static class Placeholders{
		public static String target = "";
		public static String sender = "";
		public static String reason = "";
		public static String duration = "";
		public static String date = "";
		public static String plugin = "&7[&4DiscordBans&9]&r";
	}
	
	public static String Parse(String text) {
		
		if(text.contains("%target%")) { text = text.replace("%target%", Placeholders.target); }
		if(text.contains("%sender%")) { text = text.replace("%sender%", Placeholders.sender); }
		if(text.contains("%reason%")) { text = text.replace("%reason%", Placeholders.reason); }
		if(text.contains("%duration%")) { text = text.replace("%duration%", Placeholders.duration); }
		if(text.contains("%date%")) { text = text.replace("%date%", Placeholders.date); }
		if(text.contains("%plugin%")) { text = text.replace("%plugin%", Placeholders.plugin); }
		return text;
	}

	public static String Get(JavaPlugin caller, String key) {
		return Parse(Config.GetString(caller, key));
	}
}
