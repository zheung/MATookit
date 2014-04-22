package org.danor.matookit;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class FcConfig //Singleton
{
	private final Map<String, String[]> config = new LinkedHashMap<String, String[]>();
	
	private FcConfig() throws Exception
	{
		String[] configs = (new String(FcUtil.Input(new File("./wrk/dat/cfg")))).split("\r\n");
		
		for(String c:configs)
		{
			String[] kv = c.split(":");
			
			if(kv.length == 2)
				config.put(kv[0], kv[1].split("[|]"));
			else
				config.put(kv[0], new String[] {""});
		}
		
		FcLog.getInstance().log("Config-Init");
	}
	
	protected synchronized String[] load(String key)
	{
		String[] values = config.get(key);
		
		try
		{
			FcLog.getInstance().log("Config-Load-"+key);
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return values;
	}

	protected synchronized void save(String key, String...values) throws Exception
	{
		config.put(key, values);
		
		StringBuilder sb = new StringBuilder();
		
		for(Entry<String, String[]> e:config.entrySet())
		{
			sb.append(e.getKey()).append(":");
			
			StringBuilder sb2 = new StringBuilder();
			
			for(String v:e.getValue())
				sb2.append(v).append("|");
			
			sb.append(sb2.substring(0,sb2.length()-1)).append("\r\n");
		}
		
		FcUtil.Output(new File("./wrk/dat/cfg"), sb.substring(0,sb.length()-2).getBytes(), false);
		
		FcLog.getInstance().log("Config-Save-"+key, true);
	}

	private static class FcConfigContainer
	{
		private static FcConfig instance;
		static
		{
			try { instance = new FcConfig(); }
			catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public static FcConfig getInstance() { return FcConfigContainer.instance; }
}
