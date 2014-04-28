package danor.matookit.utils;

import java.io.File;
import java.util.List;

import org.dom4j.Element;

public class UConfig
{
	private final UXml xml = new UXml(new File("./wrk/dat/cfg.xml"));
	
	private UConfig() throws Exception
	{
		ULog.log("Config-Init");
	}
	
	public synchronized static String[] load(String key) throws Exception
	{
		List<?> elements = FcConfigContainer.instance.xml.list(key+">Item");
		
		if(elements.size() == 0)
			return new String[]{FcConfigContainer.instance.xml.value(key)};
		
		String[] configs = new String[elements.size()];
		
		int count = 0;
    	for(Object e:elements)
    	{
    		FcConfigContainer.instance.xml.set((Element)e);
    		
    		configs[count++] = FcConfigContainer.instance.xml.value(null);
    	}
		
    	FcConfigContainer.instance.xml.move(".");
    	
		ULog.log("Config-Load-"+key);
		
		return configs;
	}

	public synchronized static void save(String key, String...values) throws Exception
	{
		FcConfigContainer.instance.xml.move(key);
		
		FcConfigContainer.instance.xml.save(null, values[0]);
		
		FcConfigContainer.instance.xml.move(".");
		
		ULog.log("Config-Save-"+key);
	}

	private static class FcConfigContainer
	{
		private static UConfig instance;
		static
		{
			try { instance = new UConfig(); }
			catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public static UConfig getInstance() { return FcConfigContainer.instance; }
}
