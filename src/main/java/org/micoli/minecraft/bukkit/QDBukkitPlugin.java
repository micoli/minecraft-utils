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

// TODO: Auto-generated Javadoc
/**
 * The Class QDBukkitPlugin.
 */
public class QDBukkitPlugin extends JavaPlugin implements ActionListener {
	
	/** The logger. */
	protected static Logger logger = Logger.getLogger("Minecraft");
	
	/** The instance. */
	protected static QDBukkitPlugin instance;
	
	/** The command string. */
	protected static String commandString = "QDBukkitPlugin";
	
	/** The comments. */
	protected static boolean comments = true;
	
	/** The last msg. */
	protected static String lastMsg = "";
	
	/** The pdf file. */
	public PluginDescriptionFile pdfFile;
	
	/** The pm. */
	public PluginManager pm;

	/** The vault permission. */
	public static Permission vaultPermission = null;
	
	/** The vault economy. */
	public static Economy vaultEconomy = null;
	
	/** The vault chat. */
	public static Chat vaultChat = null;
	
	/** The database. */
	protected static MyDatabase database;
	
	/** The config file. */
	protected FileConfiguration configFile;

	/**
	 * Gets the single instance of QDBukkitPlugin.
	 *
	 * @return the instance
	 */
	public static QDBukkitPlugin getInstance() {
		return instance;
	}

	/**
	 * Gets the command string.
	 *
	 * @return the command string
	 */
	public static String getCommandString() {
		return commandString;
	}

	/**
	 * Sets the comments.
	 *
	 * @param player the player
	 * @param active the active
	 */
	public static void setComments(Player player, boolean active) {
		comments = active;
		player.sendMessage(ChatFormater.format("{ChatColor.RED} %s", (active ? "comments activated" : "comments desactived")));
	}

	/**
	 * Gets the comments.
	 *
	 * @return the comments
	 */
	public static boolean getComments() {
		return comments;
	}

	/**
	 * Log.
	 *
	 * @param str the str
	 */
	public static void log(String str) {
		ServerLogger.log(str);
	}

	/**
	 * Send comments.
	 *
	 * @param player the player
	 * @param text the text
	 * @param args the args
	 */
	public static void sendComments(Player player, String text,Object... args) {
		sendComments(player, ChatFormater.format(text,args));
	}
	
	/**
	 * Send comments.
	 *
	 * @param player the player
	 * @param text the text
	 */
	public static void sendComments(Player player, String text) {
		sendComments(player, text,false);
	}

	/**
	 * Send comments.
	 *
	 * @param player the player
	 * @param text the text
	 * @param global the global
	 */
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

	/* (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
	 */
	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		log(ChatFormater.format("%s version disabled", pdfFile.getName(), pdfFile.getVersion()));
	}

	/* (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
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

	/**
	 * Load configuration.
	 */
	protected void loadConfiguration() {
		configFile = getConfig();
		configFile.set("database.driver", configFile.getString("database.driver", "org.sqlite.JDBC"));
		configFile.set("database.url", configFile.getString("database.url", "jdbc:sqlite:{DIR}{NAME}.db"));
		configFile.set("database.username", configFile.getString("database.username", "root"));
		configFile.set("database.password", configFile.getString("database.password", ""));
		configFile.set("database.isolation", configFile.getString("database.isolation", "SERIALIZABLE"));
		configFile.set("database.logging", configFile.getBoolean("database.logging", false));
		configFile.set("database.rebuild", configFile.getBoolean("database.rebuild", true));
		saveConfig();
	}

	/* (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#getDatabase()
	 */
	@Override
	public EbeanServer getDatabase() {
		return database.getDatabase();
	}

	/**
	 * Gets the static database.
	 *
	 * @return the static database
	 */
	public static EbeanServer getStaticDatabase() {
		return database.getDatabase();
	}

	/**
	 * Gets the database orm classes.
	 *
	 * @return the database orm classes
	 */
	protected java.util.List<Class<?>> getDatabaseORMClasses() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		return list;
	};

	/**
	 * Initialize database.
	 */
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

	/**
	 * Setup permissions.
	 *
	 * @return true, if successful
	 */
	protected boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			vaultPermission = permissionProvider.getProvider();
		}
		return (vaultPermission != null);
	}

	/**
	 * Setup chat.
	 *
	 * @return true, if successful
	 */
	protected boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			vaultChat = chatProvider.getProvider();
		}

		return (vaultChat != null);
	}

	/**
	 * Setup economy.
	 *
	 * @return true, if successful
	 */
	protected boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			vaultEconomy = economyProvider.getProvider();
		}

		return (vaultEconomy != null);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event) {
	}
}