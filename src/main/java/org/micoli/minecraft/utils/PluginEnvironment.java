package org.micoli.minecraft.utils;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.micoli.minecraft.bukkit.QDBukkitPlugin;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

// TODO: Auto-generated Javadoc
/**
 * The Class PluginEnvironment.
 */
public class PluginEnvironment {
	
	/**
	 * Gets the world guard.
	 *
	 * @param server the server
	 * @return the world guard
	 */
	static public WorldGuardPlugin getWorldGuard(QDBukkitPlugin qdplugin ,Server server) {
		Plugin plugin = server.getPluginManager().getPlugin("WorldGuard");

		// WorldGuard may not be loaded
		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			qdplugin.logger.log("WorldGuard not found");
			return null; // Maybe you want throw an exception instead
		}

		return (WorldGuardPlugin) plugin;
	}

	/**
	 * Gets the world edit.
	 *
	 * @param server the server
	 * @return the world edit
	 */
	static public WorldEditPlugin getWorldEdit(QDBukkitPlugin qdplugin,Server server) {
		Plugin plugin = server.getPluginManager().getPlugin("WorldEdit");
		plugin.getDatabase();
		if (plugin == null || !(plugin instanceof WorldEdit)) {
			Plugin[] plugs = server.getPluginManager().getPlugins();
			for (int t = 0; t < plugs.length; t++) {
				if (plugs[t].getName().trim().equalsIgnoreCase("WorldEdit")) {
					plugin = plugs[t];
					break;
				}
			}
		}
		// WorldEdit may not be loaded
		if (plugin == null || !(plugin instanceof WorldEditPlugin)) {
			qdplugin.logger.log("WorldEdit not found");
			return null; // Maybe you want throw an exception instead
		}

		return (WorldEditPlugin) plugin;
	}
}
