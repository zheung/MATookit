package danor.matookit.utils;

import java.io.File;
import java.util.List;

import org.dom4j.Element;

import danor.matookit.functions.FServer;

public class UKey
{
	private final UXml xml;
	
	public UKey() throws Exception
	{
		xml = new UXml(new File(FServer.dirDatAll, "arb.xml"));
	}

	public static synchronized String[] Data(String typKey, String id) throws Exception
	{
		getInstance();
		
		List<?> lstElement = UKeyContainer.instance.xml.list("."+typKey+">Item");

		String[] result = null;
		for(Object e:lstElement)
    	{
			UKeyContainer.instance.xml.set((Element)e);
			
			if(UKeyContainer.instance.xml.value("ID").equals(id))
				switch(typKey)
				{
				case "Action":
					result = new String[2];
					result[0] = UKeyContainer.instance.xml.value("Url");
					result[1] = UKeyContainer.instance.xml.value("Params");
					return result;
				case "CipherAES": case "CipherRSA":
					result = new String[1];
					result[0] = UKeyContainer.instance.xml.value("Key");
					return result;
				case "Property":
					result = new String[2];
					result[0] = UKeyContainer.instance.xml.value("Key");
					result[1] = UKeyContainer.instance.xml.value("Value");
					return result;
				case "Server":
					result = new String[1];
					result[0] = UKeyContainer.instance.xml.value("Url");
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
