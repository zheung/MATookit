package danor.matookit.functions;

import java.io.File;
import java.util.List;

import danor.matookit.natures.*;
import danor.matookit.utils.*;

public class FcAction
{
	private final UcKey db;
	private final UcLog log;

	private final String header;
	private final String sUrl;

	public final String cookie;
	
	public NArthur arthur;
	
	public FcAction(boolean rqtTime, String cookie) throws Exception
	{
		this.db = UcKey.getInstance();
		this.log = UcLog.getInstance();

		log.log("Action-Init", true);
		
		File dir = new File("./wrk/pak");
		if(!dir.exists()) dir.mkdir();//ToEH

		header = "./wrk/pak/" + (rqtTime?(Long.toString(System.currentTimeMillis()) + "-"):"");
		sUrl = db.Data("Server", 1)[1];

		if(cookie == null)
		{
			log.log("Action-Cookie", true);
			String[] values = db.Data("Action", 0);
			
			UcOption option = new UcOption().put("rqtCookie", true).put("typMethod", true)
			.put("cookie", (String)null).put("url", sUrl+values[2]).put("param", (String)null).put("path", header+"1Cookie.xml");
			
			
			UcConnect connect = new UcConnect(option, db, log);
			File pakFile = connect.result;
			
			UcConvert.decryptAES(null, pakFile, db.Data("Cipher", 3)[2].getBytes());
			UcConvert.decodeUrl(null, pakFile);
			
			this.cookie = connect.cookie;

			log.log("Result-"+this.cookie, true);
		}
		else
			this.cookie = cookie;
	}
//工具
	/**
	*@param option typMethod, rqtDecryptParam, rqtDecryptFile, rqtFormatFile
	*/
	public File Connect(int typConnect, UcOption option, String...prmValues) throws Exception 
	{
		String[] values = db.Data("Action", typConnect);

		if(typConnect == 3)
		{
			values[2] = values[2].replace("(jiayi)", prmValues[0]);
			
			String[] tmpValues = prmValues;
			
			prmValues = new String[tmpValues.length];
			
			for(int i=1;i<tmpValues.length;i++)
				prmValues[i-1] = tmpValues[i];
		}
		
		UcParam param = new UcParam((typConnect == 2),db);
		if(!values[3].equals(""))
		{
			String[] prmNames = values[3].split(";");
			
			int i = 0;
			for(String n:prmNames)
				param.put(n, prmValues[i++].getBytes());
		}
		
		UcOption newOption = new UcOption().put("rqtCookie", false).put("typMethod", true)
		.put("cookie", this.cookie).put("url", sUrl+values[2]).put("param", param.get(option.getBoolean("rqtDecryptParam"))).put("path", header+values[0]+values[1]+".xml");
		
		UcConnect connect = new UcConnect(newOption, db, log);
		
		if(option.getBoolean("rqtDecryptFile"))
			UcConvert.decryptAES(null, connect.result, db.Data("Cipher", 0)[2].getBytes("utf-8"));

		if(option.getBoolean("rqtFormatFile"))
			UcConvert.xmlFormat(connect.result);
		
		if(connect.isSuccess)
			return connect.result;
		else
			return null;
	}
//操作
	public NArthur Login(String phone, String paswd) throws Exception
	{
		log.log("Action-Login-"+phone, true);
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(2, option, phone, paswd);
		
		NArthur arthur = null;
		switch(FcGain.GainError(new UcXml(pakFile)))
		{
		case "0":
			arthur = FcGain.GainArthur(pakFile);
			
			log.log("Relust-"+phone+"-Success", true);
			break;
		case "1000":
			log.log("Relust-"+phone+"-Failed-Wrong.Phone.or.Password", true);
		}
		
		this.arthur = arthur;
		return arthur;
	}
	
	public File Update(String kind,String rev) throws Exception
	{
		UcUtil.p("Action-Update-"+kind);
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		return Connect(3, option, kind, cookie, rev);
	}
	
	public void Menu() throws Exception
	{
		UcUtil.p("Action-Menu");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(10, option);
	}
	public NRevision Home() throws Exception
	{
		UcUtil.p("Action-Home");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(11, option);

		FcGain.GainBase(pakFile, arthur);
		FcGain.GainPoint(pakFile, arthur);
		FcGain.GainItems(pakFile, arthur);
		
		return FcGain.GainRevision(pakFile);
	}
	public void Collection() throws Exception
	{
		UcUtil.p("Action-Collection");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(12, option);
	}
	public void Info() throws Exception
	{
		UcUtil.p("Action-Info");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(13, option, "6", String.valueOf(arthur.base().idArthur()));
	}
	public void PointSet(int addAP, int addBC) throws Exception
	{
		UcUtil.p("Action-PointSet");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(14, option, String.valueOf(addAP), String.valueOf(addBC));
	}
	public void ItemUse(String idItem) throws Exception
	{
		UcUtil.p("Action-ItemUse-"+idItem);
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(15, option, idItem);
	}
	public void Rank(boolean top, String idRank) throws Exception
	{
		UcUtil.p("Action-Rank");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(16, option, "1", idRank, top?"1":"0");//1;;0:非最高,1:最高
	}
	public List<String[]> RankP(String from, String idRank) throws Exception
	{
		UcUtil.p("Action-RankP");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(17, option, from, idRank);//最低的iduser...
		
		return FcGain.GainRankP(pakFile);
	}
	public List<String[]> RankN(String from, String idRank) throws Exception
	{
		UcUtil.p("Action-RankN");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(18, option, from, idRank);//最低的iduser...
		
		return FcGain.GainRankP(pakFile);
	}

