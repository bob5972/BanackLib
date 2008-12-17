package net.banack.util;

import java.util.Iterator;
import java.util.Random;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Collection;
import java.util.AbstractCollection;
import java.util.Comparator;
import java.util.SortedSet;

/**
 * SkipList implementation of a sorted Collection.
 * Not multi-thread safe.
 * 
 * @author Michael Banack 
 * @version 1.1
 */
public class SkipList extends AbstractCollection implements SortedCollection
{
    private static class SkipNode extends MBNode
    {
        //instance variables
    	public SkipNode above,below,left,right;
    	public int skipped;//offset of index from the left neighbor, >=1
        
        //Constructs an empty node
        public SkipNode()
        {
        	super();
            above=below=left=right=null;
            skipped=1;//you can never skip 0 (except for negative infinity)
        }
        
        //Constructs a node with the given entry
        public SkipNode(Object v)
        {
            super(v);
            skipped=1;//you can never skip 0 (except for negative infinity)
            above=below=left=right=null;
        }
        
        //String representation of SkipNode (for debugging purposes)
        public String toString()
        {
            String oup = super.toString();
            oup+="|";
            if(above != null)
                oup+="a";
            if(below != null)
                oup +="b";
            if(left != null)
                oup += "l";
            if(right!= null)
                oup+="r";
            //Doesn't work if class is static
//            if(isInfinity(this.value()))
//                if(this.value()==POSITIVE_INFINITY)
//                    oup+="(+INFINITY)";
//                else
//                    oup+="(-INFINITY)";
//            else
                oup+=this.value();
            return oup;
        }
        
    }
    
    //static variables
    //These aren't actually used as values, just as distinct reference addresses by compare
    static private final Object NEGATIVE_INFINITY = new Object();
    static private final Object POSITIVE_INFINITY = new Object();
    //Random number generator
    static private Random myRandom = new Random();
        
    // instance variables
    private SkipNode myTopLeft;
    private SkipNode myTopRight;//makes adding levels easier
    private int mySize;
    private int myLevels;
    
    //used to make sure that iterators are still valid
    //see the iterator class for more details
    private int myModificationCount;
    
    //The comparator to be used for ordering
    private Comparator myComp;
    
    //whether or not the instance allows duplicates (ie items that are .equals() to each other)
    private boolean isASet;
    
    //IF YOU CALL THIS AFTER IT'S CONSTRUCTED I DON'T GUARANTEE WHAT WILL HAPPEN!
    protected void setIsASet(boolean b)
    {
    	isASet=b;
    }
    
    protected boolean getIsASet()
    {
    	return isASet;
    }


    //Constructs an Empty SkipList
    public SkipList()
    {
        mySize=0;
        myModificationCount=0;
        myLevels=1;
        
        myTopLeft = new SkipNode(NEGATIVE_INFINITY);
        myTopLeft.skipped = 0;
        myTopRight = new SkipNode(POSITIVE_INFINITY);
        myTopRight.skipped=1;
        
        myTopLeft.right = myTopRight;
        myTopRight.left = myTopLeft;
        
        myComp=new NaturalComparator();
        
        isASet=false;
        
        if(false)
        {
        	//so the compiler will stop yelling at me about unused methods
        	printList();
        }
    }
    
    //Constructs an empty SkipList using the specified Comparator
    public SkipList(Comparator c)
    {
    	this();
    	myComp=c;
    }
    
    //Constructs a copy of list using the elements natural order
    public SkipList(Collection list)
    {
        this();
        
       addAll(list);
    }
    
    protected SkipList(Collection list, boolean amSet)
    {
    	isASet=amSet;
    	addAll(list);
    }
    	
    
    //Private comparision function for the skip list
    //  (takes into account the Infinity nodes on the ends)
    //Note that comparing two infinities is undefined (and might throw an exception)
    private int compareValues(Object e1, Object e2)
    {
        //handles cases
        // -inf < e2 and e1 < +inf
        if(e1 == NEGATIVE_INFINITY || e2 == POSITIVE_INFINITY)
        {
            return -1;
        }
        //handles cases
        // e2 < positive inf and e1 > -inf
        if(e1 == POSITIVE_INFINITY || e2 == NEGATIVE_INFINITY)
        {
            return 1;
        }
        return myComp.compare(e1,e2);
    }

    
    //Return the number of entries in the Dictionary
    public int size()
    {
        return mySize;
    }
    
