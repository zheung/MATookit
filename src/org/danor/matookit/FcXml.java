package org.danor.matookit;

import java.io.File;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class FcXml
{
	private SAXReader xreader = new SAXReader();
	private File xmlFile;
	private Element e;
	
	public FcXml(File setFile) throws Exception
	{
		xmlFile = setFile;
		if(setFile != null)
			e = xreader.read(xmlFile).getRootElement();
	}
	
	protected FcXml set(Element setElement) throws Exception
	{
		e = setElement;
		
		return this;
	}
	protected Element move(String xmlPath) throws Exception
	{
		if(xmlPath.startsWith("."))
		{
			e = xreader.read(xmlFile).getRootElement();
			xmlPath = xmlPath.replaceAll("[.]", "");
		}
		
		Element te = e;
		if(xmlPath.indexOf("<") != -1)
		{
			int cntRise = xmlPath.split("<").length - 1;
			while(cntRise-- > 0)
				te = te.getParent();
			
			xmlPath = xmlPath.replaceAll("<", "");
		}
		
		String[] fall = xmlPath.split(">");
		
		for(String f:fall)
		{
			if(te == null) break;
			
			te = te.element(f);
		}

		return (te != null)?(e = te):null;
	}
	protected Element child(String xmlPath) throws Exception
	{
		Element te = e;
		
		if(xmlPath.startsWith("."))
		{
			te = xreader.read(xmlFile).getRootElement();
			xmlPath = xmlPath.replaceAll("[.]", "");
		}
		
		if(xmlPath.indexOf("<") != -1)
		{
			int cntRise = xmlPath.split("<").length - 1;
			while(cntRise-- > 0)
				te = te.getParent();
			
			xmlPath = xmlPath.replaceAll("<", "");
		}
		
		String[] fall = xmlPath.split(">");
		
		for(String f:fall)
		{
			if(te == null) break;
			
			te = te.element(f);
		}

		return te;
	}
	
	protected String value(String xmlPath) throws Exception
	{
		Element te = e;
		
		if(xmlPath == null)
			return te.getStringValue();
		
		if(xmlPath.startsWith("."))
		{
			te = xreader.read(xmlFile).getRootElement();
			xmlPath = xmlPath.replaceAll("[.]", "");
		}
		
		if(xmlPath.indexOf("<") != -1)
		{
			int cntRise = xmlPath.split("<").length - 1;
			while(cntRise-- > 0)
				te = te.getParent();
			
			xmlPath = xmlPath.replaceAll("<", "");
		}
		
		String[] fall = xmlPath.split(">");
		
		for(String f:fall)
		{
			if(te == null) break;
			
			te = te.element(f);
		}

		return te != null?te.getStringValue():"";
	}
	protected List<?> list(String xmlPath) throws Exception
	{
		Element te = e;
		
		if(xmlPath == null)
			return e.elements();

		if(xmlPath.startsWith("."))
		{
			te = xreader.read(xmlFile).getRootElement();
			xmlPath = xmlPath.replaceAll("[.]", "");
		}
		
		if(xmlPath.indexOf("<") != -1)
		{
			int cntRise = xmlPath.split("<").length - 1;
			while(cntRise-- > 0)
				te = te.getParent();
			
			xmlPath = xmlPath.replaceAll("<", "");
		}
		
		String[] falls = xmlPath.split(">");
		
		for(int i=0; i<falls.length - 1; i++)
			te = te.element(falls[i]);

		return te.elements(falls[falls.length - 1]);
	}


}
