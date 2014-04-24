package danor.matookit.natures;

public class NPoint
{
	/* Login: header>you_data
	 * free = free_ap_bc_point|未分配的点数
	 */
	/* Anywhere: header>you_data
	 * nowAP = ap>current|当前AP
	 * maxAP = ap>max|AP上限
	 * revAP = ap>current_time - ap>last_update_time|下一点AP恢复的剩余时间(Unix时间戳 - Unix时间戳 = 秒)
	 * nowBC = bc>current|当前bc
	 * maxBC = bc>max|bc上限
	 * revBC = bc>current_time - bc>last_update_time|下一点bc恢复的剩余时间(Unix时间戳 - Unix时间戳 = 秒)
	 */
	private String free;
	
	private int nowAP;
	private int maxAP;
	private int revAP;
	
	private int nowBC;
	private int maxBC;
	private int revBC;
	
//	private McPoint timer;
	
	public synchronized String free() { return free; }
	public synchronized void free(String free) { this.free = free; }
	
	public synchronized int nowAP() { return nowAP; }
	public synchronized void nowAP(int nowAP) { this.nowAP = nowAP; }
	
	public synchronized int maxAP() { return maxAP; }
	public synchronized void maxAP(int maxAP) { this.maxAP = maxAP; }
	
	public synchronized int revAP() { return revAP; }
	public synchronized void revAP(int revAP) { this.revAP = revAP; }
	
	public synchronized int nowBC() { return nowBC; }
	public synchronized void nowBC(int nowBC) { this.nowBC = nowBC; }
	
	public synchronized int maxBC() { return maxBC; }
	public synchronized void maxBC(int maxBC) { this.maxBC = maxBC; }
	
	public synchronized int revBC() { return revBC; }
	public synchronized void revBC(int revBC) { this.revBC = revBC; }
}
