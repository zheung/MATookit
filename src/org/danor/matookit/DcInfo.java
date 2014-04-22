package org.danor.matookit;

public class DcInfo
{
	protected String name;//header>you_data>name
	protected String level;//header>you_data>town_level
	protected String country;//header>you_data>country_id
	protected String uid;//body>login>user_id
	
	protected String gold;//header>you_data>gold
	protected String mc;//header>you_data>cp
	protected String frdPoint;//header>you_data>friendship_point
	protected String gachatk;//header>you_data>gacha_ticket
	protected String gachapt;//header>you_data>gacha_point
	
	protected String exgauge;//header>you_data>ex_gauge
	protected String ptsFree;//header>you_data>free_ap_bc_point

	protected String frdCount;//header>you_data>friends
	protected String frdMax;//header>you_data>friend_max
	protected String frdInvitation;//header>you_data>friends_invitations
	
	protected boolean hasReward;//body>mainmenu>rewards
	protected boolean hasFairy;//header>you_data>fairy_appearance
	
	protected String bflevel;
	protected String bfgold;
	protected String bfexp;
	protected String exp;
	
	protected String cntRewardCard;
}

class DcFake//header>you_data>fakecard 因子
{
	public String kid;//knight_id
	public String[] count;//[parts_no] count
}