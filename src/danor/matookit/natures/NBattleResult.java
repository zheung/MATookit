package danor.matookit.natures;

public class NBattleResult
{
	private String error;
	
	private NCards cardList;
	private NCards ecardList;

	private String gold;
	private String exp;
	
	private String aftHP;
	private String bfrHP;
	
	private boolean lvUP;
	
	private String items;

	public synchronized String error() { return error; }
	public synchronized String error(String error) { return (this.error = error); }

	public synchronized NCards cardList() { return cardList; }
	public synchronized void cardList(NCards cardlist) { this.cardList = cardlist; }

	public synchronized NCards eCardList() { return ecardList; }
	public synchronized void eCardList(NCards ecardlist) { this.ecardList = ecardlist; }

	public synchronized String gold() { return gold; }
	public synchronized void gold(String gold) { this.gold = gold; }

	public synchronized String exp() { return exp; }
	public synchronized void exp(String exp) { this.exp = exp; }

	public synchronized String aftHP() { return aftHP; }
	public synchronized void aftHP(String aftHP) { this.aftHP = aftHP; }

	public synchronized String bfrHP() { return bfrHP; }
	public synchronized void bfrHP(String bfrHP) { this.bfrHP = bfrHP; }

	public synchronized boolean lvUP() { return lvUP; }
	public synchronized void lvUP(boolean lvUP) { this.lvUP = lvUP; }

	public synchronized String items() { return items; }
	public synchronized void items(String items) { this.items = items; }
	
	//TODO Okay-then battle action
}