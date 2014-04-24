package danor.matookit.natures;

import java.util.ArrayList;

public class NBattleAttackers extends ArrayList<NBattleAttacker>
{
	private static final long serialVersionUID = 1L;
	
	public boolean add(NBattleAttacker attacker)
	{
		for(NBattleAttacker a:this)
			if(a.match().idArthur().equals(attacker.match().idArthur()))
			{
				a.attackHP(attacker.attackHP());
				a.attackNow(attacker.attackNow());
				
				return false;
			}
		
		super.add(attacker);
		
		return true;
	}
}