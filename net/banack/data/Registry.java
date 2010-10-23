package net.banack.data;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registry
{
	private Map<String, Data> map;
	
	public Registry()
	{
		map = new HashMap<String, Data>();
	}
	
	// Clobbers existing values
	public void put(String key, String value)
	{
		map.put(key, new Data(value));
	}
	
	public void put(String key, Data value)
	{
		map.put(key, value);
	}
	
	public Data getData(String key)
	{
		return map.get(key);
	}
	
	public boolean containsKey(String key)
	{
		return map.containsKey(key);
	}
	
	public String getString(String key, String defaultValue)
	{
		if (map.containsKey(key)) {
			return map.get(key).getString();
		} else {
			return defaultValue;
		}
	}
	
	public int getInt(String key, int defaultValue)
	{
		if (map.containsKey(key)) {
			Data d = getData(key);
			if (d.isInt()) {
				return d.getInt();
			}
		}
		
		return defaultValue;
	}
	
	public boolean getBoolean(String key, boolean defaultValue)
	{
		if (map.containsKey(key)) {
			Data d = getData(key);
			if (d.isBoolean()) {
				return d.getBoolean();
			}
		}
		
		return defaultValue;
	}
	
	// parse a line and insert it into the registry
	//TODO Exception model! Line numbers?
	//TODO handle "strings with spaces"
	private static void parseLine(Registry reg, String line) throws IOException
	{
		line = line.trim();
		if (line.length() == 0) {
			//blank line (or all whitespace)
			return;
		}
		
		if (line.charAt(0) == '#') {
			//comments
			return;
		}
		
		Pattern keyP = Pattern.compile("(\\S+)\\s*=.*");
		Pattern valueP = Pattern.compile("[^=]*=\\s*(\\S+)\\s*");
		
		Matcher keyM = keyP.matcher(line);
		Matcher valueM = valueP.matcher(line);		
		
		if (!keyM.matches()) {
			throw new IOException("Bad format: no key");
		}
		
		if (!valueM.matches()) {
			throw new IOException("Bad format: no value");
		}
		
		String key = keyM.group(1);
		String value = valueM.group(1);
		
		if (key.indexOf('=') != -1) {
			throw new IOException("Bad format: '=' found in key");
		}
		
		reg.put(key, value);
	}
	
	public static Registry parseRegistry(Reader r) throws IOException
	{
		Registry oup = new Registry();
		
		LineNumberReader inp = new LineNumberReader(r);
		
		String next = inp.readLine();
		
		while(next != null) {
			parseLine(oup, next);
			next = inp.readLine();
		}
		
		return oup;
	}
}
