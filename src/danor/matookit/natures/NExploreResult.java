package danor.matookit.natures;

public class NExploreResult//body>explore
{
	/* Explore: body>explore
	 * prog = progress|区域探索进度
	 * type = event_type|探索事件类型:
	 * 0,平安无事; 1,遭遇妖精; 2,遭遇玩家; 3,获得新卡; 5,探索完毕; 6,AP不足; 12,回复AP; 13,回复BC 19,获得物品
	 * gold = gold|获得的金币
	 * exp = _exp|获得的经验
	 * restExp = next_exp|剩余经验
	 */
	private String prog;
	private String type;
	
	private String getGold;
	private String getExp;
	private String restExp;
	
	private String recover;
	private String frdPoint;
	private NMatch encounter;
	private NFairy fairy;
	
	private NCard newCard;
	private NCard aftCard;
	private NCard bfrCard;
	
	private String idItem;
	private String getItem;
	
	private boolean isLvUP;
	
	private boolean hasNextFloor;

	public synchronized String prog() { return prog; }
	public synchronized void prog(String prog) { this.prog = prog; }

	public synchronized String type() { return type; }
	public synchronized void type(String type) { this.type = type; }

	public synchronized String getGold() { return getGold; }
	public synchronized void getGold(String getGold) { this.getGold = getGold; }

	public synchronized String getExp() { return getExp; }
	public synchronized void getExp(String getExp) { this.getExp = getExp; }

	public synchronized String restExp() { return restExp; }
	public synchronized void restExp(String restExp) { this.restExp = restExp; }

	public synchronized String recover() { return recover; }
	public synchronized void recover(String recover) { this.recover = recover; }

	public synchronized String frdPoint() { return frdPoint; }
	public synchronized void frdPoint(String frdPoint) { this.frdPoint = frdPoint; }

	public synchronized NMatch encounter() { return encounter; }
	public synchronized void encounter(NMatch encounter) { this.encounter = encounter; }

	public synchronized NFairy fairy() { return fairy; }
	public synchronized void fairy(NFairy fairy) { this.fairy = fairy; }

	public synchronized NCard newCard() { return newCard; }
	public synchronized void newCard(NCard newCard) { this.newCard = newCard; }

	public synchronized NCard aftCard() { return aftCard; }
	public synchronized void aftCard(NCard aftCard) { this.aftCard = aftCard; }

	public synchronized NCard bfrCard() { return bfrCard; }
	public synchronized void bfrCard(NCard bfrCard) { this.bfrCard = bfrCard; }

	public synchronized String idItem() { return idItem; }
	public synchronized String idItem(String idItem) { return (this.idItem = idItem); }

	public synchronized String getItem() { return getItem; }
	public synchronized void getItem(String Item) { this.getItem = Item; }

	public synchronized boolean lvUP(){ return isLvUP; }
	public synchronized void lvUP(boolean isLvUP) { this.isLvUP = isLvUP; }

	public synchronized boolean hasNextFloor() { return hasNextFloor; }
	public synchronized void hasNextFloor(boolean hasNextFloor) { this.hasNextFloor = hasNextFloor; }
}
