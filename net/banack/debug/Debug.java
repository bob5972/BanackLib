package net.banack.debug;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Debug
{
	private static boolean DEBUG=false;
	private static boolean CRASH_ON_ERROR=false;
	
	public static boolean STD_OUT_MESSAGES=false;
	public static boolean STD_OUT_MESSAGES_INFO=false;
	public static boolean STD_OUT_MESSAGES_WARN=false;
	public static boolean STD_OUT_MESSAGES_ERROR=false;
	
	public static boolean STD_ERR_MESSAGES=false;
	public static boolean STD_ERR_MESSAGES_INFO=false;
	public static boolean STD_ERR_MESSAGES_WARN=false;
	public static boolean STD_ERR_MESSAGES_ERROR=true;
	
	private static String LOG_FILE = "debug.log";

	public static boolean LOG_MESSAGES=true;
	public static boolean LOG_MESSAGES_INFO=true;
	public static boolean LOG_MESSAGES_WARN=true;
	public static boolean LOG_MESSAGES_ERROR=true;
	
	private static PrintWriter logOut;
	
	
	
	public static void enableDebug()
	{
		DEBUG=true;
	}
	
	public static void disableDebug()
	{
		DEBUG=false;
	}
	
	public static boolean isDebug()
	{
		return DEBUG;
	}
	
	public static boolean crashOnError()
	{
		return CRASH_ON_ERROR;
	}
	
	public static void setCrashOnError(boolean b)
	{
		CRASH_ON_ERROR=b;
	}
	
	public static void stdOutPrint(String msg)
	{
		if(!DEBUG)
			return;
		if(STD_OUT_MESSAGES)
		{
			System.out.println(msg);
		}
	}
	
	public static void stdErrPrint(String msg)
	{
		if(!DEBUG)
			return;
		if(STD_ERR_MESSAGES)
			System.err.println(msg);
	}
	
	public static void logPrint(String msg)
	{
		if(!DEBUG)
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
		if(!DEBUG)
			return;
		String oup = message.toString();
		if(STD_OUT_MESSAGES_INFO)
			stdOutPrint(oup);
		if(STD_ERR_MESSAGES_INFO)
			stdErrPrint(oup);
		if(LOG_MESSAGES_INFO)
			logPrint(oup);
		
		printStackTrace();
	}
	
	public static void warn(Object message)
	{
		if(!DEBUG)
			return;
		String oup = message.toString();
		if(STD_OUT_MESSAGES_WARN)
			stdOutPrint(oup);
		if(STD_ERR_MESSAGES_WARN)
			stdErrPrint(oup);
		if(LOG_MESSAGES_WARN)
			logPrint(oup);
		
		printStackTrace();
	}
	
	public static void error(Object message)
	{
		if(!DEBUG)
			return;
		String oup = message.toString();
		
		if(STD_OUT_MESSAGES_ERROR)
			stdOutPrint(oup);
		if(STD_ERR_MESSAGES_ERROR)
			stdErrPrint(oup);
		if(LOG_MESSAGES_ERROR)
			logPrint(oup);
		
		printStackTrace();
		
		if(CRASH_ON_ERROR)
		{
			crash();
		}
	}
	
	private static void printStackTrace()
	{
		Exception e = new Exception();
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
	
	public static void crash(Exception e)
	{
		e.printStackTrace();
		String msg = e.getMessage();
		
		if(msg == null)
			msg = "";
		msg = "Fatal Error"+((msg.equals(""))?("!"):(": "+msg));
		
		printStackTrace();
		
		stdOutPrint(msg);
		stdErrPrint(msg);
		logPrint(msg);
		
		if(!STD_OUT_MESSAGES && !STD_ERR_MESSAGES && !LOG_MESSAGES)
		{
			e.printStackTrace();
			System.err.println(msg);
		}
		
		System.exit(1);
	}
}
