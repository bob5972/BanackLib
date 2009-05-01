package net.banack.util;

//for oracle testing
import java.util.HashMap;

public class IntMap
{
	private static final int DEFAULT_SIZE = 7;
	private static final double DEFAULT_LOAD = 0.75;
	
	private int[] myKeys;
	private int[] myValues;
	private BitArray myDeletedFlags;//myFlags.get(x) is true if myKeys[x] is active
	private BitArray myFullFlags;//myFlags.get(x) is true if myKeys[x] is "full" for hashing purposes
	private int mySize;
	private int myFreeSpace;
	private int tableSize;
	private double myLoad;
	private boolean useElementDefaults;
	
	private static final int SEARCH_INCR=3;
	
	//constructs an empty intmap
	public IntMap()
	{
		this(DEFAULT_SIZE,DEFAULT_LOAD);
	}
	
	public IntMap(int size)
	{
		this(size,DEFAULT_LOAD);
	}
	
	//constructs an intmap with at least size default space
	public IntMap(int size,double load)
	{
		mySize=0;
		tableSize=size+(SEARCH_INCR+1)-(size%SEARCH_INCR);//searches are done by SEARCH_INCR
		myFreeSpace=tableSize;
		myKeys = new int[tableSize];
		myValues = new int[tableSize];
		myDeletedFlags = new BitArray(tableSize);
		myFullFlags = new BitArray(tableSize);
		myLoad = load;
		useElementDefaults = false;
		
		if(!(myLoad > 0 && myLoad <= 1))
			throw new IllegalArgumentException("Invalid load="+load+"; load must be between 0 and 1");
	}
	
	public void setUseElementDefaults(boolean b)
	{
		useElementDefaults = b;
	}
	
	//Clears this hashtable so that it contains no keys. 
	public void makeEmpty()
	{
		mySize=0;
		myFreeSpace=tableSize;
		myDeletedFlags.resetAll();
		myFullFlags.resetAll();
	}
	
	//Clears this hashtable so that it contains no keys. 
	public final void clear()
	{
		makeEmpty();
	}

	//Tests if the specified object is a key in this hashtable. 
	public boolean containsKey(int key)
	{
		return findKey(key) != -1;
	}
	
	//positive value between 0 and tableSize-1
	private int hash(int key)
	{
		int hash = key%tableSize;
		if(hash < 0)
			hash = -hash;
		return hash;
	}
	
	//returns the index of a valid key in the table or -1
	private int findKey(int key)
	{
		int hash = hash(key);
		
		if(!myFullFlags.get(hash))
			return -1;
		else if(myKeys[hash] == key && !myDeletedFlags.get(hash))
			return hash;
			
		int x=hash;
		x+=SEARCH_INCR;
		x%=tableSize;
		while(x!= hash)
		{
			if(!myFullFlags.get(x))
				return -1;
			else if(myKeys[x] == key && !myDeletedFlags.get(x))
				return x;
			x+=SEARCH_INCR;
			x%=tableSize;
		}
		
		//we've been through the whole table
		return -1;
	}
	
	//returns the index of where the key should go if it were to be inserted next
	private int getIndexOfKey(int key)
	{
		int hash = hash(key);
		
		if(!myFullFlags.get(hash))
			return hash;
		else if(myKeys[hash] == key)
			return hash;
			
		int x=hash;
		x+=SEARCH_INCR;
		x%=tableSize;
		while(x!= hash)
		{
			if(!myFullFlags.get(x))
				return x;
			else if(myKeys[x] == key)
				return x;
			x+=SEARCH_INCR;
			x%=tableSize;
		}
		
		//we've been through the whole table without finding a free spot
		return -1;
	}
		

	//Returns the value to which the specified key is mapped in this hashtable. 
	public int get(int key)
	{
		int i = findKey(key);
		if(i == -1)
		{
			if(useElementDefaults)
				return 0;
			throw new java.util.NoSuchElementException("Unknown key in IntMap.");
		}
		
		return myValues[i];
	}
	
	//increment given key, if the key is not present, assume  a value of 0
	//return the new value
	public int increment(int key)
	{
		return increment(key,1);
	}
	
	//decrement given key, if the key is not present, assume  a value of 0
	//return the new value
	public int decrement(int key)
	{
		return increment(key,-1);
	}
	
	//increment given key by the given amount, if the key is not present assume a value of 0
	//return the new value
	public int increment(int key, int amount)
	{
		int i = findKey(key);
		if(i==-1)
		{
			put(key,amount);
			return amount;
		}
		
		myValues[i] += amount;
		return myValues[i];
		
	}

	//Tests if this hashtable maps no keys to values.
	public boolean isEmpty()
	{
		return mySize ==0;
	}

