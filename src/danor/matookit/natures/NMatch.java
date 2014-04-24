package danor.matookit.natures;

public class NMatch
{
	/* FairyInfo: body>fairy_floor>explore>fairy>attacker_history>attacker(s)
	 * idArthur = user_id|亚瑟识别的ID
	 * (ds)discoverer|是否发现者
	 * name = user_name|亚瑟名称
	 * (ds)attack_point|消耗的生命值
	 * (ds)attack_times|攻击次数
	 * (NCard)leader = leader_card>|队长卡
	 * idTown = country_id|派别的ID
	 * statFriend = status_friend|是否好友: 0,不是好友; 2,好友或自己
	 * statYell = status_yell|是否问号过(今天): 1,未问好; 2,已问好
	 */
	private final String idArthur;//user_id
	private final String name;//user_name
	private NCard leader;//leader_card
	private final String idTown;//
	private String statFriend;//
	private String statYell;//status_yell: 1,未问好; 2,已问好

	private String maxBC;
	private String win;//results>win
	private String lose;//results>lose
	private String lv;//town_level
	private String exp;//next_exp
	private String nowFriend;//friends
	private String maxFriend;//friend_max
	private String lastLogin;//last_login
	private String sp;//ex_gage
	private String maxCard;//max_card_num
	
	public NMatch(String idArthur, String name, String idTown) { this.idArthur = idArthur;
		this.name = name;
		this.idTown = idTown; }

	public synchronized String idArthur() { return idArthur; }
	
	public synchronized String name() { return name; }

	public synchronized NCard leader() { return leader; }
	public synchronized void leader(NCard leader) { this.leader = leader; }
	
	public synchronized String idTown() { return idTown; }

	public synchronized String statFriend() { return statFriend; }
	public synchronized void statFriend(String statFriend) { this.statFriend = statFriend; }

	public synchronized String statYell() { return statYell; }
	public synchronized void statYell(String statYell) { this.statYell = statYell; }

	public synchronized String maxBC() { return maxBC; }
	public synchronized void maxBC(String maxBC) { this.maxBC = maxBC; }

	public synchronized String win() { return win; }
	public synchronized void win(String win) { this.win = win; }

	public synchronized String lose() { return lose; }
	public synchronized void lose(String lose) { this.lose = lose; }

	public synchronized String lv() { return lv; }
	public synchronized void lv(String lv) { this.lv = lv; }

	public synchronized String exp() { return exp; }
	public synchronized void exp(String exp) { this.exp = exp; }

	public synchronized String nowFriend() { return nowFriend; }
	public synchronized void nowFriend(String nowFriend) { this.nowFriend = nowFriend; }

	public synchronized String maxFriend() { return maxFriend; }
	public synchronized void maxFriend(String maxFriend) { this.maxFriend = maxFriend; }

	public synchronized String lastLogin() { return lastLogin; }
	public synchronized void lastLogin(String lastLogin) { this.lastLogin = lastLogin; }

	public synchronized String sp() { return sp; }
	public synchronized void sp(String sp) { this.sp = sp; }

	public synchronized String maxCard() { return maxCard; }
	public synchronized void maxCard(String maxCard) { this.maxCard = maxCard; }
}
