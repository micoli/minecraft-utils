package org.micoli.minecraft.bukkit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface QDCommand {
	public enum SenderType { PLAYER, CONSOLE, BOTH };

	public String aliases();
	public String description() default  "";
	public String help() default "";
	public SenderType senderType() default SenderType.PLAYER;
	public String[] permissions() ;
	
}