    //Returns true iff the dictionary is empty
    public boolean isEmpty()
    {
        return mySize==0;
    }
    
    //returns true iff an entry matching key k and value v is in the list
    public boolean contains(Object v)
    {
        SkipNode target = findNode(v);
        if(target == null)
            return false;       
        return true;
    }
    
    
    //returns the object at index i (in sorted order)
    //  in other words, the ith object in sorted order (starting at 0 and going to mySize-1)
    public Object get(int i)
    {
    	if(i < 0 || i >= mySize)
    		throw new IndexOutOfBoundsException("Requested index i="+i+" is out of bounds!");
    	i++;//array indexes are one less than the skipped
    	int cur = 0;
    	SkipNode current = myTopLeft;
    	while(true)
    	{
    		while(cur+current.right.skipped <= i)
	    	{
	    		current = current.right;
	    		cur+=current.skipped;
	    	}
	    	if(cur == i)
	    		return current.getValue();
	    	else
	    		current=current.below;
    	}
    }
    
    //balances the SkipList perfectly
    //  really only useful if you're done mucking with it
    //  and just want to do lookups
    public void balance()
    {
    	//We don't have to increment myModificationCount because the iterators only move along the bottom
    	// but if you change the iterator than BEWARE
    	SkipNode current = myTopLeft;
    	
    	while(current.below != null)
    		current = current.below;
    	myLevels = 1;
    	
    	while(current.right != null)
	    {
	    	current.above = null;
	    	current=current.right;
	    }
    	// get the last one
    	current.above=null;
    	
    	int newLev = (int)(Math.log(mySize)/Math.log(2));
    	for(int x=0;x<newLev;x++)
    	{
    		addTopLevel();
    		current = myTopLeft.below.right;
    		int y=0;
    		while(current.getValue() != POSITIVE_INFINITY)
    		{
    			
    			if(y%2 == 1)
    			{
    				int sCount=0;
    				SkipNode temp = current;
    				
    				while(temp.above == null)
    	            {
    					sCount+=temp.skipped;
    					temp = temp.left;    	            	            	
    	            }
    	            
    				temp=temp.above;
    	            
    	            // insert new node
    	            SkipNode n = new SkipNode(current.getValue());
    	            n.left = temp;
    	            n.right=temp.right;
    	            n.skipped = sCount;
    	                        
    	            temp.right.left=n;
    	            temp.right=n;
    	            
    	            n.right.skipped -= sCount;
    	            
    	            n.below=current;
    	            current.above = n;
    			}
    			
    			y++;
    			current=current.right;
    				
    		}
    	}
    	
    }
    	
    
    //returns the upper-most, left-most SkipNode containing item in the list
    //or null if it is not in the list
    private SkipNode findNode(Object item)
    {
        SkipNode current = myTopLeft;
        
        current = moveRightUntilNextIsGreaterOrCurrentIsEqual(current,item);
        while(compareValues(current.value(),item) != 0)
        {
            if(current.below == null)
            {
                //we are already at the bottom and haven't found it
                return null;
            }
            
            current = current.below;
            current = moveRightUntilNextIsGreaterOrCurrentIsEqual(current,item);
        }        
        return current;
    }
    
    // see SortedCollection.successor
    public Object successor(Object item)
    {
     	  SkipNode current = myTopRight;
          
          current = moveLeftUntilNextIsSmallerOrEqual(current,item);
          while(compareValues(current.value(),item) != 0)
          {
              if(current.below == null)
              {
                  //we are already at the bottom and item is not in the list
                  break;
              }
              
              current = current.below;
              current = moveLeftUntilNextIsSmallerOrEqual(current,item);
          }
          
          if(current.value() == POSITIVE_INFINITY)
        	  return null;
        	  
          return current.value();
     }
    