	//Maps the specified key to the specified value in this hashtable.
	//returns the old value (if any)
	public void put(int key, int value) 
	{
		//the mySize>=tableSize is just in case of weird rounding on the myLoad check
		if( ((double)mySize)/tableSize >= myLoad || mySize >=tableSize)
			rehash();
		//we are guaranteed to have an empty spot
		int ind = getIndexOfKey(key);
		
		//are we replacing a value?
		if(myFullFlags.get(ind) && !myDeletedFlags.get(ind))
		{
			if(myKeys[ind] != key)
				throw new IllegalStateException("The IntMap is broken!");
			myValues[ind] = value;
			return;
		}
		
		if(myFullFlags.get(ind) && myDeletedFlags.get(ind))
		{
			myFreeSpace--;
			mySize++;
		}
		
		mySize++;
		myFreeSpace--;		
		myFullFlags.set(ind);
		myDeletedFlags.reset(ind);
		myKeys[ind] = key;
		myValues[ind] = value;
		return;
	}
		
		
	
	//Rehashes the contents of the hashtable into a hashtable with a larger capacity. 
	private  void rehash()
	{
		int [] oldKeys = myKeys;
		int [] oldValues = myValues;
		
		BitArray oldFullFlags = myFullFlags;
		BitArray oldDeletedFlags = myDeletedFlags;
		
		tableSize*=2;
		tableSize += (SEARCH_INCR+1)-(tableSize%SEARCH_INCR);
		myFullFlags = new BitArray(tableSize);
		myDeletedFlags=new BitArray(tableSize);
		myKeys = new int[tableSize];
		myValues = new int[tableSize];		
		makeEmpty();		
		
		for(int x=0;x<oldKeys.length;x++)
		{
			if(oldFullFlags.get(x) && !oldDeletedFlags.get(x))
				put(oldKeys[x],oldValues[x]);
		}
	}
		
		
	
	//Removes the key (and its corresponding value) from this hashtable.
	//returns true if the key was found and removed
	//returns false if the key wasn't found
	public boolean remove(int key)
	{
		int ind = getIndexOfKey(key);
		
		if(ind == -1)
			return false;
			
		if(myFullFlags.get(ind) && !myDeletedFlags.get(ind))
		{
			if(myKeys[ind] != key)
				throw new IllegalStateException("The IntMap is broken!");
			myDeletedFlags.set(ind);
			mySize--;
			myFreeSpace++;			
			return true;
		}
		
		return false;
	}
		

	//Returns the number of keys in this hash table.
	public int size()
	{
		return mySize;
	}
	
	public static void debugTest()
	{
		IntMap map = new IntMap();
		HashMap<Integer,Integer> oracle = new HashMap<Integer,Integer>();
		java.util.Random r = new java.util.Random(101);
		boolean fail = false;
		
		System.out.println("IntMap tests...");
		
		for(int x=0;x<100;x++)
		{
			map.put(x,x+1);
			if(!map.containsKey(x))
			{
				fail=true;
				System.out.println("Failed contains test at x="+x);
			}
		}
		
		for(int x=0;x<100;x++)
		{
			if(map.get(x) != x+1)
			{
				fail = true;
				System.out.println("Failed simple put test at x="+x);
			}
		}
		
		for(int x=0;x<100;x++)
		{
			map.remove(x);
			if(map.containsKey(x))
			{
				fail=true;
				System.out.println("Failed remove test at x="+x);
			}
		}
		
		for(int x=0;x<100;x++)
		{
			map.put(x,x+1);
			if(!map.containsKey(x))
			{
				fail=true;
				System.out.println("Failed contains test2 at x="+x);
			}
		}
		
		map.makeEmpty();
		if(map.size() != 0)
		{
			fail = true;
			System.out.println("Failed makeEmpty size check!");
		}
		
		for(int x=0;x<100;x++)
		{
			if(map.containsKey(x))
			{
				fail = true;
				System.out.println("Failed make empty test at x="+x);
			}
		}
		
		map = new IntMap(2);
		
		for(int x=0;x<200;x++)
		{
			int n = r.nextInt(25);
			if(map.containsKey(n) != oracle.containsKey(new Integer(n)))
			{
				fail = true;
				System.out.println("Failed oracle preTest at x="+x);
			}
			
			if(r.nextBoolean())
			{
				map.put(x,x+1);
				oracle.put(new Integer(x),new Integer(x+1));
			}
			else
			{
				map.remove(x);
				oracle.remove(new Integer(x));
			}
			
			if(map.containsKey(n) != oracle.containsKey(new Integer(n)))
			{
				fail = true;
				System.out.println("Failed oracle postTest at x="+x);
			}
		}
		
		if(fail)
			System.out.println("IntMap tests failed!");
		else
			System.out.println("IntMap tests succeeded!");	
	}
}
	