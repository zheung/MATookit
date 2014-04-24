package danor.matookit.natures;

public class NArea//body>exploration_area>area_info_list>area_info
{
	private final String idArea;//id
	private final String name;//name
	private String prgArea;//prog_area
	private String prgItem;//prog_item
	private final boolean isEvent;//area_type
	private final NFloors floors = new NFloors();
	
	public NArea(String idArea, String name, boolean isEvent)
	{
		this.idArea = idArea;
		this.name = name;
		this.isEvent = isEvent;
	}
	
	public synchronized String idArea() { return idArea; }

	public synchronized String name() { return name; }
	
	public synchronized String prgArea() { return prgArea; }
	public synchronized void prgArea(String prgArea) { this.prgArea = prgArea; }
	
	public synchronized String prgItem() { return prgItem; }
	public synchronized void prgItem(String prgItem) { this.prgItem = prgItem; }
	
	public synchronized boolean isEvent() { return isEvent; }
	
	public synchronized NFloors floors() { return floors; }
}