    // see SortedCollection.predecessor
    public Object predecessor(Object item)
    {
		SkipNode current = myTopLeft;
		  
		current = moveRightUntilNextIsGreater(current,item);
		while(compareValues(current.value(),item) != 0)
		{
		    if(current.below == null)
		    {
		        //we are already at the bottom and item is not in the list
		        break;
		    }
		    
		    current = current.below;
		    current = moveRightUntilNextIsGreaterOrEqual(current,item);
		}
		
		if(current.value() == NEGATIVE_INFINITY)
			return null;
		 
		return current.value();
	}

        
    //Insert item into the SkipList
    //returns true (the item is always inserted, see Collection.add )
    public boolean add(Object item)
    {
        myModificationCount++;//notify the iterators of a change
        int curLevel =0;//keeps track of the current level
        
        Stack updateStack = new Stack();
        
        SkipNode current=myTopLeft;
        while(current.below != null)
        {
            current = moveRightUntilNextIsGreater(current,item);
            updateStack.push(current);
            current=current.below;
            curLevel++;
        }
        //get to the right place on the last row
        current=moveRightUntilNextIsGreater(current,item);
        
        //we should now be in the correct position to insert item
        
        if(isASet)
        {
        	//if its already here, don't modify the set and return false (as per contract of Set.add() )
        	if(compareValues(current.value(),item) == 0)
        	{
        		myModificationCount--;//seeing as we didn't actually modify anything...
        		return false;
        	}
        }
                
        //we now have to insert item
        SkipNode newNode = new SkipNode(item);
        newNode.left = current;
        newNode.right=current.right;
        current.right.left = newNode;
        current.right = newNode;
        
        //update skipped counts
        while(!updateStack.isEmpty())
        {
        	SkipNode n = (SkipNode)updateStack.pop();
        	n.right.skipped++;
        }

        //Remember where this node is to set the above pointer in a moment
        SkipNode lastNode = newNode;
        
        //should eventually terminate...
        while(myRandom.nextBoolean())
        {
        	int sCount = 0;
            if(curLevel <= 0)
            {
                //we're at the top, trying to add a new node
                addTopLevel();
            }
            while(current.above == null)
            {
            	sCount+=current.skipped;
            	current = current.left;
            }
            
            current=current.above;
            
            //insert new node
            newNode = new SkipNode(item);
            newNode.left = current;
            newNode.right=current.right;
            newNode.skipped = sCount;
                        
            current.right.left=newNode;
            current.right=newNode;
            
            newNode.right.skipped -= sCount;
            
            newNode.below=lastNode;
            lastNode.above = newNode;
            
            //remember the last node for next loop
            lastNode = newNode;
            curLevel--;            
        }
        //whew...we made it!
        
        //newNode has been inserted
        mySize++;
        return true;
    }
    
    //returns the rightmost node less than or equal to item in current's row
    //cannot be called from POSITIVE_INFINITY
    private SkipNode moveRightUntilNextIsGreater(SkipNode current,Object item)
    {
        while(compareValues(current.right.value(),item) <= 0)
        {
            current=current.right;
            //will eventually hit POSITIVE_INFINITY and exit the loop
        }
        return current;
    }
    
    //returns the rightmost node less than item, or the leftmost node equal to item in current's row
    //cannot be called from POSITIVE_INFINITY
    private SkipNode moveRightUntilNextIsGreaterOrCurrentIsEqual(SkipNode current,Object item)
    {
        while(compareValues(current.right.value(),item) < 0)
        {
            current=current.right;
            //will eventually hit POSITIVE_INFINITY and exit the loop
        }
        if(compareValues(current.right.value(),item) == 0)
        	return current.right;
        return current;
    }
    
    //returns the rightmost node less than item in current's row
    //cannot be called from POSITIVE_INFINITY
    private SkipNode moveRightUntilNextIsGreaterOrEqual(SkipNode current,Object item)
    {
        while(compareValues(current.right.value(),item) < 0)
        {
            current=current.right;
            //will eventually hit POSITIVE_INFINITY and exit the loop
        }
        return current;
    }
    
