package org.micoli.minecraft.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

// TODO: Auto-generated Javadoc
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
				.setPrettyPrinting()
				//.setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		String writer;
		try {
			writer = gson.toJson(object);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return writer;
	}
	
	/**
	 * Import from json.
	 *
	 * @param json the json
	 * @param type the type
	 * @return the object
	 */
	public static Object importFromJson(String json,Type type){
		Gson gson = new Gson();
		return gson.fromJson(json, type);
	}

	public static Object importFromJson(File jsonFile,Type type) throws JsonIOException, JsonSyntaxException, FileNotFoundException{
		Gson gson = new Gson();
		return gson.fromJson(new FileReader(jsonFile.getAbsoluteFile()), type);
	}
}
