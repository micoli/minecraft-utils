package org.micoli.minecraft.utils;

import java.util.EnumSet;
import java.util.Set;

import org.bukkit.ChatColor;

// TODO: Auto-generated Javadoc
/**
 * The Class ChatFormater.
 */
public class ChatFormater {
	
	/**
	 * Format.
	 *
	 * @param str the str
	 * @param args the args
	 * @return the string
	 */
	public static String format(String str,Object... args ){
		String result = String.format(str, args);
		Set<ChatColor> allChatColors= EnumSet.allOf(ChatColor.class);
		for(ChatColor col : allChatColors){
			result = result.replaceAll("\\{ChatColor\\."+col.name()+"\\}", col.toString());
		}
		return result;
	}
}