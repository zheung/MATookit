package org.danor.matookit;

import java.io.File;
import java.util.List;

import org.dom4j.Element;

public class FcDataBase
{
	private final FcXml xml;
	
	public FcDataBase() throws Exception
	{
		xml = new FcXml(new File("./wrk/dat/arb"));
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
		private static FcDataBase instance;
		static
		{
			try { instance = new FcDataBase(); }
			catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public static FcDataBase getInstance() { return FcDataBaseContainer.instance; }
}
