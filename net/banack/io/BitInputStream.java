package net.banack.io;

import java.io.IOException;
import java.io.InputStream;


/**
 * Class for reading bits from an InputStream one at a time.
 * 
 * @author Michael Banack
 * @version 1.0c
 */

public class BitInputStream extends InputStream
{
	/** Stores a reference to the input stream. */
	private InputStream myInp;
	/** Stores the current byte being read from. */
	private int myBuffer;
	/** Stores the next bit to be read from the buffer. */
	private int myPos;
	/** Stores the size of a byte. */
	private static final int BYTE_SIZE = 8;
	/** Should contain BYTE_SIZE 1's. */
	private static final int BYTE_MASK = 0xFF;

	/**
	 * Constructs a BitInputStream from an InputStream
	 */
	public BitInputStream(InputStream inp)
	{
		myInp = inp;
		myPos = 0;
	}
	
	/**
	 * Reads 8 bits and returns them as an integer from 0 to 255. If there are not 8-bits remaining in the stream, then
	 * it reads as many as it can, and puts them in the higher bits of the byte.
	 * 
	 * @throws IOException if an I/O Error occurs.
	 * @return the next 8-bits as an integer from 0 to 255, or -1 if the end of the stream has been reached
	 */
	public int read() throws IOException
	{
		int oup = 0;
		int tBit = 0;
		for (int x=0;x<BYTE_SIZE;x++)
		{
			tBit = readBit();
			if (tBit == -1)
			{
				//End of Stream Reached
				if(x==0)
					return -1;
				else
					return oup;
			}
			
			tBit <<= (BYTE_SIZE-x-1); 
			oup |= tBit;
		}
		return oup;
	}
	
	/**
	 * Reads the next bit from the input stream.
	 * @throws IOException if an I/O Error occurs.
	 * @return 0 or 1 if successful, -1 if the end of the stream is reached.
	 */
	
	public int readBit() throws IOException
	{
		if ((myPos & BYTE_MASK) == 0)
		{
			myBuffer = myInp.read();
			myPos = 1 << (BYTE_SIZE -1 );
		}
		if (myBuffer == -1)
			return -1;
		//End of Stream Reached
		
		int oup = (myBuffer & myPos) == 0? 0 : 1;
		myPos >>=1;
		return oup;
	}
	
	/**
	 * BitInputStream does not support mark operations.
	 * @return false
	 * @see java.io.InputStream InputStream.mark(int readLimit)
	 */
	public boolean markSupported()
	{
		return false;
	}
	
	/**
	 * Closes the underlying stream.
	 * @throws IOException if an I/O Error occurs.
	 */
	public void close() throws IOException
	{
		myInp.close();
	}
	
	/**
	 * Returns the number of bytes that can be read from the underlying stream without blocking.
	 * Only complete bytes are counted.  Stray bits are ignored.
	 * @throws IOException if an I/O Error occurs.
	 */
	public int available() throws IOException
	{
		return myInp.available();
	}
			
};

