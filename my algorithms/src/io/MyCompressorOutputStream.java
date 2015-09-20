package io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * The Class MyCompressorOutputStream.
 */
public class MyCompressorOutputStream extends OutputStream {
	
	/** The output stream. */
	private OutputStream out;
	
	/** The last read byte. */
	private int lastByte;
	
	/** The amount of times a byte was read. */
	private int count;
	
	/**
	 * Instantiates a new my compressor output stream.
	 *
	 * @param out the output stream
	 */
	public MyCompressorOutputStream(OutputStream out) {
		this.out = out;
		this.count = 0;
	}

	/**
	 * Writes a byte to the output stream and the amount of times it appears sequentially
	 * 
	 * @param b the byte to be written, represented by an int
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) throws IOException {
		if (count == 0) {
			lastByte = b;
			count++;
		}
		else if (b == lastByte) {
			if (count == 255) {
				out.write(lastByte);
				out.write(count);
				count = 0;
			}
			count++;
		}
		else {
			out.write(lastByte);
			out.write(count);
			lastByte = b;
			count = 1;
		}

	}
	
	/**
	 * Writes a byte array to the output stream
	 * 
	 * @param b the byte array to be written
	 * @see java.io.OutputStream#write(byte[])
	 */
	@Override
	public void write(byte[] b) throws IOException {
		super.write(b);
		//if (count > 0) {
		//write also the last byte and its amount
		out.write(lastByte);
		out.write(count);
		//}
		count = 0;		
	}

}
