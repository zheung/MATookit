package danor.matookit.natures;

import java.util.ArrayList;

public class NMatches extends ArrayList<NMatch>
{
	private static final long serialVersionUID = 1L;
	
	public boolean add(NMatch match)
	{
		for(NMatch a:this)
			if(a.idArthur().equals(match.idArthur()))
			{
				a.leader(match.leader());
				a.statFriend(match.statFriend());
				a.statYell(match.statYell());
				a.maxBC(match.maxBC());
				a.win(match.win());
				a.lose(match.lose());
				a.exp(match.exp());
				a.nowFriend(match.nowFriend());
				a.maxFriend(match.maxFriend());
				a.lastLogin(match.lastLogin());
				a.sp(match.sp());
				a.maxCard(match.maxCard());
				
				return false;
			}
		
		super.add(match);
		
		return true;
	}
}