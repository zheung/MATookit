package danor.matookit.natures.data.card;


public class NDataCardSkillFull extends NDataCardSkill
{
	private final String name;
	private final String kana;
	private final String desc;
	private final String type;
	
	private final String idSkill;
	private final String eftMin;
	private final String eftMax;
	private final String pesc;
	
	public NDataCardSkillFull(String name, String kana, String desc, String type, String idSkill, String eftMin, String eftMax, String pesc)
	{
		this.name = name;
		this.kana = kana;
		this.desc = desc;
		this.type = type;
		
		this.idSkill = idSkill;
		this.eftMin = eftMin;
		this.eftMax = eftMax;
		this.pesc = pesc;
	}
	
	public synchronized String name() { return name; }
	
	public synchronized String kana() { return kana; }
	
	public synchronized String desc() { return desc; }
	
	public synchronized String type() { return type; }
	
	public synchronized String idSkill() { return idSkill; }
	
	public synchronized String eftMin() { return eftMin; }
	
	public synchronized String eftMax() { return eftMax; }
	
	public synchronized String pesc() { return pesc; }
}
