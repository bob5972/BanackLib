/*
 * This file is part of BanackLib.
 * Copyright (c)2011 Michael Banack <bob5972@banack.net>
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

import java.io.Writer;
import java.io.IOException;

public class Dumper {
    protected Writer myOup;
    
    public Dumper(Writer w)
    {
        myOup = w;
    }
    
    public void writeInt(int i) throws IOException
    {
        String str = Integer.toString(i);
        myOup.write(str);
        myOup.write(" ");
    }
    
    public void writeChar(char c) throws IOException
    {
        myOup.write(c);
    }
    
    public void writeWord(String w) throws IOException
    {
        writeString(w);
        myOup.write(" ");
    }
    
    public void writeString(String w) throws IOException
    {
        myOup.write(w);
    }
}
