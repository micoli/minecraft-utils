package org.micoli.minecraft.bukkit;


public class QDCommandException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3445876474770110991L;

	String valeur;

	public QDCommandException() {
		super();
	}

	public QDCommandException(String message) {
		super(message);
	}

	public QDCommandException(Throwable t) {
		super(t);
	}
}
