package danor.matookit.functions;

import java.io.File;
import java.util.List;

import danor.matookit.natures.*;
import danor.matookit.utils.*;

public class FAction
{
	private final String header;
	private final String sUrl;
	private final FServer server;

	public final String cookie;
	
	private NArthur arthur;
	private NRevision rev;
	
	public FAction(boolean rqtTime, String cookie, FServer server) throws Exception
	{

		ULog.log("Action-Init");
		
		if(!server.dirPak().exists()) server.dirPak().mkdir();//ToEH

		header = server.dirPak().getPath()+"/"+(rqtTime?(Long.toString(System.currentTimeMillis()) + "-"):"");
		sUrl = UUtil.Key(server.fileArb(), "Server", server.toString())[0];
		this.server = server;

		if(cookie == null)
		{
			ULog.log("Action-Cookie");
			
			UOption option = new UOption().put("rqtCookie", true).put("typMethod", true).put("server", server.toString())
			.put("cookie", (String)null).put("url", sUrl+UUtil.Key(server.fileArb(), "Action", "Cookie")[0])
			.put("param", (String)null).put("path", header+"1Cookie.xml");
			
			String[][] pp = new String[6][2];
			for(int i=0;i<6;i++)
				pp[i] = UUtil.Key(server.fileArb(), "Property", i+"");
			option.put("property", pp);
			
			UConnect connect = new UConnect(option);
			File pakFile = connect.result;
			
			UConvert.decryptAES(null, pakFile, UUtil.Key(server.fileArb(), "Cipher", "Cok")[0].getBytes());
			UConvert.decodeUrl(null, pakFile);
			
			this.cookie = connect.cookie;

			ULog.log("Result-"+this.cookie);
		}
		else
			this.cookie = cookie;
	}
//工具
	/**
	*@param option typMethod, rqtDecryptParam, rqtDecryptFile, rqtFormatFile
	*/
	public File Connect(String typAction, UOption option, String...prmValues) throws Exception 
	{
		String[] values = UUtil.Key(server.fileArb(), "Action", typAction);

		if(typAction.equals("Update"))
		{
			values[0] = values[0].replace("(jiayi)", prmValues[0]);
			
			String[] tmpValues = prmValues;
			
			prmValues = new String[tmpValues.length];
			
			for(int i=1;i<tmpValues.length;i++)
				prmValues[i-1] = tmpValues[i];
		}
		
		UParam param = new UParam((server.isCN() && typAction.equals("Login")), UUtil.Key(server.fileArb(), "Cipher", "Prm")[0]);
		if(!values[1].equals(""))
		{
			String[] prmNames = values[1].split(";");
			
			int i = 0;
			for(String n:prmNames)
				param.put(n, prmValues[i++].getBytes());
		}
		
		UOption newOption = new UOption().put("rqtCookie", false).put("typMethod", true)
				.put("server", server.toString()).put("cookie", this.cookie).put("url", sUrl+values[0])
				.put("param", param.get(option.getBoolean("rqtDecryptParam"))).put("path", header+typAction+".xml");
		
		String[][] pp = new String[6][2];
		for(int i=0;i<6;i++)
			pp[i] = UUtil.Key(server.fileArb(), "Property", i+"");
		newOption.put("property", pp);
		
		UConnect connect = new UConnect(newOption);
		
		if(option.getBoolean("rqtDecryptFile"))
			UConvert.decryptAES(null, connect.result, UUtil.Key(server.fileArb(), "Cipher", "Pak")[0].getBytes("utf-8"));

		if(option.getBoolean("rqtFormatFile"))
			UConvert.xmlFormat(connect.result);
		
		if(connect.isSuccess)
			return connect.result;
		else
			return null;
	}
//操作
	public NArthur Login(String phone, String paswd) throws Exception
	{
		ULog.log("Action-Login-"+server.toString()+"-"+phone);
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		
		File pakFile = null;
		if(server.isCN())
			pakFile = Connect("Login", option, phone, paswd);
		else
		{
			String param = "35";
			for(int i=0; i<10; i++) param+=(int)(10*(Math.random()));
			
			pakFile = Connect("Login", option, phone, paswd, param);
		}

		NArthur arthur = null;
		switch(FGain.GainError(new UXml(pakFile)))
		{
		case "0":
			arthur = FGain.GainArthur(pakFile);
			rev = FGain.GainRevision(pakFile);
			
			ULog.log("Result-"+phone+"-Success");
			break;
		case "1000":
			ULog.log("Result-"+phone+"-Failed-Wrong.Phone.or.Password");
		}
		
		this.arthur = arthur;
		return arthur;
	}
	
	public File Update(String typRes,String rev)
	{
		UUtil.p("Action-Update-"+typRes);
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		
		File pakFile = null;
		try	{ pakFile = Connect("Update", option, typRes, cookie, rev); }
		catch (Exception e) { e.printStackTrace(); }
		
		File renameFile = new File(pakFile.getParent()+"/"+typRes+"-"+rev+".xml");
		
		pakFile.renameTo(renameFile);
		
		return renameFile;
	}
	
