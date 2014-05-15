package danor.matookit.functions;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.dom4j.Element;

import danor.matookit.natures.NRevision;
import danor.matookit.utils.*;

public class FResource
{
	private final FAction action;
	
	private final File revFile;
	private final File revFolderBos;
	private final File revFolderCrd;
	
	private final NRevision revClient = new NRevision();
	private final NRevision revServer;

	private final String rUrl;
	
	public FResource(FAction action, File pakFile) throws Exception
	{
		this.action = action;
		
		if(pakFile != null)
		{
//			UConvert.decryptAES(null, pakFile, UUtil.Key(action.server().fileArb(), "Cipher", "Pak")[0].getBytes("utf-8"));
//			UConvert.xmlFormat(pakFile);
			
			revServer = FGain.GainRevision(pakFile);
		}
		else
			revServer = action.rev();
		
		revFile = new File(action.server().dirDat(), "rev.xml");
		revFolderBos = new File(action.server().dirRes(), "bos");
		revFolderCrd = new File(action.server().dirRes(), "crd");
		
		rUrl = UUtil.Key(action.server().fileArb(), "Server", action.server().res())[0];
		
		if(!revFile.exists())
		{
			revFile.getParentFile().mkdirs();
			
			for(Field f:revClient.getClass().getDeclaredFields())
			{
				f.setAccessible(true);
				f.set(revClient,"0");
			}
			
			UXml xml = new UXml(revFile, "Revision");
			xml.addTo("Newest");
			for(Field f:revClient.getClass().getDeclaredFields())
	        {
	        	f.setAccessible(true);
	        	xml.add(f.getName(), (String) f.get(revClient));
	        }
			
	        xml.save();
		}
		else
		{
			UXml xml = new UXml(revFile);
			
			for(Field f:revClient.getClass().getDeclaredFields())
			{
				f.setAccessible(true);
				f.set(revClient, xml.value("Newest>"+f.getName()));
			}
		}
	}

