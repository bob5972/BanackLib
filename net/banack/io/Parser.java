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
	private PushbackReader myInp;
	private char nextChar;
	
	
	public Parser(Reader r)
	{
		myInp=new PushbackReader(r);
	}
	
	//Reads the next integer from the Stream, base 10, discarding any other characters it encounters
	public int parseInt() throws IOException
	{
		int oup = 0;
		
		int state=0;
		boolean isNegative=false;
		
		readChar();
		
		while(true)
		{
			switch(state)
			{
				case 0: //bad char
					while(nextChar != '-' && !isDigit(nextChar))
					{
						readChar();
					}
					if(nextChar == '-')
						state =1;
					else //isDigit(nextChar)
						state = 2;
				break;
				case 1: //found '-'
					readChar();
					if(!isDigit(nextChar))
						state=0;
					else
					{
						state=2;
						isNegative=true;
					}
				break;
				case 2: //found #
					while(isDigit(nextChar))
					{
						oup*=10;
						oup += nextChar - '0';
						readChar();
					}
					myInp.unread(nextChar);
					if(isNegative)
						oup=-oup;
				return oup;
			}			
		}		
	}
	
	private boolean isDigit(char c)
	{
		return '0' <= c && c <= '9';
	}
	
	//put a new char in nextChar
	private void readChar() throws IOException
	{
		nextChar=(char)myInp.read();
	}
	
	public int[] parseIntArray(int size) throws IOException
	{
		int[] oup = new int[size];
		
		for(int x=0;x<size;x++)
		{
			oup[x] = parseInt();
		}
		return oup;
	}
	
	public void parseIntArray(int[] a) throws IOException
	{
		for(int x=0;x<a.length;x++)
		{
			a[x] = parseInt();
		}
	}
	
	public char parseChar() throws IOException
	{
		readChar();
		return nextChar;
	}
			
	
	public String parseWord() throws IOException
	{
		StringBuffer oup = new StringBuffer();
		readChar();
		while(Character.isWhitespace(nextChar))
		{
			readChar();
		}
		
		while(!Character.isWhitespace(nextChar))
		{
			oup.append(nextChar);
			readChar();
		}
		myInp.unread(nextChar);
		return oup.toString();	
	}
	
	public String[] readWords() throws IOException
	{
		Stack oup = new Stack();
		StringBuffer cur = new StringBuffer();

		readChar();
		
		while(nextChar != '\n')
		{
			cur.setLength(0);
			while(Character.isWhitespace(nextChar) && nextChar != '\n')
			{
				readChar();
			}
			if(nextChar == '\n')
				break;
			
			while(!Character.isWhitespace(nextChar))
			{
				cur.append(nextChar);
				readChar();
			}
			oup.push(cur);
		}
		
		String[] sOup = new String[oup.size()];
		for(int x=sOup.length-1;x>=0;x--)
		{
			sOup[x] = (String)oup.pop();
		}
		
		return sOup;
	}
		
	
	public String readLine() throws IOException
	{
		StringBuffer oup = new StringBuffer();
		readChar();
		while(nextChar != '\n')
		{
			oup.append(nextChar);
			readChar();
		}
		
		return oup.toString();
	}	
}