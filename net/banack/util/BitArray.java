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

package net.banack.util;

import java.util.Arrays;

/**
 * @author Michael Banack
 */
public class BitArray
{
	private int[] myBits;
	private int mySize;
	private boolean fillValue;//fills in new bits with fillValue
	
	private static final int ALL_ON = 0xFFFFFFFF;
	private static final int UNIT_SIZE = 32;
	
	/**
	 * Create a new BitArray of the given size, initialized to false.
	 */
	public BitArray(int size)
	{
		this(size,false);
	}
	
	/**
	 * Create a new BitArray of the given size, initialized to initial
	 * @param size The initial size of the array
	 * @param initial The default fill value for the array
	 */
	public BitArray(int size, boolean initial)
	{
		mySize = size;
		int arrSize = size/UNIT_SIZE+((size%UNIT_SIZE>0)?1:0);
		myBits = new int[arrSize];
		int bFill = 0;
		if(initial)
			bFill = ALL_ON;
		
		Arrays.fill(myBits,bFill);
		
		fillValue=initial;
	}
	
	/**
	 * @return The value new bits will get initialized to.
	 */
	public boolean getFillValue()
	{
		return fillValue;
	}
	
	/**
	 * Sets the value new bits will get initialized to.
	 */
	public void setFillValue(boolean b)
	{
		fillValue=b;
	}
	
	/**
	 * Returns the value of the bit at i.
	 */
	public boolean get(int i)
	{	
		if(i < 0 || i > mySize)
			throw new IndexOutOfBoundsException("requested index="+i+", max index="+(mySize-1));
		return (myBits[i/UNIT_SIZE] & (1<<(i%UNIT_SIZE))) != 0;	
	}
	
	/**
	 * Sets the specified bit to true.
	 * @param i The bit index to access.
	 */
	public void set(int i)
	{
		if(i < 0 || i > mySize)
			throw new IndexOutOfBoundsException("requested index="+i+", max index="+(mySize-1));
		myBits[i/UNIT_SIZE] |= (1<<(i%UNIT_SIZE));
	}
	
	/**
	 * Sets the bits from first to last inclusive.
	 */
	public void setRange(int first, int last)
	{
		if(first<0 || first > mySize || last < 0 || last >mySize || first > last)
			throw new IndexOutOfBoundsException("Invalid range ["+first+", "+last+"], max index="+(mySize-1));
		
		//UNIT_SIZE*2 guarantees we have a whole byte
		// you could make it work with just UNIT_SIZE
		// but it's probably faster to fill in small cases in one loop anyway...?
		if(last-first<UNIT_SIZE*2)
		{
			for(int x=first;x<=last;x++)
				myBits[x/UNIT_SIZE] |= (1<<(x%UNIT_SIZE));
			return;
		}

		// we've got at least a whole byte
		
		int minByte,maxByte;
		minByte=first/UNIT_SIZE+((first%UNIT_SIZE==0)?0:1);
		maxByte=(last)/UNIT_SIZE-((last%UNIT_SIZE==(UNIT_SIZE-1))?0:1);
		
		for(int x=first;x<minByte*UNIT_SIZE;x++)
			myBits[x/UNIT_SIZE] |= (1<<(x%UNIT_SIZE));
		
		for(int x=maxByte*UNIT_SIZE+1;x<=last;x++)
			myBits[x/UNIT_SIZE] |= (1<<(x%UNIT_SIZE));
		
		//fills myBits[minByte]..myBites[maxByte] with ALL_ON
		Arrays.fill(myBits,minByte,maxByte+1,ALL_ON);		
	}
	
	/**
	 * Flips the specified bit.
	 */
	public void flip(int i)
	{
		if(i < 0 || i > mySize)
			throw new IndexOutOfBoundsException("requested index="+i+", max index="+(mySize-1));
		
		int cell= i/UNIT_SIZE;
		int mask = (1<<i%UNIT_SIZE);
		
		if((myBits[cell] & mask) == 0)	
			myBits[cell] |= mask;
		else
			myBits[cell] &= ~mask;
	}
	
	
	
	/**
	 * Resets the specified bit to false.
	 * @param i The bit index to access.
	 */
	public void reset(int i)
	{
		if(i < 0 || i > mySize)
			throw new IndexOutOfBoundsException("requested index="+i+", max index="+(mySize-1));
		myBits[i/UNIT_SIZE] &= ~(1<<(i%UNIT_SIZE));
	}
	
