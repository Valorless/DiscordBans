package valorless.discordbans;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

	static String Name = "[DiscordBans] ";

	public static void Info(String msg) {
		Logger.getLogger("Minecraft").log(Level.INFO, Name + msg);
	}
	
	public static void Warning(String msg) {
		Logger.getLogger("Minecraft").log(Level.WARNING, Name + msg);
	}

	public static void Severe(String msg) {
		Logger.getLogger("Minecraft").log(Level.SEVERE, Name + msg);
	}
	
}
