package net.banack.data;

/*
 * Variable datatype class.
 */
public class Data
{
	private String value;
	
	public static enum Type {
		STRING, INT, BOOLEAN
	}
	
	public Data(String str)
	{
		value = str;
	}
	
	public Data(int x)
	{
		value = Integer.toString(x);
	}
	
	public Data(boolean b)
	{
		value = Boolean.toString(b);
	}
	
	public String getString()
	{
		return value;
	}
	
	public int getInt() throws NumberFormatException
	{
		return Integer.decode(value);
	}
	
	//TODO: Real exception model.
	public boolean getBoolean() throws NumberFormatException
	{
		if (value.equalsIgnoreCase("TRUE")) {
			return true;
		} else if (value.equalsIgnoreCase("FALSE")) {
			return false;
		}
		
		int x = getInt();
		
		if (x == 0) {
			return false;
		} else if (x == 1) {
			return true;
		}
		
		throw new NumberFormatException("Unable to cast \""+value+"\" as a Boolean");
	}
	
	public boolean isString()
	{
		return true;
	}
	
	public boolean isInt()
	{
		try {
			getInt();
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public boolean isBoolean()
	{
		try {
			getBoolean();
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public String toString()
	{
		return getString();
	}
	
	public int hashCode()
	{
		return value.hashCode();
	}
	
	//Note that unsanitized values may not be equal
	// ie while "TRUE" and "true" are the same as booleans, they are not as strings
	public boolean equals(Object rhs)
	{
		if (rhs instanceof Data) {
			Data d = (Data) rhs;
			if (d.value.equals(this.value)) {
				return true;
			}
		}
		return false;
	}
		
	
	public void sanitizeString()
	{
		
	}
	
	public void sanitizeInt() throws NumberFormatException
	{
		Data temp = new Data(getInt());
		value = temp.value;
	}
	
	public void sanitizeBoolean() throws NumberFormatException
	{
		Data temp = new Data(getBoolean());
		value = temp.value;
	}
}
