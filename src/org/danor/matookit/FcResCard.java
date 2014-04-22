package org.danor.matookit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class FcResCard
{
	private static SAXReader xreader = new SAXReader();
	
	public static List<DcDataCard> crtCard(int version) throws Exception 
	{
		File mkcFile = new File("./wrk/dat/ctg/mak-" + version + ".xml");
		File macFile = new File("./wrk/dat/ctg/ctg-" + version + ".xml");
		File mdcFile = new File("./wrk/dat/ctg/card-" + version + ".xml");
		
		List<DcDataCard> list = new ArrayList<>();
	//Read macFile
		Element r = xreader.read(macFile).getRootElement().element("body").element("master_data").element("master_card_data");
		
		for(Iterator<?> i = r.elementIterator("card"); i.hasNext();)
	    {
	    	Element e = (Element) i.next();
	    	DcDataCard dc = new DcDataCard();
			
			dc.cID = e.element("master_card_id").getStringValue();
			dc.country = e.element("country_id").getStringValue();
			dc.name = e.element("name").getStringValue();
			dc.desc = e.element("char_description").getStringValue().replace("|","");
			dc.sklName = e.element("skill_name").getStringValue();
			dc.sklKana = e.element("skill_kana").getStringValue();
			dc.sklDesc = e.element("skill_description").getStringValue().replace("|"," ");
			dc.sklType = e.element("skill_type").getStringValue();
			dc.illustrator = e.element("illustrator").getStringValue();
			dc.cost = e.element("cost").getStringValue();
			dc.star = e.element("rarity").getStringValue();
			dc.salePrice = e.element("sale_price").getStringValue();
			dc.basHP = e.element("base_hp").getStringValue();
			dc.basAK = e.element("base_power").getStringValue();
			dc.maxLV = e.element("max_lv").getStringValue();
			dc.i1ID = e.element("image1_id").getStringValue();
			dc.i2ID = e.element("image2_id").getStringValue();
			dc.grwType = e.element("grow_type").getStringValue();
			dc.grwName = e.element("grow_name").getStringValue();
			dc.grwDesc = e.element("growth_rate_text").getStringValue();
			dc.frmID = e.element("form_id").getStringValue();
			dc.sex = e.element("distinction").getStringValue();
			dc.version = e.element("card_version").getStringValue();
			dc.maxHP = e.element("lvmax_hp").getStringValue();
			dc.maxAK = e.element("lvmax_power").getStringValue();
			dc.hloMaxLV = e.element("max_lv_holo").getStringValue();
			
			Element te =  e.element("compound_target_id");
			if(te != null)
				dc.cpdTarget = te.getStringValue();
			te =  e.element("compound_result_id");
			if(te != null)
				dc.cpdResult = te.getStringValue();
			te =  e.element("evolution_base_price");
			if(te != null)
				dc.cpdPrices = te.getStringValue();
			
			dc.isSync = "1";
			list.add(dc);
		}
		
	//Read mkcFile
		if(mkcFile.exists())
		{
			BufferedReader br = new BufferedReader(new FileReader(mkcFile));
			
			br.readLine();
			while(br.readLine() != null)
			{
				String cID = br.readLine().replaceAll("<.*?>","").replace("\t", "");
				
				DcDataCard dc = null;
				for(DcDataCard c:list)
					if(c.cID.equals(cID))
					{
						dc = c;
						break;
					}
				
				if(dc != null)
				{
					br.readLine(); br.readLine();
					br.readLine(); br.readLine();
					dc.sklID = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					br.readLine();
					dc.sklVMin = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.sklVMax = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.sklPesc = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					br.readLine(); br.readLine();
					br.readLine(); br.readLine();
					br.readLine(); br.readLine();
					br.readLine(); br.readLine();
					br.readLine();
					dc.lmtHP = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.lmtAK = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.lmtLV = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					br.readLine();
					dc.hloLmtLV = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.hloEX = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					br.readLine();
					br.readLine();
					
					dc.isSync = "0";
				}
				else
				{
					dc = new DcDataCard();
					
					dc.cID = cID;
					
					dc.name = br.readLine().replaceAll("<.*?>","").replace("\t", "").replace("&amp;#10;", " ").replace("&amp;", "/");
					dc.star = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.country = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.cost = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.sklID = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.sklDesc = br.readLine().replaceAll("<.*?>","").replace("\t", "").replace("&amp;#10;", "").replace("&amp;", "/");;
					dc.sklVMin = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.sklVMax = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.sklPesc = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.sklType = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.grwDesc = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.sex = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.illustrator = br.readLine().replaceAll("<.*?>","").replace("\t", "").replace("&amp;#10;", "").replace("&amp;", "/");;
					dc.basHP = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.basAK = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.maxHP = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.maxAK = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.maxLV = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.lmtHP = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.lmtAK = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.lmtLV = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.hloMaxLV = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.hloLmtLV = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.hloEX = br.readLine().replaceAll("<.*?>","").replace("\t", "");
					dc.sklName = br.readLine().replaceAll("<.*?>","").replace("\t", "").replace("&amp;#10;", "").replace("&amp;", "/");;
					br.readLine();
					
					dc.isSync = "2";
					
					list.add(dc);
				}
			}		
			br.close();
		}
	//Create Xml File
		Document d = DocumentHelper.createDocument();	
		r = d.addElement("DataBase").addElement("maCard");
		
		for(DcDataCard dc:list)
		{
			Element e = r.addElement("Card");
			
			e.addElement("CardID").setText(dc.cID);
			e.addElement("Name").setText(dc.name);
			e.addElement("Sex").setText(dc.sex);
			e.addElement("Country").setText(dc.country);
			e.addElement("Desc").setText(dc.desc==null?"None":dc.desc);
			e.addElement("Illustrator").setText(dc.illustrator);
			e.addElement("Star").setText(dc.star);
			e.addElement("FormID").setText(dc.frmID==null?"None":dc.frmID);
			e.addElement("Version").setText(dc.version==null?"None":dc.version);
			e.addElement("BasImage").setText(dc.i1ID==null?"None":dc.i1ID);
			e.addElement("MaxImage").setText(dc.i2ID==null?"None":dc.i2ID);
			e.addElement("CompoundTarget").setText(dc.cpdTarget==null?"None":dc.cpdTarget);
			e.addElement("CompoundResult").setText(dc.cpdResult==null?"None":dc.cpdResult);
			e.addElement("CompoundPrices").setText(dc.cpdPrices==null?"None":dc.cpdPrices);
			e.addElement("SalePrice").setText(dc.salePrice==null?"None":dc.salePrice);
			e.addElement("SkillName").setText(dc.sklName==null?"None":dc.sklName);
			e.addElement("SkillKana").setText(dc.sklKana==null?"None":dc.sklKana);
			e.addElement("SkillDesc").setText(dc.sklDesc==null?"None":dc.sklDesc);
			e.addElement("SkillVMin").setText(dc.sklVMin==null?"None":dc.sklVMin);
			e.addElement("SkillVMax").setText(dc.sklVMax==null?"None":dc.sklVMax);
			e.addElement("SkillPesc").setText(dc.sklPesc==null?"None":dc.sklPesc);
			e.addElement("SkillType").setText(dc.sklType.equals("")?"None":dc.sklType);
			e.addElement("SkillID").setText(dc.sklID==null?"None":dc.sklID);
			e.addElement("GrowType").setText(dc.grwType==null?"None":dc.grwType);
			e.addElement("GrowName").setText(dc.grwName==null?"None":dc.grwName);
			e.addElement("GrowDesc").setText(dc.grwDesc);
			e.addElement("Cost").setText(dc.cost);
			e.addElement("BasicHP").setText(dc.basHP);
			e.addElement("BasicAK").setText(dc.basAK);
			e.addElement("MaxumHP").setText(dc.maxHP);
			e.addElement("MaxumAK").setText(dc.maxAK);
			e.addElement("LimitHP").setText(dc.lmtHP==null?"None":dc.lmtHP);
			e.addElement("LimitAK").setText(dc.lmtAK==null?"None":dc.lmtAK);
			e.addElement("MaxumLV").setText(dc.maxLV);
			e.addElement("LimitLV").setText(dc.lmtLV==null?"None":dc.lmtLV);
			e.addElement("HoloMaxumLV").setText(dc.hloMaxLV==null?"None":dc.hloMaxLV);
			e.addElement("HoloLimitLV").setText(dc.hloLmtLV==null?"None":dc.hloLmtLV);
			e.addElement("HoloEX").setText(dc.hloEX==null?"None":dc.hloEX);
			e.addElement("Snyc").setText(dc.isSync);
		}
		
		mdcFile.createNewFile();

		OutputFormat ofm = OutputFormat.createPrettyPrint();
		ofm.setEncoding("UTF-8"); //设置XML文档的编码类型
		ofm.setSuppressDeclaration(false);
		ofm.setIndent(true); //设置是否缩进
		ofm.setIndent("	"); //以空格方式实现缩进
		ofm.setNewlines(true); //设置是否换行
		
		XMLWriter output = new XMLWriter(new FileOutputStream(mdcFile), ofm);
		output.write(d);
        output.close();
		
		return list;
	}

	public static List<DcDataCard> anlCard(int version) throws Exception
	{
	    Element e = xreader.read(new File("./wrk/dat/ctg/card-" + version + ".xml")).getRootElement().element("maCard");
	    List<DcDataCard> list = new ArrayList<>();
	    
	    for(Iterator<?> i = e.elementIterator("Card"); i.hasNext();)
    	{
	    	DcDataCard dc = new DcDataCard();
	    	
	    	Element e2 = (Element) i.next();
	    	
	    	dc.cID = e2.element("CardID").getStringValue();
			dc.name = e2.element("Name").getStringValue();
			dc.sex = e2.element("Sex").getStringValue();
			dc.country = e2.element("Country").getStringValue();
			dc.desc = e2.element("Desc").getStringValue();
			dc.illustrator = e2.element("Illustrator").getStringValue();
			dc.star = e2.element("Star").getStringValue();
			dc.frmID = e2.element("FormID").getStringValue();
			dc.version = e2.element("Version").getStringValue();
			dc.i1ID = e2.element("BasImage").getStringValue();
			dc.i2ID = e2.element("MaxImage").getStringValue();
			dc.cpdTarget = e2.element("CompoundTarget").getStringValue();
			dc.cpdResult = e2.element("CompoundResult").getStringValue();
			dc.cpdPrices = e2.element("CompoundPrices").getStringValue();
			dc.salePrice = e2.element("SalePrice").getStringValue();
			dc.sklName = e2.element("SkillName").getStringValue();
			dc.sklKana = e2.element("SkillKana").getStringValue();
			dc.sklDesc = e2.element("SkillDesc").getStringValue();
			dc.sklVMin = e2.element("SkillVMin").getStringValue();
			dc.sklVMax = e2.element("SkillVMax").getStringValue();
			dc.sklPesc = e2.element("SkillPesc").getStringValue();
			dc.sklType = e2.element("SkillType").getStringValue();
			dc.sklID = e2.element("SkillID").getStringValue();
			dc.grwType = e2.element("GrowType").getStringValue();
			dc.grwName = e2.element("GrowName").getStringValue();
			dc.grwDesc = e2.element("GrowDesc").getStringValue();
			dc.cost = e2.element("Cost").getStringValue();
			dc.basHP = e2.element("BasicHP").getStringValue();
			dc.basAK = e2.element("BasicAK").getStringValue();
			dc.maxHP = e2.element("MaxumHP").getStringValue();
			dc.maxAK = e2.element("MaxumAK").getStringValue();
			dc.lmtHP = e2.element("LimitHP").getStringValue();
			dc.lmtAK = e2.element("LimitAK").getStringValue();
			dc.maxLV = e2.element("MaxumLV").getStringValue();
			dc.lmtLV = e2.element("LimitLV").getStringValue();
			dc.hloMaxLV = e2.element("HoloMaxumLV").getStringValue();
			dc.hloLmtLV = e2.element("HoloLimitLV").getStringValue();
			dc.hloEX = e2.element("HoloEX").getStringValue();
			dc.isSync = e2.element("Snyc").getStringValue();
	    	
	    	list.add(dc);
    	}
	    
		return list;
	}

}