	public void FairyList(boolean isGain) throws Exception
	{
		UcUtil.p("Action-FairyList");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(20, option);
		
		if(isGain)
			FcGain.GainFairyList(pakFile, arthur);
	}
	public void FairyInfo(NFairy fairy) throws Exception
	{
		UcUtil.p("Action-FairyInfo");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(21, option, "1", fairy.typRace(), fairy.idSerial(), fairy.finder().idArthur());

		FcGain.GainFairyInfo(pakFile, fairy, arthur);
	}
	public NBattleResult FairyFight(NFairy fairy) throws Exception
	{
		UcUtil.p("Action-FairyFight");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(22, option, fairy.typRace(), fairy.idSerial(), fairy.finder().idArthur());

		return FcGain.GainFairyFight(fairy, arthur, pakFile);
	}
	public void FairyLose(NFairy fairy) throws Exception
	{
		UcUtil.p("Action-FairyLose");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(23, option, fairy.typRace(), fairy.idSerial(), fairy.finder().idArthur());
	}
	
	public void RewardList(NArthur arthur) throws Exception
	{
		UcUtil.p("Action-RewardList");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(30, option);
		
		FcGain.GainRewardList(pakFile, arthur);	
	}
	public void RewardGain(String ids) throws Exception
	{
		UcUtil.p("Action-RewardGain");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(31, option, ids);
	}

	public void FriendList(NArthur arthur) throws Exception
	{
		UcUtil.p("Action-FriendList");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(40, option, "0");
		
		FcGain.GainFairyList(pakFile, arthur);	
	}
	public NMatches FriendNotice() throws Exception
	{
		UcUtil.p("Action-FriendNotice");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(41, option, "0");
		
		return FcGain.GainMatches("body>friend_notice>user_list", pakFile);	
	}
	public NMatches FriendInvitation() throws Exception
	{
		UcUtil.p("Action-FriendInvitation");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(42, option, "0");
		
		return FcGain.GainMatches("body>friend_appstate>user_ex", pakFile);	
	}
	public NMatches FriendOtherList() throws Exception
	{
		UcUtil.p("Action-FriendOtherList");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(43, option);
		
		return FcGain.GainMatches("body/player_search/user_list", pakFile);	
	}
	public NMatches FriendSearch(String name) throws Exception
	{
		UcUtil.p("Action-FriendSearch");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(44, option, name);
		
		return FcGain.GainMatches("body/player_search/user_list", pakFile);	
	}
	public void FriendInfo(String fUID) throws Exception
	{
		UcUtil.p("Action-FriendInfo");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(45, option, "1", String.valueOf(fUID));
	}
	public boolean FriendInvite(String uid) throws Exception
	{
		UcUtil.p("Action-FriendInvite");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(46, option, "1", String.valueOf(uid));

		return FcGain.GainFriendInvite(pakFile);
	}
	public boolean FriendApprove(String uid) throws Exception
	{
		UcUtil.p("Action-FriendApprove");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(47, option, "1", uid);
		
		return FcGain.GainFriendInvite(pakFile);	
	}
	public boolean FriendRemove(String idFriend) throws Exception
	{
		UcUtil.p("Action-FriendRemove");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(48, option, "1", idFriend);
		
		return FcGain.GainFriendRemove(pakFile);
	}
	
	public void AreaList() throws Exception
	{
		UcUtil.p("Action-AreaList");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(50, option);
		
		FcGain.GainAreaList(pakFile, arthur);
	}
	public void FloorList(NArea area) throws Exception
	{
		UcUtil.p("Action-FloorList");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(51, option, String.valueOf(area.idArea()));
		
		FcGain.GainFloors(pakFile, area);
	}
	public NFloor Floor(NArea area, String idFloor) throws Exception
	{
		UcUtil.p("Action-Floor");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(52, option, area.idArea(), "1", idFloor);
		
		return FcGain.GainFloor(area, pakFile);
	}
	public NExploreResult Explore(NArea area, String idFloor) throws Exception
	{
		UcUtil.p("Action-Explore");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(53, option, area.idArea(), "1", idFloor);

		return FcGain.GainExplore(arthur, area, pakFile);
	}
	public void ExploreJ(NArea area, String idFloor) throws Exception
	{
		UcUtil.p("Action-ExploreJ");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(53, option, area.idArea(), "0", idFloor);
	}
	
	public void Tutorial(String step) throws Exception
	{
		UcUtil.p("Action-Tutorial");
		
		UcOption option = new UcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(90, option, cookie.split("=")[1], step);
	}
}
