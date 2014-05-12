package danor.matookit.utils;

import java.util.Map;
import java.util.TreeMap;

public class UOption
{
	private final Map<String, String> optString = new TreeMap<String, String>();
	private final Map<String, Boolean> optBoolean = new TreeMap<String, Boolean>();
	private final Map<String, String[][]> optStrings = new TreeMap<String, String[][]>();
	
	public UOption put(String pKey, String pValue)
	{
		optString.put(pKey, pValue);
		return this;
	}
	public UOption put(String pKey, Boolean pValue)
	{
		optBoolean.put(pKey, pValue);
		return this;
	}
	public UOption put(String pKey, String[][] pValue)
	{
		optStrings.put(pKey, pValue);
		return this;
	}

	public String getString(String gKey) throws Exception
	{
		return optString.get(gKey);
	}
	public Boolean getBoolean(String gKey) throws Exception
	{
		return optBoolean.get(gKey);
	}
	public String[][] getStrings(String gKey) throws Exception
	{
		return optStrings.get(gKey);
	}

}

