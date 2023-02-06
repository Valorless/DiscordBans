package valorless.discordbans;

public class FormatText {
	
	public static class Format{
		public static String target = "";
		public static String sender = "";
		public static String reason = "";
		public static String duration = "";
		public static String date = "";
		public static String plugin = "&7[&4DiscordBans&9]&r";
	}
	
	public static String Entry(String text) {
		if(text.contains("%target%")) { text = text.replace("%target%", Format.target); }
		if(text.contains("%sender%")) { text = text.replace("%sender%", Format.sender); }
		if(text.contains("%reason%")) { text = text.replace("%reason%", Format.reason); }
		if(text.contains("%duration%")) { text = text.replace("%duration%", Format.duration); }
		if(text.contains("%date%")) { text = text.replace("%date%", Format.date); }
		if(text.contains("%plugin%")) { text = text.replace("%plugin%", Format.plugin); }
		return text;
	}
}
