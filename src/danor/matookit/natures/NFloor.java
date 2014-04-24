package danor.matookit.natures;

public class NFloor//body>exploration_floor>floor_info_list>floor_info(s)
{
	private final String idFloor;//id
	private final boolean isNormal;//0,普通区域; 1,Boss区域
	private final String cost;//cost
	private String prog;//progress
	//found_item_list>found_item(s)
	private final String[] idCards;//tpye=="1"?user_card>master_card_id
	private final boolean hasFragment;//tpye=="2"
	private boolean[] unlock = {false, false, false, false};//unlock
	private boolean hasNext;
	
	public NFloor(String idFloor, boolean isNormal, String cost, String[] idCards, boolean hasFragment)
	{
		this.idFloor = idFloor;
		this.isNormal = isNormal;
		this.cost = cost;
		this.idCards = idCards;
		this.hasFragment = hasFragment;
	}

	public synchronized String idFloor() { return idFloor; }

	public synchronized boolean isNormal() { return isNormal; }

	public synchronized String cost() { return cost; }

	public synchronized String prog() { return prog; }
	public synchronized void prog(String prog) { this.prog = prog; }

	public synchronized String[] idCards() { return idCards; }

	public synchronized boolean hasFragment() { return hasFragment; }

	public synchronized boolean[] unlock() { return unlock; }
	public synchronized void unlock(boolean[] unlock) { this.unlock = unlock; }

	public synchronized boolean hasNext() { return hasNext; }

	public synchronized void hasNext(boolean hasNext) { this.hasNext = hasNext; }
}
