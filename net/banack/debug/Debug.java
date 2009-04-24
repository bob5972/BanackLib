package net.banack.debug;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import net.banack.util.Stack;

public class Debug
{
	private static boolean DEBUG=false;
	private static boolean MESSAGES=false;
	public static boolean CRASH_ON_ERROR=false;
	
	public static boolean STACK_TRACE_ON_VERBOSE=false;//LOTS OF OUTPUT!
	public static boolean STACK_TRACE_ON_INFO=false;
	public static boolean STACK_TRACE_ON_WARN=true;
	public static boolean STACK_TRACE_ON_ERROR=true;
	
	
	public static boolean STD_OUT_MESSAGES=false;
	public static boolean STD_OUT_MESSAGES_VERBOSE=false;
	public static boolean STD_OUT_MESSAGES_INFO=false;
	public static boolean STD_OUT_MESSAGES_WARN=false;
	public static boolean STD_OUT_MESSAGES_ERROR=false;
	
	public static boolean STD_ERR_MESSAGES=false;
	public static boolean STD_ERR_MESSAGES_VERBOSE=false;
	public static boolean STD_ERR_MESSAGES_INFO=false;
	public static boolean STD_ERR_MESSAGES_WARN=true;
	public static boolean STD_ERR_MESSAGES_ERROR=true;
	
	private static String LOG_FILE = "debug.log";

	
	public static boolean LOG_MESSAGES=false;
	public static boolean LOG_MESSAGES_VERBOSE=false;
	public static boolean LOG_MESSAGES_INFO=true;
	public static boolean LOG_MESSAGES_WARN=true;
	public static boolean LOG_MESSAGES_ERROR=true;
	
	private static PrintWriter logOut;
	
	private static Stack<Long>  timerStack = new Stack<Long>();
	
	
	
	public static void enableDebug()
	{
		DEBUG=true;
	}
	
	public static void setDebug(boolean b)
	{
		DEBUG = b;
	}
	
	public static void disableDebug()
	{
		DEBUG=false;
	}
	
	public static void enableMessages()
	{
		MESSAGES=true;
	}
	
	public static void setMessages(boolean b)
	{
		MESSAGES=b;
	}
	
	public static void disableMessages()
	{
		MESSAGES=false;
	}
	
	public static boolean isDebug()
	{
		return DEBUG;
	}
	
	public static boolean showMessages()
	{
		return MESSAGES;
	}
	
	public static boolean showWarnings()
	{
		return LOG_MESSAGES_WARN || STD_ERR_MESSAGES_WARN || STD_OUT_MESSAGES_WARN;
	}
	
	public static boolean showErrors()
	{
		return LOG_MESSAGES_ERROR || STD_ERR_MESSAGES_ERROR || STD_OUT_MESSAGES_ERROR;
	}
	
	public static boolean showInfo()
	{
		return LOG_MESSAGES_INFO || STD_ERR_MESSAGES_INFO || STD_OUT_MESSAGES_INFO;
	}
	
	
	public static void stdOutPrint(String msg)
	{
		if(!MESSAGES)
			return;
		if(STD_OUT_MESSAGES)
		{
			System.out.println(msg);
		}
	}
	
	public static void stdErrPrint(String msg)
	{
		if(!MESSAGES)
			return;
		if(STD_ERR_MESSAGES)
			System.err.println(msg);
	}
	
	public static void logPrint(String msg)
	{
		if(!MESSAGES)
			return;
		if(LOG_MESSAGES)
		{
			if(logOut==null)
			{
				try {
					logOut = new PrintWriter(new FileOutputStream(LOG_FILE));
				}
				catch(FileNotFoundException e)
				{
					System.err.println("Unable to initialize debug log!");
					e.printStackTrace();
					System.exit(1);
				}
			}
			
			logOut.println(msg);
		}
	}
	
	public static void info(Object message)
	{
		if(!MESSAGES)
			return;
		String oup = message.toString();
		if(STD_OUT_MESSAGES_INFO)
			stdOutPrint(oup);
		if(STD_ERR_MESSAGES_INFO)
			stdErrPrint(oup);
		if(LOG_MESSAGES_INFO)
			logPrint(oup);
		
		if(STACK_TRACE_ON_INFO)
			printStackTrace();
	}
	
	public static void verbose(Object message)
	{
		if(!MESSAGES)
			return;
		String oup = message.toString();
		if(STD_OUT_MESSAGES_VERBOSE)
			stdOutPrint(oup);
		if(STD_ERR_MESSAGES_VERBOSE)
			stdErrPrint(oup);
		if(LOG_MESSAGES_VERBOSE)
			logPrint(oup);
		
		if(STACK_TRACE_ON_VERBOSE)
			printStackTrace();
	}
	
	public static void warn(Object message)
	{
		if(!MESSAGES)
			return;
		String oup = message.toString();
		if(STD_OUT_MESSAGES_WARN)
			stdOutPrint(oup);
		if(STD_ERR_MESSAGES_WARN)
			stdErrPrint(oup);
		if(LOG_MESSAGES_WARN)
			logPrint(oup);
		
		if(STACK_TRACE_ON_WARN)
			printStackTrace();
	}
	
	public static void error(Object message)
	{
		if(!MESSAGES)
			return;
		String oup = message.toString();
		
		if(STD_OUT_MESSAGES_ERROR)
			stdOutPrint(oup);
		if(STD_ERR_MESSAGES_ERROR)
			stdErrPrint(oup);
		if(LOG_MESSAGES_ERROR)
			logPrint(oup);
		
		if(STACK_TRACE_ON_ERROR)
			printStackTrace();
		
		if(CRASH_ON_ERROR)
		{
			crash();
		}
	}
	
	public static void printStackTrace()
	{
		Exception e = new Exception();
		printStackTrace(e);
	}
	
	public static void printStackTrace(Exception e)
	{
		if(STD_OUT_MESSAGES)
			e.printStackTrace(System.out);
		if(STD_ERR_MESSAGES)
			e.printStackTrace(System.err);
		if(LOG_MESSAGES)
			e.printStackTrace(logOut);
		
	}
	
	public static void crash()
	{
		crash(new Exception());
	}
	
	public static void crash(String msg)
	{
		crash(new Exception(msg));
	}
	
	public static void crash(Exception e, String msg)
	{
		print(msg);
		crash(e);
	}
	
	public static void print(String msg)
	{
		stdOutPrint(msg);
		stdErrPrint(msg);
		logPrint(msg);
	}
	
	public static void crash(Exception e)
	{
		print("Crashing!");
		String msg = e.getMessage();
		
		if(msg == null)
			msg = "";
		msg = "Fatal Error"+((msg.equals(""))?("!"):(": "+msg));
		
		print(msg);
		printStackTrace(e);
		
		if(!STD_OUT_MESSAGES && !STD_ERR_MESSAGES && !LOG_MESSAGES)
		{
			System.err.println(msg);
			e.printStackTrace();			
		}
		
		System.exit(1);
	}
	
	public static void pushTime()
	{
		timerStack.push(System.currentTimeMillis());
	}
	
	public static long getElapsedTime()
	{
		return System.currentTimeMillis()-timerStack.top();
	}
	
	public static long popElapsedTime()
	{
		return System.currentTimeMillis()-timerStack.pop();
	}
}
