package org.micoli.minecraft.utils;

/**
 * The Class StringUtils.
 */
public class StringUtils {
	private StringUtils(){
		
	}
	/**
	 * Fixed length.
	 * 
	 * @param string
	 *            the string
	 * @param length
	 *            the length
	 * @return the string
	 */
	public static String fixedLength(String string, int length) {
		return String.format("%-" + String.format("%d", length) + "s", string.substring(0, Math.min(string.length(), length)));
	}

	/**
	 * Join a collection
	 *
	 * @param strings the strings
	 * @return the string
	 */
	public static String join(String[] strings) {
		return join(strings, ", ");
	}

	/**
	 * Join a collection.
	 *
	 * @param strings the strings
	 * @param sepa the sepa
	 * @return the string
	 */
	public static String join(String[] strings, String delim) {
		if(strings==null){
			return "";
		}
		String sepa = "";
		StringBuffer buffer = new StringBuffer();
		for (int i=0;i<strings.length;i++) {
			buffer.append(sepa).append(strings[i]);
			sepa = delim;
		}
		return buffer.toString();
	}
}