	private File gainData(String kind, String revService, String revClient, String rename) throws Exception
	{
		File ctgFile = new File(action.server().dirDat(), "ctg");
		if(!ctgFile.exists()) ctgFile.mkdirs();
		File ctgFileRename = new File(ctgFile, rename+"-"+revService+".xml");
		
		if(!ctgFileRename.exists())
		{
			File pakFile = action.Update(kind, revClient);
			pakFile.renameTo(ctgFileRename);
		}
		
		return ctgFileRename;
	}
//rev+res
	public void gainCrd(int from, int to) throws Exception
	{
		if(revClient.revCrd().equals("0"))
			revFolderCrd.mkdirs();
		
		if(Integer.parseInt(revServer.revCrd()) > Integer.parseInt(revClient.revCrd()))
		{
			File pakFile = gainData("card", revServer.revCrd(), revClient.revCrd(), "crd");
			
			List<NCrd> list = readCrd(pakFile);
			
			if(!FGain.GainImagedl(pakFile, "card").equals("") && from==0)
			{
				if(action.server()==FServer.MY1)
				{
					File[] revFolderCrdNew = new File[]{ new File(revFolderCrd, "kor/_new/"),
							new File(revFolderCrd, "twn/_new/"), new File(revFolderCrd, "eng/_new/")};
					
					for(File f:revFolderCrdNew)
					{
						if(f.exists())
							for(File ff:f.listFiles())
							{
								File oldFile = new File(f.getParentFile(), ff.getName());
								backup(oldFile);
								f.renameTo(oldFile);
							}
						else
							f.mkdirs();
					}
				}
				else
				{
					File revFolderCrdNew = new File(revFolderCrd, "_new");
					
					if(revFolderCrdNew.exists())
						for(File f:revFolderCrdNew.listFiles())
						{
							File oldFile = new File(revFolderCrd, f.getName());
							backup(oldFile);
							f.renameTo(oldFile);
						}
					else
						revFolderCrdNew.mkdirs();
				}
		}
			
			for(String i:FGain.GainImagedl(pakFile, "card").split(","))
				for(NCrd c:list)
					if(i.equals(c.idCard) && Integer.parseInt(i) >= from &&
					((to==0)?true:Integer.parseInt(i)<=to))
						if(action.server()==FServer.MY1)
							doadCrdMY(c);
						else
							doadCrd(c);
			
			if(to==0)
				save("revCrd", revServer.revCrd());
		}
	}
	private List<NCrd> readCrd(File pakFile) throws Exception
	{
		UXml xml = new UXml(pakFile);
		
		List<NCrd> list = new ArrayList<NCrd>();
		List<?> lstCrd = xml.list("body>master_data>master_card_data>card");
		
		for(Object c:lstCrd)
	    {
	    	Element e = (Element) c;
	    	NCrd dc = new NCrd();
			
			dc.idCard = e.element("master_card_id").getStringValue();
			dc.idImageNorrmal = e.element("image1_id").getStringValue();
			dc.idImageArousal(e.element("image2_id").getStringValue());
			dc.version = e.element("card_version").getStringValue();
			
			list.add(dc);
		}
		return list;
	}
	private void doadCrdMY(NCrd card) throws Exception
	{
		File[] revFolderCrdNew = new File[]{ new File(revFolderCrd, "kor/_new/"+card.idCard),
				new File(revFolderCrd, "twn/_new/"+card.idCard), new File(revFolderCrd, "eng/_new/"+card.idCard)};
		
		String[][] ss = new String[][] {{"Kor","contents"},{"Twn","contents_tw"},{"Eng","contents_en"}};
	//创建参数
		UOption option = new UOption().put("rqtCookie", false).put("typMethod", false).put("server", action.server().toString())
				.put("cookie", (String)null).put("param", (String)null);
		
		String[][] pp = new String[4][2];
		for(int ii:new int[]{0,1,3,4}) pp[ii>2?ii-1:ii] = UUtil.Key(action.server().fileArb(), "Property", ii+"");
		option.put("property", pp);

		for(int i=0;i<3;i++)
		{
		//大图-普卡-基本
			ULog.log("Doad-Crd-"+card.idCard+"-Ful-Nor-Bac-"+ss[i][0]);
			doadCrd(option, revFolderCrdNew[i].getPath()+"/"+ss[i][0].toLowerCase()+"/"+card.idCard+"_"+ss[i][0]+"FulNorBac.png",
					rUrl.replace("contents", ss[i][1])+"card_full/full_thumbnail_chara_"+card.idImageNorrmal+".dat?cyt=1");
		//大图-普卡-满级
			ULog.log("Doad-Crd-"+card.idCard+"-Ful-Nor-Max-"+ss[i][0]);
			doadCrd(option, revFolderCrdNew[i].getPath()+"/"+ss[i][0].toLowerCase()+"/"+card.idCard+"_"+ss[i][0]+"FulNorMax.png",
					rUrl.replace("contents",ss[i][1])+"card_full_max/full_thumbnail_chara_"+card.idImageArousal+".dat?cyt=1");
		//大图-闪卡-基本
			ULog.log("Doad-Crd-"+card.idCard+"-Ful-Hlo-Bac-"+ss[i][0]);
			doadCrd(option, revFolderCrdNew[i].getPath()+"/"+ss[i][0].toLowerCase()+"/"+card.idCard+"_"+ss[i][0]+"FulHloBac.png",
					rUrl.replace("contents",ss[i][1])+"card_full_h/full_thumbnail_chara_"+card.idImageNorrmal+"_horo.dat?cyt=1");
		//大图-闪卡-满级
			ULog.log("Doad-Crd-"+card.idCard+"-Ful-Hlo-Max-"+ss[i][0]);
			doadCrd(option, revFolderCrdNew[i].getPath()+"/"+ss[i][0].toLowerCase()+"/"+card.idCard+"_"+ss[i][0]+"FulHloMax.png",
					rUrl.replace("contents",ss[i][1])+"card_full_h_max/full_thumbnail_chara_"+card.idImageArousal+"_horo.dat?cyt=1");
		//头像-小图-立绘
			ULog.log("Doad-Crd-"+card.idCard+"-Pack-"+ss[i][0]);
			try {
				FPack pack = new FPack(rUrl.replace("contents", ss[i][1])+"2/card/card"+card.idCard+"_(zkd).pack?cyt=1",
						revFolderCrdNew[i].getPath(), "", action.server());
				pack.downloadPack();
			
				new File(revFolderCrdNew[i], "thumbnail_chara_"+card.idImageNorrmal+".png")
				.renameTo(new File(revFolderCrdNew[i], "/"+ss[i][0].toLowerCase()+"/"+card.idCard+"_"+ss[i][0]+"TumNorBac.png"));
				new File(revFolderCrdNew[i], "thumbnail_chara_"+card.idImageArousal+".png")
				.renameTo(new File(revFolderCrdNew[i], "/"+ss[i][0].toLowerCase()+"/"+card.idCard+"_"+ss[i][0]+"TumNorMax.png"));
				new File(revFolderCrdNew[i], "thumbnail_chara_"+card.idImageNorrmal+"_horo.png")
				.renameTo(new File(revFolderCrdNew[i], "/"+ss[i][0].toLowerCase()+"/"+card.idCard+"_"+ss[i][0]+"TumHloBac.png"));
				new File(revFolderCrdNew[i], "thumbnail_chara_"+card.idImageArousal+"_horo.png")
				.renameTo(new File(revFolderCrdNew[i], "/"+ss[i][0].toLowerCase()+"/"+card.idCard+"_"+ss[i][0]+"TumHloMax.png"));
				new File(revFolderCrdNew[i], "face_"+card.idImageNorrmal+".png")
				.renameTo(new File(revFolderCrdNew[i], "/"+ss[i][0].toLowerCase()+"/"+card.idCard+"_"+ss[i][0]+"FacBac.png"));
				new File(revFolderCrdNew[i], "face_"+card.idImageArousal+".png")
				.renameTo(new File(revFolderCrdNew[i], "/"+ss[i][0].toLowerCase()+"/"+card.idCard+"_"+ss[i][0]+"FacMax.png"));
				new File(revFolderCrdNew[i], "adv_chara"+card.idImageNorrmal+".png")
				.renameTo(new File(revFolderCrdNew[i], "/"+ss[i][0].toLowerCase()+"/"+card.idCard+"_"+ss[i][0]+"AdvBac.png"));
				new File(revFolderCrdNew[i], "adv_chara"+card.idImageArousal+".png")
				.renameTo(new File(revFolderCrdNew[i], "/"+ss[i][0].toLowerCase()+"/"+card.idCard+"_"+ss[i][0]+"AdvMax.png"));
			} catch(Exception e) { ULog.log(e.toString()); e.printStackTrace(); };
		}
	}
	private void doadCrd(NCrd card) throws Exception
	{
		File revFolderCrdNew = new File(revFolderCrd, "_new/"+card.idCard);
		revFolderCrdNew.mkdirs();
		
		UOption option = new UOption().put("rqtCookie", false).put("typMethod", false).put("server", action.server().toString())
				.put("cookie", (String)null).put("param", (String)null);
		
		String[][] pp = new String[4][2];
		for(int ii:new int[]{0,1,3,4}) pp[ii>2?ii-1:ii] = UUtil.Key(action.server().fileArb(), "Property", ii+"");
		option.put("property", pp);

		UConnect connect;
		
		ULog.log("Doad-Crd-"+card.idCard+"-Ful-Nor-Bac");
		option.put("path", revFolderCrdNew.getPath()+"/" + card.idCard + "_FulNorBac.png")
		.put("url", rUrl+"card_full/full_thumbnail_chara_"+card.idImageNorrmal+"?cyt=1");
		
		try {
			connect = new UConnect(option);
			UConvert.decryptAES(null, connect.result, UUtil.Key(action.server().fileArb(), "Cipher", "Res")[0].getBytes("utf-8"));
		} catch(Exception e) { ULog.log(e.toString()); e.printStackTrace(); };
		
		ULog.log("Doad-Crd-"+card.idCard+"-Ful-Nor-Max");
		option.put("path", revFolderCrdNew.getPath()+"/" + card.idCard + "_FulNorMax.png")
		.put("url", rUrl+"card_full_max/full_thumbnail_chara_"+card.idImageArousal+"?cyt=1");
		
		try {
			connect = new UConnect(option);
			UConvert.decryptAES(null, connect.result, UUtil.Key(action.server().fileArb(), "Cipher", "Res")[0].getBytes("utf-8"));
		} catch(Exception e) { ULog.log(e.toString()); e.printStackTrace(); };
		
		ULog.log("Doad-Crd-"+card.idCard+"-Ful-Hlo-Nor");
		option.put("path", revFolderCrdNew.getPath()+"/" + card.idCard + "_FulHloBac.png")
		.put("url", rUrl+"card_full_h/full_thumbnail_chara_"+card.idImageNorrmal+"_horo?cyt=1");
		
		try {
			connect = new UConnect(option);
			UConvert.decryptAES(null, connect.result, UUtil.Key(action.server().fileArb(), "Cipher", "Res")[0].getBytes("utf-8"));
		} catch(Exception e) { ULog.log(e.toString()); e.printStackTrace(); };
	//大图-闪卡-满级
		ULog.log("Doad-Crd-"+card.idCard+"-Ful-Hlo-Max");
		option.put("path", revFolderCrdNew.getPath()+"/" + card.idCard + "_FulHloMax.png")
		.put("url", rUrl+"card_full_h_max/full_thumbnail_chara_"+card.idImageArousal+"_horo?cyt=1");
		
		try {
			connect = new UConnect(option);
			UConvert.decryptAES(null, connect.result, UUtil.Key(action.server().fileArb(), "Cipher", "Res")[0].getBytes("utf-8"));
		} catch(Exception e) { ULog.log(e.toString()); e.printStackTrace(); };
	//头像-小图-立绘	
		try {
			ULog.log("Doad-Crd-"+card.idCard+"-Pack");
			FPack pack = new FPack(rUrl+card.version+"/card/card"+card.idCard+"_(zkd).pack?cyt=1", revFolderCrdNew.getPath(), "", action.server());
			pack.downloadPack();
		
			new File(revFolderCrdNew, "thumbnail_chara_"+card.idImageNorrmal+".png")
			.renameTo(new File(revFolderCrdNew, card.idCard+"_TumNorBac.png"));
			new File(revFolderCrdNew, "thumbnail_chara_"+card.idImageArousal+".png")
			.renameTo(new File(revFolderCrdNew, card.idCard+"_TumNorMax.png"));
			new File(revFolderCrdNew, "thumbnail_chara_"+card.idImageNorrmal+"_horo.png")
			.renameTo(new File(revFolderCrdNew, card.idCard+"_TumHloBac.png"));
			new File(revFolderCrdNew, "thumbnail_chara_"+card.idImageArousal+"_horo.png")
			.renameTo(new File(revFolderCrdNew, card.idCard+"_TumHloMax.png"));
			new File(revFolderCrdNew, "face_"+card.idImageNorrmal+".png")
			.renameTo(new File(revFolderCrdNew, card.idCard+"_FacBac.png"));
			new File(revFolderCrdNew, "face_"+card.idImageArousal+".png")
			.renameTo(new File(revFolderCrdNew, card.idCard+"_FacMax.png"));
			new File(revFolderCrdNew, "adv_chara"+card.idImageNorrmal+".png")
			.renameTo(new File(revFolderCrdNew, card.idCard+"_AdvBac.png"));
			new File(revFolderCrdNew, "adv_chara"+card.idImageArousal+".png")
			.renameTo(new File(revFolderCrdNew, card.idCard+"_AdvMax.png"));
		} catch(Exception e) { ULog.log(e.toString()); e.printStackTrace(); };
	}
	private void doadCrd(UOption option, String path, String url) throws Exception
	{
		option.put("path", path).put("url", url);
		try {
			UConnect connect = new UConnect(option);
			UConvert.decryptAES(null, connect.result, UUtil.Key(action.server().fileArb(), "Cipher", "Res")[0].getBytes("utf-8"));
		} catch(Exception e) { ULog.log(e.toString()); e.printStackTrace(); };
	}
	
	
	public void gainBos(int form) throws Exception
	{
		if(revClient.revBos().equals("0"))
			revFolderBos.mkdirs();
		
		if(Integer.parseInt(revServer.revBos()) > Integer.parseInt(revClient.revBos()))
		{
			File pakFile = gainData("boss", revServer.revBos(), revClient.revBos(), "bos");
			
			List<NBos> list = readBos(pakFile);
			
			if(!FGain.GainImagedl(pakFile, "boss").equals(""))
			{
				File revFolderBosNew = new File(revFolderBos, "_new");
				
				if(revFolderBosNew.exists())
					for(File f:revFolderBosNew.listFiles())
					{
						File oldFile = new File(revFolderBos, f.getName());
						backup(oldFile);
						f.renameTo(oldFile);
					}
				
				for(String i:FGain.GainImagedl(pakFile, "boss").split(","))
					for(NBos b:list)
						if(i.equals(b.idBoss) && Integer.parseInt(i) >= form)
							doadBos(b);
			}

			save("revBos", revServer.revBos());
		}
	}
	private List<NBos> readBos(File pakFile) throws Exception
	{
		UXml xml = new UXml(pakFile);
		
		List<NBos> list = new ArrayList<NBos>();
		List<?> lstBos = xml.list("body>master_data>master_boss_data>boss");
		
		for(Object c:lstBos)
	    {
	    	Element e = (Element) c;
	    	NBos dc = new NBos();
			
			dc.idBoss = e.element("master_boss_id").getStringValue();
			dc.idImageBos = e.element("card_image_id").getStringValue();
			dc.version = e.element("boss_version").getStringValue();
			
			list.add(dc);
		}
		return list;
	}
	private void doadBos(NBos boss) throws Exception
	{
		File revFolderBosNew = new File(revFolderBos, "_new");
		revFolderBosNew.mkdirs();
		
		ULog.log("Doad-Bos-"+boss.idBoss+"-Pack");
		FPack pack = new FPack(rUrl+boss.version+"/boss/boss"+boss.idImageBos+"_(zkd).pack?cyt=1", revFolderBosNew.getPath(), "", action.server());
		pack.downloadPack();
		
		new File(revFolderBosNew.getPath()+"/boss_full"+boss.idImageBos+".png").renameTo(new File(revFolderBosNew.getPath()+"/"+boss.idBoss+"_"+boss.idImageBos+".png"));
	}

