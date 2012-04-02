package org.micoli.minecraft.utils;

import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServerLogger {
	private static Logger logger = Logger.getLogger("Minecraft");
	private static String prefix = "plugin";

	public static void setPrefix(String prf){
		prefix = prf;
	}
	public static void log(String str) {
		logger.info("["+prefix+"]"+str);
	}

	public static void log(String str,Object... args ) {
		logger.info("["+prefix+"]"+ChatFormater.format(str, args));
	}
	public static void dump(Object obj){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		logger.info("["+prefix+"] "+obj.getClass()+"\n"+gson.toJson(obj));
	}

}