    //keeps moving left until the next one is smaller or equal to item
    //cannot be called from NEGATIVE_INFINITY
    private SkipNode moveLeftUntilNextIsSmallerOrEqual(SkipNode current,Object item)
    {
        while(compareValues(current.left.value(),item) > 0)
        {
            current=current.left;
            //will eventually hit NEGATIVE_INFINITY and exit the loop
        }
        return current;
    }
    
    //returns the rightmost node greater than or equal to item in current's row
    //cannot be called from NEGATIVE_INFINITY
    private SkipNode moveLeftUntilNextIsSmaller(SkipNode current,Object item)
    {
        while(compareValues(current.left.value(),item) >= 0)
        {
            current=current.left;
            //will eventually hit NEGATIVE_INFINITY and exit the loop
        }
        return current;
    }

    
    //adds a level to the top of the list
    private void addTopLevel()
    {
        SkipNode newLeft = new SkipNode(NEGATIVE_INFINITY);
        SkipNode newRight = new SkipNode(POSITIVE_INFINITY);
        
        newLeft.below = myTopLeft;
        newRight.below = myTopRight;
        newLeft.right = newRight;
        newRight.left = newLeft;
        
        newLeft.skipped = 0;
        newRight.skipped = mySize+1;

        myTopLeft.above = newLeft;
        myTopRight.above = newRight;
        
        myTopLeft = newLeft;
        myTopRight=newRight;
        myLevels++;
    }
    
    //removes the object v
    //returns true iff the collection was modified
    public boolean remove(Object v)
    {
        SkipNode target = findNode(v);
        if(target == null)
        {
            //it wasn't here
            return false;
        }
        removeNode(target);//unlink target and its vertical chain
        mySize--;
        return true;
    }
    
    
    //removes the node specified, and all of the nodes above and below it
    //adjusts the size
    //n must be in the list
    private void removeNode(SkipNode n)
    {
        //n must exist in the list, so fix modification count
        myModificationCount++;
        
        SkipNode badNode = n;
        
        //until we are at the bottom
        while(badNode.below != null)
        {
            //unlink badNode from the list
            badNode.left.right = badNode.right;
            badNode.right.left = badNode.left;
            badNode.right.skipped += badNode.skipped;
            //advance badNode down
            badNode = badNode.below;
        }
        //unlink last node
        badNode.left.right = badNode.right;
        badNode.right.left = badNode.left;
        
        badNode = n;
        //now do the same thing going up from n
        //its ok to remove badNode twice,
        //because it just sets his neighbor's pointers agian
        while(badNode.above != null)
        {
            //unlink badNode from the list
            badNode.left.right = badNode.right;
            badNode.right.left = badNode.left;
            badNode.right.skipped += badNode.skipped;
            //advance badNode up
            badNode = badNode.above;
        }
        //unlink last node
        badNode.left.right = badNode.right;
        badNode.right.left = badNode.left;
        badNode.right.skipped += badNode.skipped;
        
        //badNode is now attached to a free vertical chain of nodes
        //that the garbage collector will take care of        
        removeExtraLevels();
    }
    
    //remove any extra empty levels if they exist
    private void removeExtraLevels()
    {
        //while there are blank levels that are not the bottom
        while(myTopLeft.right == myTopRight && myTopLeft.below !=null)
        {
            myTopLeft = myTopLeft.below;
            myTopLeft.above = null;
            
            myTopRight = myTopRight.below;
            myTopRight.above =null;
            myLevels--;
        }
    }
    
    public Comparator comparator()
	{
		if(myComp instanceof NaturalComparator)
			return null;
		else
			return myComp;
	}

	public Object first()
	{
		if(mySize==0)
			throw new NoSuchElementException("first() called on empty SkipList!");
		return get(0);
	}

	public SortedCollection headCollection(Object toElement)
	{
		return subCollection(NEGATIVE_INFINITY,toElement);
	}

	public Object last()
	{
		if(isEmpty())
			throw new NoSuchElementException("first() called on empty SkipList!");
		return get(mySize-1);
	}

