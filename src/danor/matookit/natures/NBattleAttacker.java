package danor.matookit.natures;

public class NBattleAttacker
{
	/* FairyInfo: body>fairy_floor>explore>fairy>attacker_history>attacker(s)
	 * (NMatch)match = this|发现者
	 * atkHP = attack_point|消耗的生命值
	 * atkTimes = attack_times|攻击次数
	 */
	private final NMatch match;
	private String atkHP;//attack_point
	private String atkTimes;//
 
	public NBattleAttacker(NMatch match)
	{
		this.match = match;
	}

	public synchronized NMatch match() { return match; }

	public synchronized String attackHP() { return atkHP; }
	public synchronized void attackHP(String attackHP) { this.atkHP = attackHP; }
	
	public synchronized String attackNow() { return atkTimes; }
	public synchronized void attackNow(String atkTimes) { this.atkTimes = atkTimes; }
}
