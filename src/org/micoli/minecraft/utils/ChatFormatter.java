package org.micoli.minecraft.utils;

import java.util.EnumSet;
import java.util.Set;
import org.bukkit.ChatColor;

public class ChatFormatter {
	public static String format(String str,Object... args ){
		String result = String.format(str, args);
		Set<ChatColor> allChatColors= EnumSet.allOf(ChatColor.class);
		for(ChatColor col : allChatColors){
			result = result.replaceAll("\\{ChatColor\\."+col.name()+"\\}", col.toString());
		}
		return result;
	}
}
