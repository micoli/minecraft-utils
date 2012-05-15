package org.micoli.minecraft.utils;

import java.lang.reflect.Field;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCore;
import org.dynmap.bukkit.DynmapPlugin;
import org.micoli.minecraft.bukkit.QDBukkitPlugin;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

// TODO: Auto-generated Javadoc
/**
 * The Class PluginEnvironment.
 */
public class PluginEnvironment {
	
	/** The world guard plugin. */
	private static WorldGuardPlugin worldGuardPlugin=null;
	
	/** The world edit plugin. */
	private static WorldEditPlugin worldEditPlugin=null;
	
	/** The dynmap common api plugin. */
	private static DynmapCommonAPI dynmapCommonAPIPlugin=null;
	
	/** The dynmap plugin. */
	private static DynmapPlugin dynmapPlugin=null;

	/** The dynmap core plugin. */
	private static DynmapCore dynmapCorePlugin=null;
	
	/** The vault permission. */
	private static Permission vaultPermission = null;
	
	/** The vault economy. */
	private static Economy vaultEconomy = null;
	
	/** The vault chat. */
	private static Chat vaultChat = null;
	

	/**
	 * Gets the world guard.
	 *
	 * @param qdplugin the qdplugin
	 * @return the world guard
	 */
	static public WorldGuardPlugin getWorldGuardPlugin(QDBukkitPlugin qdplugin) {
		if(worldGuardPlugin!=null){
			return worldGuardPlugin;
		}
		Plugin plugin = qdplugin.getServer().getPluginManager().getPlugin("WorldGuard");

		// WorldGuard may not be loaded
		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			qdplugin.logger.log("WorldGuard not found");
			return null; // Maybe you want throw an exception instead
		}
		setWorldGuardPlugin ((WorldGuardPlugin) plugin);
		return worldGuardPlugin;
	}
	
	/**
	 * Sets the world guard plugin.
	 *
	 * @param worldGuardPlugin the worldGuardPlugin to set
	 */
	public static void setWorldGuardPlugin(WorldGuardPlugin worldGuardPlugin) {
		PluginEnvironment.worldGuardPlugin = worldGuardPlugin;
	}

	/**
	 * Gets the world edit.
	 *
	 * @param qdplugin the qdplugin
	 * @return the world edit
	 */
	static public WorldEditPlugin getWorldEditPlugin(QDBukkitPlugin qdplugin) {
		if(worldEditPlugin!=null){
			return worldEditPlugin;
		}
		Plugin plugin = qdplugin.getServer().getPluginManager().getPlugin("WorldEdit");
		plugin.getDatabase();
		if (plugin == null || !(plugin instanceof WorldEdit)) {
			Plugin[] plugs = qdplugin.getServer().getPluginManager().getPlugins();
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
		setWorldEditPlugin((WorldEditPlugin) plugin);
		return worldEditPlugin;
	}
	
	/**
	 * Sets the world edit plugin.
	 *
	 * @param worldEditPlugin the worldEditPlugin to set
	 */
	public static void setWorldEditPlugin(WorldEditPlugin worldEditPlugin) {
		PluginEnvironment.worldEditPlugin = worldEditPlugin;
	}
	
	/**
	 * Gets the dynmap common api plugin.
	 *
	 * @param qdplugin the qdplugin
	 * @return the dynmap common api plugin
	 */
	static public DynmapCommonAPI getDynmapCommonAPIPlugin(QDBukkitPlugin qdplugin) {
		if(dynmapCommonAPIPlugin!=null){
			return dynmapCommonAPIPlugin;
		}
		setDynmapCommonAPIPlugin((DynmapCommonAPI) qdplugin.getServer().getPluginManager().getPlugin("dynmap"));
		return dynmapCommonAPIPlugin;
	}
	
	/**
	 * Sets the dynmap common api plugin.
	 *
	 * @param dynmapCommonAPIPlugin the dynmapCommonAPIPlugin to set
	 */
	public static void setDynmapCommonAPIPlugin(DynmapCommonAPI dynmapCommonAPIPlugin) {
		PluginEnvironment.dynmapCommonAPIPlugin = dynmapCommonAPIPlugin;
	}
	/**
	 * Gets the dynmap plugin.
	 *
	 * @param qdplugin the qdplugin
	 * @return the dynmap plugin
	 */
	static public DynmapPlugin getDynmapPlugin(QDBukkitPlugin qdplugin) {
		if(dynmapPlugin!=null){
			return dynmapPlugin;
		}
		setDynmapPlugin( (DynmapPlugin) qdplugin.getServer().getPluginManager().getPlugin("dynmap"));
		return dynmapPlugin;
	}
	
	/**
	 * Sets the dynmap plugin.
	 *
	 * @param dynmapPlugin the dynmapPlugin to set
	 */
	public static void setDynmapPlugin(DynmapPlugin dynmapPlugin) {
		PluginEnvironment.dynmapPlugin = dynmapPlugin;
	}
	
	/**
	 * Gets the dynmap core.
	 *
	 * @param qdplugin the qdplugin
	 * @return the dynmap core
	 */
	public static DynmapCore getDynmapCorePlugin(QDBukkitPlugin qdplugin) {
		if(dynmapCorePlugin!=null){
			return dynmapCorePlugin;
		}
		DynmapPlugin dm = getDynmapPlugin(qdplugin);

		Class<? extends DynmapPlugin> presumedClass = dm.getClass();
		Field f;
		try {
			f = presumedClass.getDeclaredField("core");
			f.setAccessible(true);
			setDynmapCorePlugin((DynmapCore) f.get(dm));
			return dynmapCorePlugin;
		} catch (SecurityException e) {
			qdplugin.logger.dumpStackTrace(e);
		} catch (NoSuchFieldException e) {
			qdplugin.logger.dumpStackTrace(e);
		} catch (IllegalArgumentException e) {
			qdplugin.logger.dumpStackTrace(e);
		} catch (IllegalAccessException e) {
			qdplugin.logger.dumpStackTrace(e);
		}
		return null;
	}
	
	/**
	 * Sets the dynmap core plugin.
	 *
	 * @param dynmapCorePlugin the dynmapCorePlugin to set
	 */
	public static void setDynmapCorePlugin(DynmapCore dynmapCorePlugin) {
		PluginEnvironment.dynmapCorePlugin = dynmapCorePlugin;
	}
	
	/**
	 * Gets the vault economy.
	 *
	 * @param qdplugin the qdplugin
	 * @return the vaultEconomy
	 */
	public static Economy getVaultEconomy(QDBukkitPlugin qdplugin) {
		if(PluginEnvironment.vaultEconomy!=null){
			return PluginEnvironment.vaultEconomy;
		}
		setupEconomy(qdplugin);
		return PluginEnvironment.vaultEconomy;
	}

	/**
	 * Sets the vault economy.
	 *
	 * @param vaultEconomy the vaultEconomy to set
	 */
	public static void setVaultEconomy(Economy vaultEconomy) {
		PluginEnvironment.vaultEconomy = vaultEconomy;
	}

	/**
	 * Gets the vault permission.
	 *
	 * @param qdplugin the qdplugin
	 * @return the vaultPermission
	 */
	public static Permission getVaultPermission(QDBukkitPlugin qdplugin) {
		if(PluginEnvironment.vaultPermission!=null){
			return PluginEnvironment.vaultPermission;
		}
		setupPermissions(qdplugin);
		return PluginEnvironment.vaultPermission;
	}

	/**
	 * Sets the vault permission.
	 *
	 * @param vaultPermission the vaultPermission to set
	 */
	public static void setVaultPermission(Permission vaultPermission) {
		PluginEnvironment.vaultPermission = vaultPermission;
	}

	/**
	 * Gets the vault chat.
	 *
	 * @param qdplugin the qdplugin
	 * @return the vaultChat
	 */
	public static Chat getVaultChat(QDBukkitPlugin qdplugin) {
		if(PluginEnvironment.vaultChat!=null){
			return PluginEnvironment.vaultChat;
		}
		setupChat(qdplugin);
		return PluginEnvironment.vaultChat;
	}

	/**
	 * Sets the vault chat.
	 *
	 * @param vaultChat the vaultChat to set
	 */
	public static void setVaultChat(Chat vaultChat) {
		PluginEnvironment.vaultChat = vaultChat;
	}
	
	/**
	 * Setup permissions.
	 *
	 * @param qdplugin the qdplugin
	 * @return true, if successful
	 */
	protected static boolean setupPermissions(QDBukkitPlugin qdplugin) {
		RegisteredServiceProvider<Permission> permissionProvider = qdplugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			setVaultPermission(permissionProvider.getProvider());
		}
		return (PluginEnvironment.vaultPermission != null);
	}

	/**
	 * Setup economy.
	 *
	 * @param qdplugin the qdplugin
	 * @return true, if successful
	 */
	protected static boolean setupEconomy(QDBukkitPlugin qdplugin) {
		RegisteredServiceProvider<Economy> economyProvider = qdplugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			setVaultEconomy(economyProvider.getProvider());
		}
	
		return (PluginEnvironment.vaultEconomy != null);
	}
	
	/**
	 * Setup chat.
	 *
	 * @param qdplugin the qdplugin
	 * @return true, if successful
	 */
	protected static boolean setupChat(QDBukkitPlugin qdplugin) {
		RegisteredServiceProvider<Chat> chatProvider =qdplugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			setVaultChat(chatProvider.getProvider());
		}

		return (PluginEnvironment.vaultChat != null);
	}


}
