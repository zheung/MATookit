package danor.matookit.natures;

public class NCard//header>you_data>owner_card_list>user_card
{
	private final String idSerial;//serial_id
	private final String idCard;//master_card_id
	private String isHolo;//holography
	private String hp;//hp
	private String atk;//power
	private String lv;//lv
	private String maxLV;//lv_max
	private String exp;//exp 拥有的经验
	private String expMax;//max_exp 满级经验
	private String expNxt;//next_exp 到达下一级所需的经验
	private String prcSale;//sale_price
	private String prcCompound;//material_price
	private String prcEvolution;//evolution_price
	private String plusLimitNow;//plus_limit_count
	private String isOverLimit;//limit_over
	
	public NCard(String idSerial, String idCard)
	{
		this.idSerial = idSerial;
		this.idCard = idCard;
	}

	public synchronized String idSerial() { return idSerial; }
	
	public synchronized String idCard() { return idCard; }
	
	public synchronized String isHolo() { return isHolo; }
	public synchronized void isHolo(String isHolo) { this.isHolo = isHolo; }
	
	public synchronized String hp() { return hp; }
	public synchronized void hp(String hp) { this.hp = hp; }
	
	public synchronized String atk() { return atk; }
	public synchronized void atk(String atk) { this.atk = atk; }
	
	public synchronized String lv() { return lv; }
	public synchronized void lv(String lv) { this.lv = lv; }
	
	public synchronized String maxLV() { return maxLV; }
	public synchronized void maxLV(String maxLV) { this.maxLV = maxLV; }
	
	public synchronized String exp() { return exp; }
	public synchronized void exp(String exp) { this.exp = exp; }
	
	public synchronized String expMax() { return expMax; }
	public synchronized void expMax(String expMax) { this.expMax = expMax; }
	
	public synchronized String expNxt() { return expNxt; }
	public synchronized void expNxt(String expNxt) { this.expNxt = expNxt; }
	
	public synchronized String prcSale() { return prcSale; }
	public synchronized void prcSale(String prcSale) { this.prcSale = prcSale; }
	
	public synchronized String prcCompound() { return prcCompound; }
	public synchronized void prcCompound(String prcCompound) { this.prcCompound = prcCompound; }
	
	public synchronized String prcEvolution() { return prcEvolution; }
	public synchronized void prcEvolution(String prcEvolution) { this.prcEvolution = prcEvolution; }
	
	public synchronized String plusLimitNow() { return plusLimitNow; }
	public synchronized void plusLimitNow(String plusLimitNow) { this.plusLimitNow = plusLimitNow; }
	
	public synchronized String IsOverLimit() { return isOverLimit; }
	public synchronized void IsOverLimit(String isOverLimit) { this.isOverLimit = isOverLimit; }
}