	public void Menu() throws Exception
	{
		UUtil.p("Action-Menu");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("Menu", option);
	}
	public NRevision Home() throws Exception
	{
		UUtil.p("Action-Home");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("Home", option);

		FGain.GainBase(pakFile, arthur);
		FGain.GainPoint(pakFile, arthur);
		FGain.GainItems(pakFile, arthur);
		
		return FGain.GainRevision(pakFile);
	}
	public void Collection() throws Exception
	{
		UUtil.p("Action-Collection");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("Collection", option);
	}
	public void Info() throws Exception
	{
		UUtil.p("Action-Info");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("Info", option, "6", String.valueOf(arthur.base().idArthur()));
	}
	public void PointSet(int addAP, int addBC) throws Exception
	{
		UUtil.p("Action-PointSet");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("PointSet", option, String.valueOf(addAP), String.valueOf(addBC));
	}
	public void ItemUse(String idItem) throws Exception
	{
		UUtil.p("Action-ItemUse-"+idItem);
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("ItemUse", option, idItem);
	}
	public void Rank(boolean top, String idRank) throws Exception
	{
		UUtil.p("Action-Rank");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("Rank", option, "1", idRank, top?"1":"0");//1;;0:非最高,1:最高
	}
	public List<String[]> RankP(String from, String idRank) throws Exception
	{
		UUtil.p("Action-RankP");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("RankP", option, from, idRank);//最低的iduser...
		
		return FGain.GainRankP(pakFile);
	}
	public List<String[]> RankN(String from, String idRank) throws Exception
	{
		UUtil.p("Action-RankN");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("RankN", option, from, idRank);//最低的iduser...
		
		return FGain.GainRankP(pakFile);
	}

	public void FairyList(boolean isGain) throws Exception
	{
		UUtil.p("Action-FairyList");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FairyList", option);
		
		if(isGain)
			FGain.GainFairyList(pakFile, arthur);
	}
	public void FairyInfo(NFairy fairy) throws Exception
	{
		UUtil.p("Action-FairyInfo");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FairyInfo", option, "1", fairy.typRace(), fairy.idSerial(), fairy.finder().idArthur());

		FGain.GainFairyInfo(pakFile, fairy, arthur);
	}
	public NBattleResult FairyFight(NFairy fairy) throws Exception
	{
		UUtil.p("Action-FairyFight");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FairyFight", option, fairy.typRace(), fairy.idSerial(), fairy.finder().idArthur());

		return FGain.GainFairyFight(fairy, arthur, pakFile);
	}
	public void FairyLose(NFairy fairy) throws Exception
	{
		UUtil.p("Action-FairyLose");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("FairyLose", option, fairy.typRace(), fairy.idSerial(), fairy.finder().idArthur());
	}
	
	public void RewardList(NArthur arthur) throws Exception
	{
		UUtil.p("Action-RewardList");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("RewardList", option);
		
		FGain.GainRewardList(pakFile, arthur);	
	}
	public void RewardGain(String ids) throws Exception
	{
		UUtil.p("Action-RewardGain");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("RewardGain", option, ids);
	}

	public void FriendList(NArthur arthur) throws Exception
	{
		UUtil.p("Action-FriendList");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FriendList", option, "0");
		
		FGain.GainFairyList(pakFile, arthur);	
	}
	public NMatches FriendNotice() throws Exception
	{
		UUtil.p("Action-FriendNotice");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FriendNotice", option, "0");
		
		return FGain.GainMatches("body>friend_notice>user_list", pakFile);	
	}
	public NMatches FriendInvitation() throws Exception
	{
		UUtil.p("Action-FriendInvitation");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FriendInvitation", option, "0");
		
		return FGain.GainMatches("body>friend_appstate>user_ex", pakFile);	
	}
	public NMatches FriendOtherList() throws Exception
	{
		UUtil.p("Action-FriendOtherList");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FriendOtherList", option);
		
		return FGain.GainMatches("body/player_search/user_list", pakFile);	
	}
	public NMatches FriendSearch(String name) throws Exception
	{
		UUtil.p("Action-FriendSearch");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FriendSearch", option, name);
		
		return FGain.GainMatches("body/player_search/user_list", pakFile);	
	}
	public void FriendInfo(String fUID) throws Exception
	{
		UUtil.p("Action-FriendInfo");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("FriendInfo", option, "1", String.valueOf(fUID));
	}
	public boolean FriendInvite(String uid) throws Exception
	{
		UUtil.p("Action-FriendInvite");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FriendInvite", option, "1", String.valueOf(uid));

		return FGain.GainFriendInvite(pakFile);
	}
	public boolean FriendApprove(String uid) throws Exception
	{
		UUtil.p("Action-FriendApprove");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FriendApprove", option, "1", uid);
		
		return FGain.GainFriendInvite(pakFile);	
	}
	public boolean FriendRemove(String idFriend) throws Exception
	{
		UUtil.p("Action-FriendRemove");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FriendRemove", option, "1", idFriend);
		
		return FGain.GainFriendRemove(pakFile);
	}
	
	public void AreaList() throws Exception
	{
		UUtil.p("Action-AreaList");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("AreaList", option);
		
		FGain.GainAreaList(pakFile, arthur);
	}
	public void FloorList(NArea area) throws Exception
	{
		UUtil.p("Action-FloorList");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FloorList", option, String.valueOf(area.idArea()));
		
		FGain.GainFloors(pakFile, area);
	}
	public NFloor Floor(NArea area, String idFloor) throws Exception
	{
		UUtil.p("Action-Floor");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("Floor", option, area.idArea(), "1", idFloor);
		
		return FGain.GainFloor(area, pakFile);
	}
	public NExploreResult Explore(NArea area, String idFloor) throws Exception
	{
		UUtil.p("Action-Explore");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("Explore", option, area.idArea(), "1", idFloor);

		return FGain.GainExplore(arthur, area, pakFile);
	}
	public void ExploreJ(NArea area, String idFloor) throws Exception
	{
		UUtil.p("Action-ExploreJ");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("ExploreJ", option, area.idArea(), "0", idFloor);
	}
	
	public void Tutorial(String step) throws Exception
	{
		UUtil.p("Action-Tutorial");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("Tutorial", option, cookie.split("=")[1], step);
	}

	public NArthur arthur() { return arthur; }
	public NRevision rev() { return rev; }
	public FServer server() { return server; }
}
