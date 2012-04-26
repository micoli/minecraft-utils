package org.micoli.minecraft.bukkit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.micoli.minecraft.bukkit.QDCommand.SenderType;
import org.micoli.minecraft.utils.ChatFormater;
import org.micoli.minecraft.utils.ExceptionUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class QDCommandManager.
 */
public class QDCommandManager implements CommandExecutor {

	/** The plugin. */
	private QDBukkitPlugin plugin;
	
	/** The list aliases. */
	private HashMap<String, Method> listAliases = new HashMap<String, Method>();
	
	/** The list command. */
	private HashMap<String, QDCommand> listCommand = new HashMap<String, QDCommand>();

	/**
	 * Instantiates a new qD command manager.
	 *
	 * @param plugin the plugin
	 * @param classes the classes
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
		plugin.getCommand(plugin.getCommandString()).setExecutor(this);
		// ServerLogger.log("----------------------------");
	}
	
	/**
	 * Show help.
	 *
	 * @param senderType the sender type
	 * @param sender the sender
	 * @param toLog 
	 * @return true, if successful
	 */
	protected boolean showHelp(SenderType senderType, CommandSender sender, boolean toLog){
		Iterator<String> annotationIterator = listCommand.keySet().iterator();
		while (annotationIterator.hasNext()) {
			QDCommand cmd = listCommand.get(annotationIterator.next());
			String errorStr = isCommandNotAllowed(sender, senderType, cmd);
			if(!errorStr.equalsIgnoreCase("")){
				plugin.logger.log("not allowed "+cmd.aliases()+" "+errorStr);
			}
			commandUsageFeedBack(toLog?SenderType.CONSOLE:senderType, sender, cmd);
		}
		return true;
	}
	
	/**
	 * Command usage feed back.
	 *
	 * @param senderType the sender type
	 * @param sender the sender
	 * @param command the command
	 */
	public void commandUsageFeedBack(SenderType senderType, CommandSender sender, QDCommand command) {
		commandUsageFeedBack(senderType, sender, command,"");
	}

	/**
	 * Command usage feed back.
	 *
	 * @param senderType the sender type
	 * @param sender the sender
	 * @param command the command
	 * @param complement the complement
	 */
	public void commandUsageFeedBack(SenderType senderType, CommandSender sender, QDCommand command,String complement) {
		commandFeedBack(senderType, sender, "{ChatColor.RED}/%s {ChatColor.GREEN}%s {ChatColor.BLUE}%s {ChatColor.RED}\n{ChatColor.GREEN}%s %s", plugin.getCommandString(), command.aliases(), command.usage(),command.description(), complement);
	}

	/**
	 * Command feed back.
	 *
	 * @param senderType the sender type
	 * @param sender the sender
	 * @param str the str
	 * @param args the args
	 */
	public void commandFeedBack(SenderType senderType, CommandSender sender, String str, Object... args) {
		if (senderType == SenderType.CONSOLE) {
			plugin.logger.log(str, args);
		}
		if (senderType == SenderType.PLAYER) {
			((Player) sender).sendMessage(ChatFormater.format(str, args));
		}
	}
	
	
	/**
	 * Checks if is command not allowed.
	 *
	 * @param sender the sender
	 * @param senderType the sender type
	 * @param currentCommand the current command
	 * @return the string
	 */
	public String isCommandNotAllowed(CommandSender sender,SenderType senderType,QDCommand currentCommand){
		if (currentCommand.senderType() == QDCommand.SenderType.CONSOLE && senderType != SenderType.CONSOLE) {
			return  "requires you to be on the console";
		}

		if (currentCommand.senderType() == QDCommand.SenderType.PLAYER && senderType != SenderType.PLAYER) {
			return "requires you to be a player";
		}

		if (currentCommand.senderType() == QDCommand.SenderType.PLAYER && senderType != SenderType.CONSOLE) {
			if (currentCommand.permissions().length > 0 && !((Player) sender).isOp()) {
				for (String permission : currentCommand.permissions()) {
					plugin.logger.log(permission);
					if (!plugin.vaultPermission.has(((Player) sender), permission)) {
						return "You need permissions " + permission;
					}
				}
			}
		}
		return "";
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
			if (command.getName().equalsIgnoreCase(plugin.getCommandString())) {
				if (args.length > 0) {
					String subCommand = args[0].toLowerCase();
					plugin.logger.log("Command=> %s", args[0]);
					if(subCommand.equalsIgnoreCase("helplog")){
						showHelp(senderType, sender,true);
						return true;
					}
					if (listAliases.containsKey(subCommand)) {
						QDCommand currentCommand = listCommand.get(subCommand);
						Method currentMethod = listAliases.get(subCommand);
						String errorStr = isCommandNotAllowed(sender, senderType,currentCommand);
						if(!errorStr.equalsIgnoreCase("")){
							commandFeedBack(senderType, sender, errorStr);
							return false;
						}
						try {
							currentMethod.invoke(plugin, sender, command, label, args);
							return true;
						} catch (InvocationTargetException e) {
							if(e.getCause() instanceof QDCommandUsageException){
								commandUsageFeedBack(senderType, sender, currentCommand, e.getCause().getMessage());
							}else{
								commandFeedBack(senderType, sender, "{ChatColor.RED} %s", e.getCause().getMessage());
								plugin.logger.log(ExceptionUtils.getStackTrace(e));
							}
						} catch (Exception e) {
							commandFeedBack(senderType, sender, "{ChatColor.RED} %s", e.getMessage());
							plugin.logger.log(ExceptionUtils.getStackTrace(e));
							throw e;
						}
					} else {
						return this.onCommandWithoutAnnotation(sender, command, label, args);
					}
				} else {
					showHelp(senderType, sender,false);
				}
			}
			return false;
		} catch (Exception ex) {
			plugin.logger.log("Command failure: %s", ex.getMessage());
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * On command without annotation.
	 *
	 * @param sender the sender
	 * @param command the command
	 * @param label the label
	 * @param args the args
	 * @return true, if successful
	 */
	private boolean onCommandWithoutAnnotation(CommandSender sender, Command command, String label, String[] args) {
		((Player) sender).sendMessage(ChatFormater.format("{ChatColor.RED} command unknown"));
		return false;
	}
}
