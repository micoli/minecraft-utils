package org.micoli.minecraft.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

// TODO: Auto-generated Javadoc
/**
 * The Class ExceptionUtils.
 */
public class ExceptionUtils {
	
	/**
	 * Gets the stack trace.
	 *
	 * @param throwable the throwable
	 * @return the stack trace
	 */
	public static String getStackTrace(Throwable throwable) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		throwable.printStackTrace(printWriter);
		return writer.toString();
	}
}