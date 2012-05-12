package org.micoli.minecraft.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The Class Json.
 */
public class Json {
	
	/**
	 * Export object to json.
	 *
	 * @param filename the filename
	 * @param object the object
	 */
	public static void exportObjectToJson(String filename,Object object){
		Gson gson = new GsonBuilder()
				.setExclusionStrategies (new JsonExclusionStrategy())
				.excludeFieldsWithModifiers(Modifier.TRANSIENT)
				.setPrettyPrinting().create();
		FileWriter writer;
		try {
			writer = new FileWriter(filename);
			writer.write(gson.toJson(object));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Export object to json.
	 *
	 * @param object the object
	 * @return the string
	 */
	public static String exportObjectToJson(Object object){
		Gson gson = new GsonBuilder()
				.setExclusionStrategies (new JsonExclusionStrategy())
				.excludeFieldsWithModifiers(Modifier.TRANSIENT)
				.setPrettyPrinting().create();
		String writer;
		try {
			writer = gson.toJson(object);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return writer;
	}
	
	public static Object importFromJson(String json,Type type){
		Gson gson = new Gson();
		return gson.fromJson(json, type);
	}
}
