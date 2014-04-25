package danor.matookit.functions;

import java.awt.TrayIcon;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import danor.matookit.natures.*;
import danor.matookit.utils.*;

public class FFryAuto extends TimerTask
{
	private FAction action;

	private JTable tblFryList;
	
	private final String[] priority = UConfig.getInstance().load("FairyList");
	
	private TrayIcon tray;
	
	public FFryAuto(FAction action, JTable tblFryList, TrayIcon tray)
	{
		this.action = action;
		this.tblFryList = tblFryList;
		this.tray = tray;
	}

	public void run()
	{
		try
		{
			ULog.getInstance().log("Timer-FryAuto", true);
		//刷新妖精列表
			action.FairyList(true);
		//标志-是否进入过妖精信息界面
			boolean flgFairyInfo = false;
		//根据优先级排序妖精
			List<NFairy> lstFairywP = new ArrayList<NFairy>();
			
			for(int i = 0; i <= priority.length; i++)
				for(int j = action.arthur.fairys().size() - 1; j >= 0; j--)//逆序
				{
					NFairy f = action.arthur.fairys().get(j);//提取-妖精
					
					if(i < priority.length)//在优先表中
					{
						for(String p:priority[i].split(","))
							if(p.equals(f.name()))
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
			for(NFairy f:lstFairywP)//枚举-妖精
			{
			//妖精在显示表上的行
				UTableRow fr = getRow(f);
				
				if(f.statAlive().equals("1"))//判断-妖精存活
				{
					ULog.getInstance().log(f.name() + "-Lv." + UUtil.Align(f.lv(), "0", 3, 0) + "-" + UUtil.TimeShift(f.restTime()) + "-" + f.finder().name(), true);
					
				//更新妖精信息到显示表
					if(fr.getRowAt() == -1)
					{
						fr.getValues()[0] = "??";
						fr.getValues()[1] = f.name();
						fr.getValues()[2] = f.lv();
						fr.getValues()[3] = f.finder().name();
						fr.getValues()[4] = "未获取";
						fr.getValues()[5] = "未获取";
						fr.getValues()[6] = UUtil.TimeShift(f.restTime());
						fr.getValues()[7] = f.idSerial();
						fr.rowSave();
					}
				
					if(!f.battled())//判断-妖精未攻击
					{
						action.FairyInfo(f);//获取妖精信息
						
						fr.getValues()[4] = calHP(f);
						fr.getValues()[5] = calCL(f);
						fr.rowSave();
						
						flgFairyInfo = true;//判定-进入过获取妖精的界面
						
						if(!f.nowHP().equals("0"))//判断-获取信息后仍未击退
						{
							if(f.battled())
							{
								fr.rowSave(0, "是");
								
								if(lstFairywP.indexOf(f) != lstFairywP.size()-1)
									action.FairyList(false);//返回列表
							}
							else
							{
								NBattleResult result = action.FairyFight(f);//获取战斗结果

								if(result != null && result.error() != null)
								{
									if(result.error().equals("0"))//逻辑-是否成功发起战斗
									{
										ULog.getInstance().log("Result-Battle-RemainHp-"+result.aftHP(), true);
										fr.rowSave(0, "是");
										tray.displayMessage("已舔妖精", f.name() + "-Lv." + UUtil.Align(f.lv(), "0", 3, 0) + "-" + f.finder().name() + "\n" + UUtil.TimeShift(f.restTime()) + "-Remain: " + result.aftHP() + " Hp", TrayIcon.MessageType.NONE);

										Thread.sleep(7000);
										
										if(result.aftHP().equals("0"))//逻辑: 是否成功击退
										{
											action.FairyLose(f);//获取击退失败界面

											if(action.arthur.friends().now() != action.arthur.friends().max())//逻辑: 本人好友已满
												action.FairyInfo(f);//获取妖精信息
										}
									}
									else if(result.error().equals("1050"))
										ULog.getInstance().log("BC不足", true);
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

	private String calCL(NFairy f)
	{
		int base = 0;
		int weight = 10;

		if(f.name().contains("觉醒的")||f.name().contains("狂暴的"))
		{
			base=1000;
			weight = 40;
		}
		
		double percent = (Double.valueOf(f.nowHP())/Double.valueOf(f.maxHP()));
		
		return String.valueOf((int)(percent*(base+weight*Integer.valueOf(f.lv()))));
	}

	public String calHP(NFairy f)
	{
		int p = (int)(100*(Double.valueOf(f.nowHP())/Double.valueOf(f.maxHP())));
		
		return "("+p+") "+f.nowHP()+"/"+f.maxHP();
	}
	
	private UTableRow getRow(NFairy f)
	{
		for(int i=0;i<tblFryList.getRowCount();i++)
			if(((String)((DefaultTableModel)tblFryList.getModel()).getValueAt(i,7)).equals(f.idSerial()))
				return new UTableRow(i,8,tblFryList);
		
		return new UTableRow(-1,8,tblFryList);
	}
}

