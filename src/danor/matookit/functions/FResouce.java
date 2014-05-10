package danor.matookit.functions;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import danor.matookit.natures.NRevision;
import danor.matookit.utils.*;

public class FResouce
{
	private final FAction action;
	
	private final File revFile = new File("./wrk.cn/dat/rev.xml");
	private final File revFolderBos = new File("./wrk.cn/res/bos");
	private final File revFolderCrd = new File("./wrk.cn/res/crd");
	
	private final NRevision revClient = new NRevision();
	private final NRevision revServer;
	
	public FResouce(FAction action) throws Exception
	{
		this.action = action;
		revServer = action.rev();
		
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
//rev+res
	public void gainCrd() throws Exception
	{
		if(revClient.revCrd().equals("0"))
			revFolderCrd.mkdirs();
		
		if(Integer.parseInt(revServer.revCrd()) > Integer.parseInt(revClient.revCrd()))
		{
			File pakFile = gainData("card", revServer.revCrd(), revClient.revCrd(), "crd");
			String content = new String(UUtil.Input(pakFile), "utf-8").replaceAll("&#10;", "|").replaceAll("&", "^");
			UUtil.Output(pakFile, content.getBytes("utf-8"), false);
			UConvert.xmlFormat(pakFile);
			
			List<NCrd> list = readCrd(pakFile);
			
			if(!FGain.GainImagedl(pakFile, "card").equals(""))
			{
				File revFolderCrdNew = new File(revFolderCrd, "_new");
				
				if(revFolderCrdNew.exists())
					for(File f:revFolderCrdNew.listFiles())
					{
						File oldFile = new File(revFolderCrd, f.getName());
						backup(oldFile);
						f.renameTo(oldFile);
					}
			}
			
			for(String i:FGain.GainImagedl(pakFile, "card").split(","))
				for(NCrd c:list)
					if(i.equals(c.idCard) && Integer.parseInt(i)>=161)
						downCrd(c);
			
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
			dc.idImageArousal = e.element("image2_id").getStringValue();
			dc.version = e.element("card_version").getStringValue();
			
			list.add(dc);
		}
		return list;
	}
	private void downCrd(NCrd card) throws Exception
	{
		File revFolderCrdNew = new File(revFolderCrd, "_new/"+card.idCard);
		revFolderCrdNew.mkdirs();
		
		String rUrl = UKey.Data("Server", "CN1")[0];
		
		UOption option;
		UConnect connect;
		try {
			UUtil.p("Dowanload-Card"+card.idCard+"-Ful-Nor-Bac");
			option = new UOption().put("rqtCookie", false).put("typMethod", false)
					.put("cookie", (String)null).put("url", rUrl+card.version+"/card_full/full_thumbnail_chara_"+(card.idImageNorrmal.equals("None")?card.idCard:card.idImageNorrmal)+"?cyt=1")
					.put("param", (String)null).put("path", revFolderCrdNew.getPath()+"/" + card.idCard + "_FulNorBac.png");
			connect = new UConnect(option);
			UConvert.decryptAES(null, connect.result, UKey.Data("CipherAES", "1")[0].getBytes("utf-8"));
		} catch(Exception e) { ULog.log(e.toString()); };
		
		
		try {
			UUtil.p("Dowanload-Card"+card.idCard+"-Ful-Nor-Max");
			option = new UOption().put("rqtCookie", false).put("typMethod", false)
					.put("cookie", (String)null).put("url", rUrl+card.version+"/card_full_max/full_thumbnail_chara_"+(card.idImageNorrmal.equals("None")?"5"+card.idCard:card.idImageArousal)+"?cyt=1")
					.put("param", (String)null).put("path", revFolderCrdNew.getPath()+"/" + card.idCard + "_FulNorMax.png");
			connect = new UConnect(option);
			UConvert.decryptAES(null, connect.result, UKey.Data("CipherAES", "1")[0].getBytes("utf-8"));
		} catch(Exception e) { ULog.log(e.toString()); };

		try {
			UUtil.p("Dowanload-Card"+card.idCard+"-Ful-Hlo-Nor");
			option = new UOption().put("rqtCookie", false).put("typMethod", false)
					.put("cookie", (String)null).put("url", rUrl+card.version+"/card_full_h/full_thumbnail_chara_"+(card.idImageNorrmal.equals("None")?card.idCard:card.idImageNorrmal)+"_horo?cyt=1")
					.put("param", (String)null).put("path", revFolderCrdNew.getPath()+"/" + card.idCard + "_FulHloBac.png");
			connect = new UConnect(option);
			UConvert.decryptAES(null, connect.result, UKey.Data("CipherAES", "1")[0].getBytes("utf-8"));
		} catch(Exception e) { ULog.log(e.toString()); };

		try {
			UUtil.p("Dowanload-Card"+card.idCard+"-Ful-Hlo-Max");
			option = new UOption().put("rqtCookie", false).put("typMethod", false)
					.put("cookie", (String)null).put("url", rUrl+card.version+"/card_full_h_max/full_thumbnail_chara_"+(card.idImageNorrmal.equals("None")?"5"+card.idCard:card.idImageArousal)+"_horo?cyt=1")
					.put("param", (String)null).put("path", revFolderCrdNew.getPath()+"/" + card.idCard + "_FulHloMax.png");
			connect = new UConnect(option);
			UConvert.decryptAES(null, connect.result, UKey.Data("CipherAES", "1")[0].getBytes("utf-8"));
		} catch(Exception e) { ULog.log(e.toString()); };
		
		try {
			UUtil.p("Dowanload-Card-Pack");
			FPack pack = new FPack(rUrl+card.version+"/card/card"+card.idCard+"_(zkd).pack?cyt=1", revFolderCrdNew.getPath(), "");
			pack.downloadPack();
		
			new File(revFolderCrdNew.getPath()+"/thumbnail_chara_"+(card.idImageNorrmal.equals("None")?card.idCard:card.idImageNorrmal)+".png").renameTo(new File(revFolderCrdNew.getPath()+"/" + card.idCard + "_TumNorBac.png"));
			new File(revFolderCrdNew.getPath()+"/thumbnail_chara_"+(card.idImageNorrmal.equals("None")?"5"+card.idCard:card.idImageArousal)+".png").renameTo(new File(revFolderCrdNew.getPath()+"/" + card.idCard + "_TumNorMax.png"));
			new File(revFolderCrdNew.getPath()+"/thumbnail_chara_"+(card.idImageNorrmal.equals("None")?card.idCard:card.idImageNorrmal)+"_horo.png").renameTo(new File(revFolderCrdNew.getPath()+"/" + card.idCard + "_TumHloBac.png"));
			new File(revFolderCrdNew.getPath()+"/thumbnail_chara_"+(card.idImageNorrmal.equals("None")?"5"+card.idCard:card.idImageArousal)+"_horo.png").renameTo(new File(revFolderCrdNew.getPath()+"/" + card.idCard + "_TumHloMax.png"));
			new File(revFolderCrdNew.getPath()+"/face_"+(card.idImageNorrmal.equals("None")?card.idCard:card.idImageNorrmal)+".png").renameTo(new File(revFolderCrdNew.getPath()+"/" + card.idCard + "_FacBac.png"));
			new File(revFolderCrdNew.getPath()+"/face_"+(card.idImageNorrmal.equals("None")?"5"+card.idCard:card.idImageArousal)+".png").renameTo(new File(revFolderCrdNew.getPath()+"/" + card.idCard + "_FacMax.png"));
			new File(revFolderCrdNew.getPath()+"/adv_chara"+(card.idImageNorrmal.equals("None")?card.idCard:card.idImageNorrmal)+".png").renameTo(new File(revFolderCrdNew.getPath()+"/" + card.idCard + "_AdvBac.png"));
			new File(revFolderCrdNew.getPath()+"/adv_chara"+(card.idImageNorrmal.equals("None")?"5"+card.idCard:card.idImageArousal)+".png").renameTo(new File(revFolderCrdNew.getPath()+"/" + card.idCard + "_AdvMax.png"));
		} catch(Exception e) { ULog.log(e.toString()); };
	}
	
	public void gainBos() throws Exception
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
						if(i.equals(b.idBoss))
							downBos(b);
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
	private void downBos(NBos boss) throws Exception
	{
		File revFolderBosNew = new File(revFolderBos, "_new");
		
		revFolderBosNew.mkdirs();
		
		String rUrl = UKey.Data("Server", "CN1")[0];
		
		UUtil.p("Dowanload-Boss-"+boss.idBoss+"-Pack");
		FPack pack = new FPack(rUrl+boss.version+"/boss/boss"+boss.idImageBos+"_(zkd).pack?cyt=1", revFolderBosNew.getPath(), "");
		pack.downloadPack();
		
		new File(revFolderBosNew.getPath()+"/boss_full"+boss.idImageBos+".png").renameTo(new File(revFolderBosNew.getPath()+"/"+boss.idBoss+"_"+boss.idImageBos+".png"));
	}

	public void gainItm() throws Exception
	{
		File revFolderItm = new File("./wrk.cn/res/itm");
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
				String rUrl = UKey.Data("Server", "CN1")[0];
				
				UUtil.p("Dowanload-Itm-Pack");
				FPack pack = new FPack(rUrl+revServer.revItm()+"/item/item_0_(zkd).pack?cyt=1", revFolderItmNew.getPath(), "");
				pack.downloadPack();

				for(File f:revFolderItmNew.listFiles())
					f.renameTo(new File(revFolderItmNew.getParent()+"/"+f.getName().replace("item_", "")));
			}

			save("revItm", revServer.revItm());
		}
	}
