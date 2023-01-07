package org.kalbinvv.carryon.utils;

import org.bukkit.entity.Player;

public class ChatUtils {
	
	private static boolean messagesEnabled;
	
	public static boolean isMessagesEnabled() {
		return messagesEnabled;
	}

	public static void setMessagesEnabled(boolean messagesEnabled) {
		ChatUtils.messagesEnabled = messagesEnabled;
	}

	public static String removeAllColorsCodes(String unformattedString) {
		return unformattedString.replaceAll("§\\w", "");
	}
	
	public static void sendMessage(String prefix, String message, Player player) {
		if(!isMessagesEnabled()) {
			return;
		}
		
		if(message.isEmpty()) {
			return;
		}
		
		if(prefix.isEmpty()) {
			player.sendMessage(message);
		}else {
			player.sendMessage(String.format("%s %s", prefix, message));
		}
	}
	
}
