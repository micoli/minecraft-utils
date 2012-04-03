package org.micoli.minecraft.utils;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

// TODO: Auto-generated Javadoc
//http://forums.bukkit.org/threads/the-task-class.32623/
/**
 * The Class Task.
 */
public class Task implements Runnable {
	
	/** The plugin. */
	private JavaPlugin plugin;
	
	/** The arguments. */
	private Object[] arguments;
	
	/** The task id. */
	private int taskID = 0;

	/**
	 * Creates the.
	 *
	 * @param plugin the plugin
	 * @param arguments the arguments
	 * @return the task
	 */
	public static Task create(JavaPlugin plugin, Object... arguments) {
		return new Task(plugin, arguments);
	}
	
	/**
	 * Instantiates a new task.
	 *
	 * @param plugin the plugin
	 * @param arguments the arguments
	 */
	public Task(JavaPlugin plugin, Object... arguments) {
		this.plugin = plugin;
		this.arguments = arguments;
	}

	/**
	 * Gets the plugin.
	 *
	 * @return the plugin
	 */
	public JavaPlugin getPlugin() {
		return this.plugin;
	}
	
	/**
	 * Gets the server.
	 *
	 * @return the server
	 */
	public Server getServer() {
		return this.plugin.getServer();
	}
	
	/**
	 * Gets the arg.
	 *
	 * @param index the index
	 * @return the arg
	 */
	public Object getArg(int index) {
		return arguments[index];
	}
	
	/**
	 * Gets the int arg.
	 *
	 * @param index the index
	 * @return the int arg
	 */
	public int getIntArg(int index) {
		return (Integer) getArg(index);
	}
	
	/**
	 * Gets the long arg.
	 *
	 * @param index the index
	 * @return the long arg
	 */
	public long getLongArg(int index) {
		return (Long) getArg(index);
	}
	
	/**
	 * Gets the float arg.
	 *
	 * @param index the index
	 * @return the float arg
	 */
	public float getFloatArg(int index) {
		return (Float) getArg(index);
	}
	
	/**
	 * Gets the double arg.
	 *
	 * @param index the index
	 * @return the double arg
	 */
	public double getDoubleArg(int index) {
		return (Double) getArg(index);
	}
	
	/**
	 * Gets the string arg.
	 *
	 * @param index the index
	 * @return the string arg
	 */
	public String getStringArg(int index) {
		return (String) getArg(index);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

	}

	/**
	 * Checks if is queued.
	 *
	 * @return true, if is queued
	 */
	public boolean isQueued() {
		return this.getServer().getScheduler().isQueued(this.taskID);
	}
	
	/**
	 * Checks if is running.
	 *
	 * @return true, if is running
	 */
	public boolean isRunning() {
		return this.getServer().getScheduler().isCurrentlyRunning(this.taskID);
	}
	
	/**
	 * Stop.
	 */
	public void stop() {
		this.getServer().getScheduler().cancelTask(this.taskID);
	}
	
	/**
	 * Start.
	 */
	public void start() {
		start(false);
	}
	
	/**
	 * Start.
	 *
	 * @param Async the async
	 */
	public void start(boolean Async) {
		startDelayed(0, Async);
	}
	
	/**
	 * Start delayed.
	 *
	 * @param tickDelay the tick delay
	 */
	public void startDelayed(long tickDelay) {
		startDelayed(tickDelay, false);
	}
	
	/**
	 * Start delayed.
	 *
	 * @param tickDelay the tick delay
	 * @param Async the async
	 */
	public void startDelayed(long tickDelay, boolean Async) {
		if (Async) {
			this.taskID = this.getServer().getScheduler().scheduleAsyncDelayedTask(this.plugin, this, tickDelay);
		} else {
			this.taskID = this.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, this, tickDelay);
		}
	}
	
	/**
	 * Start repeating.
	 *
	 * @param tickInterval the tick interval
	 */
	public void startRepeating(long tickInterval) {
		startRepeating(tickInterval, false);
	}
	
	/**
	 * Start repeating.
	 *
	 * @param tickInterval the tick interval
	 * @param Async the async
	 */
	public void startRepeating(long tickInterval, boolean Async) {
		startRepeating(0, tickInterval, Async);
	}
	
	/**
	 * Start repeating.
	 *
	 * @param tickDelay the tick delay
	 * @param tickInterval the tick interval
	 * @param Async the async
	 */
	public void startRepeating(long tickDelay, long tickInterval, boolean Async) {
		if (Async) {
			this.taskID = this.getServer().getScheduler().scheduleAsyncRepeatingTask(this.plugin, this, tickDelay, tickInterval);
		} else {
			this.taskID = this.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, this, tickDelay, tickInterval);
		}
	}

}