//只有rev
	public void gainCtg() throws Exception
	{
		if(Integer.parseInt(revServer.revCtg()) > Integer.parseInt(revClient.revCtg()))
		{
			gainData("card_category", revServer.revCtg(), revClient.revCtg(), "ctg");
			
			save("revCtg", revServer.revCtg());
		}
	}
	public void gainCmb() throws Exception
	{
		if(Integer.parseInt(revServer.revCmb()) > Integer.parseInt(revClient.revCmb()))
		{
			gainData("combo", revServer.revCmb(), revClient.revCmb(), "cmb");
			
			save("revCmb", revServer.revCmb());
		}
	}
//只有res
	public void gainRes() throws Exception
	{
		File revFolderRes = new File("./wrk.cn/res/res");
		
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
			
			String rUrl = UKey.Data("Server", "CN1")[0];
			
			UUtil.p("Dowanload-Res-Pack");
			FPack pack = new FPack(rUrl+revServer.resRes()+"/res/res0_(zkd).pack?cyt=1", revFolderResNew.getPath(), "");
			pack.downloadPack();
			
			save("resRes", revServer.resRes());
		}
	}
	public void gainSou() throws Exception
	{
		File revFolderSou = new File("./wrk.cn/res/sou");
		
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
			
			String rUrl = UKey.Data("Server", "CN1")[0];
			
			UUtil.p("Dowanload-Sou-Pack");
			FPack pack = new FPack(rUrl+revServer.resSou()+"/sound/sound0_(zkd).pack?cyt=1", revFolderSouNew.getPath(), "");
			pack.downloadPack();
			
			save("resSou", revServer.resSou());
		}
	}
	public void gainAdv() throws Exception
	{
		File revFolderAdv = new File("./wrk.cn/res/adv");
		
		if(revClient.resAdv().equals("0"))
			revFolderAdv.mkdirs();
		
		File revFolderAdvNew = new File(revFolderAdv, "_new");
		revFolderAdvNew.mkdirs();
		if(Integer.parseInt(revServer.resAdv()) > Integer.parseInt(revClient.resAdv()))
		{
			for(File f:revFolderAdvNew.listFiles())
			{
				File oldFile = new File(revFolderAdv, f.getName());
				backup(oldFile);
				f.renameTo(oldFile);
			}
			
			revFolderAdvNew = new File(revFolderAdv, "_new/"+revServer.resAdv());
			revFolderAdvNew.mkdirs();
			
			String rUrl = UKey.Data("Server", "CN1")[0];
			
			UUtil.p("Dowanload-Adv-Pack");
			FPack pack = new FPack(rUrl+revServer.resAdv()+"/advbg/advbg0_(zkd).pack?cyt=1", revFolderAdvNew.getPath(), "");
			pack.downloadPack();
			
			save("resAdv", revServer.resAdv());
		}
	}
	public void gainCmp() throws Exception
	{
		File revFolderCmp = new File("./wrk.cn/res/cmp");
		
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
			
			String rUrl = UKey.Data("Server", "CN1")[0];
			
			UUtil.p("Dowanload-Cmp-Pack");
			FPack pack = new FPack(rUrl+revServer.resCmp()+"/cmpsheet/cmpsheet0_(zkd).pack?cyt=1", revFolderCmpNew.getPath(), "");
			pack.downloadPack();
			
			save("resCmp", revServer.resCmp());
		}
	}
	public void gainGac() throws Exception
	{
		File revFolderGac = new File("./wrk.cn/res/gac");
		
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
			
			String rUrl = UKey.Data("Server", "CN1")[0];
			
			UUtil.p("Dowanload-Gac-Pack");
			FPack pack = new FPack(rUrl+revServer.resGac()+"/gacha/gacha0_(zkd).pack?cyt=1", revFolderGacNew.getPath(), "");
			pack.downloadPack();

			save("resGac", revServer.resGac());
		}
	}
	public void gainBan() throws Exception
	{
		File revFolderBan = new File("./wrk.cn/res/ban");
		
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
			
			String rUrl = UKey.Data("Server", "CN1")[0];
			
			UUtil.p("Dowanload-Ban-Pack");
			FPack pack = new FPack(rUrl+revServer.resBan()+"/eventbanner/eventbanner0_(zkd).pack?cyt=1", revFolderBanNew.getPath(), "");
			pack.downloadPack();
			
			save("resBan", revServer.resBan());
		}
	}
	public void gainPvl() throws Exception
	{
		File revFolderPvl = new File("./wrk.cn/res/pvl");
		
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
			
			String rUrl = UKey.Data("Server", "CN1")[0];
			
			UUtil.p("Dowanload-Pvl-Pack");
			FPack pack = new FPack(rUrl+revServer.resPvl()+"/privilege/privilege0_(zkd).pack?cyt=1", revFolderPvlNew.getPath(), "");
			pack.downloadPack();
			
			save("resPvl", revServer.resPvl());
		}
	}
	
	private File gainData(String kind, String revService, String revClient, String rename) throws Exception
	{
		File ctgFileRename = new File(new File("./wrk.cn/dat/ctg"), rename+"-"+revService+".xml");
		
		if(ctgFileRename.exists())
			ctgFileRename.delete();
		
		action.Update(kind, revClient).renameTo(ctgFileRename);
		
		return ctgFileRename;
	}

	public void save(String revName, String revNew) throws Exception
	{
		for(Field f:revClient.getClass().getDeclaredFields())
		{
			f.setAccessible(true);
			if(f.getName().equals(revName))
				f.set(revClient, revNew);
		}
		
		UXml xml = new UXml(revFile);
		
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
	}
	class NBos
	{
		private String idBoss;
		private String idImageBos;
		private String version;
	}
}
