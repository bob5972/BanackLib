package net.banack.data;

import java.util.ArrayList;
import java.util.Iterator;

public class Record
{
	//TODO: use a better data structure
	private ArrayList<Data> fields;
	
	public Record()
	{
		fields = new ArrayList<Data>();
	}
	
	public Record(Data d[])
	{
		this();
		
		for(int x=0;x<d.length;x ++) {
			fields.add(d[x]);
		}
	}
	
	public Record(Iterable<Data> list)
	{
		this();
		
		Iterator<Data> i = list.iterator();
		
		while(i.hasNext()) {
			fields.add(i.next());
		}
	}
	
	public int size() {
		return fields.size();
	}
	
	public void append(Data d) {
		fields.add(d);
	}
	
	public Data get(int x) {
		return fields.get(x);
	}
	
	public Iterator<Data> iterator() {
		return fields.iterator();
	}
	
	public String toString() {
		StringBuffer oup = new StringBuffer();
		Iterator<Data> i = fields.iterator();
		
		while(i.hasNext()) {
			Data cur = i.next();
			String s = encode(cur.toString());
			
			oup.append(s);
			oup.append(", ");
		}
		
		return oup.toString();
	}
	
	//TODO: write a real parser
	public static String encode(String s) {
		StringBuffer oup = new StringBuffer(s.length()+10);
		
		oup.append("\"");		
		
		for(int x=0;x<s.length();x++) {
			char c = s.charAt(x);
			switch(c) {
				case '"':
					throw new RuntimeException("Unable to encode character");
				default:
					oup.append(c);
					break;
			}
		}
		
		oup.append("\"");		
		return oup.toString();
	}
	
	public static Record parseRecord(String s) {
		Record oup = new Record();
		s.trim();
		
		StringBuffer cur = new StringBuffer();
		
		if (s.length() == 0) {
			return oup;
		}
		
		int start = s.indexOf('"');
		while(start != -1) {
			s = s.substring(start);
			s.trim();//trims are cheap
			
			int end = s.indexOf('"');
			
			if (end == -1) {
				throw new RuntimeException("Bad format: unterminated quote");
			}
			
			cur.append(s.substring(1, end));			
			oup.append(new Data(cur.toString()));
			s = s.substring(end+1);
			s.trim();
			
			start = s.indexOf('"');
		}
		
		return oup;		
	}
}
