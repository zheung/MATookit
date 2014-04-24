package danor.matookit.functions;

import java.awt.TrayIcon;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.danor.matookit.objects.DcFairy;
import org.danor.matookit.objects.DcResultFight;
import org.danor.util.UcTableRow;

public class FcFryAuto extends TimerTask
{
	private FcAction action;

	private JTable tblFryList;
	
	private final String[] priority = FcConfig.getInstance().load("FairyList");
	
	private TrayIcon tray;
	
	public FcFryAuto(FcAction action, JTable tblFryList, TrayIcon tray)
	{
		this.action = action;
		this.tblFryList = tblFryList;
		this.tray = tray;
	}

	public void run()
	{
		try
		{
			FcLog.getInstance().log("Timer-FryAuto", true);
		//刷新妖精列表
			action.FairyList(true);
		//标志-是否进入过妖精信息界面
			boolean flgFairyInfo = false;
		//根据优先级排序妖精
			List<DcFairy> lstFairywP = new ArrayList<DcFairy>();
			
			for(int i = 0; i <= priority.length; i++)
				for(int j = action.arthur.fairys.size() - 1; j >= 0; j--)//逆序
				{
					DcFairy f = action.arthur.fairys.get(j);//提取-妖精
					
					if(i < priority.length)//在优先表中
					{
						for(String p:priority[i].split(","))
							if(p.equals(f.name))
							{
								lstFairywP.add(f);
								break;
							}
					}
					else if(i == priority.length)//不在优先表中
					{
						if(lstFairywP.indexOf(f) == -1)
							lstFairywP.add(f);
					}
				}	
		//根据排序表处理妖精	
			for(DcFairy f:lstFairywP)//枚举-妖精
			{
			//妖精在显示表上的行
				UcTableRow fr = getRow(f);
				
				if(f.livestat.equals("1"))//判断-妖精存活
				{
					FcLog.getInstance().log(f.name + "-Lv." + FcUtil.Align(f.level, "0", 3, 0) + "-" + FcUtil.TimeShift(f.time) + "-" + f.user.name, true);
					
				//更新妖精信息到显示表
					if(fr.getRowAt() == -1)
					{
						fr.getValues()[0] = "??";
						fr.getValues()[1] = f.name;
						fr.getValues()[2] = f.level;
						fr.getValues()[3] = f.user.name;
						fr.getValues()[4] = "未获取";
						fr.getValues()[5] = "未获取";
						fr.getValues()[6] = FcUtil.TimeShift(f.time);
						fr.getValues()[7] = f.sid;
						fr.rowSave();
					}
				
					if(!f.isFighted)//判断-妖精未攻击
					{
						action.FairyInfo(f);//获取妖精信息
						
						fr.getValues()[4] = calHP(f);
						fr.getValues()[5] = calCL(f);
						fr.rowSave();
						
						flgFairyInfo = true;//判定-进入过获取妖精的界面
						
						if(!f.HP.equals("0"))//判断-获取信息后仍未击退
						{
							if(f.isFighted)
							{
								fr.rowSave(0, "是");
								
								if(lstFairywP.indexOf(f) != lstFairywP.size()-1)
									action.FairyList(false);//返回列表
							}
							else
							{
								DcResultFight result = action.FairyFight(f);//获取战斗结果

								if(result != null && result.error != null)
								{
									if(result.error.equals("0"))//逻辑-是否成功发起战斗
									{
										FcLog.getInstance().log("Result-Battle-RemainHp-"+result.aftHP, true);
										fr.rowSave(0, "是");
										tray.displayMessage("已舔妖精", f.name + "-Lv." + FcUtil.Align(f.level, "0", 3, 0) + "-" + f.user.name + "\n" + FcUtil.TimeShift(f.time) + "-Remain: " + result.aftHP + " Hp", TrayIcon.MessageType.NONE);

										Thread.sleep(7000);
										
										if(result.aftHP.equals("0"))//逻辑: 是否成功击退
										{
											action.FairyLose(f);//获取击退失败界面

											if(action.arthur.info.frdCount != action.arthur.info.frdMax)//逻辑: 本人好友已满
												action.FairyInfo(f);//获取妖精信息
										}
									}
									else if(result.error.equals("1050"))
										FcLog.getInstance().log("BC不足", true);
								}
							}
						}
						else
							if(lstFairywP.indexOf(f) != lstFairywP.size()-1)
								action.FairyList(false);
					}
				}
			}

			if(!flgFairyInfo)//操作: 返回菜单
				action.Menu();
		}
		catch(Exception e1) { e1.printStackTrace(); }
	}

	private String calCL(DcFairy f)
	{
		int base = 0;
		int weight = 10;

		if(f.name.contains("觉醒的")||f.name.contains("狂暴的"))
		{
			base=1000;
			weight = 40;
		}
		
		double percent = (Double.valueOf(f.HP)/Double.valueOf(f.maxHP));
		
		return String.valueOf((int)(percent*(base+weight*Integer.valueOf(f.level))));
	}

	public String calHP(DcFairy f)
	{
		int p = (int)(100*(Double.valueOf(f.HP)/Double.valueOf(f.maxHP)));
		
		return "("+p+") "+f.HP+"/"+f.maxHP;
	}
	
	private UcTableRow getRow(DcFairy f)
	{
		for(int i=0;i<tblFryList.getRowCount();i++)
			if(((String)((DefaultTableModel)tblFryList.getModel()).getValueAt(i,7)).equals(f.sid))
				return new UcTableRow(i,8,tblFryList);
		
		return new UcTableRow(-1,8,tblFryList);
	}
}

