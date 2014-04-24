package danor.matookit.utils;

import java.util.Map;
import java.util.TreeMap;

public class UcOption
{
	private final Map<String, String> optString = new TreeMap<String, String>();
	private final Map<String, Boolean> optBoolean = new TreeMap<String, Boolean>();
	
	public UcOption put(String pKey, String pValue)
	{
		optString.put(pKey, pValue);
		return this;
	}
	public UcOption put(String pKey, Boolean pValue)
	{
		optBoolean.put(pKey, pValue);
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
}

