package danor.matookit.functions;

import java.io.File;
import java.util.List;

import danor.matookit.natures.*;
import danor.matookit.utils.*;

public class FAction
{
	private final UKey db;
	private final ULog log;

	private final String header;
	private final String sUrl;

	public final String cookie;
	
	public NArthur arthur;
	
	public FAction(boolean rqtTime, String cookie) throws Exception
	{
		this.db = UKey.getInstance();
		this.log = ULog.getInstance();

		ULog.log("Action-Init");
		
		File dir = new File("./wrk/pak");
		if(!dir.exists()) dir.mkdir();//ToEH

		header = "./wrk/pak/" + (rqtTime?(Long.toString(System.currentTimeMillis()) + "-"):"");
		sUrl = db.Data("Server", 1)[1];

		if(cookie == null)
		{
			ULog.log("Action-Cookie");
			String[] values = db.Data("Action", 0);
			
			UOption option = new UOption().put("rqtCookie", true).put("typMethod", true)
			.put("cookie", (String)null).put("url", sUrl+values[2]).put("param", (String)null).put("path", header+"1Cookie.xml");
			
			
			UConnect connect = new UConnect(option, db, log);
			File pakFile = connect.result;
			
			UConvert.decryptAES(null, pakFile, db.Data("Cipher", 3)[2].getBytes());
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
	public File Connect(int typConnect, UOption option, String...prmValues) throws Exception 
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
		
		UParam param = new UParam((typConnect == 2),db);
		if(!values[3].equals(""))
		{
			String[] prmNames = values[3].split(";");
			
			int i = 0;
			for(String n:prmNames)
				param.put(n, prmValues[i++].getBytes());
		}
		
		UOption newOption = new UOption().put("rqtCookie", false).put("typMethod", true)
		.put("cookie", this.cookie).put("url", sUrl+values[2]).put("param", param.get(option.getBoolean("rqtDecryptParam"))).put("path", header+values[0]+values[1]+".xml");
		
		UConnect connect = new UConnect(newOption, db, log);
		
		if(option.getBoolean("rqtDecryptFile"))
			UConvert.decryptAES(null, connect.result, db.Data("Cipher", 0)[2].getBytes("utf-8"));

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
		ULog.log("Action-Login-"+phone);
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(2, option, phone, paswd);
		
		NArthur arthur = null;
		switch(FGain.GainError(new UXml(pakFile)))
		{
		case "0":
			arthur = FGain.GainArthur(pakFile);
			
			ULog.log("Relust-"+phone+"-Success");
			break;
		case "1000":
			ULog.log("Relust-"+phone+"-Failed-Wrong.Phone.or.Password");
		}
		
		this.arthur = arthur;
		return arthur;
	}
	
	public File Update(String kind,String rev) throws Exception
	{
		UUtil.p("Action-Update-"+kind);
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", kind.equals("card")?false:true);
		
		File pakFile = Connect(3, option, kind, cookie, rev);
		
		File renameFile = new File(pakFile.getParent()+"/"+kind+"-"+rev+".xml");
		
		pakFile.renameTo(renameFile);
		
		return renameFile;
	}
	
	public void Menu() throws Exception
	{
		UUtil.p("Action-Menu");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(10, option);
	}
	public NRevision Home() throws Exception
	{
		UUtil.p("Action-Home");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(11, option);

		FGain.GainBase(pakFile, arthur);
		FGain.GainPoint(pakFile, arthur);
		FGain.GainItems(pakFile, arthur);
		
		return FGain.GainRevision(pakFile);
	}
	public void Collection() throws Exception
	{
		UUtil.p("Action-Collection");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(12, option);
	}
	public void Info() throws Exception
	{
		UUtil.p("Action-Info");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(13, option, "6", String.valueOf(arthur.base().idArthur()));
	}
	public void PointSet(int addAP, int addBC) throws Exception
	{
		UUtil.p("Action-PointSet");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(14, option, String.valueOf(addAP), String.valueOf(addBC));
	}
	public void ItemUse(String idItem) throws Exception
	{
		UUtil.p("Action-ItemUse-"+idItem);
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(15, option, idItem);
	}
	public void Rank(boolean top, String idRank) throws Exception
	{
		UUtil.p("Action-Rank");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(16, option, "1", idRank, top?"1":"0");//1;;0:非最高,1:最高
	}
	public List<String[]> RankP(String from, String idRank) throws Exception
	{
		UUtil.p("Action-RankP");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(17, option, from, idRank);//最低的iduser...
		
		return FGain.GainRankP(pakFile);
	}
	public List<String[]> RankN(String from, String idRank) throws Exception
	{
		UUtil.p("Action-RankN");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(18, option, from, idRank);//最低的iduser...
		
		return FGain.GainRankP(pakFile);
	}

	public void FairyList(boolean isGain) throws Exception
	{
		UUtil.p("Action-FairyList");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(20, option);
		
		if(isGain)
			FGain.GainFairyList(pakFile, arthur);
	}
	public void FairyInfo(NFairy fairy) throws Exception
	{
		UUtil.p("Action-FairyInfo");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(21, option, "1", fairy.typRace(), fairy.idSerial(), fairy.finder().idArthur());

		FGain.GainFairyInfo(pakFile, fairy, arthur);
	}
	public NBattleResult FairyFight(NFairy fairy) throws Exception
	{
		UUtil.p("Action-FairyFight");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(22, option, fairy.typRace(), fairy.idSerial(), fairy.finder().idArthur());

		return FGain.GainFairyFight(fairy, arthur, pakFile);
	}
	public void FairyLose(NFairy fairy) throws Exception
	{
		UUtil.p("Action-FairyLose");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(23, option, fairy.typRace(), fairy.idSerial(), fairy.finder().idArthur());
	}
	
	public void RewardList(NArthur arthur) throws Exception
	{
		UUtil.p("Action-RewardList");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(30, option);
		
		FGain.GainRewardList(pakFile, arthur);	
	}
	public void RewardGain(String ids) throws Exception
	{
		UUtil.p("Action-RewardGain");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(31, option, ids);
	}

	public void FriendList(NArthur arthur) throws Exception
	{
		UUtil.p("Action-FriendList");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(40, option, "0");
		
		FGain.GainFairyList(pakFile, arthur);	
	}
	public NMatches FriendNotice() throws Exception
	{
		UUtil.p("Action-FriendNotice");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(41, option, "0");
		
		return FGain.GainMatches("body>friend_notice>user_list", pakFile);	
	}
	public NMatches FriendInvitation() throws Exception
	{
		UUtil.p("Action-FriendInvitation");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(42, option, "0");
		
		return FGain.GainMatches("body>friend_appstate>user_ex", pakFile);	
	}
	public NMatches FriendOtherList() throws Exception
	{
		UUtil.p("Action-FriendOtherList");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(43, option);
		
		return FGain.GainMatches("body/player_search/user_list", pakFile);	
	}
	public NMatches FriendSearch(String name) throws Exception
	{
		UUtil.p("Action-FriendSearch");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(44, option, name);
		
		return FGain.GainMatches("body/player_search/user_list", pakFile);	
	}
	public void FriendInfo(String fUID) throws Exception
	{
		UUtil.p("Action-FriendInfo");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(45, option, "1", String.valueOf(fUID));
	}
	public boolean FriendInvite(String uid) throws Exception
	{
		UUtil.p("Action-FriendInvite");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(46, option, "1", String.valueOf(uid));

		return FGain.GainFriendInvite(pakFile);
	}
	public boolean FriendApprove(String uid) throws Exception
	{
		UUtil.p("Action-FriendApprove");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(47, option, "1", uid);
		
		return FGain.GainFriendInvite(pakFile);	
	}
	public boolean FriendRemove(String idFriend) throws Exception
	{
		UUtil.p("Action-FriendRemove");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(48, option, "1", idFriend);
		
		return FGain.GainFriendRemove(pakFile);
	}
	
	public void AreaList() throws Exception
	{
		UUtil.p("Action-AreaList");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(50, option);
		
		FGain.GainAreaList(pakFile, arthur);
	}
	public void FloorList(NArea area) throws Exception
	{
		UUtil.p("Action-FloorList");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(51, option, String.valueOf(area.idArea()));
		
		FGain.GainFloors(pakFile, area);
	}
	public NFloor Floor(NArea area, String idFloor) throws Exception
	{
		UUtil.p("Action-Floor");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(52, option, area.idArea(), "1", idFloor);
		
		return FGain.GainFloor(area, pakFile);
	}
	public NExploreResult Explore(NArea area, String idFloor) throws Exception
	{
		UUtil.p("Action-Explore");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect(53, option, area.idArea(), "1", idFloor);

		return FGain.GainExplore(arthur, area, pakFile);
	}
	public void ExploreJ(NArea area, String idFloor) throws Exception
	{
		UUtil.p("Action-ExploreJ");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(53, option, area.idArea(), "0", idFloor);
	}
	
	public void Tutorial(String step) throws Exception
	{
		UUtil.p("Action-Tutorial");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect(90, option, cookie.split("=")[1], step);
	}
}
