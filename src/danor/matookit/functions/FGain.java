package danor.matookit.functions;

import org.dom4j.Element;

import danor.matookit.middles.MPoint;
import danor.matookit.natures.*;
import danor.matookit.utils.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FGain
{
	public static String GainError(UXml xml) throws Exception
	{
		return xml.value("header>error>code");
	}

	public static NRevision GainRevision(File xmlFile) throws Exception
	{
		UXml xml = new UXml(xmlFile);
		
		xml.move("header>revision");
		
		NRevision rev = new NRevision();
		
		rev.revCrd(xml.value("card_rev"));
		rev.revBos(xml.value("boss_rev"));
		rev.revItm(xml.value("item_rev"));
		rev.revCtg(xml.value("card_category_rev"));
		rev.revGac(xml.value("gacha_rev"));
		rev.revPvl(xml.value("privilege_rev"));
		rev.revCmb(xml.value("combo_rev"));
		rev.revBan(xml.value("eventbanner_rev"));

		rev.revJob(xml.value("job_rev"));
		rev.revJbs(xml.value("jobskill_rev"));
		
		rev.revBan(xml.value("eventbanner_rev"));
		
    	for(Object e:xml.list("resource_rev"))
    	{
    		xml.set((Element)e);
    		
    		String resrev = xml.value("revision");
    		
    		switch(xml.value("filename"))
    		{
    		case "res":
    			rev.resRes(resrev);
    			break;
    		case "sound":
    			rev.resSou(resrev);
    			break;
    		case "advbg":
    			rev.resAdv(resrev);
    			break;    	
    		case "cmpsheet":
    			rev.resCmp(resrev);
    			break;    		
    		case "gacha":
    			rev.resGac(resrev);
    			break;
    		case "privilege":
    			rev.resPvl(resrev);
    			break;
    		case "eventbanner":
    			rev.resBan(resrev);
    			break;
    		case "treasurebox":
    			rev.resTsb(resrev);
    		}
    	}
    	
    	String pn = xml.value(".header>mainbg>pack_name");
    	if(pn != null)
    		rev.resMbg(pn.split("_")[pn.split("_").length-1]);
		
		return rev;
	}
	public static String GainImagedl(File xmlFile, String type) throws Exception
	{
		return new UXml(xmlFile).value("body>master_data>master_"+type+"_data>imagedl_list");
	}
	
	public static NArthur GainArthur(File xmlFile, NArthur arthur) throws Exception
	{
		UXml xml = new UXml(xmlFile);
		
		if(arthur == null)
		{
			arthur = new NArthur(
				new NBase(xml.value("body>login>user_id"), xml.value("header>your_data>name")),
				new NTown(xml.value("header>your_data>country_id")));

		}
		else
			GainCards(xmlFile, arthur);
		
		arthur.guild().idGuild(xml.value("header>your_data>guild_id"));
		arthur.guild().name(xml.value("header>your_data>guild_name"));
		
		GainBase(xmlFile, arthur);
		GainItems(xmlFile, arthur);
		GainPoint(xmlFile, arthur);
		
		return arthur;
	}
	public static void GainBase(File xmlFile, NArthur arthur) throws Exception
	{
		UXml xml = new UXml(xmlFile);
		
		arthur.town().hasReward(xml.value("body>mainmenu>rewards").equals("1"));

		xml.move("header>your_data");
		
	   	arthur.town().lv(xml.value("town_level"));
		arthur.base().gold(xml.value("gold"));
		arthur.shop().mc(xml.value("cp"));

		arthur.gacha().pointFriend(xml.value("friendship_point"));
		arthur.gacha().ticket(xml.value("gacha_ticket"));
		arthur.gacha().point(xml.value("gacha_point"));
		
		arthur.battle().sp(xml.value("ex_gauge"));
		arthur.point().free(xml.value("free_ap_bc_point"));
		
		arthur.friends().now(xml.value("friends"));
		arthur.friends().max(xml.value("friend_max"));
		arthur.friends().ivt(xml.value("friends_invitations"));
		
		arthur.town().hasFairy(xml.value("fairy_appearance").equals("1"));
		
		UUtil.p("Gain-Base");
	}
	public static void GainCards(File xmlFile, NArthur arthur) throws Exception
	{
		UXml xml = new UXml(xmlFile);

	    xml.move("header>your_data>owner_card_list");
	    
		for(Object e:xml.list("user_card"))
			arthur.cards().add(GainCard((Element)e));
	    
		UUtil.p("Gain-Cards");
	}
	public static void GainItems(File xmlFile, NArthur arthur) throws Exception
    {
		UXml xml = new UXml(xmlFile);

    	xml.move("header>your_data");
    	
    	List<?> el = xml.list("itemlist");
    	if(el != null)
	    	for(Object e:el)
	    	{
	    		xml.set((Element)e);
	    		
	    		NItem item = new NItem(xml.value("item_id"));
	    		
	    		item.now(xml.value("num"));
	    		
	    		arthur.items().add(item);
	    	}
    
    	UUtil.p("Gain-Items");
    }
	public static void GainPoint(File xmlFile, NArthur arthur) throws Exception
	{
		UXml xml = new UXml(xmlFile);
		
	    xml.move("header>your_data>ap");
	    arthur.point().nowAP(Integer.parseInt(xml.value("current")));
	    arthur.point().maxAP(Integer.parseInt(xml.value("max")));
	    arthur.point().revAP(Integer.parseInt(xml.value("current_time")));
	    arthur.point().revAP(arthur.point().revAP() - Integer.parseInt(xml.value("last_update_time")));
		
	    xml.move("<bc");
	    arthur.point().nowBC(Integer.parseInt(xml.value("current")));
	    arthur.point().maxBC(Integer.parseInt(xml.value("max")));
	    arthur.point().revBC(Integer.parseInt(xml.value("current_time")));
	    arthur.point().revBC(arthur.point().revBC() - Integer.parseInt(xml.value("last_update_time")));
		
	    MPoint.update();
	    
		UUtil.p("Gain-Point");
	}

	public static void GainFairyList(File xmlFile, NArthur arthur) throws Exception
	{
		UXml xml = new UXml(xmlFile);
		
    	xml.move("body>fairy_select");
    	
    	arthur.fairys().restReward(xml.value("remaining_rewards"));

		if(!arthur.fairys().isEmpty())
			for(NFairy f:arthur.fairys())
				f.onList(false);
		
    	for(Object e:xml.list("fairy_event"))
    	{
    		xml.set((Element)e);
    		xml.move("fairy");
    		
    		String idSerial = xml.value("serial_id");
    		
    		NFairy fairy = null;

    		for(NFairy f:arthur.fairys())
    			if(f.idSerial().equals(idSerial))
    			{
    				fairy = f;
    				break;
    			}

    		if(fairy == null)
    		{
    			NMatch match = GainMatch(xml.find("<user"));
    			fairy = new NFairy(match, idSerial, xml.value("race_type"), xml.value("name"), xml.value("lv"), xml.value("<start_time"));
    		}
    		
    		fairy.onList(true);
    		fairy.restTime(xml.value("time_limit"));

    		xml.move("<");
    		fairy.statAlive(xml.value("put_down"));
    		fairy.statReward(xml.value("reward_status"));
    		
    		arthur.fairys().add(fairy);
    	}
	}
	public static void GainFairyInfo(File xmlFile, NFairy fairy, NArthur arthur) throws Exception
	{
		UXml xml = new UXml(xmlFile);
		
    	xml.move("body>fairy_floor>explore>fairy");
    	
    	fairy.nowHP(xml.value("hp"));
    	fairy.maxHP(xml.value("hp_max"));

    	xml.move("attacker_history");
    	if((xml.value("attacker")) != null)
	    	for(Object e:xml.list("attacker"))
	    	{
	    		NBattleAttacker attacker = new NBattleAttacker(GainFairyAttacker((Element)e));
	    		
	    		xml.set((Element)e);
	    		attacker.attackHP(xml.value("attack_point"));
	    		attacker.attackNow(xml.value("attack_times"));
	    		
	    		if(attacker.match().idArthur().equals(arthur.base().idArthur()))
	    			fairy.battled(true);
	    		
	    		fairy.battleAttackers().add(attacker);
	    	}
    	
	}
	public static NMatch GainFairyAttacker(Element e) throws Exception
	{
		UXml xml = new UXml(null);
		xml.set(e);
		
		NMatch match = new NMatch(xml.value("user_id"), xml.value("user_name"), xml.value("country_id"));
		
		match.statYell(xml.value("status_yell"));
		match.statFriend(xml.value("status_friend"));

		return match;
	}
	public static NBattleResult GainFairyFight(NFairy fairy, NArthur arthur, File xmlFile) throws Exception
	{
		UXml xml = new UXml(xmlFile);
		
		NBattleResult result = new NBattleResult();
		
		if((result.error(GainError(xml))).equals("0"))
		{
			GainPoint(xmlFile, arthur);

			GainCards(xmlFile, arthur);
			GainItems(xmlFile, arthur);

			xml.move("header>your_data");

			arthur.town().lv(xml.value("town_level"));		
			arthur.base().gold(xml.value("gold"));	
			
			xml.move(".body>battle_battle");
			
			for(Object e:xml.list("battle_player_list"))
	    	{
				xml.set((Element)e);
	    		if(xml.value("player_enemy").equals("1"))
	    			result.bfrHP(xml.value("hp"));
	    	}
			
			result.aftHP(xml.value(".body>explore>fairy>hp"));
	
			xml.move(".body>battle_result");
	    	
			arthur.town().exp(xml.value("after_exp"));
			result.gold(String.valueOf(Integer.parseInt(xml.value("after_gold")) - Integer.parseInt(xml.value("before_gold"))));
			result.exp(String.valueOf(Integer.parseInt(arthur.town().exp()) - Integer.parseInt(xml.value("before_exp"))));
			result.lvUP(xml.value("before_level").equals(xml.value("after_level")));
			
			if((xml.value("special_item")) != null)
				for(Object e:xml.list("special_item"))
		    	{
					xml.set((Element)e);
		    		boolean flag = false;
		    		for(NItem i:arthur.items())
		    			if(xml.value("item_id").equals(i.idItem()))
		    			{
		    				i.now(xml.value("after_count"));
		    				i.old(xml.value("before_count"));
		    				flag = !flag;
		    			}
		    		
		    		if(!flag)
		    		{
		    			NItem newItem = new NItem(xml.value("item_id"));
	    				newItem.now(xml.value("after_count"));
	    				newItem.old(xml.value("before_count"));
		    			
		    			arthur.items().add(newItem);
		    		}
		    	}
			
	    	return result;
		}
		
		return null;
	}

	public static void GainRewardList(File xmlFile, NArthur arthur) throws Exception
	{
		UXml xml = new UXml(xmlFile);
		
    	xml.move("body>rewardbox_list");
    	for(Object e:xml.list("rewardbox"))
    	{
    		xml.set((Element)e);
    		
    		String type = xml.value("type");
    		String value = null;
    		
    		switch(type)
    		{
    		case "1"://card
    			value = xml.value("card_id");
    			break;
    		case "3"://gold
    		case "4"://fspoint
    			value = xml.value("point");
    			break;
    		case "2"://item
    			value =  xml.value("item_id");
    		case "5"://gachatk
    			value = xml.value("get_num");
    		}
    		
    		NReward reward = new NReward(xml.value("id"), type, xml.value("title"), xml.value("content"), value, xml.value("date"));
    		
    		arthur.rewards().add(reward);
    	}
	}

	public static boolean GainFriendInvite(File xmlFile) throws Exception
	{
		UXml xml = new UXml(xmlFile);
    	
		return xml.value("body>friend_act_res>success").equals("1");
	}
	public static void GainFriends(File xmlFile, NArthur arthur) throws Exception
	{
		NMatches matches = FGain.GainMatches("body/friend_list", xmlFile);

		for(NMatch m:matches)
			arthur.friends().add(m);
	}
	public static NMatches GainMatches(String xPath, File xmlFile) throws Exception
	{
		UXml xml = new UXml(xmlFile);
		
		NMatches matches = new NMatches();
		
		xml.move(xPath);
		
    	for(Object e:xml.list("user"))
    	{
    		NMatch match = GainMatch((Element)e);
    		
    		matches.add(match);
    	}

		return matches;
	}
	public static boolean GainFriendRemove(File pakFile) throws Exception
	{
		return new UXml(pakFile).value("body>friend_act_res>success").equals("1");
	}
	
	public static void GainAreaList(File xmlFile, NArthur arthur) throws Exception
	{
		UXml xml = new UXml(xmlFile);
		
		xml.move("body>exploration_area>area_info_list");
		
    	for(Object e:xml.list("area_info"))
    	{
    		xml.set((Element)e);
    		
    		NArea area = new NArea(xml.value("id"), xml.value("name"), xml.value("area_type").equals("1"));
    		area.prgArea(xml.value("prog_area"));
    		area.prgItem(xml.value("prog_item"));
    		
    		arthur.areas().add(area);
    	}
	}
	public static void GainFloors(File xmlFile, NArea area) throws Exception
	{
		UXml xml = new UXml(xmlFile);
		
		xml.move("body>exploration_floor>floor_info_list");
		
    	for(Object e:xml.list("floor_info"))
    		area.floors().add(GainFloor((Element)e));
	}
	public static NFloor GainFloor(NArea area, File xmlFile) throws Exception
	{
		UXml xml = new UXml(xmlFile);
		
		NFloor floor = GainFloor(xml.move("body>get_floor>floor_info"));
		
		area.floors().add(floor);
		
		return floor;
	}
	public static NFloor GainFloor(Element e) throws Exception
	{
		UXml xml = new UXml(null);
		
		xml.set(e);
		
		boolean isNormal = xml.value("type").equals("0");
		
		String cost = isNormal?xml.value("cost"):null;
		
		String[] idCards = new String[3];
		boolean[] unlock = new boolean[3];
		
		boolean hasFragment = false;
		String prog = null;
		if(isNormal)
		{
			prog = xml.value("progress");
    		
    		xml.move("found_item_list");
    		int cntCard = 0;
    		for(Object e2:xml.list("found_item"))
    		{
    			xml.set((Element)e2);
    			if(xml.value("type").equals("1"))
    			{
    				idCards[cntCard] = xml.value("user_card>master_card_id");
    				unlock[cntCard++] = xml.value("unlock").equals("0")?false:true;
    			}
    			else
    			{
    				hasFragment = true;
    				unlock[2] = xml.value("unlock").equals("0")?false:true;
    			}
    		}
			
		}
		else
			idCards[0] = xml.value("boss_id");
		
		xml.set(e);
    	NFloor floor = new NFloor(xml.value("id"), isNormal, cost, idCards, hasFragment);
    	
    	if(isNormal)
    	{
    		floor.prog(prog);
    		floor.unlock(unlock);
    		floor.hasNext(xml.move(".body>get_floor>next_floor") != null);
    	}
    	
		return floor;
	}
	public static NExploreResult GainExplore(NArthur arthur, NArea area, File xmlFile) throws Exception
	{
		UXml xml = new UXml(xmlFile);
		
		NExploreResult result = new NExploreResult();
		
		GainPoint(xmlFile, arthur);

		if(xml.value("header>your_data>owner_card_list") != null)
			GainCards(xmlFile, arthur);
		
		GainItems(xmlFile, arthur);
		
		xml.move("header>your_data");
		arthur.base().gold(xml.value("gold"));
		arthur.gacha().pointFriend(xml.value("friendship_point"));
		arthur.town().lv(xml.value("town_level"));
	    
		xml.move(".body>explore");
		
		result.prog(xml.value("progress"));
		result.type(xml.value("event_type"));
		result.hasNextFloor((xml.value("next_floor") != null)&&(!xml.value("next_floor>floor_info>type").equals("1")));
		
		if(!result.type().equals("6"))
		{
			result.lvUP((xml.value("lvup").equals("1")));
			result.getGold(xml.value("gold"));
			result.getExp(xml.value("get_exp"));
			result.restExp(xml.value("next_exp"));
		}
		
		switch(result.type())
		{
		case "13":
		case "12":
			result.recover(xml.value("recover"));
			break;
		case "19":
			if(xml.value("special_item") != null)
				if(!(result.idItem(xml.value("special_item>item_id"))).equals(""))
					result.getItem(String.valueOf(Integer.parseInt(xml.value("special_item>after_count")) - Integer.parseInt(xml.value("special_item>before_count"))));
				else
					result.type("0");
			break;
		case "2":
			result.frdPoint(xml.value("friendship_point"));
			result.encounter(GainMatch(xml.find("encounter")));
			break;
		case "1":
			result.fairy(GainFairy(xml.find("fairy"), arthur));
			break;
		case "3":
			GainCard(xml.find("user_card"));
			break;
		case "15":
			int i = 0;
			for(Object e:xml.list("autocomp_card"))
				switch(i++)
				{
				case 0:
					result.aftCard(GainCard((Element)e));
					break;
				case 1:
					result.newCard(GainCard((Element)e));
					break;
				case 2:
					result.bfrCard(GainCard((Element)e));
					break;
				}
			break;
		case "5":
			if(result.hasNextFloor())
				area.floors().add(GainFloor(xml.move("next_floor>floor_info")));
			break;
		}
	
		if((xml.value("special_item")) != null)
			for(Object e:xml.list("special_item"))
			{
				xml.set((Element)e);
				boolean flag = false;
				for(NItem i:arthur.items())
					if(xml.value("item_id").equals(i.idItem()))
					{
						i.now(xml.value("after_count"));
						i.old(xml.value("before_count"));
						flag = !flag;
					}
				
				if(!flag)
				{
					NItem newItem = new NItem(xml.value("item_id"));
					newItem.now(xml.value("after_count"));
					newItem.old(xml.value("before_count"));
					
					arthur.items().add(newItem);
				}
			}
		
		return result;
	}

	private static NCard GainCard(Element e) throws Exception
	{
		UXml xml = new UXml(null);
		xml.set(e);
		
		NCard card = new NCard(xml.value("serial_id"), xml.value("master_card_id"));
		card.isHolo(xml.value("holography"));
		card.hp(xml.value("hp"));
		card.atk(xml.value("power"));
		card.lv(xml.value("lv"));
		card.maxLV(xml.value("lv_max"));
		card.exp(xml.value("exp"));
		card.expMax(xml.value("max_exp"));
		card.expNxt(xml.value("next_exp"));
		card.prcSale(xml.value("sale_price"));
		card.prcCompound(xml.value("material_price"));
		card.prcEvolution(xml.value("evolution_price"));
		card.plusLimitNow(xml.value("plus_limit_count"));
		card.IsOverLimit(xml.value("limit_over"));
		
		return card;
	}
	private static NFairy GainFairy(Element e, NArthur arthur) throws Exception
	{
		UXml xml = new UXml(null);
		xml.set(e);
		
		String idSerial = xml.value("serial_id");
		String typRace = xml.value("race_type");
		String mid = xml.value("master_boss_id");
		String name = xml.value("name");
		String lv = xml.value("lv");
		String hp = xml.value("hp");
		String maxHP = xml.value("hp_max");
		String findTime = xml.value("time_limit");
		
		NMatch match = new NMatch(arthur.base().idArthur(), arthur.base().name(), arthur.town().idTown());
		
		NFairy fairy = new NFairy(match, idSerial, typRace, name, lv, findTime);
		
		fairy.nowHP(hp);
		fairy.maxHP(maxHP);
		fairy.idBoss(mid);

    	return fairy;
	}
	private static NMatch GainMatch(Element e) throws Exception
	{
		UXml xml = new UXml(null);
		xml.set(e);
		
		NMatch match = new NMatch(xml.value("id"), xml.value("name"), xml.value("country_id"));
		
		match.maxBC(xml.value("cost"));
		match.win(xml.value("results>win"));
		match.lose(xml.value("results>lose"));
		match.lv(xml.value("town_level"));
		match.exp(xml.value("next_exp"));
		match.nowFriend(xml.value("friends"));
		match.maxFriend(xml.value("friend_max"));
		match.lastLogin(xml.value("last_login"));
		match.sp(xml.value("ex_gage"));
		match.maxCard(xml.value("max_card_num"));

		match.leader(GainCard(xml.move("leader_card")));
		
		return match;
	}

	public static List<String[]> GainRankP(File xmlFile) throws Exception
	{
		List<String[]> lstRank = new ArrayList<>();
		
		UXml xml = new UXml(xmlFile);
		
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

	public static void GainInfo(File xmlFile, NArthur arthur) throws Exception
	{
		UXml xml = new UXml(xmlFile);
		
		xml.move("body>player_info>user");
		
		arthur.battle().win(xml.value("results>win"));
		arthur.battle().lose(xml.value("results>lose"));
		arthur.town().lv(xml.value("town_level"));
		arthur.town().exp(xml.value("next_exp"));
		arthur.friends().now(xml.value("friends"));
		arthur.friends().max(xml.value("friend_max"));
		arthur.battle().sp(xml.value("ex_gage"));
		arthur.guild().idGuild(xml.value("guild_id"));
		arthur.guild().name(xml.value("guild_name"));
		
		arthur.friends().cmt(xml.value("<default_comment>coment"));
	}
	public static NArthur GainHome(File xmlFile, NArthur arthur, boolean isInit) throws Exception
	{
		UXml xml = new UXml(xmlFile);
		
		xml.move("header>your_data");
		
		if(isInit)
			arthur = new NArthur(
					new NBase(xml.value("id"), xml.value("name")),
					new NTown(xml.value("country_id")));
		
		arthur.battle().win(xml.value("results>win"));
		arthur.battle().lose(xml.value("results>lose"));
		arthur.town().lv(xml.value("town_level"));
		arthur.town().exp(xml.value("next_exp"));
		arthur.friends().now(xml.value("friends"));
		arthur.friends().max(xml.value("friend_max"));
		arthur.battle().sp(xml.value("ex_gage"));
		arthur.guild().idGuild(xml.value("guild_id"));
		arthur.guild().name(xml.value("guild_name"));
		
		arthur.friends().cmt(xml.value("<default_comment>coment"));
		
		return arthur;
	}

}