package danor.matookit.natures;

public class NArthur
{
	private final NBase base;
	private final NTown town;
	private final NPoint point = new NPoint();
	private final NBattle battle = new NBattle();
	private final NGacha gacha = new NGacha();
	private final NShop shop = new NShop();
	private final NRevision rev = new NRevision();
	
	private final NFriends friends = new NFriends();
	private final NCards cards = new NCards();
	private final NItems items = new NItems();
	private final NAreas areas = new NAreas();
	private final NFairys fairys = new NFairys();
	private final NRewards rewards = new NRewards();

	public NArthur(NBase base, NTown town)
	{
		this.base = base;
		this.town = town;
	}
	
	public synchronized NBase base() { return base; }
	public synchronized NTown town() { return town; }
	public synchronized NPoint point() { return point; }
	public synchronized NBattle battle() { return battle; }
	public synchronized NGacha gacha() { return gacha; }
	public synchronized NShop shop() { return shop; }
	public synchronized NRevision rev() { return rev; }

	public synchronized NFriends friends() { return friends; }
	public synchronized NCards cards() { return cards; }
	public synchronized NItems items() { return items; }
	public synchronized NAreas areas() { return areas; }
	public synchronized NFairys fairys() { return fairys; }
	public synchronized NRewards rewards() { return rewards; }
}
