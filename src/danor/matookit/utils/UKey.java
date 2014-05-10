package danor.matookit.utils;

import java.io.File;
import java.util.List;

import org.dom4j.Element;

public class UKey
{
	private final UXml xml;
	
	public UKey() throws Exception
	{
		xml = new UXml(new File("./wrk/dat/arb"));
	}

	public static synchronized String[] Data(String form, int id) throws Exception
	{
		getInstance();
		
		List<?> r = UKeyContainer.instance.xml.list("."+form);

		for(Object e:r)
    	{
			UKeyContainer.instance.xml.set((Element)e);
    		if(UKeyContainer.instance.xml.value("ID").equals(String.valueOf(id)))
    		{
    			String[] result = new String[((Element)e).elements().size()];

    			List<?> rs = UKeyContainer.instance.xml.list(null);
    			
    			int i = 0;
    			for(Object ee:rs)
    				result[i++] = UKeyContainer.instance.xml.set((Element)ee).value(null);
    			
    			return result;
    		}
    	}
		
		return null;
	}

	private static class UKeyContainer
	{
		private static UKey instance;
		static
		{
			try { instance = new UKey(); }
			catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	private static UKey getInstance() { return UKeyContainer.instance; }
}
