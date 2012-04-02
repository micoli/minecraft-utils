package org.micoli.minecraft.bukkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.micoli.minecraft.utils.ChatFormater;
import org.micoli.minecraft.utils.ServerLogger;

import com.avaje.ebean.EbeanServer;
import com.lennardf1989.bukkitex.MyDatabase;

public class QDBukkitPlugin extends JavaPlugin implements ActionListener {
	protected static Logger logger = Logger.getLogger("Minecraft");
	protected static QDBukkitPlugin instance;
	protected static String commandString = "QDBukkitPlugin";
	protected static boolean comments = true;
	protected static String lastMsg = "";
	public PluginDescriptionFile pdfFile;
	public PluginManager pm;

	public static Permission vaultPermission = null;
	public static Economy vaultEconomy = null;
	public static Chat vaultChat = null;
	protected static MyDatabase database;

	/**
	 * @return the instance
	 */
	public static QDBukkitPlugin getInstance() {
		return instance;
	}

	public static String getCommandString() {
		return commandString;
	}

	public static void setComments(Player player, boolean active) {
		comments = active;
		player.sendMessage(ChatFormater.format("{ChatColor.RED} %s", (active ? "comments activated" : "comments desactived")));
	}

	public static boolean getComments() {
		return comments;
	}

	public static void log(String str) {
		logger.info(str);
	}

	public static void sendComments(Player player, String text,Object... args) {
		sendComments(player, ChatFormater.format(text,args));
	}
	
	public static void sendComments(Player player, String text) {
		sendComments(player, text,false);
	}

	public static void sendComments(Player player, String text, boolean global) {
		if (getComments()) {
			if (!QDBukkitPlugin.lastMsg.equalsIgnoreCase(text)) {
				QDBukkitPlugin.lastMsg = text + "";
				if (global) {
					getInstance().getServer().broadcastMessage(text);
				} else {
					player.sendMessage(text);
				}
			}
		}
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		log(ChatFormater.format("%s version disabled", pdfFile.getName(), pdfFile.getVersion()));
	}

	@Override
	public void onEnable() {
		instance = this;
		pm = getServer().getPluginManager();
		pdfFile = getDescription();
		log(ChatFormater.format("%s version %s : initialization", pdfFile.getName(), pdfFile.getVersion()));
		ServerLogger.setPrefix(pdfFile.getName());

		setupPermissions();
		setupChat();
		setupEconomy();
		loadConfiguration();
		initializeDatabase();
	}

	protected void loadConfiguration() {
		FileConfiguration config = getConfig();
		config.set("database.driver", config.getString("database.driver", "org.sqlite.JDBC"));
		config.set("database.url", config.getString("database.url", "jdbc:sqlite:{DIR}{NAME}.db"));
		config.set("database.username", config.getString("database.username", "root"));
		config.set("database.password", config.getString("database.password", ""));
		config.set("database.isolation", config.getString("database.isolation", "SERIALIZABLE"));
		config.set("database.logging", config.getBoolean("database.logging", false));
		config.set("database.rebuild", config.getBoolean("database.rebuild", true));
		saveConfig();
	}

	@Override
	public EbeanServer getDatabase() {
		return database.getDatabase();
	}

	public static EbeanServer getStaticDatabase() {
		return database.getDatabase();
	}

	protected java.util.List<Class<?>> getDatabaseORMClasses() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		return list;
	};

	protected void initializeDatabase() {
		FileConfiguration config = getConfig();

		database = new MyDatabase(this) {
			protected java.util.List<Class<?>> getDatabaseClasses() {
				return getDatabaseORMClasses();
			};
		};

		database.initializeDatabase(config.getString("database.driver"), config.getString("database.url"), config.getString("database.username"), config.getString("database.password"), config.getString("database.isolation"), config.getBoolean("database.logging", false), config.getBoolean("database.rebuild", true));

		config.set("database.rebuild", false);
		saveConfig();
	}

	protected boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			vaultPermission = permissionProvider.getProvider();
		}
		return (vaultPermission != null);
	}

	protected boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			vaultChat = chatProvider.getProvider();
		}

		return (vaultChat != null);
	}

	protected boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			vaultEconomy = economyProvider.getProvider();
		}

		return (vaultEconomy != null);
	}

	public void actionPerformed(ActionEvent event) {
	}
}