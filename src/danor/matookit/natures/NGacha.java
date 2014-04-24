package danor.matookit.natures;

public class NGacha
{
	/* Login: header>you_data
	 * ticket = gacha_ticket|蛋劵
	 * point = gacha_point|蛋劵碎片
	 * pointFriend = friendship_point|友情点
	 */	
	private String ticket;
	private String point;
	
	private String pointFriend;
	
	public synchronized String ticket() { return ticket; }
	public synchronized void ticket(String ticket) { this.ticket = ticket; }

	public synchronized String point() { return point; }
	public synchronized void point(String point) { this.point = point; }
	
	public synchronized String pointFriend() { return pointFriend; }
	public synchronized void pointFriend(String pointFriend) { this.pointFriend = pointFriend; }
}
