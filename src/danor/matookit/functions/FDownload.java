package danor.matookit.functions;

import java.io.File;

import danor.matookit.natures.data.NDataCard;
import danor.matookit.utils.*;

public class FDownload
{
	public static void dwnMainbg(String kind, FServer server) throws Exception
	{
		UUtil.pp("<Download-Mainbg>");
		String rUrl = UUtil.Key(server.fileArb(), "Server", "CN1")[0];
		
//		int[] levels = {1,5,10,20,30,40,50,60,70,80,90,100};
		int[] levels = {5,10,20,30,40,50,60,70,80,90,100};

		for(int lv:levels)
		{
			File fld = new File("./dat/Mainbg/["+kind+"]/org/" + (lv<10?"0"+lv:lv) + "/");
			if(!fld.exists()) fld.mkdirs();
	
			UUtil.pp("DownloadMainbg-"+kind+"-Lv" + (lv<10?"0"+lv:lv));
			FPack pack = new FPack(rUrl+"2/mainbg/mainbg_"+lv+"_"+kind+"_(zkd).pack?cyt=1", fld.getPath(), "", null);
			
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
				
				FPack.mrgMainbg(picTop, picBtm, new File(fld.getParentFile().getParent()+"/["+(lv<10?"0"+lv:lv)+"]["+kind+"]["+typMainbg2[i++]+"].png"));
			}
		}
		
		UUtil.pp("</Download-Mainbg>");
	}
	
	public static void dwnCard(NDataCard dataCard, FServer server) throws Exception
	{
		UUtil.p("<Download-Card> "+dataCard.name()+"-"+dataCard.idCard());
		
		String rUrl = UUtil.Key(server.fileArb(), "Server", "CN1")[0];
		
		File fld = new File("./dat/Card/"+dataCard.version()+"/"+dataCard.name());
		if(!fld.exists()) fld.mkdirs();
		
		UUtil.p("Dowanload-Card-Full-Bac");
		UOption option = new UOption().put("rqtCookie", false).put("typMethod", false)
				.put("cookie", (String)null).put("url", rUrl+dataCard.version()+"/card_full/full_thumbnail_chara_"+(dataCard.idImageNorrmal().equals("None")?dataCard.idCard():dataCard.idImageNorrmal())+"?cyt=1")
				.put("param", (String)null).put("path", fld.getPath()+"\\[" + dataCard.name() + "][普卡][普].png");
		
		int i=0;
		String[][] pp = new String[4][2];
		for(int ii:new int[]{0,1,3,4}) pp[i++] = UUtil.Key(server.fileArb(), "Property", ii+"");
		option.put("property", pp);
		
		UConnect connect = new UConnect(option);
		UConvert.decryptAES(null, connect.result, UUtil.Key(server.fileArb(), "CipherAES", "1")[0].getBytes("utf-8"));
		UUtil.p("Dowanload-Card-Full-Max");
		option = new UOption().put("rqtCookie", false).put("typMethod", false)
				.put("cookie", (String)null).put("url", rUrl+dataCard.version()+"/card_full_max/full_thumbnail_chara_"+(dataCard.idImageNorrmal().equals("None")?"5"+dataCard.idCard():dataCard.idImageArousal())+"?cyt=1")
				.put("param", (String)null).put("path", fld.getPath()+"\\[" + dataCard.name() + "][普卡][满].png");
		
		
		connect = new UConnect(option);
		UConvert.decryptAES(null, connect.result, UUtil.Key(server.fileArb(), "CipherAES", "1")[0].getBytes("utf-8"));
		UUtil.p("Dowanload-Card-Full-Hlo-Bac");
		option = new UOption().put("rqtCookie", false).put("typMethod", false)
				.put("cookie", (String)null).put("url", rUrl+dataCard.version()+"/card_full_h/full_thumbnail_chara_"+(dataCard.idImageNorrmal().equals("None")?dataCard.idCard():dataCard.idImageNorrmal())+"_horo?cyt=1")
				.put("param", (String)null).put("path", fld.getPath()+"\\[" + dataCard.name() + "][闪卡][普].png");
		connect = new UConnect(option);
		UConvert.decryptAES(null, connect.result, UUtil.Key(server.fileArb(), "CipherAES", "1")[0].getBytes("utf-8"));
		UUtil.p("Dowanload-Card-Full-Hlo-Max");
		option = new UOption().put("rqtCookie", false).put("typMethod", false)
				.put("cookie", (String)null).put("url", rUrl+dataCard.version()+"/card_full_h_max/full_thumbnail_chara_"+(dataCard.idImageNorrmal().equals("None")?"5"+dataCard.idCard():dataCard.idImageArousal())+"_horo?cyt=1")
				.put("param", (String)null).put("path", fld.getPath()+"\\[" + dataCard.name() + "][闪卡][满].png");
		connect = new UConnect(option);
		UConvert.decryptAES(null, connect.result, UUtil.Key(server.fileArb(), "CipherAES", "1")[0].getBytes("utf-8"));
		
		UUtil.p("Dowanload-Card-Pack");
		FPack pack = new FPack(rUrl+dataCard.version()+"/card/card"+dataCard.idCard()+"_(zkd).pack?cyt=1", fld.getPath(), "", server);
		pack.downloadPack();
		
		new File(fld.getPath()+"/face_"+(dataCard.idImageNorrmal().equals("None")?dataCard.idCard():dataCard.idImageNorrmal())+".png").renameTo(new File(fld.getPath()+"/[" + dataCard.name() + "][头像][普].png"));
		new File(fld.getPath()+"/face_"+(dataCard.idImageNorrmal().equals("None")?"5"+dataCard.idCard():dataCard.idImageArousal())+".png").renameTo(new File(fld.getPath()+"/[" + dataCard.name() + "][头像][满].png"));
		new File(fld.getPath()+"/adv_chara"+(dataCard.idImageNorrmal().equals("None")?dataCard.idCard():dataCard.idImageNorrmal())+".png").renameTo(new File(fld.getPath()+"/[" + dataCard.name() + "][立绘][普].png"));
		new File(fld.getPath()+"/adv_chara"+(dataCard.idImageNorrmal().equals("None")?"5"+dataCard.idCard():dataCard.idImageArousal())+".png").renameTo(new File(fld.getPath()+"/[" + dataCard.name() + "][立绘][满].png"));
		new File(fld.getPath()+"/thumbnail_chara_"+(dataCard.idImageNorrmal().equals("None")?dataCard.idCard():dataCard.idImageNorrmal())+".png").renameTo(new File(fld.getPath()+"/[" + dataCard.name() + "][临普][普].png"));
		new File(fld.getPath()+"/thumbnail_chara_"+(dataCard.idImageNorrmal().equals("None")?"5"+dataCard.idCard():dataCard.idImageArousal())+".png").renameTo(new File(fld.getPath()+"/[" + dataCard.name() + "][临普][满].png"));
		new File(fld.getPath()+"/thumbnail_chara_"+(dataCard.idImageNorrmal().equals("None")?dataCard.idCard():dataCard.idImageNorrmal())+"_horo.png").renameTo(new File(fld.getPath()+"/[" + dataCard.name() + "][临闪][普].png"));
		new File(fld.getPath()+"/thumbnail_chara_"+(dataCard.idImageNorrmal().equals("None")?"5"+dataCard.idCard():dataCard.idImageArousal())+"_horo.png").renameTo(new File(fld.getPath()+"/[" + dataCard.name() + "][临闪][满].png"));
	}
}
