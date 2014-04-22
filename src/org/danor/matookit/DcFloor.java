package org.danor.matookit;

public class DcFloor//body>exploration_floor>floor_info_list>floor_info< ('<'==枚举)
{
	protected String idFloor;//id
	protected boolean type;
	protected String cost;//cost
	protected String prog;//progress
	//found_item_list>found_item<
	protected String[] idCard = {"0","0","0"};//tpye=="1"?user_card>master_card_id
	protected boolean hasFragment;//tpye=="2"
	protected boolean[] unlock = {false, false, false, false};//unlock
	protected boolean hasNext;
	
	protected String idFairy;
}
