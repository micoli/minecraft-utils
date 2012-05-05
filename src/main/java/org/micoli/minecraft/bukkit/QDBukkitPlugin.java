package org.micoli.minecraft.bukkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.micoli.minecraft.utils.ChatFormater;
import org.micoli.minecraft.utils.PluginEnvironment;
import org.micoli.minecraft.utils.ServerLogger;

import com.avaje.ebean.EbeanServer;
import com.lennardf1989.bukkitex.MyDatabase;

/**
 * The Class QDBukkitPlugin.
 */
public class QDBukkitPlugin extends JavaPlugin implements ActionListener {
	
	/** The logger. */
	public ServerLogger logger = new ServerLogger();
	
	/** The instance. */
	protected static QDBukkitPlugin instance;
	
	/** The command string. */
	protected String commandString = "QDBukkitPlugin";
	
	/** The comments. */
	protected boolean comments = true;
	
	/** The last msg. */
	protected String lastMsg = "";
	
	/** The pdf file. */
	protected PluginDescriptionFile pdfFile;
	
	/** The pm. */
	private PluginManager pm;

	/** The database. */
	protected MyDatabase database;
	
	/** The config file. */
	protected FileConfiguration configFile;

	/** The config file. */
	protected boolean withDatabase =false;

	/** The executor. */
	protected QDCommandManager executor;

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
	public String getCommandString() {
		return commandString;
	}

	/**
	 * Sets the comments.
	 *
	 * @param player the player
	 * @param active the active
	 */
	public void setComments(Player player, boolean active) {
		comments = active;
		player.sendMessage(ChatFormater.format("{ChatColor.RED} %s", (active ? "comments activated" : "comments desactived")));
	}

	/**
	 * Gets the comments.
	 *
	 * @return the comments
	 */
	public boolean getComments() {
		return comments;
	}

	/**
	 * Send comments.
	 *
	 * @param player the player
	 * @param text the text
	 * @param args the args
	 */
	public void sendComments(Player player, String text,Object... args) {
		if (player==null){
			sendComments(player, ChatFormater.format(text,args));
		}else{
			logger.log(text,args);
		}
	}
	
	/**
	 * Send comments.
	 *
	 * @param player the player
	 * @param text the text
	 */
	public void sendComments(Player player, String text) {
		if (player==null){
			sendComments(player, text,false);
		}else{
			logger.log(text);
		}
	}

	/**
	 * Send comments.
	 *
	 * @param player the player
	 * @param text the text
	 * @param global the global
	 */
	public void sendComments(Player player, String text, boolean global) {
		if (getComments()) {
			if (!this.lastMsg.equalsIgnoreCase(text)) {
				this.lastMsg = text + "";
				if (global) {
					getInstance().getServer().broadcastMessage(text);
				} else {
					player.sendMessage(text);
				}
			}
		}
	}

	/**
	 * Send comments.
	 *
	 * @param player the player
	 * @param text the text
	 * @param global the global
	 */
	public void sendComments(Player player, String[] texts, boolean global) {
		if (getComments()) {
			if (global) {
				for(String s:texts){
					getInstance().getServer().broadcastMessage(s);
				}
			} else {
				player.sendMessage(texts);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
	 */
	public void onDisable() {
		logger.log(ChatFormater.format("%s version disabled", pdfFile.getName(), pdfFile.getVersion()));
	}

	/* (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	@Override
	public void onEnable() {
		instance = this;
		setPm(getServer().getPluginManager());
		pdfFile = getDescription();
		logger.setPrefix(pdfFile.getName());
		logger.log("%s version %s : initialization", pdfFile.getName(), pdfFile.getVersion());

		loadConfiguration();
		if(withDatabase){
			initializeDatabase();
		}
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
	 * Gets the database.
	 *
	 * @return the database
	 */
	public EbeanServer getStaticDatabase() {
		return database.getDatabase();
	}

	/**
	 * Gets the database orm classes.
	 *
	 * @return the database orm classes
	 */
	protected java.util.List<Class<?>> getDatabaseORMClasses() {
		return new ArrayList<Class<?>>();
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

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event) {
	}

	/**
	 * @return the pm
	 */
	public PluginManager getPm() {
		return pm;
	}

	/**
	 * @param pm the pm to set
	 */
	public void setPm(PluginManager pm) {
		this.pm = pm;
	}
}