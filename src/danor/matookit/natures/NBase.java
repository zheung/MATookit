package danor.matookit.natures;

public class NBase
{
	/* Login: root
	 * idArthur = body>login>user_id|主人的识别ID
	 * name = header>you_data>name|主人的名称
	 * gold = header>you_data>gold|主人的金币
	 */
	private final String idArthur;

	private final String name;

	private String gold;
	private String oldGold;
	
	public NBase(String idArthur, String name)
	{
		this.idArthur = idArthur;
		this.name = name;
	}

	public synchronized String idArthur() { return idArthur; }
	
	public synchronized String name() { return name; }

	public synchronized String gold() { return gold; }
	public synchronized void gold(String gold) { this.gold = gold; }

	public synchronized String oldGold() { return oldGold; }
	public synchronized void oldGold(String oldGold) { this.oldGold = oldGold; }
}