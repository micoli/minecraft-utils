package org.micoli.minecraft.bukkit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface QDCommand.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface QDCommand {

	/**
	 * The Enum SenderType.
	 */
	public enum SenderType {
		/** The PLAYER. */
		PLAYER,
		/** The CONSOLE. */
		CONSOLE,
		/** The BOTH. */
		BOTH
	};

	/**
	 * Aliases.
	 * 
	 * @return the string
	 */
	public String aliases();

	/**
	 * Description.
	 * 
	 * @return the string
	 */
	public String description() default "";

	/**
	 * Usage.
	 * 
	 * @return the string
	 */
	public String usage() default "";

	/**
	 * Help.
	 * 
	 * @return the string
	 */
	public String help() default "";

	/**
	 * Sender type.
	 * 
	 * @return the sender type
	 */
	public SenderType senderType() default SenderType.PLAYER;

	/**
	 * Permissions.
	 * 
	 * @return the string[]
	 */
	public String[] permissions();

}
