package org.micoli.minecraft.bukkit;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.micoli.minecraft.bukkit.QDCommand.SenderType;
import org.micoli.minecraft.utils.ChatFormater;
import org.micoli.minecraft.utils.ServerLogger;

/**
 * The Class QDCommandManager.
 */
public class QDCommandManager implements CommandExecutor {

	/** The plugin. */
	private QDBukkitPlugin plugin;
	private HashMap<String, Method> listAliases = new HashMap<String, Method>();
	private HashMap<String, QDCommand> listCommand = new HashMap<String, QDCommand>();

	/**
	 * Instantiates a new qD command manager.
	 * 
	 * @param plugin
	 *            the plugin
	 */
	@SuppressWarnings("rawtypes")
	public QDCommandManager(QDBukkitPlugin plugin, Class[] classes) {
		this.plugin = plugin;
		for (Class classe : classes) {
			for (Method method : classe.getMethods()) {
				if (method.isAnnotationPresent(QDCommand.class)) {
					QDCommand annotation = method.getAnnotation(QDCommand.class);
					listAliases.put(annotation.aliases().toLowerCase(), method);
					listCommand.put(annotation.aliases().toLowerCase(), annotation);
					// ServerLogger.log("Method : " + method.getName());
					// ServerLogger.log("Aliases-> : " + annotation.aliases());
					// ServerLogger.log("Help-> : " + annotation.help());
					// ServerLogger.log("Description-> : " +
					// annotation.description());
				}
			}
		}
		plugin.getCommand(QDBukkitPlugin.getCommandString()).setExecutor(this);
		// ServerLogger.log("----------------------------");
	}
	
	public void commandFeedBack(SenderType senderType,CommandSender sender, String str,Object... args ){
		if(senderType == SenderType.CONSOLE){
			ServerLogger.log(str,args);
		}
		if(senderType == SenderType.PLAYER){
			((Player) sender).sendMessage(ChatFormater.format(str,args));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		SenderType senderType;
		if (sender instanceof Player) {
			senderType = SenderType.PLAYER;
		} else {
			senderType = SenderType.CONSOLE;
		}
		try {
			if (command.getName().equalsIgnoreCase(QDBukkitPlugin.getCommandString())) {
				if (args.length > 0) {
					String subCommand = args[0].toLowerCase();
					ServerLogger.log("Command %s", args[0]);
					if (listAliases.containsKey(subCommand)) {
						QDCommand currentCommand = listCommand.get(subCommand);
						Method currentMethod = listAliases.get(subCommand);
						if (currentCommand.senderType() == QDCommand.SenderType.CONSOLE && senderType != SenderType.CONSOLE) {
							commandFeedBack(senderType,sender,"requires you to be on the console");
							return false;
						}
						
						if (currentCommand.senderType() == QDCommand.SenderType.PLAYER && senderType != SenderType.PLAYER) {
							commandFeedBack(senderType,sender,"requires you to be a player");
						}

						if (currentCommand.senderType() == QDCommand.SenderType.PLAYER && senderType != SenderType.CONSOLE) {
							if(currentCommand.permissions().length>0 && !((Player)sender).isOp()){
								for(String permission: currentCommand.permissions()){
									ServerLogger.log(permission);
									if (!QDBukkitPlugin.vaultPermission.has(((Player)sender), permission)){
										commandFeedBack(senderType,sender," you need permissions "+permission);
										return false;
									}
								}
							}
						}
						try {
							currentMethod.invoke(plugin, sender, command, label, args);
							return true;
						} catch (Exception e) {
							commandFeedBack(senderType,sender,"{ChatColor.RED} %s", e.getMessage());
							e.printStackTrace();
							throw e;
						}
					} else {
						return this.onCommandWithoutAnnotation(sender, command, label, args);
					}
				} else {
					commandFeedBack(senderType,sender,"{ChatColor.RED} Need more arguments");
				}
			}
			return false;
		} catch (Exception ex) {
			ServerLogger.log("Command failure: %s", ex.getMessage());
			ex.printStackTrace();
		}
		return false;
	}

	private boolean onCommandWithoutAnnotation(CommandSender sender, Command command, String label, String[] args) {
		((Player) sender).sendMessage(ChatFormater.format("{ChatColor.RED} command unknown"));
		return false;
	}
}
