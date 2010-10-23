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

import java.io.Reader;
import java.io.BufferedReader;
import java.io.PushbackReader;
import java.io.IOException;
import net.banack.util.MethodNotImplementedException;
import net.banack.util.Stack;

//very primitive...

public class Parser
{		
	protected PushbackReader myInp;
	protected char nextChar;
	
	
	public Parser(Reader r)
	{
		myInp=new PushbackReader(r);
	}
	
	//Reads the next integer from the Stream, base 10, discarding any other characters it encounters
	public int readInt() throws IOException
	{
		int oup = 0;
		
		int state=0;
		boolean isNegative=false;
		
		fillChar();
		
		while(true)
		{
			switch(state)
			{
				case 0: //bad char
					while(nextChar != '-' && !isDigit(nextChar))
					{
						fillChar();
					}
					if(nextChar == '-') {
						state = 1;
					} else { //isDigit(nextChar)
						state = 2;
					}
				break;
				case 1: //found '-'
					fillChar();
					if(!isDigit(nextChar)) {
						state=0;
					} else {
						state=2;
						isNegative=true;
					}
				break;
				case 2: //found #
					while(isDigit(nextChar)) {
						oup*=10;
						oup += nextChar - '0';
						fillChar();
					}
					myInp.unread(nextChar);
					if(isNegative) {
						oup=-oup;
					}
				return oup;
			}			
		}		
	}
	
	protected static boolean isDigit(char c)
	{
		return '0' <= c && c <= '9';
	}
	
	//put a new char in nextChar
	protected void fillChar() throws IOException
	{
		nextChar=(char)myInp.read();
	}
	
	public int[] readIntArray(int size) throws IOException
	{
		int[] oup = new int[size];
		
		for(int x=0;x<size;x++)
		{
			oup[x] = readInt();
		}
		return oup;
	}
	
	public void readIntArray(int[] a) throws IOException
	{
		for(int x=0;x<a.length;x++)
		{
			a[x] = readInt();
		}
	}
	
	public char readChar() throws IOException
	{
		fillChar();
		return nextChar;
	}
	
	public char readNonWhitespaceChar() throws IOException
	{
		fillChar();
		while(Character.isWhitespace(nextChar))
			fillChar();
		return nextChar;
	}
			
	
	public String readWord() throws IOException
	{
		StringBuffer oup = new StringBuffer();
		fillChar();
		while(Character.isWhitespace(nextChar))
		{
			fillChar();
		}
		
		while(!Character.isWhitespace(nextChar))
		{
			oup.append(nextChar);
			fillChar();
		}
		myInp.unread(nextChar);
		return oup.toString();	
	}
	
	public String[] readWords() throws IOException
	{
		String line = readLine();
		
		return parseWords(line);
	}
	
	//reads num words
	public String[] readWords(int num) throws IOException
	{
		String[] oup = new String[num];
		for(int x=0;x<num;x++)
		{
			oup[x] = readWord();
		}
		return oup;
	}
		
	
	public String readLine() throws IOException
	{
		StringBuffer oup = new StringBuffer();
		fillChar();
		while(nextChar != '\n')
		{
			oup.append(nextChar);
			fillChar();
		}
		
		return oup.toString();
	}
	
	//parse a string into its whitespace seperated tokens
	public static String[] parseWords(String line)
	{
		StringBuffer cur = new StringBuffer();
		Stack<String> oup = new Stack<String>();
		
		int x = 0;
		int len = line.length();
		
		while(x < len)
		{
			cur.setLength(0);
			while(x< len && Character.isWhitespace(line.charAt(x)))
			{
				x++;
			}
			if(x >= len)
				break;
			
			while(x< len && !Character.isWhitespace(line.charAt(x)))
			{
				cur.append(line.charAt(x));
				x++;
			}
			oup.push(cur.toString());
		}
		
		String[] sOup = new String[oup.size()];
		for(x=sOup.length-1;x>=0;x--)
		{
			sOup[x] = (String)(oup.pop());
		}

		return sOup;
	}
	
	public static int parseInt(String text)
	{
		int oup = 0;
		
		int state=0;
		boolean isNegative=false;
		
		int x =0;
		
		try {
			while(true)
			{
				switch(state)
				{
					case 0: //bad char
						while(x<text.length() && text.charAt(x) != '-' && !isDigit(text.charAt(x)))
						{
							x++;
						}
						if(x>=text.length())
							throw new IndexOutOfBoundsException();//cheap goto
						if(text.charAt(x) == '-')
							state =1;
						else //isDigit(nextChar)
							state = 2;
					break;
					case 1: //found '-'
						x++;
						if(x>=text.length())
							throw new IndexOutOfBoundsException();//cheap goto
						if(!isDigit(text.charAt(x)))
							state=0;
						else
						{
							state=2;
							isNegative=true;
						}
					break;
					case 2: //found #
						while(isDigit(text.charAt(x)))
						{
							oup*=10;
							oup += text.charAt(x) - '0';
							x++;
							if(x>=text.length())
								break;
						}
						if(isNegative)
							oup=-oup;
					return oup;
				}
			}
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new NumberFormatException("Malformed integer: "+text);
		}
	}
	
}