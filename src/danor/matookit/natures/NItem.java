package danor.matookit.natures;

public class NItem//header>you_data>itemlist 道具
{
	private String idItem;//item_id
	private String now;//num
	
	private String old;

	public NItem(String idItem)
	{
		this.idItem = idItem;
	}

	public synchronized String idItem() { return idItem; }

	public synchronized String now() { return now; }
	public synchronized void now(String now) { this.now = now; }

	public synchronized String old() { return old; }
	public synchronized void old(String old) { this.old = old; }
}