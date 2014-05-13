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
	
	private String revCrd;
	private String revBos;
	private String revItm;
	private String revCtg;
	private String revGac;
	private String revPvl;
	private String revCmb;
	private String revBan;
	
	private String resRes;
	private String resSou;
	private String resAdv;
	private String resCmp;
	private String resGac;
	private String resBan;
	private String resPvl;

	private String resMbg;

	public synchronized String revCrd() { return revCrd; }
	public synchronized void revCrd(String revCrd) { this.revCrd = revCrd; }
	
	public synchronized String revBos() { return revBos; }
	public synchronized void revBos(String revBos) { this.revBos = revBos; }
	
	public synchronized String revItm() { return revItm; }
	public synchronized void revItm(String revItm) { this.revItm = revItm; }
	
	public synchronized String revCtg() { return revCtg; }
	public synchronized void revCtg(String revCtg) { this.revCtg = revCtg; }
	
	public synchronized String revGac() { return revGac; }
	public synchronized void revGac(String revGac) { this.revGac = revGac; }
	
	public synchronized String revPvl() { return revPvl; }
	public synchronized void revPvl(String revPvl) { this.revPvl = revPvl; }
	
	public synchronized String revCmb() { return revCmb; }
	public synchronized void revCmb(String revCmb) { this.revCmb = revCmb; }
	
	public synchronized String revBan() { return revBan; }
	public synchronized void revBan(String revBan) { this.revBan = revBan; }
	
	public synchronized String resRes() { return resRes; }
	public synchronized void resRes(String resRes) { this.resRes = resRes; }
	
	public synchronized String resSou() { return resSou; }
	public synchronized void resSou(String resSou) { this.resSou = resSou; }
	
	public synchronized String resAdv() { return resAdv; }
	public synchronized void resAdv(String resAdv) { this.resAdv = resAdv; }
	
	public synchronized String resCmp() { return resCmp; }
	public synchronized void resCmp(String resCmp) { this.resCmp = resCmp; }
	
	public synchronized String resGac() { return resGac; }
	public synchronized void resGac(String resGac) { this.resGac = resGac; }
	
	public synchronized String resBan() { return resBan; }
	public synchronized void resBan(String resBan) { this.resBan = resBan; }
	
	public synchronized String resPvl() { return resPvl; }
	public synchronized void resPvl(String resPvl) { this.resPvl = resPvl; }

	public synchronized String resMbg() { return resMbg; }
	public synchronized void resMbg(String resMbg) { this.resMbg = resMbg; }
}
