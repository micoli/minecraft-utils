package org.micoli.minecraft.utils;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writer;
		try {
			writer = new FileWriter(filename);
			writer.write(gson.toJson(object));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
