package org.danor.matookit;

import java.io.File;

public class FcDownload
{
	protected static void dwnMainbg(String kind, FcDataBase db, FcLog log) throws Exception
	{
		FcUtil.pp("<Download-Mainbg>");
		String rUrl = db.Data("Server", 0)[1];
		
//		int[] levels = {1,5,10,20,30,40,50,60,70,80,90,100};
		int[] levels = {5,10,20,30,40,50,60,70,80,90,100};

		for(int lv:levels)
		{
			File fld = new File("./dat/Mainbg/["+kind+"]/org/" + (lv<10?"0"+lv:lv) + "/");
			if(!fld.exists()) fld.mkdirs();
	
			FcUtil.pp("DownloadMainbg-"+kind+"-Lv" + (lv<10?"0"+lv:lv));
			FcPack pack = new FcPack(rUrl+"2/mainbg/mainbg_"+lv+"_"+kind+"_(zkd).pack?cyt=1", fld.getPath(), "", db, log);
			
			for(File f:pack.downloadPack())
				if(f.getName().indexOf("rja") != -1)
					f.delete();
			
			String[] typMainbg = {"an","mg","nn","nt"};
			String[] typMainbg2 = {"日中","日落","日升","月夜"};
			
			int i = 0;
			for(String t:typMainbg)
			{
				File picTop = new File(fld.getPath() + "/["+(lv<10?"0"+lv:lv)+"]["+kind+"]["+typMainbg2[i]+"][星空].png");
				File picBtm = new File(fld.getPath() + "/["+(lv<10?"0"+lv:lv)+"]["+kind+"]["+typMainbg2[i]+"][城堡].png");
				
				new File(fld.getPath() + "/mainbg_"+t+"_0.png").renameTo(picTop);
				new File(fld.getPath() + "/mainbg_"+t+"_1.png").renameTo(picBtm);
				
				FcPack.mrgMainbg(picTop, picBtm, new File(fld.getParentFile().getParent()+"/["+(lv<10?"0"+lv:lv)+"]["+kind+"]["+typMainbg2[i++]+"].png"));
			}
		}
		
		FcUtil.pp("</Download-Mainbg>");
	}
	
	protected static void dwnAdvbg(String version, FcDataBase db, FcLog log) throws Exception
	{
		FcUtil.pp("<Download-Advbg> "+version);
		
		String rUrl = db.Data("Server", 0)[1];
		
		File fld = new File("./dat/Advbg/"+version);
		if(!fld.exists()) fld.mkdirs();
		
		FcPack pack = new FcPack(rUrl+version+"/advbg/advbg0_(zkd).pack?cyt=1", fld.getPath(), "", db, log);
		pack.downloadPack();
	}
	
	protected static void dwnItem(String version, FcDataBase db, FcLog log) throws Exception
	{
		FcUtil.pp("<Download-Item> "+version);
		
		String rUrl = db.Data("Server", 0)[1];
		
		File fld = new File("./dat/Item/"+version);
		if(!fld.exists()) fld.mkdirs();
		
		FcPack pack = new FcPack(rUrl+version+"/item/item_0_(zkd).pack?cyt=1", fld.getPath(), "", db, log);
		pack.downloadPack();
	}