	/**
	 * Resets the bits from first to last inclusive.
	 */
	public void resetRange(int first, int last)
	{
		if(first<0 || first > mySize || last < 0 || last >mySize || first > last)
			throw new IndexOutOfBoundsException("Invalid range ["+first+", "+last+"], max index="+(mySize-1));
		
		//UNIT_SIZE*2 guarantees we have a whole byte
		// you could make it work with just UNIT_SIZE
		// but it's probably faster to fill in small cases in one loop anyway...?
		if(last-first<UNIT_SIZE*2)
		{
			for(int x=first;x<=last;x++)
				myBits[x/UNIT_SIZE] &= ~(1<<(x%UNIT_SIZE));
			return;
		}
		// we've got at least a whole byte
		
		int minByte,maxByte;
		minByte=first/UNIT_SIZE+((first%UNIT_SIZE==0)?0:1);
		maxByte=(last)/UNIT_SIZE-((last%UNIT_SIZE==UNIT_SIZE-1)?0:1);
		
		for(int x=first;x<minByte*UNIT_SIZE;x++)
			myBits[x/UNIT_SIZE] &= ~(1<<(x%UNIT_SIZE));
		
		for(int x=maxByte*UNIT_SIZE+1;x<=last;x++)
			myBits[x/UNIT_SIZE] &= ~(1<<(x%UNIT_SIZE));
		
		//fills myBits[minByte]..myBites[maxByte] with 0
		Arrays.fill(myBits,minByte,maxByte+1,0);
	}
	
	/**
	 * Sets all of the bits to true.
	 */
	public void setAll()
	{
		setRange(0,mySize-1);
	}
	
	/**
	 * Resets all of the bits to false.
	 */
	public void resetAll()
	{
		resetRange(0,mySize-1);
	}
		
	/**
	 * Stores the boolean value b in index i.
	 * @param i The index to load
	 * @param b The value to be loaded
	 */
	public void put(int i, boolean b)
	{
		if(b)
			set(i);
		else
			reset(i);
	}
	
	/**
	 * Returns the current size of the BitArray.
	 */
	public int size()
	{
		return mySize;
	}
	
	/**
	 * Resizes the BitArray to a new size of length.
	 * New indices are initialized to the fill value.
	 */
	//internal array only grows, never shrinks unless trim is called
	public void resize(int length)
	{
		if(length<=0)
			throw new IllegalArgumentException("Nonpositive size requested!");
		int oldSize=mySize;
		mySize=length;
		
		if(mySize <= oldSize)
			return;
		
		int oldValidCellCount = oldSize/UNIT_SIZE+((oldSize%UNIT_SIZE>0)?1:0);
		int newValidCellCount = length/UNIT_SIZE+((length%UNIT_SIZE>0)?1:0);
		
				
		if(length>myBits.length*UNIT_SIZE)
		{
			int[] temp = myBits;
			myBits= new int[newValidCellCount];
			System.arraycopy(temp,0,myBits,0,oldValidCellCount);
		}
		
		//fill any new values
		int bFill = 0;
		if(fillValue)
			bFill = ALL_ON;
		
		//myBits[0] through myBits[oldValidCellCount-1] contain all our old data
		//this fills from oldValidCellCount..newValidCellCount-1
		Arrays.fill(myBits,oldValidCellCount,newValidCellCount,bFill);
		
		//now fill in any stray bits on the end of myBits[oldValidCellCount]
		for(int x=oldSize;x<oldSize+(UNIT_SIZE-oldSize%UNIT_SIZE);x++)
		{
			if(fillValue)
				myBits[x/UNIT_SIZE] |= (1<<(x%UNIT_SIZE));
			else
				myBits[x/UNIT_SIZE] &= ~(1<<(x%UNIT_SIZE));
		}
	}
	
	/**
	 * Shrinks the internal array to the minimum possible size.
	 */
	public void trim()
	{
		if(mySize<= (myBits.length-1)*UNIT_SIZE)
		{
			int arrSize=mySize/UNIT_SIZE+((mySize%UNIT_SIZE>0)?1:0);
			if(arrSize < 1)
				arrSize = 1;
			int[] temp = myBits;
			myBits= new int[arrSize];
			
			System.arraycopy(temp,0,myBits,0,arrSize);
		}
	}
	