	public void gainItm() throws Exception
	{
		File revFolderItm = new File(action.server().dirRes(), "itm");
		if(revClient.revItm().equals("0"))
			revFolderItm.mkdirs();
		
		if(Integer.parseInt(revServer.revItm()) > Integer.parseInt(revClient.revItm()))
		{
			File pakFile = gainData("item", revServer.revItm(), revClient.revItm(), "itm");
			
			if(!FGain.GainImagedl(pakFile, "item").equals(""))
			{
				File revFolderItmNew = new File(revFolderItm, "_new");
				revFolderItmNew.mkdirs();
				
				for(File f:revFolderItmNew.listFiles())
				{
					File oldFile = new File(revFolderItm, f.getName());
					backup(oldFile);
					f.renameTo(oldFile);
				}
				
				ULog.log("Doad-Itm-Pack");
				FPack pack = new FPack(rUrl+revServer.revItm()+"/item/item_0_(zkd).pack?cyt=1", revFolderItmNew.getPath(), "", action.server());
				pack.downloadPack();

				for(File f:revFolderItmNew.listFiles())
					f.renameTo(new File(revFolderItmNew.getParent()+"/"+f.getName().replace("item_", "")));
			}

			save("revItm", revServer.revItm());
		}
	}

	public void gainGac() throws Exception
	{
		if(Integer.parseInt(revServer.revGac()) > Integer.parseInt(revClient.revGac()))
		{
			ULog.log("Doad-Gac");
			gainData("gacha", revServer.revGac(), revClient.revGac(), "gac");
			
			save("revGac", revServer.revGac());
		}
		
		File revFolderGac = new File(action.server().dirRes(), "gac");
		
		if(revClient.resGac().equals("0"))
			revFolderGac.mkdirs();
		
		File revFolderGacNew = new File(revFolderGac, "_new");
		revFolderGacNew.mkdirs();
		
		if(Integer.parseInt(revServer.resGac()) > Integer.parseInt(revClient.resGac()))
		{
			for(File f:revFolderGacNew.listFiles())
			{
				File oldFile = new File(revFolderGac, f.getName());
				backup(oldFile);
				f.renameTo(oldFile);
			}
			
			revFolderGacNew = new File(revFolderGac, "_new/"+revServer.resGac());
			revFolderGacNew.mkdirs();
			
			ULog.log("Doad-Gac-Pack");
			FPack pack = new FPack(rUrl+revServer.resGac()+"/gacha/gacha0_(zkd).pack?cyt=1", revFolderGacNew.getPath(), "", action.server());
			pack.downloadPack();

			save("resGac", revServer.resGac());
		}
	}
	public void gainBan() throws Exception
	{
		if(!action.server().equals(FServer.KR1) && Integer.parseInt(revServer.revBan()) > Integer.parseInt(revClient.revBan()))
		{
			ULog.log("Doad-Ban");
			gainData("eventbanner", revServer.revBan(), revClient.revBan(), "ban");
			
			save("revBan", revServer.revBan());
		}
		
		File revFolderBan = new File(action.server().dirRes(), "ban");
		
		if(revClient.resBan().equals("0"))
			revFolderBan.mkdirs();
		
		File revFolderBanNew = new File(revFolderBan, "_new");
		revFolderBanNew.mkdirs();
		if(Integer.parseInt(revServer.resBan()) > Integer.parseInt(revClient.resBan()))
		{
			for(File f:revFolderBanNew.listFiles())
			{
				File oldFile = new File(revFolderBan, f.getName());
				backup(oldFile);
				f.renameTo(oldFile);
			}
			
			revFolderBanNew = new File(revFolderBan, "_new/"+revServer.resBan());
			revFolderBanNew.mkdirs();
			
			ULog.log("Doad-Ban-Pack");
			FPack pack = new FPack(rUrl+revServer.resBan()+"/eventbanner/eventbanner0_(zkd).pack?cyt=1", revFolderBanNew.getPath(), "", action.server());
			pack.downloadPack();
			
			save("resBan", revServer.resBan());
		}
	}
	public void gainPvl() throws Exception
	{
		if(Integer.parseInt(revServer.revPvl()) > Integer.parseInt(revClient.revPvl()))
		{
			ULog.log("Doad-Pvl");
			gainData("privilege", revServer.revPvl(), revClient.revPvl(), "pvl");
			
			save("revPvl", revServer.revPvl());
		}
		
		File revFolderPvl = new File(action.server().dirRes(), "pvl");
		
		if(revClient.resPvl().equals("0"))
			revFolderPvl.mkdirs();
		
		File revFolderPvlNew = new File(revFolderPvl, "_new");
		revFolderPvlNew.mkdirs();

		if(Integer.parseInt(revServer.resPvl()) > Integer.parseInt(revClient.resPvl()))
		{
			for(File f:revFolderPvlNew.listFiles())
			{
				File oldFile = new File(revFolderPvl, f.getName());
				backup(oldFile);
				f.renameTo(oldFile);
			}
			
			revFolderPvlNew = new File(revFolderPvl, "_new/"+revServer.resPvl());
			revFolderPvlNew.mkdirs();
			
			ULog.log("Doad-Pvl-Pack");
			FPack pack = new FPack(rUrl+revServer.resPvl()+"/privilege/privilege0_(zkd).pack?cyt=1", revFolderPvlNew.getPath(), "", action.server());
			pack.downloadPack();
			
			save("resPvl", revServer.resPvl());
		}
	}
//只有rev
	public void gainCtg() throws Exception
	{
		if(Integer.parseInt(revServer.revCtg()) > Integer.parseInt(revClient.revCtg()))
		{
			ULog.log("Doad-Ctg");
			gainData("card_category", revServer.revCtg(), revClient.revCtg(), "ctg");
			
			save("revCtg", revServer.revCtg());
		}
	}
	public void gainCmb() throws Exception
	{
		if(Integer.parseInt(revServer.revCmb()) > Integer.parseInt(revClient.revCmb()))
		{
			ULog.log("Doad-Cmb");
			gainData("combo", revServer.revCmb(), revClient.revCmb(), "cmb");
			
			save("revCmb", revServer.revCmb());
		}
	}
//只有res
	public void gainRes() throws Exception
	{
		File revFolderRes = new File(action.server().dirRes(), "res");
		
		if(revClient.resRes().equals("0"))
			revFolderRes.mkdirs();
		
		File revFolderResNew = new File(revFolderRes, "_new");
		revFolderResNew.mkdirs();
		if(Integer.parseInt(revServer.resRes()) > Integer.parseInt(revClient.resRes()))
		{
			for(File f:revFolderResNew.listFiles())
			{
				File oldFile = new File(revFolderRes, f.getName());
				backup(oldFile);
				f.renameTo(oldFile);
			}
			
			revFolderResNew = new File(revFolderRes, "_new/"+revServer.resRes());
			revFolderResNew.mkdirs();
			
			ULog.log("Doad-Res-Pack");
			FPack pack = new FPack(rUrl+revServer.resRes()+"/res/res0_(zkd).pack?cyt=1", revFolderResNew.getPath(), "", action.server());
			pack.downloadPack();
			
			save("resRes", revServer.resRes());
		}
	}
	public void gainSou() throws Exception
	{
		File revFolderSou = new File(action.server().dirRes(), "sou");
		
		if(revClient.resSou().equals("0"))
			revFolderSou.mkdirs();
		
		File revFolderSouNew = new File(revFolderSou, "_new");
		revFolderSouNew.mkdirs();
		if(Integer.parseInt(revServer.resSou()) > Integer.parseInt(revClient.resSou()))
		{
			for(File f:revFolderSouNew.listFiles())
			{
				File oldFile = new File(revFolderSou, f.getName());
				backup(oldFile);
				f.renameTo(oldFile);
			}
			
			revFolderSouNew = new File(revFolderSou, "_new/"+revServer.resSou());
			revFolderSouNew.mkdirs();
			
			
			
			UUtil.p("Dowanload-Sou-Pack");
			FPack pack = new FPack(rUrl+revServer.resSou()+"/sound/sound0_(zkd).pack?cyt=1", revFolderSouNew.getPath(), "", action.server());
			pack.downloadPack();
			
			save("resSou", revServer.resSou());
		}
	}
	public void gainAdv() throws Exception
	{
		File revFolderAdv = new File(action.server().dirRes(), "adv");
		
		if(revClient.resAdv().equals("0"))
			revFolderAdv.mkdirs();
		
		File revFolderAdvNew = new File(revFolderAdv, "_new");
		revFolderAdvNew.mkdirs();
		if(Integer.parseInt(revServer.resAdv()) > Integer.parseInt(revClient.resAdv()))
		{
			if(!new File(revFolderAdvNew, "itr.log").exists())
				for(File f:revFolderAdvNew.listFiles())
				{
					File oldFile = new File(revFolderAdv, f.getName());
					backup(oldFile);
					f.renameTo(oldFile);
				}
			
			revFolderAdvNew = new File(revFolderAdv, "_new/"+revServer.resAdv());
			revFolderAdvNew.mkdirs();
			
			ULog.log("Doad-Adv-Pack");
			FPack pack = new FPack(rUrl+revServer.resAdv()+"/advbg/advbg0_(zkd).pack?cyt=1", revFolderAdvNew.getPath(), "", action.server());
			pack.downloadPack();
			
			save("resAdv", revServer.resAdv());
		}
	}
	public void gainCmp() throws Exception
	{
		File revFolderCmp = new File(action.server().dirRes(), "cmp");
		
		if(revClient.resCmp().equals("0"))
			revFolderCmp.mkdirs();
		
		File revFolderCmpNew = new File(revFolderCmp, "_new");
		revFolderCmpNew.mkdirs();
		if(Integer.parseInt(revServer.resCmp()) > Integer.parseInt(revClient.resCmp()))
		{
			for(File f:revFolderCmpNew.listFiles())
			{
				File oldFile = new File(revFolderCmp, f.getName());
				backup(oldFile);
				f.renameTo(oldFile);
			}
			
			revFolderCmpNew = new File(revFolderCmp, "_new/"+revServer.resCmp());
			revFolderCmpNew.mkdirs();
			
			
			
			ULog.log("Doad-Cmp-Pack");
			FPack pack = new FPack(rUrl+revServer.resCmp()+"/cmpsheet/cmpsheet0_(zkd).pack?cyt=1", revFolderCmpNew.getPath(), "", action.server());
			pack.downloadPack();
			
			save("resCmp", revServer.resCmp());
		}
	}
//无版本号
	public void gainMbg() throws Exception
	{
		
		File revFolderMbg = new File(action.server().dirRes(), "mbg");
		if(revClient.resMbg().equals("0"))
			revFolderMbg.mkdirs();

		int[] levels = {1,5,10,20,30,40,50,60,70,80,90,100};

		if(!revServer.resMbg().equals(revClient.resMbg()))
		{
			File revFolderMbgNew = new File(revFolderMbg, "_new");
			revFolderMbgNew.mkdirs();
			
			for(File f:revFolderMbgNew.listFiles())
			{
				File oldFile = new File(revFolderMbg, f.getName());
				backup(oldFile);
				f.renameTo(oldFile);
			}
			
			revFolderMbgNew = new File(revFolderMbg, "_new/"+revServer.resMbg());
			revFolderMbgNew.mkdirs();
			
			for(int lv:levels)
			{
				ULog.log("Doad-Mbg-"+revServer.resMbg()+"-Lv-" + (lv<10?"0"+lv:lv));
				
				try {
					FPack pack = new FPack(rUrl+"2/mainbg/mainbg_"+lv+"_"+revServer.resMbg()+"_(zkd).pack?cyt=1", revFolderMbgNew.getPath(), "", action.server());
				
					for(File f:pack.downloadPack())
						if(f.getName().indexOf("rja") != -1)
							f.delete();
					
					String[][] typMainbg = {{"an","Ann"},{"mg","Non"},{"nn","Bnn"},{"nt","Mon"}};
					
					for(String[] t:typMainbg)
					{
						File picStr = new File(revFolderMbgNew, (lv<10?"0"+lv:lv)+"_"+t[1]+"Str.png");
						File picSur = new File(revFolderMbgNew, (lv<10?"0"+lv:lv)+"_"+t[1]+"Sur.png");
						File picFul = new File(revFolderMbgNew, (lv<10?"0"+lv:lv)+"_"+t[1]+"Ful.png");
						
						new File(revFolderMbgNew.getPath() + "/mainbg_"+t[0]+"_0.png").renameTo(picStr);
						new File(revFolderMbgNew.getPath() + "/mainbg_"+t[0]+"_1.png").renameTo(picSur);
						
						ULog.log("merg-Mbg-"+revServer.resMbg()+"-Lv-" + (lv<10?"0"+lv:lv));
						mrgMbg(picStr, picSur, picFul);
					}
				} catch(Exception e) { ULog.log(e.toString()); e.printStackTrace(); };
			}
		}
		
		save("resMbg", revServer.resMbg());
	}
	public static void mrgMbg(File picTop,File picBtm, File picDst) throws Exception
	{
		BufferedImage imgTop = ImageIO.read(picTop);
		BufferedImage imgBtm = ImageIO.read(picBtm);
		
		int wid = imgTop.getWidth();
		int heiTop = imgTop.getHeight()-16;
		int heiBtm = imgBtm.getHeight();
		
	//读取RGB
		int[] imgTopArray = imgTop.getRGB(0, 0, wid, heiTop, null, 0, wid);
		int[] imgBtmArray = imgBtm.getRGB(0, 0, wid, heiBtm, null, 0, wid);

	//生成新图片
		BufferedImage ImageNew = new BufferedImage(wid, heiTop + heiBtm, 2);
		ImageNew.setRGB(0, 0, wid, heiTop, imgTopArray, 0, wid);
		ImageNew.setRGB(0, heiTop, wid, heiBtm, imgBtmArray, 0, wid);

	//写图片
		ImageIO.write(ImageNew, "png", picDst);
	}
	
	
	public void diff() throws Exception
	{
		for(Field f:revClient.getClass().getDeclaredFields())
		{
			f.setAccessible(true);
			String rc = (String) f.get(revClient);
			
			Field fs = revClient.getClass().getDeclaredField(f.getName());
			fs.setAccessible(true);
			String rs = (String) fs.get(revServer);
			
			if(!rc.equals(rs))
				UUtil.pp("New-"+f.getName()+"-"+rs+"-Old-"+rc);
		}
	}
	private void save(String revName, String revNew) throws Exception
	{
		for(Field f:revClient.getClass().getDeclaredFields())
		{
			f.setAccessible(true);
			if(f.getName().equals(revName))
				f.set(revClient, revNew);
		}
		
		UXml xml = new UXml(revFile);
		
		ULog.log("Save-Rev-"+revName+"-"+revNew);
		xml.save("Newest>"+revName, revNew);
	}
	public static void backup(File file)
	{
		for(int i=1;;i++)
			if(!new File(file.getPath()+"_bak_"+i).exists())
			{
				file.renameTo(new File(file.getPath()+"_bak_"+i));
				break;
			}
	}
	
	class NCrd
	{
		private String idCard;
		private String idImageNorrmal;
		private String idImageArousal;
		private String version;
		
		public void idImageArousal(String idImageArousal)
		{
			if(idImageArousal == null || idImageArousal.equals(idImageNorrmal))
				this.idImageArousal = (Integer.parseInt(idImageNorrmal)+5000)+"";
			else
				this.idImageArousal = idImageArousal;
		}
	}
	class NBos
	{
		private String idBoss;
		private String idImageBos;
		private String version;
	}
}
