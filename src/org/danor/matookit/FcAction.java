package org.danor.matookit;

import java.io.File;
import java.util.List;

public class FcAction
{
	private final FcDataBase db;
	private final FcLog log;

	private final String header;
	private final String sUrl;

	protected final String cookie;
	
	protected DcArthur arthur;
	
	protected FcAction(boolean rqtTime, String cookie) throws Exception
	{
		this.db = FcDataBase.getInstance();
		this.log = FcLog.getInstance();

		log.log("Action-Init", true);
		
		File dir = new File("./wrk/pak");
		if(!dir.exists()) dir.mkdir();//ToEH

		header = "./wrk/pak/" + (rqtTime?(Long.toString(System.currentTimeMillis()) + "-"):"");
		sUrl = db.Data("Server", 1)[1];

		if(cookie == null)
		{
			log.log("Action-Cookie", true);
			String[] values = db.Data("Action", 0);
			
			FcOption option = new FcOption().put("rqtCookie", true).put("typMethod", true)
			.put("cookie", (String)null).put("url", sUrl+values[2]).put("param", (String)null).put("path", header+"1Cookie.xml");
			
			
			FcConnect connect = new FcConnect(option, db, log);
			File pakFile = connect.result;
			
			FcConvert.decryptAES(null, pakFile, db.Data("Cipher", 3)[2].getBytes());
			FcConvert.decodeUrl(null, pakFile);
			
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
	protected File Connect(int typConnect, FcOption option, String...prmValues) throws Exception 
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
		
		FcParam param = new FcParam((typConnect == 2),db);
		if(!values[3].equals(""))
		{
			String[] prmNames = values[3].split(";");
			
			int i = 0;
			for(String n:prmNames)
				param.put(n, prmValues[i++].getBytes());
		}
		
		FcOption newOption = new FcOption().put("rqtCookie", false).put("typMethod", true)
		.put("cookie", this.cookie).put("url", sUrl+values[2]).put("param", param.get(option.getBoolean("rqtDecryptParam"))).put("path", header+values[0]+values[1]+".xml");
		
		FcConnect connect = new FcConnect(newOption, db, log);
		
		if(option.getBoolean("rqtDecryptFile"))
			FcConvert.decryptAES(null, connect.result, db.Data("Cipher", 0)[2].getBytes("utf-8"));

		if(option.getBoolean("rqtFormatFile"))
			FcConvert.xmlFormat(connect.result);
		
		if(connect.isSuccess)
			return connect.result;
		else
			return null;
	}
//操作
	protected DcArthur Login(String phone, String paswd) throws Exception
	{
		log.log("Action-Login-"+phone, true);
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(2, option, phone, paswd);
		
		DcArthur arthur = null;
		switch(FcGain.GainError(new FcXml(pakFile)))
		{
		case "0":
			arthur = new DcArthur();
			
			arthur.info = FcGain.GainInfo(pakFile);
			FcGain.GainPoint(arthur.points, pakFile);
			arthur.cards = FcGain.GainCards(pakFile);
			arthur.items = FcGain.GainItems(pakFile);
			
			log.log("Relust-"+phone+"-Success", true);
			break;
		case "1000":
			log.log("Relust-"+phone+"-Failed-Wrong.Phone.or.Password", true);
		}
		
		this.arthur = arthur;
		return arthur;
	}
	
	protected File Update(String kind,String rev) throws Exception
	{
		FcUtil.p("Action-Update-"+kind);
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		return Connect(3, option, kind, cookie, rev);
	}
	
	protected void Menu() throws Exception
	{
		FcUtil.p("Action-Menu");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(10, option);
	}
	protected DcRevision Home() throws Exception
	{
		FcUtil.p("Action-Home");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(11, option);

		arthur.info = FcGain.GainInfo(pakFile);
		FcGain.GainPoint(arthur.points, pakFile);
		arthur.items = FcGain.GainItems(pakFile);
		
		return FcGain.GainRevision(pakFile);
	}
	protected void Collection() throws Exception
	{
		FcUtil.p("Action-Collection");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(12, option);
	}
	protected void Info() throws Exception
	{
		FcUtil.p("Action-Info");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(13, option, "6", String.valueOf(arthur.info.uid));
	}
	protected void PointSet(int addAP, int addBC) throws Exception
	{
		FcUtil.p("Action-PointSet");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(14, option, String.valueOf(addAP), String.valueOf(addBC));
	}
	protected void ItemUse(String idItem) throws Exception
	{
		FcUtil.p("Action-ItemUse-"+idItem);
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(15, option, idItem);
	}
	protected void Rank(boolean top, String idRank) throws Exception
	{
		FcUtil.p("Action-Rank");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(16, option, "1", idRank, top?"1":"0");//1;;0:非最高,1:最高
	}
	protected List<String[]> RankP(String from, String idRank) throws Exception
	{
		FcUtil.p("Action-RankP");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(17, option, from, idRank);//最低的iduser...
		
		return FcGain.GainRankP(pakFile);
	}
	protected List<String[]> RankN(String from, String idRank) throws Exception
	{
		FcUtil.p("Action-RankN");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(18, option, from, idRank);//最低的iduser...
		
		return FcGain.GainRankP(pakFile);
	}

	protected void FairyList(boolean isGain) throws Exception
	{
		FcUtil.p("Action-FairyList");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(20, option);
		
		if(isGain)
			FcGain.GainFairyList(pakFile, arthur);
	}
	protected void FairyInfo(DcFairy fairy) throws Exception
	{
		FcUtil.p("Action-FairyInfo");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(21, option, "1", fairy.typRace, fairy.sid, fairy.user.uid);

		FcGain.GainFairyInfo(pakFile, fairy, arthur);
	}
	protected DcResultFight FairyFight(DcFairy fairy) throws Exception
	{
		FcUtil.p("Action-FairyFight");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(22, option, fairy.typRace, fairy.sid, fairy.user.uid);

		return FcGain.GainFairyFight(fairy, arthur, pakFile);
	}
	protected void FairyLose(DcFairy fairy) throws Exception
	{
		FcUtil.p("Action-FairyLose");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(23, option, fairy.typRace, fairy.sid, fairy.user.uid);
	}
	
	protected List<DcReward> RewardList() throws Exception
	{
		FcUtil.p("Action-RewardList");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(30, option);
		
		return FcGain.GainRewardList(pakFile);	
	}
	protected void RewardGain(String ids) throws Exception
	{
		FcUtil.p("Action-RewardGain");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(31, option, ids);
	}

	protected List<DcPlayer> FriendList() throws Exception
	{
		FcUtil.p("Action-FriendList");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(40, option, "0");
		
		return FcGain.GainPlayerList("body/friend_list", pakFile);	
	}
	protected List<DcPlayer> FriendNotice() throws Exception
	{
		FcUtil.p("Action-FriendNotice");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(41, option, "0");
		
		return FcGain.GainPlayerList("body>friend_notice>user_list", pakFile);	
	}
	protected List<DcPlayer> FriendInvitation() throws Exception
	{
		FcUtil.p("Action-FriendInvitation");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(42, option, "0");
		
		return FcGain.GainPlayerList("body>friend_appstate>user_ex", pakFile);	
	}
	protected List<DcPlayer> FriendOtherList() throws Exception
	{
		FcUtil.p("Action-FriendOtherList");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(43, option);
		
		return FcGain.GainPlayerList("body/player_search/user_list", pakFile);	
	}
	protected List<DcPlayer> FriendSearch(String name) throws Exception
	{
		FcUtil.p("Action-FriendSearch");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(44, option, name);
		
		return FcGain.GainPlayerList("body/player_search/user_list", pakFile);	
	}
	protected void FriendInfo(String fUID) throws Exception
	{
		FcUtil.p("Action-FriendInfo");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(45, option, "1", String.valueOf(fUID));
	}
	protected boolean FriendInvite(String uid) throws Exception
	{
		FcUtil.p("Action-FriendInvite");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(46, option, "1", String.valueOf(uid));

		return FcGain.GainFriendInvite(pakFile);
	}
	protected boolean FriendApprove(String uid) throws Exception
	{
		FcUtil.p("Action-FriendApprove");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(47, option, "1", uid);
		
		return FcGain.GainFriendInvite(pakFile);	
	}
	protected boolean FriendRemove(String idFriend) throws Exception
	{
		FcUtil.p("Action-FriendRemove");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(48, option, "1", idFriend);
		
		return FcGain.GainFriendRemove(pakFile);
	}
	
	protected List<DcArea> AreaList() throws Exception
	{
		FcUtil.p("Action-AreaList");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(50, option);
		
		return FcGain.GainAreaList(arthur, pakFile);
	}
	protected DcArea FloorList(DcArea area) throws Exception
	{
		FcUtil.p("Action-FloorList");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(51, option, String.valueOf(area.idArea));
		
		return FcGain.GainFloors(area, pakFile);
	}
	protected DcFloor Floor(DcArea area, String idFloor) throws Exception
	{
		FcUtil.p("Action-Floor");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(52, option, area.idArea, "1", idFloor);
		
		return FcGain.GainFloor(area, pakFile);
	}
	protected DcResultExplore Explore(DcArea area, String idFloor) throws Exception
	{
		FcUtil.p("Action-Explore");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(53, option, area.idArea, "1", idFloor);

		return FcGain.GainExplore(arthur, area, pakFile);
	}
	protected void ExploreJ(DcArea area, String idFloor) throws Exception
	{
		FcUtil.p("Action-ExploreJ");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(53, option, area.idArea, "0", idFloor);
	}
	
	protected void Tutorial(String step) throws Exception
	{
		FcUtil.p("Action-Tutorial");
		
		FcOption option = new FcOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(90, option, cookie.split("=")[1], step);
	}
}
