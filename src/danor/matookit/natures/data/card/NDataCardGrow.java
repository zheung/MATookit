package danor.matookit.natures.data.card;

public class NDataCardGrow
{
	private final String type;
	private final String name;
	private final String desc;
	
	public NDataCardGrow(String type, String name, String desc)
	{
		this.type = type;
		this.name = name;
		this.desc = desc;
	}
	
	public synchronized String type() { return type; }
	public synchronized String name() { return name; }
	public synchronized String desc() { return desc; }
}
