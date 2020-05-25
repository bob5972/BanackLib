/*
 * This file is part of BanackLib.
 * Copyright (c)2009 Michael Banack <github@banack.net>
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

package net.banack.debug;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import net.banack.util.Stack;

public class Debug {
    private static boolean DEBUG = false;
    
    public static class MessageOptions {
        public boolean stdOut;
        public boolean stdErr;
        public boolean log;
        
        public MessageOptions(boolean out, boolean err, boolean log)
        {
            this.stdOut = out;
            this.stdErr = err;
            this.log = log;
        }
    }
    
    public static MessageOptions printOptions;
    public static MessageOptions warningOptions;
    public static MessageOptions logOptions;
    public static MessageOptions panicOptions;
    
    /*
     * Initialize message options.
     */
    static {
        enableMessages();
    }
    
    private static String LOG_FILE = "debug.log";
    
    private static PrintWriter logOut;
    private static Stack<Long> timerStack = new Stack<Long>();
    
    public static void setMessageDefaults()
    {
        
    }
    
    public static void enableDebug()
    {
        DEBUG = true;
    }
    
    public static void setDebug(boolean b)
    {
        DEBUG = b;
    }
    
    public static void disableDebug()
    {
        DEBUG = false;
    }
    
    public static boolean isDebug()
    {
        return DEBUG;
    }
    
    public static void enableMessages()
    {
        printOptions = new MessageOptions(true, false, true);
        warningOptions = new MessageOptions(false, true, true);
        logOptions = new MessageOptions(false, false, true);
        panicOptions = new MessageOptions(false, true, true);
    }
    
    public static void disableMessages()
    {
        printOptions = new MessageOptions(false, false, false);
        warningOptions = new MessageOptions(false, true, false);
        logOptions = new MessageOptions(false, false, true);
        panicOptions = new MessageOptions(false, true, true);
    }
    
    
    public static void setMessages(boolean b)
    {
        if (b) {
            enableMessages();
        } else {
            disableMessages();
        }
    }
    
    
    public static void stdOutPrint(String msg)
    {
        System.out.println(msg);
    }
    
    public static void stdErrPrint(String msg)
    {
        System.err.println(msg);
    }
    
    private static void initLog()
    {
        if (logOut == null) {
            try {
                logOut = new PrintWriter(new FileOutputStream(LOG_FILE));
            }
            catch (FileNotFoundException e) {
                System.err.println("Unable to initialize debug log!");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
    
    public static void logPrint(String msg)
    {
        initLog();
        
        logOut.println(msg);
        logOut.flush();
    }
    
    public static void printMessage(MessageOptions opt, String msg)
    {
        if (opt.stdOut) {
            stdOutPrint(msg);
        }
        
        if (opt.stdErr) {
            stdErrPrint(msg);
        }
        
        if (opt.log) {
            logPrint(msg);
        }
    }
    
    
    public static void warning(Object message)
    {
        String oup = message.toString();
        
        printMessage(warningOptions, oup);
    }
    
    public static void printStackTrace(MessageOptions opt, Exception e)
    {
        if (opt.stdOut) {
            e.printStackTrace(System.out);
        }
        
        if (opt.stdErr) {
            e.printStackTrace(System.err);
        }
        
        if (opt.log) {
            initLog();
            e.printStackTrace(logOut);
        }
        
    }
    
    public static void panic()
    {
        panic(new Exception());
    }
    
    public static void panic(String msg)
    {
        panic(new Exception(msg));
    }
    
    public static void panic(Exception e, String msg)
    {
        msg = "Panic" + ((msg.equals("")) ? ("!\n") : (": " + msg));
        printMessage(panicOptions, msg);
        panic(e);
    }
    
    public static void print(String msg)
    {
        printMessage(printOptions, msg);
    }
    
    public static void panic(Exception e)
    {
        String msg = e.getMessage();
        
        if (msg == null) {
            msg = "";
        }
        msg = "Panic" + ((msg.equals("")) ? ("!\n") : (": " + msg));
        
        print(msg);
        printStackTrace(panicOptions, e);
        
        System.exit(1);
    }
    
    public static void pushTime()
    {
        timerStack.push(System.currentTimeMillis());
    }
    
    public static long getElapsedTime()
    {
        return System.currentTimeMillis() - timerStack.top();
    }
    
    public static long popElapsedTime()
    {
        return System.currentTimeMillis() - timerStack.pop();
    }
}