	public SortedCollection subCollection(Object fromElement, Object toElement)
	{
		if(compareValues(fromElement,toElement) > 0)
			throw new IllegalArgumentException("Requested a subCollection with fromElement > toElement!");
		return new SubList(fromElement,toElement);
	}

	public SortedCollection tailCollection(Object fromElement)
	{
		return subCollection(fromElement, POSITIVE_INFINITY);
	}
	
	private class SubList extends AbstractCollection implements SortedCollection
	{
		private Object fromElement;
		private Object toElement;
		
		public SubList(Object fromE, Object toE)
		{
			fromElement = fromE;
			toElement=toE;
		}
		
		public Object getFromElement()
		{
			return fromElement;
		}
		
		public Object getToElement()
		{
			return toElement;
		}

		public Comparator comparator()
		{
			return SkipList.this.comparator();
		}

		public Object first()
		{
			SkipNode n = this.firstNode();
			
			if(n == null)			
				throw new NoSuchElementException("Called first() on empty sub SortedCollection!");
			else
				return n.value();
		}
		
		public SkipNode firstNode()
		{
			SkipNode current = myTopLeft;
	          
			current = moveRightUntilNextIsGreaterOrEqual(current,fromElement);
			while(compareValues(current.value(),fromElement) != 0)
			{
			    if(current.below == null)
			    {
			        //we are already at the bottom and item is not in the list
			        break;
			    }
			    
			    current = current.below;
			    current = moveRightUntilNextIsGreaterOrEqual(current,fromElement);
			}
			
			if(current.right.value() != POSITIVE_INFINITY)
			{
				if(compareValues(current.right.value(),toElement) < 0)
					return current.right;
			}
			
			return null;
		}

		public SortedCollection headCollection(Object toElem)
		{
			return subCollection(fromElement,toElem);
		}

		public Object last()
		{
			SkipNode current = myTopRight;
	          
			current = moveLeftUntilNextIsSmaller(current,fromElement);
			while(current.below != null)
			{	              
				current = current.below;
				current = moveLeftUntilNextIsSmaller(current,fromElement);
			}
			      
			if(current.left.value() != NEGATIVE_INFINITY)
			{
				if(compareValues(current.left.value(),fromElement) >= 0)
					return current.left.value();
			}
			      
			throw new NoSuchElementException("Called last() on empty sub SortedCollection!");
		}

		public SortedCollection subCollection(Object fromElem, Object toElem)
		{
			Object right,left;
			right = fromElement;
			left=toElement;
			if(compareValues(right,fromElem) < 0)
				right = fromElem;
			if(compareValues(left,toElem) > 0)
				left = toElem;
			
			if(isASet)
				return new SubSet((SubList)SkipList.this.subCollection(left,right));
			else
				return SkipList.this.subCollection(left,right);
		}

		public SortedCollection tailCollection(Object fromElem)
		{
			return subCollection(fromElem,toElement);
		}

		public boolean add(Object arg)
		{
			if(SkipList.this.compareValues(fromElement,arg) < 0 || SkipList.this.compareValues(arg,toElement) >= 0)
				throw new IllegalArgumentException("Attempted to add an element to a Sorted SubCollection that was outside the specified range!");
			return SkipList.this.add(arg);
		}

		public boolean addAll(Collection arg)
		{
			Iterator i = arg.iterator();
			boolean changed=false;
			while(i.hasNext())
			{
				if(this.add(i.next()))
					changed=true;
			}
			return changed;
		}

		public void clear()
		{
			Iterator i = this.iterator();
			while(i.hasNext())
			{
				SkipList.this.remove(i.next());
			}
		}

		public boolean contains(Object arg)
		{
			if(SkipList.this.compareValues(fromElement,arg) < 0 || SkipList.this.compareValues(arg,toElement) >= 0)
				return false;
			return SkipList.this.contains(arg);
		}

		public boolean containsAll(Collection arg)
		{
			Iterator i = arg.iterator();
			while(i.hasNext())
			{
				if(!this.contains(i.next()))
					return false;
			}
			return true;
		}

