package danor.matookit.natures;

import java.util.ArrayList;

public class NFloors extends ArrayList<NFloor>
{
	private static final long serialVersionUID = 1L;
	
	public boolean add(NFloor floor)
	{
		for(NFloor a:this)
			if(a.idFloor().equals(floor.idFloor()))
			{
				a.prog(floor.prog());
				a.hasNext(floor.hasNext());
				a.unlock(floor.unlock());
				
				return false;
			}
		
		super.add(floor);
		
		return true;
	}	
}