	public static void debugTest()
	{
		boolean fail = false;
		BitArray ba = new BitArray(33);
		
		System.out.println("BitArray tests...");
		for(int x=0;x<ba.size();x++)
		{
			if(ba.get(x) != false)
			{
				fail = true;
				System.out.println("Failed initialization test at x="+x);
				break;
			}
		}
		
		int[] sizeCheck = {1,2,3,4,5,6,7,8,9,10,30,31,32,33,34,35,36,10,100,33,2,99,97,3,101,36,45,96,13,17,19,23,29,31,37,43,47,60,61,62,63,200,317};
		
		//System.out.println("\tRuning resize tests...");
		for(int x=0;x<sizeCheck.length;x++)
		{
			ba.resize(sizeCheck[x]);
			if(ba.size() != sizeCheck[x])
			{
				fail=true;
				System.out.println("Failed resize test"+x+" size="+ba.size()+", requested="+sizeCheck[x]);
			}
		}
		
		for(int x=0;x<sizeCheck.length;x++)
		{
			ba.trim();
			ba.resize(sizeCheck[x]);
			if(ba.size() != sizeCheck[x])
			{
				fail=true;
				System.out.println("Failed trim+resize test"+x+" size="+ba.size()+", requested="+sizeCheck[x]);
			}
			ba.trim();
		}
		
		ba.trim();
		
		for(int z=0;z<sizeCheck.length;z++)
		{
			//System.out.println("\tRunning test batch "+(z+1)+" of "+sizeCheck.length+" size="+sizeCheck[z]);
			ba.resize(sizeCheck[z]);
			ba.trim();
			
			ba.resetAll();
		
			for(int x=0;x<ba.size();x++)
			{
				ba.set(x);
				if(ba.get(x) != true)
				{
					fail = true;
					System.out.println("Failed set test at z="+z+" x="+x);
					break;
				}
			}
			
			ba.setAll();
			for(int x=0;x<ba.size();x++)
			{
				ba.reset(x);
				if(ba.get(x) != false)
				{
					fail = true;
					System.out.println("Failed reset test at z="+z+" x="+x);
					break;
				}
			}
		
			
			ba.setAll();
			for(int x=0;x<ba.size();x++)
			{
				if(ba.get(x) != true)
				{
					fail = true;
					System.out.println("Failed setAll test at z="+z+" x="+x);
					break;
				}
			}
			ba.resetAll();
			for(int x=0;x<ba.size();x++)
			{
				if(ba.get(x) != false)
				{
					fail = true;
					System.out.println("Failed resetAll test at z="+z+" x="+x);
					break;
				}
			}
			
			for(int y=0;y<ba.size();y++)
			{
				ba.resetAll();
				ba.set(y);
				for(int x=0;x<ba.size();x++)
				{
					if(x != y && ba.get(x) != false)
					{
						fail = true;
						System.out.println("Failed selective set test at z="+z+" y="+y+" x="+x);
						break;
					}
					else if(x == y && ba.get(x) != true)
					{
						fail = true;
						System.out.println("Failed selective set test at set bit z="+z+" y="+y+" x="+x);
						break;
					}
				}
			}

			ba.resize(sizeCheck[z]);
			ba.trim();
	
			for(int y=0;y<ba.size();y++)
			{
				ba.setAll();
				ba.reset(y);
				for(int x=0;x<ba.size();x++)
				{
					if(x != y && ba.get(x) != true)
					{
						fail = true;
						System.out.println("Failed selective reset test at y="+y+" x="+x);
						break;
					}
					else if(x == y && ba.get(x) != false)
					{
						fail = true;
						System.out.println("Failed selective reset test at reset bit y="+x);
						break;
					}
				}
			}
		
			BitArray bb = new BitArray(ba.size());
			
			for(int y=0;y<ba.size();y++)
			{
				for(int q=y;q<ba.size();q++)
				{
					ba.resetAll();
					for(int x=0;x<ba.size();x++)
					{
						if(ba.get(x)!=false)
						{
							fail=true;
							System.out.println("Failed resetAll for setRange pretest at x="+x+"z="+z);
						}
					}
					
					try{
						ba.setRange(y,q);
					}
					catch(RuntimeException e)
					{
						System.out.println("Failed at ba.setRange("+y+", "+q+"); y="+y+" z="+z+" q="+q);
						throw e;
					}
					
					bb.setAll();
					for(int x=0;x<bb.size();x++)
					{
						if(bb.get(x)!=true)
						{
							fail=true;
							System.out.println("Failed setAll for resetRange pretest at x="+x+"z="+z);
						}
					}
					
					bb.resetRange(y,q);
					
					for(int x=0;x<ba.size();x++)
					{
						if(y<=x && x<=q && ba.get(x)!= true)
						{
							fail=true;
							System.out.println(ba);
							System.out.println("Failed setRange test at y="+y+" x="+x+" z="+z+"q= "+q);
						}
						if((y>x || x>q) && ba.get(x)!= false)
						{
							fail=true;
							System.out.println(ba);
							System.out.println("Failed setRange test at y="+y+" x="+x+" z="+z+"q= "+q);
						}
						if(y<=x && x<=q && bb.get(x) != false)
						{
							fail = true;
							System.out.println("Failed resetRange test at y="+y+" x="+x+" z="+z+"q= "+q);
						}
						if((y>x || x>q) && bb.get(x)!= true)
						{
							fail=true;
							System.out.println("Failed resetRange test at y="+y+" x="+x+" z="+z+"q= "+q);
						}
					}
				}
			}
		}
			
		if(!fail)
			System.out.println("BitArray tests successful!");
		else
			System.out.println("BitArray tests failed.");
	}
	
	public String toString()
	{
		StringBuffer oup = new StringBuffer(size());
		for(int x=0;x<size();x++)
		{
			if(get(x))
				oup.append("1");
			else
				oup.append("0");
		}
		return oup.toString();
	}
}