		public boolean isEmpty()
		{
			SkipNode current = myTopLeft;
	          
			current = moveRightUntilNextIsGreaterOrEqual(current,fromElement);
			while(compareValues(current.value(),fromElement) != 0)
			{
			    if(current.below == null)
			    {
			        //we are already at the bottom and item is not in the list
			        break;
			    }
			    
			    current = current.below;
			    current = moveRightUntilNextIsGreaterOrEqual(current,fromElement);
			}
			
			if(current.right.value() != POSITIVE_INFINITY)
			{
				if(compareValues(current.right.value(),toElement) < 0)
					return false;
			}
			
			return true;
		}

		public Iterator iterator()
		{
			return new SkipIterator(this.firstNode(),toElement);
		}

		public boolean remove(Object arg)
		{
			if(SkipList.this.compareValues(fromElement,arg) < 0 || SkipList.this.compareValues(arg,toElement) >= 0)
				return false;
			return SkipList.this.remove(arg);
		}

		public boolean removeAll(Collection arg)
		{
			boolean changed=false;
			Iterator i= arg.iterator();
			while(i.hasNext())
			{
				if(SkipList.this.remove(i.next()))
					changed=true;
			}
			return changed;
		}

		public boolean retainAll(Collection arg)
		{
			Iterator i = this.iterator();
			boolean changed=false;
			while(i.hasNext())
			{
				Object cur = i.next();
				if(!arg.contains(cur))
				{
					i.remove();
					changed=true;
				}
			}
			return changed;
			
			
		}

		//this operation is O(n)...
		public int size()
		{
			SkipNode current = myTopLeft;
	          
			current = moveRightUntilNextIsGreaterOrEqual(current,fromElement);
			while(compareValues(current.value(),fromElement) != 0)
			{
			    if(current.below == null)
			    {
			        //we are already at the bottom and item is not in the list
			        break;
			    }
			    
			    current = current.below;
			    current = moveRightUntilNextIsGreaterOrEqual(current,fromElement);
			}
			
			if(current.right.value() != POSITIVE_INFINITY)
			{
				if(compareValues(current.right.value(),toElement) < 0)
				{
					current=current.right;
					int oup = 0;
					while(compareValues(current.value(),toElement) < 0)
					{
						oup++;
						current = current.right;
					}
					return oup;
				}
			}
			
			return 0;
		}

		public Object[] toArray()
		{
			Stack oup = new Stack();
			SkipNode current = myTopLeft;
	          
			current = moveRightUntilNextIsGreaterOrEqual(current,fromElement);
			while(compareValues(current.value(),fromElement) != 0)
			{
			    if(current.below == null)
			    {
			        //we are already at the bottom and item is not in the list
			        break;
			    }
			    
			    current = current.below;
			    current = moveRightUntilNextIsGreaterOrEqual(current,fromElement);
			}
			
			if(current.right.value() != POSITIVE_INFINITY)
			{
				if(compareValues(current.right.value(),toElement) < 0)
				{
					current=current.right;
					while(compareValues(current.value(),toElement) < 0)
					{
						oup.push(current.value());
						current = current.right;
					}
					return oup.toArray();
				}
			}
			
			return new Object[0];
		}

		public Object predecessor(Object e)
		{
			Object oup = SkipList.this.predecessor(e);
			if(SkipList.this.compareValues(fromElement,oup) < 0 || SkipList.this.compareValues(oup,toElement) >= 0)
				return null;
			return oup;
		}

		public Object successor(Object e)
		{
			Object oup = SkipList.this.successor(e);
			if(SkipList.this.compareValues(fromElement,oup) < 0 || SkipList.this.compareValues(oup,toElement) >= 0)
				return null;
			return oup;
		}		
	}
	
	private class SubSet extends SubList implements SortedSet
	{
		public SubSet(Object from, Object to)
		{
			super(from,to);
			if(!isASet)
				throw new UnsupportedOperationException("Constructed a SubSet from bounds in a nonset!");
		}
		
		public SubSet(SubList s)
		{
			super(s.getFromElement(),s.getToElement());
			if(!isASet)
				throw new UnsupportedOperationException("Constructed a SubSet from SubList in a nonset!");
				
		}
		
