package org.danor.matookit;

import java.util.List;

public class DcFairy//body>fairy_select>fairy_event>
{
	protected DcPlayer user;//user
	//fairy>
	protected String sid;//serial_id
	protected String name;//name
	protected String level;//lv
	protected String time;//time_limit
	protected String livestat;//put_down 1存活2死亡3逃跑
	protected String start;//start_time
	protected String rewardstat;//reward_status 数量
	
	protected String HP;
	protected String maxHP;
	protected List<DcHisFighter> lstHisFighter;
	
	protected boolean isFighted;
	protected boolean idOnList;
	
	protected String typRace;
	
	protected String mid;
}
