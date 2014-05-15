package danor.matookit.natures;

public class NFriends extends NMatches
{
	private static final long serialVersionUID = 1L;

	private String now;//header>you_data>friends
	private String max;//header>you_data>friend_max
	private String ivt;//header>you_data>friends_invitations
	
	private String cmt;
	
	public synchronized String now() { return now; }
	public synchronized void now(String now) { this.now = now; }

	public synchronized String max() { return max; }
	public synchronized void max(String max) { this.max = max; }

	public synchronized String ivt() { return ivt; }
	public synchronized void ivt(String ivt) { this.ivt = ivt; }

	public synchronized String cmt() { return cmt; }
	public synchronized void cmt(String cmt) { this.cmt = cmt; }
}
