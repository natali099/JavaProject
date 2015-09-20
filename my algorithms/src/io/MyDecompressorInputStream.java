package io;

import java.io.IOException;
import java.io.InputStream;

/**
 * The Class MyDecompressorInputStream.
 */
public class MyDecompressorInputStream extends InputStream {
	
	/** The input stream. */
	private InputStream in;
	
	/** The last read byte. */
	private int lastByte;
	
	/** The amount of times a byte was read. */
	private int count;
	
	/**
	 * Instantiates a new my decompressor input stream.
	 *
	 * @param in the input stream
	 */
	public MyDecompressorInputStream(InputStream in) {
		this.in = in;
		count = 1;
	}
	
	/**
	 * Reads a byte and it's amount from the input stream and returns it that amount of times
	 * 
	 * @return the read byte, represented by an int
	 * @see java.io.InputStream#read()
	 */
	@Override
	public int read() throws IOException {
		if (count > 1) {
			count--;
			return lastByte;
		}
		
		lastByte = in.read();
		count = in.read();
		
		return lastByte;
	}
}
