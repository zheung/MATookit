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
		List<?> elements = UConfigContainer.instance.xml.list(key+">Item");
		
		if(elements == null)
			return new String[]{UConfigContainer.instance.xml.value(key)};
		
		String[] configs = new String[elements.size()];
		
		int count = 0;
    	for(Object e:elements)
    	{
    		UConfigContainer.instance.xml.set((Element)e);
    		
    		configs[count++] = UConfigContainer.instance.xml.value(null);
    	}
		
    	UConfigContainer.instance.xml.move(".");
    	
		ULog.log("Config-Load-"+key);
		
		return configs;
	}

	public synchronized static void save(String key, String...values) throws Exception
	{
		UConfigContainer.instance.xml.move(key);
		
		UConfigContainer.instance.xml.save("", values[0]);
		
		UConfigContainer.instance.xml.move(".");
		
		ULog.log("Config-Save-"+key);
	}

	private static class UConfigContainer
	{
		private static UConfig instance;
		static
		{
			try { instance = new UConfig(); }
			catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public static UConfig getInstance() { return UConfigContainer.instance; }
}
