package org.micoli.minecraft.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.micoli.minecraft.bukkit.QDBukkitPlugin;

public class FileUtils {
	/**
	 * Copy a file from the plugin to the data folder.
	 * 
	 * @param fileName
	 *            the file name
	 */
	public static void initializeFileFromRessource(QDBukkitPlugin plugin,String fileName,boolean force) {
		File resourceFile = new File(plugin.getDataFolder(), fileName);
		if (resourceFile.exists() || force) {
			try {
				InputStream isr = plugin.getClass().getClassLoader().getResourceAsStream(fileName);
				File fileOut = new File(plugin.getDataFolder().getAbsolutePath() + "/" + fileName);
				FileOutputStream fop = new FileOutputStream(fileOut);
				if (!fileOut.exists()) {
					fileOut.createNewFile();
				}
				try {
					byte[] buf = new byte[512];
					int len;
					while ((len = isr.read(buf)) > 0) {
						fop.write(buf, 0, len);
					}
				} finally {
					isr.close();
				}
			} catch (IOException e) {
				plugin.logger.dumpStackTrace(e);
			}
		}

	}
}
