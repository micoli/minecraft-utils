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
	private Logger logger = Logger.getLogger("Minecraft");
	
	/** The prefix. */
	private String prefix = "plugin";

	/**
	 * Sets the prefix.
	 *
	 * @param prf the new prefix
	 */
	public void setPrefix(String prf){
		prefix = prf;
	}
	
	/**
	 * Log.
	 *
	 * @param str the str
	 */
	public void log(String str) {
		logger.info("["+prefix+"] "+str);
	}

	public void log(String[] str) {
		for(String s:str){
			logger.info("["+prefix+"] "+s);
		}
	}
	/**
	 * Log.
	 *
	 * @param str the str
	 * @param args the args
	 */
	public void log(String str,Object... args) {
		logger.info("["+prefix+"] "+ChatFormater.formatClean(str, args));
	}
	
	/**
	 * Dump.
	 *
	 * @param obj the obj
	 */
	public void dump(Object obj){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		logger.info("["+prefix+"] "+obj.getClass()+"\n"+gson.toJson(obj));
	}

}