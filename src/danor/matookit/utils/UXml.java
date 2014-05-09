package danor.matookit.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class UXml
{
	public static final OutputFormat ofm = OutputFormat.createPrettyPrint();
	static
	{
		ofm.setEncoding("utf-8");
		ofm.setSuppressDeclaration(false);
		ofm.setIndent(true);
		ofm.setIndent("	");
		ofm.setNewlines(true);
	}
	
	private final File xmlFile;
	private Element element;
	
	public UXml(File xmlFile, String rootName) throws Exception
	{
		this.xmlFile = xmlFile;
		
		element = DocumentHelper.createDocument().addElement(rootName);
	}
	public UXml(File xmlFile) throws Exception
	{
		this.xmlFile = xmlFile;
		if(xmlFile != null)
			element = new SAXReader().read(xmlFile).getRootElement();
	}
//路径相关
	public Element find(String xmlPath) throws Exception
	{
		Element te = element;
	//开头带"."表示回到根节点
		if(xmlPath.startsWith("."))
		{
			te = element.getDocument().getRootElement();
			xmlPath = xmlPath.replaceAll("[.]", "");
		}
	//每个"<"表示返回父节点一次
		if(xmlPath.indexOf("<") != -1)
		{
			int cntRise = xmlPath.split("<").length - 1;
			
			if(cntRise == -1)
				cntRise = 1;
			
			while(cntRise-- > 0)
				te = te.getParent();
			
			xmlPath = xmlPath.replaceAll("[<]", "");
		}
	//前处理完还有内容的话
		if(!xmlPath.equals(""))
		{
		//">"表示子节点
			String[] fall = xmlPath.split(">");
			
			for(String f:fall)
			{
				if(te == null) break;
				
				te = te.element(f);
			}
		}
		
		return te;
	}
	
	public UXml set(Element setElement) throws Exception
	{
		element = setElement;
		
		return this;
	}
	public Element move(String xmlPath) throws Exception
	{
		Element result = find(xmlPath); 

		return (result != null)?(element = result):null;
	}
//读取相关
	public String value(String xmlPath) throws Exception
	{
		if(xmlPath == null)
			return element.getStringValue();
		
		Element result = find(xmlPath); 
		
		return (result != null)?result.getStringValue():"";
	}
	public List<?> list(String xmlPath) throws Exception
	{
		if(xmlPath == null)
			return element.elements();
		
		Element result = find(xmlPath);

		String[] fall = xmlPath.split(">");
		
		return (result != null)?result.getParent().elements(fall[fall.length-1].replace(".", "").replace("<", "")):null;
	}
//写入相关
	public void save(String xmlPath, String value) throws Exception
	{
		if(xmlPath == null)
			return;
		
		Element result = find(xmlPath);
		
		if(result != null)
			result.setText(value);
		
		save();
	}
	public void save() throws Exception
	{
		XMLWriter output = new XMLWriter(new FileOutputStream(xmlFile), ofm);
		output.write(element.getDocument());
		output.close();
	}
//创建相关
	public Element add(String nodeName)
	{
		return element.addElement(nodeName);
	}
	public Element add(String nodeName, String value)
	{
		Element e = element.addElement(nodeName);
		e.setText(value);
		
		return e;
	}
	public Element addTo(String nodeName)
	{
		return (element = element.addElement(nodeName));
	}
}
