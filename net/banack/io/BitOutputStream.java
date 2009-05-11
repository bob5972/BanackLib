/*
 * This file is part of BanackLib.
 * Copyright (c)2009 Michael Banack <bob5972@banack.net>
 * 
 * BanackLib is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * BanackLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with BanackLib.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.banack.io;

import java.io.IOException;
import java.io.OutputStream;



/**
 * Class for writing bits to an OutputStream one at a time.
 * 
 * @author Michael Banack
 * @version 1.0c
 */

public class BitOutputStream extends OutputStream
{
	/** Stores the underlying OutputStream. */
	private OutputStream myOup;
	/** Stores the next bit to be written. */
	private int myPos;
	/** Stores the buffered data to be sent to the OutputStream. */
	private int myBuffer;
	/** Stores the size of a byte. */
	public static final int BYTE_SIZE = 8;
	/** Should be BYTE_SIZE 1's */
	private static final int BYTE_MASK = 0xFF;
	
	/**
	 * Constructs a BitOutputStream around the given OutputStream.
	 */
	public BitOutputStream(OutputStream oup)
	{
		myOup = oup;
		myPos = 1 << (BYTE_SIZE -1 );
		myBuffer = 0;
	}
	
	/**
	 * Flushes any remaining data and closes the underlying stream.
	 * 0's are written to fill any last incomplete byte.
	 * @throws IOException if an I/O Error occurs.
	 */
	public void close() throws IOException
	{
		while ((myPos & BYTE_MASK) != 0)
		{
			writeBit(0);
		}
		flush();
		myOup.close();
	}
	
	/**
	 * Writes a byte to the underlying OutputStream.
	 * @throws IOException if an I/O Error occurs.
	 */
	public void write(int b) throws IOException
	{
		int tPos = 1 << (BYTE_SIZE -1 );
		for(int x=0;x<BYTE_SIZE;x++)
		{
			writeBit(b & tPos);
			tPos >>= 1;
		}
	}
	
	/** 
	 * Writes a bit to the underlying OutputStream.
	 * <p> if b == 0 it writes a 0
	 * <p> if b != 0 it writes a 1
	 * @throws IOException if an I/O Error occurs.
	 */
	public void writeBit(int b) throws IOException
	{
		if ((myPos & BYTE_MASK) == 0)
		{
			myOup.write(myBuffer);
			myBuffer = 0;
			myPos = 1 << (BYTE_SIZE - 1);
		}
		if (b != 0)
			myBuffer |= myPos;
		myPos >>=1;
	}
	
	/**
	 * Writes the bits specified by the string to the OutputStream.
	 * <p> Strings may contain a sequence of 1's, 0's and spaces such as "110 10"
	 * <p> Spaces are ignored.
	 * @throws IOException if an I/O Error occurs, particularly if bad characters are encountered
	 */
	public void writeBits(String bits) throws IOException
	{
		if (bits == null)
			return;
		for(int x=0;x<bits.length();x++)
		{	
			if (bits.charAt(x) == '1')
				writeBit(1);
			else if (bits.charAt(x) == '0')
				writeBit(0);
			else if (bits.charAt(x) == ' ')
				continue;
			else
				throw new IOException("Illegal Character \'" + bits.charAt(x) + "\'");
		}
	}
	
	/**
	 * Writes any complete bytes and calls flush() on the underlying stream.
	 * @throws IOException if an I/O Error occurs.
	 */
	public void flush() throws IOException
	{
		if ((myPos & BYTE_MASK) == 0)
		{
			myOup.write(myBuffer);
			myBuffer = 0;
			myPos = 1 << (BYTE_SIZE -1);
		}
		myOup.flush();
	}
	
	/**
	 * Returns the number of stray bits left in the stream.
	 * ie the total number of bits % BYTE_SIZE
	 */
	public int strayBits()
	{
		if ((myPos & BYTE_MASK) == 0)
			return 0;
		int tPos = myPos;
		int count = 0;
		while ((tPos & BYTE_MASK) != 0)
		{
			count++;
			tPos >>= 1;
		}
		return count;
	}
		

	
};
