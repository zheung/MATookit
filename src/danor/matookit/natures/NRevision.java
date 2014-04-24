package danor.matookit.natures;

public class NRevision
{
	/* Anywhere: header>revision
	 * revCard = card_rev|卡片的版本
	 * revBoss = boss_rev|Boss的版本
	 * revItem = item_rev|道具的版本
	 * revCategory = card_category_rev|卡片信息的版本
	 * revGacha = gacha_rev|蛋池的版本
	 * revPrivilege = privilege_rev|特权的版本(不知道有啥用)
	 * revCombo = combo_rev|Combo的版本
	 * revBanner = banner_rev|活动横幅的版本
	 */
	/* Anywhere: header>revision>resource_rev(s)
	 * resres = (>filename==res),revision|资源的版本? 
	 * resSound = (>filename==sound),revision|音效的版本? 
	 * resAdvbg = (>filename==advbg),revision|探索背景的版本 
	 * resCmpsheet = (>filename==cmpsheet),revision|图鉴卡的版本 
	 * resGacha = (>filename==gacha),revision|又是蛋池的版本?也许这个蛋池的宣传图有关 
	 * resBanner = (>filename==banner),revision|又是活动横幅的版本?
	 * resPrivilege = (>filename==privilege),revision|又是特权的版本??? 
	 */
	
	private String revCard;
	private String revBoss;
	private String revItem;
	private String revCardCategory;
	private String revGacha;
	private String revPrivilege;
	private String revCombo;
	private String revBannner;
	
	private String resRes;
	private String resSound;
	private String resAdvbg;
	private String resCmpsheet;
	private String resGacha;
	private String resBannner;
	private String resPrivilege;
	public synchronized String revCard() { return revCard; }
	public synchronized void revCard(String revCard) { this.revCard = revCard; }
	
	public synchronized String revBoss() { return revBoss; }
	public synchronized void revBoss(String revBoss) { this.revBoss = revBoss; }
	
	public synchronized String revItem() { return revItem; }
	public synchronized void revItem(String revItem) { this.revItem = revItem; }
	
	public synchronized String revCardCategory() { return revCardCategory; }
	public synchronized void revCardCategory(String revCardCategory) { this.revCardCategory = revCardCategory; }
	
	public synchronized String revGacha() { return revGacha; }
	public synchronized void revGacha(String revGacha) { this.revGacha = revGacha; }
	
	public synchronized String revPrivilege() { return revPrivilege; }
	public synchronized void revPrivilege(String revPrivilege) { this.revPrivilege = revPrivilege; }
	
	public synchronized String revCombo() { return revCombo; }
	public synchronized void revCombo(String revCombo) { this.revCombo = revCombo; }
	
	public synchronized String revBannner() { return revBannner; }
	public synchronized void revBannner(String revBannner) { this.revBannner = revBannner; }
	
	public synchronized String resRes() { return resRes; }
	public synchronized void resRes(String resRes) { this.resRes = resRes; }
	
	public synchronized String resSound() { return resSound; }
	public synchronized void resSound(String resSound) { this.resSound = resSound; }
	
	public synchronized String resAdvbg() { return resAdvbg; }
	public synchronized void resAdvbg(String resAdvbg) { this.resAdvbg = resAdvbg; }
	
	public synchronized String resCmpsheet() { return resCmpsheet; }
	public synchronized void resCmpsheet(String resCmpsheet) { this.resCmpsheet = resCmpsheet; }
	
	public synchronized String resGacha() { return resGacha; }
	public synchronized void resGacha(String resGacha) { this.resGacha = resGacha; }
	
	public synchronized String resBannner() { return resBannner; }
	public synchronized void resBannner(String resBannner) { this.resBannner = resBannner; }
	
	public synchronized String resPrivilege() { return resPrivilege; }
	public synchronized void resPrivilege(String resPrivilege) { this.resPrivilege = resPrivilege; }
}
