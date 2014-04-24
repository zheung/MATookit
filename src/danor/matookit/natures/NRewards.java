package danor.matookit.natures;

import java.util.ArrayList;

public class NRewards extends ArrayList<NReward>
{
	private static final long serialVersionUID = 1L;
	
	public boolean add(NReward area)
	{
		for(NReward a:this)
			if(a.idReward().equals(area.idReward()))
				return false;
		
		super.add(area);
		
		return true;
	}	
}