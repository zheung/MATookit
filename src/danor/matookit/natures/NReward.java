package danor.matookit.natures;

public class NReward
{
	/* RewardList: body>rewardbox_list>rewardbox(s)
	 * idReward = id|奖励的识别ID
	 * type = type|奖励类型: 1,卡片; 2,道具; 3,金币; 4,友情点; 5,蛋劵
	 * title = title|奖励标题
	 * content = content|奖励留意
	 * value = (acd:type)1,card_id; 2,item_id; 3,point; 4,point; 5,_num;|1,卡片种类的ID; 2,道具种类的ID; 3,金币数量; 4,友情点数量; 5,蛋劵数量
	 * giveTime = date|发放时间(Unix时间戳)
	 */
	private final String idReward;
	private final String type;
	private final String title;
	private final String content;
	private final String value;
	private final String date;
	
	public NReward(String idReward, String type, String title, String content, String value, String date)
	{
		this.idReward = idReward;
		this.type = type;
		this.title = title;
		this.content = content;
		this.value = value;
		this.date = date;
	}
	
	public synchronized String idReward() { return idReward; }
	public synchronized String type() { return type; }
	public synchronized String title() { return title; }
	public synchronized String content() { return content; }
	public synchronized String value() { return value; }
	public synchronized String date() { return date; }
}
