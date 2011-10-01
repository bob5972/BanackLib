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

public class ParserFormatException extends RuntimeException
{

        public ParserFormatException()
        {

        }

        public ParserFormatException(String arg0)
        {
                super(arg0);
        }

        public ParserFormatException(Throwable arg0)
        {
                super(arg0);
        }

        public ParserFormatException(String arg0, Throwable arg1)
        {
                super(arg0, arg1);
        }

}
