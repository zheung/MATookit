package danor.matookit.natures;

import java.util.ArrayList;

public class NFairys extends ArrayList<NFairy>
{
	/* FairyList: body>fairy_select>
	 * restReward = ramain_rewards|未领取的击退奖励
	 */
	private static final long serialVersionUID = 1L;
	
	private String restReward;

	public String restReward() { return restReward; }
	public void restReward(String restReward) { this.restReward = restReward; }
	
	public boolean add(NFairy fairy)
	{
		for(NFairy f:this)
			if(f.idSerial().equals(fairy.idSerial()))
			{
				f.restTime(fairy.restTime());
				f.statAlive(fairy.statAlive());
				f.statReward(fairy.statReward());
				
				f.idBoss(fairy.idBoss());
				f.nowHP(fairy.nowHP());
				f.maxHP(fairy.maxHP());
				
				f.onList(fairy.onList());
				
				return false;
			}
		
		super.add(fairy);
		
		return true;
	}
}