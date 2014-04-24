package danor.matookit.natures;

public class NFairy
{
	/* FairyList: body>fairy_select>fairy_event(s)
	 * (NMatch)finder = user>|发现者
	 * typRace = fairy>race_type|发现类型: 1,公会(没记错的话); 2,个人
	 * idSerial = fairy>serial_id|唯一识别ID
	 * name = fairy>name|名称
	 * lv = fairy>lv|等级
	 * restTime = fairy>time_limit|剩余时间(秒)
	 * statAlive = put_down|生存状态: 1,存活; 2,死亡; 3,逃跑
	 * findTime = start_time|发现时间(Unix时间戳)
	 * statReward = reward_status|可能获得的卡片数量
	 */
	/* FairyInfo: body>fairy_floor>explore>fairy
	 * (d)idSerial = serial_id|唯一识别ID
	 * idBoss = master_boss_id|对应的Boss的ID
	 * (d)typRace = race_type|发现类型: 1,公会(没记错的话); 2,个人
	 * (d)name = name|名称
	 * (d)level = lv|等级
	 * nowHP = hp|剩余生命值
	 * maxHP = hp_max|初始生命值
	 * (u)restTime = time_limit|剩余时间(秒)
	 * (d)finder.idArthur = discoverer_id|发现者的识别ID
	 * (NBattleAttackers)battleAttackers = attacker_history(s)|历史攻击者
	 * 
	 */
	private final NMatch finder;
	private final String idSerial;
	private final String typRace;
	private final String name;
	private final String lv;
	private String restTime;
	private String statAlive;
	private final String findTime;
	private String statReward;
	
	private String idBoss;
	private String nowHP;
	private String maxHP;
	private final NBattleAttackers battleAttackers = new NBattleAttackers();
	
	private boolean battled;
	private boolean onList;
	
	public NFairy(NMatch finder, String idSerial, String typRace, String name, String lv, String findTime)
	{
		this.finder = finder;
		this.idSerial = idSerial;
		this.typRace = typRace;
		this.name = name;
		this.lv = lv;
		this.findTime = findTime;
	}
	
	public synchronized NMatch finder() { return finder; }
	public synchronized String idSerial() { return idSerial; }
	public synchronized String typRace() { return typRace; }
	public synchronized String name() { return name; }
	public synchronized String lv() { return lv; }

	public synchronized String restTime() { return restTime; }
	public synchronized void restTime(String restTime) { this.restTime = restTime; }

	public synchronized String statAlive() { return statAlive; }
	public synchronized void statAlive(String statAlive) { this.statAlive = statAlive; }
	
	public synchronized String findTime() { return findTime; }

	public synchronized String statReward() { return statReward; }
	public synchronized void statReward(String statReward) { this.statReward = statReward; }

	public synchronized String idBoss() { return idBoss; }
	public synchronized void idBoss(String idBoss) { this.idBoss = idBoss; }

	public synchronized String nowHP() { return nowHP; }
	public synchronized void nowHP(String nowHP) { this.nowHP = nowHP; }
	
	public synchronized String maxHP() { return maxHP; }
	public synchronized void maxHP(String maxHP) { this.maxHP = maxHP; }

	public synchronized NBattleAttackers battleAttackers() { return battleAttackers; }

	public synchronized boolean battled() { return battled; }
	public synchronized void battled(boolean battled) { this.battled = battled; }

	public synchronized boolean onList() { return onList; }
	public synchronized void onList(boolean onList) { this.onList = onList; }
}
