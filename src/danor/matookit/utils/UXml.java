package danor.matookit.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class UXml
{
	private SAXReader xreader = new SAXReader();
	private final File xmlFile;
	private Element element;
	
	public UXml(File setFile) throws Exception
	{
		xmlFile = setFile;
		if(setFile != null)
			element = xreader.read(setFile).getRootElement();
	}
//路径相关
	public UXml set(Element setElement) throws Exception
	{
		element = setElement;
		
		return this;
	}
	public Element move(String xmlPath) throws Exception
	{
		if(xmlPath.startsWith("."))
		{
			element = element.getDocument().getRootElement();
			xmlPath = xmlPath.replaceAll("[.]", "");
		}
		
		Element te = element;
		if(xmlPath.indexOf("<") != -1)
		{
			int cntRise = xmlPath.split("<").length - 1;
			
			if(cntRise == -1)
				cntRise = 1;
			
			while(cntRise-- > 0)
				te = te.getParent();
			
			xmlPath = xmlPath.replaceAll("[<]", "");
		}
		
		String[] fall = xmlPath.split(">");
		
		if(!xmlPath.equals(""))
			for(String f:fall)
			{
				if(te == null) break;
				
				te = te.element(f);
			}

		return (te != null)?(element = te):null;
	}
	public Element child(String xmlPath) throws Exception
	{
		Element te = element;
		
		if(xmlPath.startsWith("."))
		{
			te = element.getDocument().getRootElement();
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
//读取相关
	public String value(String xmlPath) throws Exception
	{
		Element te = element;
		
		if(xmlPath == null)
			return te.getStringValue();
		
		if(xmlPath.startsWith("."))
		{
			te = element.getDocument().getRootElement();
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
	public List<?> list(String xmlPath) throws Exception
	{
		Element te = element;
		
		if(xmlPath == null)
			return element.elements();

		if(xmlPath.startsWith("."))
		{
			te = element.getDocument().getRootElement();
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
//写入相关
	public void save(String xmlPath, String value) throws Exception
	{
		Element te = element;
		
		if(xmlPath == null)
			return;
		
		if(xmlPath.startsWith("."))
		{
			te = element.getDocument().getRootElement();
			xmlPath = xmlPath.replaceAll("[.]", "");
		}
		
		if(xmlPath.indexOf("<") != -1)
		{
			int cntRise = xmlPath.split("<").length - 1;
			while(cntRise-- > 0)
				te = te.getParent();
			
			xmlPath = xmlPath.replaceAll("<", "");
		}
		
		if(!xmlPath.equals(""))
		{
			String[] fall = xmlPath.split(">");
			
			for(String f:fall)
			{
				if(te == null) break;
				
				te = te.element(f);
			}
		}
		
		if(te != null)
			te.setText(value);
		
		OutputFormat ofm = OutputFormat.createPrettyPrint();
		ofm.setEncoding("utf-8");
		ofm.setSuppressDeclaration(false);
		ofm.setIndent(true);
		ofm.setIndent("	");
		ofm.setNewlines(true);

		XMLWriter output = new XMLWriter(new FileOutputStream(xmlFile), ofm);
		output.write(te.getDocument());
		output.close();
	}
}
