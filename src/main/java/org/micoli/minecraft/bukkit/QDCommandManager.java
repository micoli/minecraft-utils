package org.micoli.minecraft.bukkit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.micoli.minecraft.bukkit.QDCommand.SenderType;
import org.micoli.minecraft.utils.ChatFormater;
import org.micoli.minecraft.utils.ExceptionUtils;
import org.micoli.minecraft.utils.PluginEnvironment;
import org.micoli.minecraft.utils.StringUtils;

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
	
	private Set<String> permissions = new HashSet<String>();

	/**
	 * Instantiates a new qD command manager.
	 *
	 * @param plugin the plugin
	 * @param classes the classes
	 */
	@SuppressWarnings("rawtypes")
	public QDCommandManager(QDBukkitPlugin plugin, Class[] classes) {
		this.plugin = plugin;
		this.plugin.setExecutor(this);
		for (Class classe : classes) {
			for (Method method : classe.getMethods()) {
				if (method.isAnnotationPresent(QDCommand.class)) {
					QDCommand annotation = method.getAnnotation(QDCommand.class);
					listAliases.put(annotation.aliases().toLowerCase(), method);
					listCommand.put(annotation.aliases().toLowerCase(), annotation);
					for(int i=0;i<annotation.permissions().length;i++){
						if(annotation.permissions()[i]!=null){
							permissions.add(annotation.permissions()[i]);
						}
					}
					StringBuffer buff = new StringBuffer();
					buff.append(String.format("Method : %s", method.getName()));
					buff.append(String.format(", Aliases : %s", annotation.aliases()));
					buff.append(String.format(", Help : %s", annotation.help()));
					buff.append(String.format(", Description : %s",	annotation.description()));
					buff.append(String.format(", Permissions : %s",	StringUtils.join(annotation.permissions())));
					plugin.logger.log(buff.toString());
					//Permission permission = PluginEnvironment.getVaultPermission(plugin);
				}
			}
		}
		plugin.getCommand(plugin.getCommandString()).setExecutor(this);
	}
	
	/**
	 * Show help.
	 *
	 * @param senderType the sender type
	 * @param sender the sender
	 * @param toLog the to log
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
		sender.sendMessage(ChatFormater.format(str, args));
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
					if (!PluginEnvironment.getVaultPermission(plugin).has(((Player) sender), permission)) {
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
					String fullCommand = "";
					for(String st : args){
						fullCommand = fullCommand +" "+ st;
					}
					plugin.logger.log("Command [%s] %s", sender.getName() ,fullCommand);
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
								commandFeedBack(SenderType.CONSOLE, null, "{ChatColor.RED} %s", e.getCause().getMessage());
								//plugin.logger.log(ExceptionUtils.getStackTrace(e));
							}
						} catch (Exception e) {
							commandFeedBack(senderType, sender, "{ChatColor.RED} %s", e.getMessage());
							commandFeedBack(SenderType.CONSOLE, null, "{ChatColor.RED} %s", e.getCause().getMessage());
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
			plugin.logger.dumpStackTrace(ex);
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
		sender.sendMessage(ChatFormater.format("{ChatColor.RED} command unknown"));
		return false;
	}

	public void dumpPermissions(CommandSender sender) {
		
		sender.sendMessage("permissions dumped to log");
		for(String permission:permissions){
			sender.sendMessage("  - "+permission);
		}
		sender.sendMessage("permissions dumped to log");
	}
}
