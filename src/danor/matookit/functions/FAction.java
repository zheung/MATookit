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

	private final boolean isCookie;
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
			.put("param", (String)null).put("path", header+"Cookie.xml");
			
			String[][] pp = new String[6][2];
			for(int i=0;i<6;i++)
				pp[i] = UUtil.Key(server.fileArb(), "Property", i+"");
			option.put("property", pp);
			
			UConnect connect = new UConnect(option);
			File pakFile = connect.result;
			
			UConvert.decryptAES(null, pakFile, UUtil.Key(server.fileArb(), "Cipher", "Cok")[0].getBytes());
			UConvert.decodeUrl(null, pakFile);
			
			this.cookie = connect.cookie;
			isCookie = false;

			ULog.log("Result-"+this.cookie);
		}
		else
		{
			this.cookie = cookie;
			isCookie = true;
		}
	}
//工具
	/**
	*@param option typMethod, rqtDecryptParam, rqtDecryptFile, rqtFormatFile
	*/
	public File Connect(String typAction, UOption option, byte[]...prmValues) throws Exception 
	{
		String[] values = UUtil.Key(server.fileArb(), "Action", typAction);

		if(typAction.equals("Update"))
		{
			values[0] = values[0].replace("(jiayi)", new String(prmValues[0]));
			
			byte[][] tmpValues = prmValues;
			
			prmValues = new byte[tmpValues.length][];
			
			for(int i=1;i<tmpValues.length;i++)
				prmValues[i-1] = tmpValues[i];
		}
		
		UParam param = new UParam(((server.isCN() || server == FServer.TW1) && typAction.equals("Login")), UUtil.Key(server.fileArb(), "Cipher", "Prm")[0]);
		if(!values[1].equals(""))
		{
			String[] prmNames = values[1].split(";");
			
			int i = 0;
			for(String n:prmNames)
				param.put(n, prmValues[i++]);
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
			pakFile = Connect("Login", option, phone.getBytes(), paswd.getBytes());
		else
		{
			String param = "35";
			for(int i=0; i<10; i++) param+=(int)(10*(Math.random()));
			
			pakFile = Connect("Login", option, phone.getBytes(), paswd.getBytes(), param.getBytes());
		}

		NArthur arthur = null;
		switch(FGain.GainError(new UXml(pakFile)))
		{
		case "0":
			arthur = FGain.GainArthur(pakFile, null);
			rev = FGain.GainRevision(pakFile);
			
			ULog.log("Result-"+phone+"-Success");
			break;
		case "1000":
			ULog.log("Result-"+phone+"-Failed-Wrong.Phone.or.Password");
		}
		
		this.arthur = arthur;
		return arthur;
	}
	public NArthur Login() throws Exception
	{
		if(isCookie)
		{
			ULog.log("Action-Login-By-"+cookie);
			Menu();
			Home(true);
			
			return arthur;
		}
		
		return null;
	}
	
	public File Update(String typRes, String rev)
	{
		UUtil.p("Action-Update-"+typRes);
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		
		File pakFile = null;
		try	{ pakFile = Connect("Update", option, typRes.getBytes(), cookie.getBytes(), rev.getBytes()); }
		catch (Exception e) { e.printStackTrace(); }
		
		File renameFile = new File(pakFile.getParentFile(), typRes+"-"+rev+".xml");
		
		pakFile.renameTo(renameFile);
		
		return renameFile;
	}
	
	public void Menu() throws Exception
	{
		UUtil.p("Action-Menu");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("Menu", option);
	}
	public void Home(boolean isInit) throws Exception
	{
		UUtil.p("Action-Home");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("Home", option);

		if(isInit)
			arthur = FGain.GainArthur(pakFile, null);
		else
			FGain.GainArthur(pakFile, arthur);
	}
	public void Collection() throws Exception
	{
		UUtil.p("Action-Collection");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("Collection", option);
	}
	public void Storage() throws Exception
	{
		UUtil.p("Action-Storage");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("Storage", option);
	}
	public void Info() throws Exception
	{
		UUtil.p("Action-Info");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("Info", option, "6".getBytes(), String.valueOf(arthur.base().idArthur()).getBytes());
		
		FGain.GainInfo(pakFile, arthur);
		
		rev = FGain.GainRevision(pakFile);
	}
	public void PointSet(int addAP, int addBC) throws Exception
	{
		UUtil.p("Action-PointSet");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("PointSet", option, String.valueOf(addAP).getBytes(), String.valueOf(addBC).getBytes());
	}
	public void ItemUse(String idItem) throws Exception
	{
		UUtil.p("Action-ItemUse-"+idItem);
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("ItemUse", option, idItem.getBytes());
	}
	public void Rank(boolean top, String idRank) throws Exception
	{
		UUtil.p("Action-Rank");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("Rank", option, "1".getBytes(), idRank.getBytes(), top?"1".getBytes():"0".getBytes());//1;;0:非最高,1:最高
	}
	public List<String[]> RankP(String from, String idRank) throws Exception
	{
		UUtil.p("Action-RankP");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("RankP", option, from.getBytes(), idRank.getBytes());//最低的iduser...
		
		return FGain.GainRankP(pakFile);
	}
	public List<String[]> RankN(String from, String idRank) throws Exception
	{
		UUtil.p("Action-RankN");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("RankN", option, from.getBytes(), idRank.getBytes());//最低的iduser...
		
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
		File pakFile = Connect("FairyInfo", option, "1".getBytes(), fairy.typRace().getBytes(), fairy.idSerial().getBytes(), fairy.finder().idArthur().getBytes());

		FGain.GainFairyInfo(pakFile, fairy, arthur);
	}
	public NBattleResult FairyFight(NFairy fairy) throws Exception
	{
		UUtil.p("Action-FairyFight");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FairyFight", option, fairy.typRace().getBytes(), fairy.idSerial().getBytes(), fairy.finder().idArthur().getBytes());

		return FGain.GainFairyFight(fairy, arthur, pakFile);
	}
	public void FairyLose(NFairy fairy) throws Exception
	{
		UUtil.p("Action-FairyLose");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("FairyLose", option, fairy.typRace().getBytes(), fairy.idSerial().getBytes(), fairy.finder().idArthur().getBytes());
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
		Connect("RewardGain", option, ids.getBytes());
	}

	public void FriendList(NArthur arthur) throws Exception
	{
		UUtil.p("Action-FriendList");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FriendList", option, "0".getBytes());
		
		FGain.GainFairyList(pakFile, arthur);	
	}
	public NMatches FriendNotice() throws Exception
	{
		UUtil.p("Action-FriendNotice");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FriendNotice", option, "0".getBytes());
		
		return FGain.GainMatches("body>friend_notice>user_list", pakFile);	
	}
	public NMatches FriendInvitation() throws Exception
	{
		UUtil.p("Action-FriendInvitation");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FriendInvitation", option, "0".getBytes());
		
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
		File pakFile = Connect("FriendSearch", option, name.getBytes());
		
		return FGain.GainMatches("body/player_search/user_list", pakFile);	
	}
	public void FriendInfo(String fUID) throws Exception
	{
		UUtil.p("Action-FriendInfo");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("FriendInfo", option, "1".getBytes(), String.valueOf(fUID).getBytes());
	}
	public boolean FriendInvite(String uid) throws Exception
	{
		UUtil.p("Action-FriendInvite");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FriendInvite", option, "1".getBytes(), String.valueOf(uid).getBytes());

		return FGain.GainFriendInvite(pakFile);
	}
	public boolean FriendApprove(String uid) throws Exception
	{
		UUtil.p("Action-FriendApprove");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FriendApprove", option, "1".getBytes(), uid.getBytes());
		
		return FGain.GainFriendInvite(pakFile);	
	}
	public boolean FriendRemove(String idFriend) throws Exception
	{
		UUtil.p("Action-FriendRemove");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("FriendRemove", option, "1".getBytes(), idFriend.getBytes());
		
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
		File pakFile = Connect("FloorList", option, String.valueOf(area.idArea()).getBytes());
		
		FGain.GainFloors(pakFile, area);
	}
	public NFloor Floor(NArea area, String idFloor) throws Exception
	{
		UUtil.p("Action-Floor");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("Floor", option, area.idArea().getBytes(), "1".getBytes(), idFloor.getBytes());
		
		return FGain.GainFloor(area, pakFile);
	}
	public NExploreResult Explore(NArea area, String idFloor) throws Exception
	{
		UUtil.p("Action-Explore");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		File pakFile = Connect("Explore", option, area.idArea().getBytes(), "1".getBytes(), idFloor.getBytes());

		return FGain.GainExplore(arthur, area, pakFile);
	}
	public void ExploreJ(NArea area, String idFloor) throws Exception
	{
		UUtil.p("Action-ExploreJ");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("ExploreJ", option, area.idArea().getBytes(), "0".getBytes(), idFloor.getBytes());
	}
	
	public void Tutorial(String step) throws Exception
	{
		UUtil.p("Action-Tutorial");
		
		UOption option = new UOption().put("typMethod", true).put("rqtDecryptParam", true).put("rqtDecryptFile", true).put("rqtFormatFile", true);
		Connect("Tutorial", option, cookie.split("=")[1].getBytes(), step.getBytes());
	}

	public NArthur arthur() { return arthur; }
	public NRevision rev() { return rev; }
	public FServer server() { return server; }
}