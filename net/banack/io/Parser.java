package net.banack.io;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.IOException;
import net.banack.util.MethodNotImplementedException;

//very primitive...

public class Parser
{		
	private BufferedReader myInp;
	private char nextChar;
	private boolean isNextCharValid;
	
	
	public Parser(Reader r)
	{
		myInp=new BufferedReader(r);
		isNextCharValid = false;
	}
	
	//Reads the next integer from the Stream, base 10, discarding any other characters it encounters
	public int parseInt() throws IOException
	{
		int oup = 0;
		
		int state=0;
		boolean isNegative=false;
		
		fillNextChar();
		
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
	
	//make sure we have a char in nextChar
	private void fillNextChar() throws IOException
	{
		if(isNextCharValid)
			return;
		
		readChar();
	}
	
	//put a new char in nextChar
	private void readChar() throws IOException
	{
		nextChar=(char)myInp.read();
		isNextCharValid=true;
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
		fillNextChar();
		isNextCharValid=false;
		return nextChar;
	}
			
	
	public String parseWord() throws IOException
	{
		StringBuffer oup = new StringBuffer();
		fillNextChar();
		while(Character.isWhitespace(nextChar))
		{
			readChar();
		}
		
		while(!Character.isWhitespace(nextChar))
		{
			oup.append(nextChar);
			readChar();
		}
		
		return oup.toString();	
	}
	
	public String readLine() throws IOException
	{
		//HACK
		//if nextChar is an endline, this'll not do what you expect
		//but I'm feeling lazy
		isNextCharValid=false;
		return nextChar+ myInp.readLine();
	}	
}