package danor.matookit.natures;

import java.util.ArrayList;

public class NAreas extends ArrayList<NArea>
{
	private static final long serialVersionUID = 1L;
	
	public boolean add(NArea area)
	{
		for(NArea a:this)
			if(a.idArea().equals(area.idArea()))
			{
				a.prgArea(area.prgArea());
				a.prgItem(area.prgItem());
				
				return false;
			}
		
		super.add(area);
		
		return true;
	}
}