	protected static void dwnCard(DcDataCard dataCard) throws Exception
	{
		FcUtil.p("<Download-Card> "+dataCard.name+"-"+dataCard.cID);
		
		String rUrl = FcDataBase.getInstance().Data("Server", 0)[1];
		
		File fld = new File("./dat/Card/"+dataCard.version+"/"+dataCard.name);
		if(!fld.exists()) fld.mkdirs();
		
		FcUtil.p("Dowanload-Card-Full-Bac");
		FcOption option = new FcOption().put("rqtCookie", false).put("typMethod", false)
				.put("cookie", (String)null).put("url", rUrl+dataCard.version+"/card_full/full_thumbnail_chara_"+(dataCard.i1ID.equals("None")?dataCard.cID:dataCard.i1ID)+"?cyt=1")
				.put("param", (String)null).put("path", fld.getPath()+"\\[" + dataCard.name + "][普卡][普].png");
		FcConnect connect = new FcConnect(option, FcDataBase.getInstance(), FcLog.getInstance());
		FcConvert.decryptAES(null, connect.result, FcDataBase.getInstance().Data("Cipher", 1)[2].getBytes("utf-8"));
		FcUtil.p("Dowanload-Card-Full-Max");
		option = new FcOption().put("rqtCookie", false).put("typMethod", false)
				.put("cookie", (String)null).put("url", rUrl+dataCard.version+"/card_full_max/full_thumbnail_chara_"+(dataCard.i1ID.equals("None")?"5"+dataCard.cID:dataCard.i2ID)+"?cyt=1")
				.put("param", (String)null).put("path", fld.getPath()+"\\[" + dataCard.name + "][普卡][满].png");
		connect = new FcConnect(option, FcDataBase.getInstance(), FcLog.getInstance());
		FcConvert.decryptAES(null, connect.result, FcDataBase.getInstance().Data("Cipher", 1)[2].getBytes("utf-8"));
		FcUtil.p("Dowanload-Card-Full-Hlo-Bac");
		option = new FcOption().put("rqtCookie", false).put("typMethod", false)
				.put("cookie", (String)null).put("url", rUrl+dataCard.version+"/card_full_h/full_thumbnail_chara_"+(dataCard.i1ID.equals("None")?dataCard.cID:dataCard.i1ID)+"_horo?cyt=1")
				.put("param", (String)null).put("path", fld.getPath()+"\\[" + dataCard.name + "][闪卡][普].png");
		connect = new FcConnect(option, FcDataBase.getInstance(), FcLog.getInstance());
		FcConvert.decryptAES(null, connect.result, FcDataBase.getInstance().Data("Cipher", 1)[2].getBytes("utf-8"));
		FcUtil.p("Dowanload-Card-Full-Hlo-Max");
		option = new FcOption().put("rqtCookie", false).put("typMethod", false)
				.put("cookie", (String)null).put("url", rUrl+dataCard.version+"/card_full_h_max/full_thumbnail_chara_"+(dataCard.i1ID.equals("None")?"5"+dataCard.cID:dataCard.i2ID)+"_horo?cyt=1")
				.put("param", (String)null).put("path", fld.getPath()+"\\[" + dataCard.name + "][闪卡][满].png");
		connect = new FcConnect(option, FcDataBase.getInstance(), FcLog.getInstance());
		FcConvert.decryptAES(null, connect.result, FcDataBase.getInstance().Data("Cipher", 1)[2].getBytes("utf-8"));
		
		FcUtil.p("Dowanload-Card-Pack");
		FcPack pack = new FcPack(rUrl+dataCard.version+"/card/card"+dataCard.cID+"_(zkd).pack?cyt=1", fld.getPath(), "", FcDataBase.getInstance(), FcLog.getInstance());
		pack.downloadPack();
		
		new File(fld.getPath()+"/face_"+(dataCard.i1ID.equals("None")?dataCard.cID:dataCard.i1ID)+".png").renameTo(new File(fld.getPath()+"/[" + dataCard.name + "][头像][普].png"));
		new File(fld.getPath()+"/face_"+(dataCard.i1ID.equals("None")?"5"+dataCard.cID:dataCard.i2ID)+".png").renameTo(new File(fld.getPath()+"/[" + dataCard.name + "][头像][满].png"));
		new File(fld.getPath()+"/adv_chara"+(dataCard.i1ID.equals("None")?dataCard.cID:dataCard.i1ID)+".png").renameTo(new File(fld.getPath()+"/[" + dataCard.name + "][立绘][普].png"));
		new File(fld.getPath()+"/adv_chara"+(dataCard.i1ID.equals("None")?"5"+dataCard.cID:dataCard.i2ID)+".png").renameTo(new File(fld.getPath()+"/[" + dataCard.name + "][立绘][满].png"));
		new File(fld.getPath()+"/thumbnail_chara_"+(dataCard.i1ID.equals("None")?dataCard.cID:dataCard.i1ID)+".png").renameTo(new File(fld.getPath()+"/[" + dataCard.name + "][临普][普].png"));
		new File(fld.getPath()+"/thumbnail_chara_"+(dataCard.i1ID.equals("None")?"5"+dataCard.cID:dataCard.i2ID)+".png").renameTo(new File(fld.getPath()+"/[" + dataCard.name + "][临普][满].png"));
		new File(fld.getPath()+"/thumbnail_chara_"+(dataCard.i1ID.equals("None")?dataCard.cID:dataCard.i1ID)+"_horo.png").renameTo(new File(fld.getPath()+"/[" + dataCard.name + "][临闪][普].png"));
		new File(fld.getPath()+"/thumbnail_chara_"+(dataCard.i1ID.equals("None")?"5"+dataCard.cID:dataCard.i2ID)+"_horo.png").renameTo(new File(fld.getPath()+"/[" + dataCard.name + "][临闪][满].png"));
	}
}
