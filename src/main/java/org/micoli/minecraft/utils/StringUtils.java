package org.micoli.minecraft.utils;

public class StringUtils {
	public static String fixedLength(String string,int length){
		return String.format("%-"+String.format("%d",length)+"s", string.substring(0, Math.min(string.length(),length)));
	}
}
