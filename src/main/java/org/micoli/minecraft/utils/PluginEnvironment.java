package org.micoli.minecraft.utils;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class PluginEnvironment {
	/**
	 * Gets the world guard.
	 * 
	 * @return the world guard
	 */
	static public WorldGuardPlugin getWorldGuard(Server server) {
		Plugin plugin = server.getPluginManager().getPlugin("WorldGuard");

		// WorldGuard may not be loaded
		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			ServerLogger.log("WorldGuard not found");
			return null; // Maybe you want throw an exception instead
		}

		return (WorldGuardPlugin) plugin;
	}

	/**
	 * Gets the world edit.
	 * 
	 * @return the world edit
	 */
	static public WorldEditPlugin getWorldEdit(Server server) {
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
			ServerLogger.log("WorldEdit not found");
			return null; // Maybe you want throw an exception instead
		}

		return (WorldEditPlugin) plugin;
	}
}
