package danor.matookit.natures;

public class NTown
{
	/* Login: root
	 * idTown = header>you_data>country_id|主城的识别ID
	 * level = header>you_data>town_level|主城的等级
	 * existReward = body>mainmenu>rewards|是否有未领取奖励
	 * existFairy = header>you_data>fairy_appearance|是否有未击退妖精
	 */
	private final String idTown;
	
	private String lv;
	private String exp;
	
	private boolean hasReward;//body>mainmenu>rewards
	private boolean hasFairy;//header>you_data>fairy_appearance
	
//	private String oldLevel;
//	private String oldExp;
	
	public NTown(String idTown)
	{
		this.idTown = idTown;
	}
	
	public synchronized String idTown() { return idTown; }

	public synchronized String lv() { return lv; }
	public synchronized void lv(String lv) { this.lv = lv; }
	
	public synchronized String exp() { return exp; }
	public synchronized void exp(String exp) { this.exp = exp; }
	
	public boolean hasReward() { return hasReward; }
	public void hasReward(boolean hasReward) { this.hasReward = hasReward; }

	public boolean hasFairy() { return hasFairy; }
	public void hasFairy(boolean hasFairy) { this.hasFairy = hasFairy; }
	
//	public synchronized String oldLevel() { return oldLevel; }
//	public synchronized void oldLevel(String oldLevel) { this.oldLevel = oldLevel; }
//	
//	public synchronized String oldExp() { return oldExp; }
//	public synchronized void oldExp(String oldExp) { this.oldExp = oldExp; }
}
