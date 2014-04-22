package org.danor.matookit;

import org.dom4j.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FcGain
{
	public static String GainError(FcXml xml) throws Exception
	{
		return xml.value("header>error>code");
	}
	public static DcRevision GainRevision(File xmlFile) throws Exception
	{
		FcXml xml = new FcXml(xmlFile);
		
		xml.move("header>revision");
		
		DcRevision rev = new DcRevision();
		
		rev.revCard = xml.value("card_rev");
		rev.revBoss = xml.value("boss_rev");
		rev.revItem = xml.value("item_rev");
		rev.revCardCategory = xml.value("card_category_rev");
		rev.revGacha = xml.value("gacha_rev");
		rev.revPrivilege = xml.value("privilege_rev");
		rev.revCombo = xml.value("combo_rev");
		rev.revBannner = xml.value("eventbanner_rev");
		
    	for(Object e:xml.list("resource_rev"))
    	{
    		xml.set((Element)e);
    		
    		switch(xml.value("filename"))
    		{
    		case "res":
    			rev.ResRes = xml.value("revision");
    			break;
    		case "sound":
    			rev.ResSound = xml.value("revision");
    			break;
    		case "advbg":
    			rev.ResAdvbg = xml.value("revision");
    			break;    	
    		case "cmpsheet":
    			rev.ResCmpsheet = xml.value("revision");
    			break;    		
    		case "gacha":
    			rev.ResGacha = xml.value("revision");
    			break;
    		case "privilege":
    			rev.ResPrivilege = xml.value("revision");
    			break;
    		case "eventbanner":
    			rev.ResBannner = xml.value("revision");
    			break;
    		}
    	}
		
		return rev;
	}
	
	protected static void GainPoint(DcPoint points, File xmlFile) throws Exception
	{
		FcXml xml = new FcXml(xmlFile);
		
	    xml.move("header>your_data>ap");
    	points.nowAP = Integer.parseInt(xml.value("current"));
		points.maxAP = Integer.parseInt(xml.value("max"));
		points.revAP = Integer.parseInt(xml.value("current_time"));
		points.revAP = points.revAP - Integer.parseInt(xml.value("last_update_time"));
		
	    xml.move("<bc");
    	points.nowBC = Integer.parseInt(xml.value("current"));
		points.maxBC = Integer.parseInt(xml.value("max"));
		points.revBC = Integer.parseInt(xml.value("current_time"));
		points.revBC = points.revBC - Integer.parseInt(xml.value("last_update_time"));
		
		FcUtil.p("Gain-Point");
	}
	protected static DcInfo GainInfo(File xmlFile) throws Exception
	{
		FcXml xml = new FcXml(xmlFile);
		
	    DcInfo info = new DcInfo();
	    
    	info.uid = xml.value("body>login>user_id");
	    info.hasReward = xml.value("body>mainmenu>rewards").equals("1");
	    
	    xml.move("header>your_data");
	    
	    info.name = xml.value("name");
	    info.level = xml.value("town_level");
		info.country = xml.value("country_id");
		
		info.gold = xml.value("gold");
		info.mc = xml.value("cp");
		info.frdPoint = xml.value("friendship_point");
		info.gachatk = xml.value("gacha_ticket");
		info.gachapt = xml.value("gacha_point");
		
		info.exgauge = xml.value("ex_gauge");
		info.ptsFree = xml.value("free_ap_bc_point");
		
		info.frdCount = xml.value("friends");
		info.frdMax = xml.value("friend_max");
		info.frdInvitation = xml.value("friends_invitations");
		
		info.hasFairy = xml.value("fairy_appearance").equals("1");
		
		FcUtil.p("Gain-Info");
	    
	    return info;
	}
	protected static List<DcCard> GainCards(File xmlFile) throws Exception
	{
		FcXml xml = new FcXml(xmlFile);

		List<DcCard> list = new ArrayList<DcCard>();
	    
	    xml.move("header>your_data>owner_card_list");
	    
		for(Object e:xml.list("user_card"))
			list.add(GainCard((Element)e));
	    
		FcUtil.p("Gain-Cards");
		
	    return list;
	}
	protected static List<DcItem> GainItems(File xmlFile) throws Exception
    {
		FcXml xml = new FcXml(xmlFile);

    	List<DcItem> list = new ArrayList<DcItem>();
    	
    	xml.move("header>your_data");
    	
    	for(Object e:xml.list("itemlist"))
    	{
    		DcItem item = new DcItem();
    		
    		xml.set((Element)e);
    		item.iid = xml.value("item_id");
    		item.count = xml.value("num");
    		
    		list.add(item);
    	}
    
    	FcUtil.p("Gain-Items");
	    return list;
    }

	protected static void GainFairyList(File xmlFile, DcArthur arthur) throws Exception
	{
		FcXml xml = new FcXml(xmlFile);
		
    	xml.move("body>fairy_select");
    	
    	arthur.info.cntRewardCard = xml.value("remaining_rewards");

		if(arthur.fairys == null)
			arthur.fairys = new ArrayList<DcFairy>();
		else
			for(DcFairy f:arthur.fairys)
				f.idOnList = false;
		
    	for(Object e:xml.list("fairy_event"))
    	{
    		xml.set((Element)e);
    		xml.move("fairy");
    		
    		String sid = xml.value("serial_id");
    		
    		DcFairy fairy = null;

    		for(DcFairy f:arthur.fairys)
    			if(f.sid.equals(sid))
    			{
    				fairy = f;
    				break;
    			}

    		if(fairy == null)
    			fairy = new DcFairy();
    		
    		fairy.idOnList = true;
    		fairy.sid = sid;
    		fairy.typRace = xml.value("race_type");
    		fairy.name = xml.value("name");
    		fairy.level = xml.value("lv");
    		fairy.time = xml.value("time_limit");
    		
    		fairy.user = new DcPlayer();
    		
    		xml.set((Element)e);
    		fairy.user = GainPlayer(xml.child("user"));

    		fairy.livestat = xml.value("put_down");
    		fairy.start = xml.value("start_time");
    		fairy.rewardstat = xml.value("reward_status");
    		
    		if(arthur.fairys.indexOf(fairy) == -1)
    			arthur.fairys.add(fairy);
    	}
    	
    	List<DcFairy> lstOutFairy  = new ArrayList<DcFairy>();
		for(DcFairy f:arthur.fairys)
			if(!f.idOnList)
				lstOutFairy.add(f);
		
		for(DcFairy f:lstOutFairy)		
			arthur.fairys.remove(f);
	}
	protected static DcFairy GainFairyInfo(File xmlFile, DcFairy fairy, DcArthur arthur) throws Exception
	{
		FcXml xml = new FcXml(xmlFile);
		
    	xml.move("body>fairy_floor>explore>fairy");
    	
    	fairy.HP = xml.value("hp");
    	fairy.maxHP = xml.value("hp_max");
    	fairy.lstHisFighter = new ArrayList<DcHisFighter>();

    	xml.move("attacker_history");
    	for(Object e:xml.list("attacker"))
    	{
    		DcHisFighter hisFighter = new DcHisFighter();
    		
    		xml.set((Element)e);
    		hisFighter.uid = xml.value("user_id");
    		hisFighter.name = xml.value("user_name");
    		hisFighter.fightHP = xml.value("attack_point");
    		hisFighter.cntFight = xml.value("attack_times");
    		
    		if(hisFighter.uid.equals(arthur.info.uid))
    			fairy.isFighted = true;
    		
    		fairy.lstHisFighter.add(hisFighter);
    	}
    	
    	return fairy;
	}
	protected static DcResultFight GainFairyFight(DcFairy fairy, DcArthur arthur, File xmlFile) throws Exception
	{
		FcXml xml = new FcXml(xmlFile);
		
		DcResultFight result = new DcResultFight();
		
		if((result.error = GainError(xml)).equals("0"))
		{
			GainPoint(arthur.points, xmlFile);
			arthur.points.timer.reset();

			GainCards(xmlFile);
			GainItems(xmlFile);

			
			xml.move("header>your_data");

			arthur.info.level = xml.value("town_level");		
			arthur.info.gold = xml.value("gold");	
			
			xml.move(".body>battle_battle");
			
			for(Object e:xml.list("battle_player_list"))
	    	{
				xml.set((Element)e);
	    		if(xml.value("player_enemy").equals("1"))
	    			result.bfrHP = xml.value("hp");
	    	}
			
			result.aftHP = xml.value(".body>explore>fairy>hp");
	
			xml.move(".body>battle_result");
	    	
			arthur.info.exp = xml.value("after_exp");
			result.gold = String.valueOf(Integer.parseInt(xml.value("after_gold")) - Integer.parseInt(xml.value("before_gold")));
			result.exp = String.valueOf(Integer.parseInt(arthur.info.exp) - Integer.parseInt(xml.value("before_exp")));
			result.isLvup = xml.value("before_level").equals(xml.value("after_level"));
			
			if((xml.value("special_item")) != null)
				for(Object e:xml.list("attacker"))
		    	{
					xml.set((Element)e);
		    		boolean flag = false;
		    		for(DcItem i:arthur.items)
		    			if(xml.value("item_id").equals(i.iid))
		    			{
		    				i.count = xml.value("after_count");
		    				i.bfcount = xml.value("before_count");
		    				flag = !flag;
		    			}
		    		
		    		if(!flag)
		    		{
		    			DcItem newItem = new DcItem();
		    			newItem.iid = xml.value("item_id");
	    				newItem.count = xml.value("after_count");
	    				newItem.bfcount = xml.value("before_count");
		    			
		    			arthur.items.add(newItem);
		    		}
		    	}
			
	    	return result;
		}
		
		return null;
	}

	protected static List<DcReward> GainRewardList(File xmlFile) throws Exception
	{
		FcXml xml = new FcXml(xmlFile);
		
    	List<DcReward> rewardlist = new ArrayList<DcReward>();

    	xml.move("body>rewardbox_list");
    	for(Object e:xml.list("rewardbox"))
    	{
    		DcReward reward = new DcReward();
    		
    		xml.set((Element)e);
    		reward.rid = xml.value("id");
    		reward.type = xml.value("type");
    		reward.title = xml.value("title");
    		reward.content = xml.value("content");
    		reward.date = xml.value("date");
    		
    		switch(reward.type)
    		{
    		case "1"://card
    			reward.cid = xml.value("card_id");
    			break;
    		case "3"://gold
    		case "4"://fspoint
    			reward.count = xml.value("point");
    			break;
    		case "2"://item
    			reward.iid =  xml.value("item_id");
    		case "5"://gachatk
    			reward.count = xml.value("get_num");
    		}
    		
    		rewardlist.add(reward);
    	}
    	
    	
    	
    	return rewardlist;
	}

	protected static boolean GainFriendInvite(File xmlFile) throws Exception
	{
		FcXml xml = new FcXml(xmlFile);
    	
		return xml.value("body>friend_act_res>success").equals("1");
	}
	protected static List<DcPlayer> GainPlayerList(String xPath, File xmlFile) throws Exception
	{
		FcXml xml = new FcXml(xmlFile);
		
		List<DcPlayer> lstPlayer = new ArrayList<DcPlayer>();
		
		xml.move(xPath);
		
    	for(Object e:xml.list("user"))
    	{
    		DcPlayer player = new DcPlayer();
    		player.leader = new DcCard();
    		
    		lstPlayer.add(GainPlayer((Element)e));
    	}

    	
    	
		return lstPlayer;
	}
	protected static boolean GainFriendRemove(File pakFile) throws Exception
	{
		return new FcXml(pakFile).value("body>friend_act_res>success").equals("1");
	}
	
	protected static List<DcArea> GainAreaList(DcArthur arthur, File xmlFile) throws Exception
	{
		FcXml xml = new FcXml(xmlFile);
		
		List<DcArea> lstArea = new ArrayList<DcArea>();
		
		xml.move("body>exploration_area>area_info_list");
		
    	for(Object e:xml.list("area_info"))
    	{
    		DcArea area = new DcArea();
    		
    		xml.set((Element)e);
    		area.idArea = xml.value("id");
    		area.name = xml.value("name");
    		area.prgArea = xml.value("prog_area");
    		area.prgItem = xml.value("prog_item");
    		area.isEvent = xml.value("area_type").equals("1");
    		
    		lstArea.add(area);
    	}
    	arthur.areas = lstArea;

    	return lstArea;
	}
	protected static DcArea GainFloors(DcArea area, File xmlFile) throws Exception
	{
		FcXml xml = new FcXml(xmlFile);
		
		area.floors = new ArrayList<DcFloor>();
		
		xml.move("body>exploration_floor>floor_info_list");
		
    	for(Object e:xml.list("floor_info"))
    		area.floors.add(GainFloor((Element)e));
    	
		return area;
	}
	protected static DcFloor GainFloor(DcArea area, File xmlFile) throws Exception
	{
		FcXml xml = new FcXml(xmlFile);
		
		DcFloor floor = GainFloor(xml.move("body>get_floor>floor_info"));
		
		floor.hasNext = xml.move(".body>get_floor>next_floor") != null;
		
		boolean hasSameFloor = false;
		for(DcFloor f:area.floors)
			if(f.idFloor.equals(floor.idFloor))
			{
				hasSameFloor = true;
				break;
			}
		
		if(!hasSameFloor)
			area.floors.add(floor);
		
		return floor;
	}
	protected static DcFloor GainFloor(Element e) throws Exception
	{
		FcXml xml = new FcXml(null);
		
		xml.set(e);
		
    	DcFloor floor = new DcFloor();
    		
		floor.idFloor = xml.value("id");
		if(floor.type = (xml.value("type").equals("0")))
		{
    		floor.prog = xml.value("progress");
    		floor.cost = xml.value("cost");
    		
    		xml.move("found_item_list");
    		int cntCard = 0;
    		for(Object e2:xml.list("found_item"))
    		{
    			xml.set((Element)e2);
    			if(xml.value("type").equals("1"))
    			{
    				floor.idCard[cntCard++] = xml.value("user_card>master_card_id");
    				floor.unlock[cntCard] = xml.value("unlock").equals("0")?false:true;
    			}
    			else
    			{
    				floor.hasFragment = true;
    				floor.unlock[3] = xml.value("unlock").equals("0")?false:true;
    			}
    		}
		}
		else
			floor.idFairy = xml.value("boss_id");
    	
		return floor;
	}
	protected static DcResultExplore GainExplore(DcArthur arthur, DcArea area, File xmlFile) throws Exception
	{
		FcXml xml = new FcXml(xmlFile);
		
		DcResultExplore result = new DcResultExplore();
		
		GainPoint(arthur.points, xmlFile);
		arthur.points.timer.reset();

		if(xml.value("header>your_data>owner_card_list") != null)
			arthur.cards = GainCards(xmlFile);
		
		arthur.items = GainItems(xmlFile);
		
		xml.move("header>your_data");
		arthur.info.gold = xml.value("gold");
		arthur.info.frdPoint = xml.value("friendship_point");
		arthur.info.level = xml.value("town_level");
	    
		xml.move(".body>explore");
		
		result.prog = xml.value("progress");
		result.type = xml.value("event_type");
		result.hasNextFloor = (!xml.value("next_floor").equals(""))&&(!xml.value("next_floor>floor_info>type").equals("1"));
		
		if(!result.type.equals("6"))
		{
			result.isLvup = (xml.value("lvup").equals("1"));
			result.gold = xml.value("gold");
			result.exp = xml.value("get_exp");
			arthur.info.exp = xml.value("next_exp");
		}
		
		switch(result.type)
		{
		case "13":
		case "12":
			result.recover = xml.value("recover");
			break;
		case "19":
			if(!(result.idItem = xml.value("special_item>item_id")).equals(""))
				result.amtItem = String.valueOf(Integer.parseInt(xml.value("special_item>after_count")) - Integer.parseInt(xml.value("special_item>before_count")));
			else
				result.type = "0";
			break;
		case "2":
			result.frdPoint = xml.value("friendship_point");
			result.encounter = GainPlayer(xml.child("encounter"));
			break;
		case "1":
			result.fairy = GainFairy(xml.child("fairy"));
			result.fairy.user = new DcPlayer();
			result.fairy.user.uid = String.valueOf(arthur.info.uid);
			break;
		case "3":
			result.newCard = GainCard(xml.child("user_card"));
			break;
		case "15":
			int i = 0;
			for(Object e:xml.list("autocomp_card"))
				switch(i++)
				{
				case 0:
					result.aftCard = GainCard((Element)e);
					break;
				case 1:
					result.newCard = GainCard((Element)e);
					break;
				case 2:
					result.bfrCard = GainCard((Element)e);
					break;
				}
			break;
		case "5":
			if(result.hasNextFloor)
				area.floors.add(GainFloor(xml.move("next_floor>floor_info")));
			break;
		}
	
		if((xml.value("special_item")) != null)
			for(Object e:xml.list("attacker"))
			{
				xml.set((Element)e);
				boolean flag = false;
				for(DcItem i:arthur.items)
					if(xml.value("item_id").equals(i.iid))
					{
						i.count = xml.value("after_count");
						i.bfcount = xml.value("before_count");
						flag = !flag;
					}
				
				if(!flag)
				{
					DcItem newItem = new DcItem();
					newItem.iid = xml.value("item_id");
					newItem.count = xml.value("after_count");
					newItem.bfcount = xml.value("before_count");
					
					arthur.items.add(newItem);
				}
			}
		
		return result;
	}

	private static DcCard GainCard(Element e) throws Exception
	{
		FcXml xml = new FcXml(null);
		xml.set(e);
		
		DcCard card = new DcCard();
		card.sid = xml.value("serial_id");
		card.mid = xml.value("master_card_id");
		card.holo = xml.value("holography");
		card.hp = xml.value("hp");
		card.atk = xml.value("power");
		card.level = xml.value("lv");
		card.levelmax = xml.value("lv_max");
		card.exp = xml.value("exp");
		card.expmax = xml.value("max_exp");
		card.expnxt = xml.value("next_exp");
		card.salep = xml.value("sale_price");
		card.mtrlp = xml.value("material_price");
		card.evltp = xml.value("evolution_price");
		card.plc = xml.value("plus_limit_count");
		card.limitover = xml.value("limit_over");
		
		return null;
	}
	private static DcFairy GainFairy(Element e) throws Exception
	{
		FcXml xml = new FcXml(null);
		xml.set(e);
		
		DcFairy fairy = new DcFairy();
		
		fairy.sid = xml.value("serial_id");
		fairy.typRace = xml.value("race_type");
		fairy.mid = xml.value("master_boss_id");
		fairy.name = xml.value("name");
		fairy.level = xml.value("lv");
    	fairy.HP = xml.value("hp");
    	fairy.maxHP = xml.value("hp_max");
    	fairy.time = xml.value("time_limit");

    	return fairy;
	}
	private static DcPlayer GainPlayer(Element e) throws Exception
	{
		FcXml xml = new FcXml(null);
		xml.set(e);
		
		DcPlayer player = new DcPlayer();
		
		player.uid = xml.value("id");
		player.name = xml.value("name");
		player.country = xml.value("country_id");
		player.cost = xml.value("cost");
		player.win = xml.value("results>win");
		player.lose = xml.value("results>lose");
		player.level = xml.value("town_level");
		player.exp = xml.value("next_exp");
		player.fCount = xml.value("friends");
		player.fMax = xml.value("friend_max");
		player.lastLogin = xml.value("last_login");
		player.exGauge = xml.value("ex_gage");
		player.cMax = xml.value("max_card_num");
		player.fStatus = xml.value("status_friend");

		xml.move("leader_card");
		player.leader = new DcCard();
		player.leader.mid = xml.value("master_card_id");
		player.leader.holo = xml.value("holography");
		player.leader.hp = xml.value("hp");
		player.leader.atk = xml.value("power");
		player.leader.level = xml.value("lv");
		
		return player;
	}

	protected static List<String[]> GainRankP(File xmlFile) throws Exception
	{
		List<String[]> lstRank = new ArrayList<>();
		
		FcXml xml = new FcXml(xmlFile);
		
		xml.move("body>ranking>user_list");
		
    	for(Object e:xml.list("user"))
    	{
    		String[] rank = new String[4];
    		
    		xml.set((Element)e);
    		
    		rank[0] = xml.value("id");
    		rank[1] = xml.value("name");
    		rank[2] = xml.value("rank");
    		rank[3] = xml.value("battle_event_point");
    		
    		lstRank.add(rank);
    	}
		
		return lstRank;
	}
}