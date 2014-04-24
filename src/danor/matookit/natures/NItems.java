package danor.matookit.natures;

import java.util.ArrayList;

public class NItems extends ArrayList<NItem>
{
	private static final long serialVersionUID = 1L;
	
	public boolean add(NItem item)
	{
		for(NItem a:this)
			if(a.idItem().equals(item.idItem()))
				return false;
		
		super.add(item);
		
		return true;
	}	
}
