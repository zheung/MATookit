package org.danor.matookit;

import java.util.Map;
import java.util.TreeMap;

public class FcOption
{
	private final Map<String, String> optString = new TreeMap<String, String>();
	private final Map<String, Boolean> optBoolean = new TreeMap<String, Boolean>();
	
	protected FcOption put(String pKey, String pValue)
	{
		optString.put(pKey, pValue);
		return this;
	}
	protected FcOption put(String pKey, Boolean pValue)
	{
		optBoolean.put(pKey, pValue);
		return this;
	}

	protected String getString(String gKey) throws Exception
	{
		return optString.get(gKey);
	}
	protected Boolean getBoolean(String gKey) throws Exception
	{
		return optBoolean.get(gKey);
	}
}

