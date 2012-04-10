package org.micoli.minecraft.utils;

import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerLogger.
 */
public class ServerLogger {
	
	/** The logger. */
	private static Logger logger = Logger.getLogger("Minecraft");
	
	/** The prefix. */
	private static String prefix = "plugin";

	/**
	 * Sets the prefix.
	 *
	 * @param prf the new prefix
	 */
	public static void setPrefix(String prf){
		prefix = prf;
	}
	
	/**
	 * Log.
	 *
	 * @param str the str
	 */
	public static void log(String str) {
		logger.info("["+prefix+"] "+str);
	}

	/**
	 * Log.
	 *
	 * @param str the str
	 * @param args the args
	 */
	public static void log(String str,Object... args ) {
		logger.info("["+prefix+"] "+ChatFormater.formatClean(str, args));
	}
	
	/**
	 * Dump.
	 *
	 * @param obj the obj
	 */
	public static void dump(Object obj){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		logger.info("["+prefix+"] "+obj.getClass()+"\n"+gson.toJson(obj));
	}

}