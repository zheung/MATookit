package danor.matookit.utils;

import java.io.File;
import java.util.List;

import org.dom4j.Element;

public class UcKey
{
	private final UcXml xml;
	
	public UcKey() throws Exception
	{
		xml = new UcXml(new File("./wrk/dat/arb"));
	}

	public synchronized String[] Data(String form, int id) throws Exception
	{
		List<?> r = xml.list("."+form);

		for(Object e:r)
    	{
			xml.set((Element)e);
    		if(xml.value("ID").equals(String.valueOf(id)))
    		{
    			String[] result = new String[((Element)e).elements().size()];

    			List<?> rs = xml.list(null);
    			
    			int i = 0;
    			for(Object ee:rs)
    				result[i++] = xml.set((Element)ee).value(null);
    			
    			return result;
    		}
    	}
		
		return null;
	}

	private static class FcDataBaseContainer
	{
		private static UcKey instance;
		static
		{
			try { instance = new UcKey(); }
			catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public static UcKey getInstance() { return FcDataBaseContainer.instance; }
}