		public SortedSet headSet(Object toElem)
		{
			if(!isASet)
				throw new UnsupportedOperationException("Called headSet on a nonset!");
			SortedCollection c = headCollection(toElem);
			return new SubSet((SubList)c);
		}

		public SortedSet subSet(Object fromElem, Object toElem)
		{
			if(!isASet)
				throw new UnsupportedOperationException("Called headSet on a nonset!");
			SortedCollection c = subCollection(fromElem,toElem);
			return new SubSet((SubList)c);
		}

		public SortedSet tailSet(Object fromElem)
		{
			if(!isASet)
				throw new UnsupportedOperationException("Called headSet on a nonset!");
			SortedCollection c = tailCollection(fromElem);
			return new SubSet((SubList)c);
		}
	}
        
    
    //Returns an iterator over the values in the collection
    //Iterators will be invalidated if the list is modified while they are in service
    //and will attempt to throw ConcurrentModificationExceptions
   
	//Entries should be in sorted order
    public Iterator iterator()
    {
        return new SkipIterator();
    }
    
    private class SkipIterator implements Iterator
    {
        private boolean canRemove;
        private int iteratorModCount;//used to store the modification count
        private SkipNode current;//always sits on the last one we returned
        private Object toElement;
        
        //constructs a new iterator at the bottom of the list
        public SkipIterator()
        {
            canRemove=false;
            //initialize modCount to that of the list
            iteratorModCount=myModificationCount;
            toElement = POSITIVE_INFINITY;

            //find the bottom row
            current=myTopLeft;
            while(current.below != null)
            {
                current = current.below;
            }
        }
        
        public SkipIterator(SkipNode from, Object toElem)
        {
        	this();
        	current = new SkipNode(null);
        	if(from == null)
        		from = myTopRight;
        	current.right = from;
        	toElement=toElem;
        }
        
        //returns true iff the next call to next will be successful
        public boolean hasNext()
        {
            if(isBad())
                throw new ConcurrentModificationException("The iterator is invalid because the list has been modified.");
            return (compareValues(current.right.value(),toElement) < 0);
        }
        
        //returns the next item
        public Object next()
        {
            if(isBad())
                throw new ConcurrentModificationException("The iterator is invalid because the list has been modified.");
            if(!hasNext())
                throw new NoSuchElementException("The iterator has run out of elements.");
            current = current.right;
            canRemove = true;
            return current.value();
        }
        
        
        //removes the last item returned by the iterator
        //can only be called once after each call to next()
        //does not invalidate this iterator if called by this iterator and not another
        public void remove()
        {            
        	if(!canRemove)
                 throw new IllegalStateException("remove() called without next()");
            if(isBad())
            	throw new ConcurrentModificationException("The iterator is invalid because the list has been modified.");
             // stores the next node for after the remove
            SkipNode nextNode = current.right; 
            SkipNode badNode = current;
            
            //give current something new to point to, such that
            // the next element is what we would have had next
            current=new SkipNode(null);
            current.right=nextNode;
             
            // remove the last item seen
             
            SkipList.this.removeNode(badNode);
            SkipList.this.mySize--;
             
             
            // update the modification count because the iterator is modifying it
            // so its alright to continue
            iteratorModCount = myModificationCount;
 
            
            
        }

        //returns true iff the iterator is invalid
        private boolean isBad()
        {
            return iteratorModCount != myModificationCount;
        }
    }
   
    //debugging function to print a picture of the list
    private void printList()
    {
        SkipNode current = myTopLeft;
        int level=0;
        while(current.below != null)
        {
            while(current.right != null)
            {
                System.out.println(current);
                current=current.right;
            }
            System.out.println(current);
            current=myTopLeft;
            level++;
            for(int x=0;x<level;x++)
            {
                current = current.below;
            }
            System.out.println("endl");
        }
        while(current.right != null)
        {
            System.out.println(current);
            current=current.right;
        }
        System.out.println(current);
        current = current.below;
        System.out.println("Done.");
    }

	
 
}