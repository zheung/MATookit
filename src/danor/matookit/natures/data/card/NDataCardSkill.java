package danor.matookit.natures.data.card;

public class NDataCardSkill
{
	private final String name;
	private final String kana;
	private final String desc;
	private final String type;
	
	private final String idSkill;
	private final String eftMin;
	private final String eftMax;
	private final String pesc;
	
	public NDataCardSkill(NDataCardSkillBuilder builder)
	{
		this.name = builder.name;
		this.kana = builder.kana;
		this.desc = builder.desc;
		this.type = builder.type;
		
		this.idSkill = builder.idSkill;
		this.eftMin = builder.eftMin;
		this.eftMax = builder.eftMax;
		this.pesc = builder.pesc;
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
