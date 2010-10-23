package net.banack.data;

import java.util.ArrayList;
import java.util.Iterator;

public class Table
{
	private String name;
	private ArrayList<Record> records;
	//TODO format check?
	
	public Table(String tableName)
	{
		this.name = tableName;
		records = new ArrayList<Record>();
	}
	
	public void append(Record r) {
		records.add(r);
	}
	
	public Iterator<Record> iterator() {
		return records.iterator();
	}
	
	public String getName() {
		return name;
	}
	
	public Record get(int i) {
		return records.get(i);
	}	
}
