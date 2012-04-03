package org.micoli.minecraft.utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

// TODO: Auto-generated Javadoc
/**
 * The Class Images.
 */
public class Images {
	
	/**
	 * Copy src into dst at.
	 *
	 * @param src the src
	 * @param dst the dst
	 * @param dx the dx
	 * @param dy the dy
	 */
	public static void copySrcIntoDstAt(final BufferedImage src, final BufferedImage dst, final int dx, final int dy) {
		// TODO: replace this by a much more efficient method
		for (int x = 0; x < src.getWidth(); x++) {
			for (int y = 0; y < src.getHeight(); y++) {
				dst.setRGB(dx + x, dy + y, src.getRGB(x, y));
			}
		}
	}

	/**
	 * Copy src into dst at memory.
	 *
	 * @param src the src
	 * @param dst the dst
	 * @param dx the dx
	 * @param dy the dy
	 */
	public static void copySrcIntoDstAtMemory(final BufferedImage src, final BufferedImage dst, final int dx, final int dy) {
		byte[] srcbuf = ((DataBufferByte) src.getRaster().getDataBuffer()).getData();
		byte[] dstbuf = ((DataBufferByte) dst.getRaster().getDataBuffer()).getData();
		int width = src.getWidth();
		int height = src.getHeight();
		int dstoffs = dx + dy * dst.getWidth();
		int srcoffs = 0;
		for (int y = 0; y < height; y++, dstoffs += dst.getWidth(), srcoffs += width) {
			System.arraycopy(srcbuf, srcoffs, dstbuf, dstoffs, width);
		}
	}
}
