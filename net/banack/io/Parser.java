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
import java.io.PushbackReader;
import java.io.IOException;
import net.banack.util.Stack;

//very primitive...

public class Parser {
    protected PushbackReader myInp;
    protected boolean hasNextChar;
    protected char nextChar;
    
    protected boolean eatGarbage;
    protected boolean eatWhitespace;
    protected boolean throwExceptions;    
    
    public Parser(Reader r)
    {
        myInp = new PushbackReader(r);
        hasNextChar = false;
        
        eatGarbage = true;
        eatWhitespace = true;
        throwExceptions = false;
    }
    
    // Reads the next integer from the Stream, base 10
    public int readInt() throws IOException
    {
        int oup = 0;
        
        int state = 0;
        boolean isNegative = false;
        
        fillChar();
        
        while (hasNextChar) {
            switch (state) {
            case 0: // bad char
                while (nextChar != '-' && !isDigit(nextChar)) {
                    if ((Character.isWhitespace(nextChar) && eatWhitespace) || 
                            eatGarbage) {
                        fillChar();
                    } else if (throwExceptions) {
                        throw new ParserFormatException("No digits found");
                    } else {
                        return 0;
                    }
                }
                
                if (nextChar == '-') {
                    state = 1;
                } else {
                    assert(isDigit(nextChar));
                    state = 2;
                }
                break;
            case 1: // found '-'
                assert(nextChar == '-');
                fillChar();
                if (!isDigit(nextChar)) {
                    state = 0;
                } else {
                    state = 2;
                    isNegative = true;
                }
                break;
            case 2: // found digit
                while (isDigit(nextChar)) {
                    oup *= 10;
                    oup += nextChar - '0';
                    fillChar();
                }
                myInp.unread(nextChar);
                if (isNegative) {
                    oup = -oup;
                }
                return oup;
            }
        }
        
        assert(!hasNextChar);
        if (throwExceptions) {
            throw new ParserFormatException("End of stream reached");
        }
        
        return 0;
    }
    
    protected static boolean isDigit(char c)
    {
        return '0' <= c && c <= '9';
    }
    
    /*
     *  Put a new char in nextChar.
     *  Return true iff we got a char
     *  
     *  The contents of nextChar should be invalid by the time any public
     *  read method returns.  Anything that a public method wants to leave
     *  "unread" should be pushed back onto myInp.
     *  
     *  In other words, each method should assume it needs to call
     *  fillChar to get a new character when it is called, and
     *  after calling any other read methods.
     */
    protected boolean fillChar() throws IOException
    {
        int next = myInp.read();
        
        nextChar = (char) next;
        
        hasNextChar = (next != -1);
        return hasNextChar;
    }
    
    /*
     * Move the contents of nextChar back into myInp.
     */
    protected void unreadChar() throws IOException
    {
        assert(hasNextChar);
        myInp.unread(nextChar);
    }
    
    protected void eatWhitespace() throws IOException
    {
        fillChar();
        while (hasNextChar && Character.isWhitespace(nextChar)) {
            fillChar();
        }
        
        if (hasNextChar) {
            unreadChar();
        }
    }
    
    // ie read 12,  34, 23,3
    public int[] readIntArray(int size) throws IOException
    {
        int[] oup = new int[size];
        
        for (int x = 0; x < size; x++) {
            oup[x] = readInt();
            eatWhitespace();
            
            fillChar();
            if (hasNextChar && nextChar != ',') {
                unreadChar();
            }
        }
        return oup;
    }
    
    public void readIntArray(int[] a) throws IOException
    {
        int size = a.length;
        
        for (int x = 0; x < size; x++) {
            a[x] = readInt();
            eatWhitespace();
            
            fillChar();
            if (hasNextChar && nextChar != ',') {
                unreadChar();
            }
        }
    }
    
    public char readChar() throws IOException
    {
        fillChar();
        
        if (!hasNextChar && throwExceptions) {
            throw new ParserFormatException("End of stream reached");
        }
        
        return nextChar;
    }
    
    public char readNonWhitespaceChar() throws IOException
    {
        eatWhitespace();
        
        fillChar();
        
        if (!hasNextChar && throwExceptions) {
            throw new ParserFormatException("End of stream reached");
        }
        
        assert(!hasNextChar || !Character.isWhitespace(nextChar));
        return nextChar;
    }
    
    
    public String readWord() throws IOException
    {
        StringBuffer oup = new StringBuffer();
        eatWhitespace();
        
        fillChar();
        
        while (hasNextChar && !Character.isWhitespace(nextChar)) {
            oup.append(nextChar);
            fillChar();
        }
        
        if (hasNextChar) {
            unreadChar();
        }
        
        return oup.toString();
    }
    
    /*
     * Reads a line, and returns words.
     */
    public String[] readWords() throws IOException
    {
        String line = readLine();
        
        return parseWords(line);
    }
    
    // reads num words
    public String[] readWords(int num) throws IOException
    {
        String[] oup = new String[num];
        for (int x = 0; x < num; x++) {
            oup[x] = readWord();
        }
        return oup;
    }
    
    
    public String readLine() throws IOException
    {
        StringBuffer oup = new StringBuffer();
        fillChar();
        while (nextChar != '\n') {
            oup.append(nextChar);
            fillChar();
        }
        
        return oup.toString();
    }
    
    // parse a string into its whitespace separated tokens
    public static String[] parseWords(String line)
    {
        StringBuffer cur = new StringBuffer();
        Stack<String> oup = new Stack<String>();
        
        int x = 0;
        int len = line.length();
        
        while (x < len) {
            cur.setLength(0);
            while (x < len && Character.isWhitespace(line.charAt(x))) {
                x++;
            }
            if (x >= len)
                break;
            
            while (x < len && !Character.isWhitespace(line.charAt(x))) {
                cur.append(line.charAt(x));
                x++;
            }
            oup.push(cur.toString());
        }
        
        String[] sOup = new String[oup.size()];
        for (x = sOup.length - 1; x >= 0; x--) {
            sOup[x] = (String) (oup.pop());
        }
        
        return sOup;
    }
    
    //This was designed for SpaceRobots, and hasn't ever been integrated...
    public static int parseInt(String text)
    {
        int oup = 0;
        
        int state = 0;
        boolean isNegative = false;
        
        int x = 0;
        
        try {
            while (true) {
                switch (state) {
                case 0: // bad char
                    while (x < text.length() && text.charAt(x) != '-'
                            && !isDigit(text.charAt(x))) {
                        x++;
                    }
                    if (x >= text.length())
                        throw new IndexOutOfBoundsException();// cheap goto
                    if (text.charAt(x) == '-')
                        state = 1;
                    else
                        // isDigit(nextChar)
                        state = 2;
                    break;
                case 1: // found '-'
                    x++;
                    if (x >= text.length())
                        throw new IndexOutOfBoundsException();// cheap goto
                    if (!isDigit(text.charAt(x)))
                        state = 0;
                    else {
                        state = 2;
                        isNegative = true;
                    }
                    break;
                case 2: // found #
                    while (isDigit(text.charAt(x))) {
                        oup *= 10;
                        oup += text.charAt(x) - '0';
                        x++;
                        if (x >= text.length())
                            break;
                    }
                    if (isNegative)
                        oup = -oup;
                    return oup;
                }
            }
        }
        catch (IndexOutOfBoundsException e) {
            throw new NumberFormatException("Malformed integer: " + text);
        }
    }
    
}
