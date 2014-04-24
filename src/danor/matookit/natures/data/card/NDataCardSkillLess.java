package danor.matookit.natures.data.card;


public class NDataCardSkillLess extends NDataCardSkill
{
	private final String name;
	private final String kana;
	private final String desc;
	private final String type;
	
	private final String idSkill;
	private final String eftMin;
	private final String eftMax;
	private final String pesc;
	
	public NDataCardSkillLess(String name, String kana, String desc, String type)
	{
		this.name = name;
		this.kana = kana;
		this.desc = desc;
		this.type = type;
		
		this.idSkill = null;
		this.eftMin = null;
		this.eftMax = null;
		this.pesc = null;
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
