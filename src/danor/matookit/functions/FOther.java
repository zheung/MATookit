package danor.matookit.functions;

import java.io.File;
import java.util.List;

import danor.matookit.natures.NArea;
import danor.matookit.natures.NFloor;
import danor.matookit.natures.data.NDataCard;
import danor.matookit.utils.UUtil;

public class FOther
{
//计算秘境进度
	public static void CalProgFloor() throws Exception
	{
		String t;
		while(!(t=UUtil.g()).equals(""))
		{
			int s=Integer.parseInt(t);
			for(int i=1;i<=s;i++)
				UUtil.p((int)Math.floor((100.0/s)*i));
			
			UUtil.p("");
		}
	}
//输出秘境的隐藏奖励
	public static void GetFound() throws Exception
	{
		FAction action = new FAction(true, null, FServer.CN1);
		action.Login("", "");
		action.AreaList();
		
		List<NDataCard> cl = FDataCard.anlCard(226);

		for(NArea a:action.arthur().areas())
			if(a.name().indexOf("【限时】") !=-1)
			{ 
				UUtil.pp(a.name()+" "+a.idArea());
				
				action.FloorList(a);

				for(NFloor f:a.floors())
					UUtil.qq("区域"+f.idFloor()+"-Cost-"+f.cost()+" ");
				UUtil.pp("");
				
				for(int i=0;i<3;i++)
				{
					for(NFloor f:a.floors())
					{
						boolean flgFind = false;
						for(NDataCard c:cl)
							if(c.idCard().equals(f.idCards()[i]))
							{
								flgFind = true;
								UUtil.qq(c.name()+" ");
							}
						
						if(!flgFind && !f.hasFragment()) UUtil.qq("null ");

					}
					
					UUtil.pp("");
				}

				for(NFloor f:a.floors())
					if(f.hasFragment())
						UUtil.qq("<因子> ");
				UUtil.pp("");
			}
	}
//旧版资源重名到新版
	public static void renameCardFileToNew(int rev) throws Exception
	{
		File rf = new File("./wrk/res/cns/crd/_new");
		
		List<NDataCard> cl = FDataCard.anlCard(rev);
		
		for(File f:rf.listFiles())
		{
			for(NDataCard c:cl)
				if(c.name().equals(f.getName()))
//				if(c.idCard().equals(f.getName()))
				{
					new File(f, "["+c.name()+"][立绘][满].png").renameTo(new File(f, c.idCard()+"_AdvMax.png"));
					new File(f, "["+c.name()+"][立绘][普].png").renameTo(new File(f, c.idCard()+"_AdvBac.png"));
					new File(f, "["+c.name()+"][头像][满].png").renameTo(new File(f, c.idCard()+"_FacMax.png"));
					new File(f, "["+c.name()+"][头像][普].png").renameTo(new File(f, c.idCard()+"_FacBac.png"));
					new File(f, "["+c.name()+"][临普][满].png").renameTo(new File(f, c.idCard()+"_TumNorMax.png"));
					new File(f, "["+c.name()+"][临普][普].png").renameTo(new File(f, c.idCard()+"_TumNorBac.png"));
					new File(f, "["+c.name()+"][临闪][满].png").renameTo(new File(f, c.idCard()+"_TumHloMax.png"));
					new File(f, "["+c.name()+"][临闪][普].png").renameTo(new File(f, c.idCard()+"_TumHloBac.png"));
					new File(f, "["+c.name()+"][普卡][满].png").renameTo(new File(f, c.idCard()+"_FulNorMax.png"));
					new File(f, "["+c.name()+"][普卡][普].png").renameTo(new File(f, c.idCard()+"_FulNorBac.png"));
					new File(f, "["+c.name()+"][闪卡][满].png").renameTo(new File(f, c.idCard()+"_FulHloMax.png"));
					new File(f, "["+c.name()+"][闪卡][普].png").renameTo(new File(f, c.idCard()+"_FulHloBac.png"));
					
					f.renameTo(new File(f.getParentFile(), c.idCard()));
					break;
				}
		}
	}
//新版资源重名到旧版
	public static void renameCardFileToOld(int rev) throws Exception
	{
		File fld = new File(FServer.CN1.dirRes(), "/crd/_new");
		List<NDataCard> cl = FDataCard.anlCard(rev);
		
		for(File fc:fld.listFiles())
		{
			NDataCard cc = null;
			for(NDataCard c:cl)
				if(c.idCard().equals(fc.getName()))
				{
					cc = c;
					String head = "["+c.name()+"]";
					for(File f:fc.listFiles())
					{
						String type = "[a][b]";
						if(f.getName().indexOf("Adv") != -1)
							type = type.replace("a", "立绘");
						else if(f.getName().indexOf("Fac") != -1)
							type = type.replace("a", "头像");
						else
						{
							if(f.getName().indexOf("Nor") != -1)
								type = type.replace("a", "a普c");
							else if(f.getName().indexOf("Hlo") != -1)
								type = type.replace("a", "a闪c");

							if(f.getName().indexOf("Ful") != -1)
								type = type.replace("c", "卡").replace("a", "");
							else if(f.getName().indexOf("Tum") != -1)
								type = type.replace("a", "临").replace("c", "");
						}
						
						if(f.getName().indexOf("Bac") != -1)
							type = type.replace("b", "普");
						else if(f.getName().indexOf("Max") != -1)
							type = type.replace("b", "满");
						
						f.renameTo(new File(f.getParentFile(), head+type+".png"));
					}
				}
			
			fc.renameTo(new File(fc.getParentFile(), cc.name()));
		}
	}
//跳过教程
	public static void jump(FAction action) throws Exception
	{
		action.Tutorial("7000");
		action.Tutorial("8000");
	}
//日服key补全
	public static String fillKeyJP(String key, String name) throws Exception
	{
		key += name;
		while(key.length() < 32) key += "0";
		
		return key;
	}
}
