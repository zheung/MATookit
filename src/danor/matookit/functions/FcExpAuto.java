package danor.matookit.functions;

import java.awt.TrayIcon;
import java.io.File;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.danor.matookit.objects.DcArea;
import org.danor.matookit.objects.DcFloor;
import org.danor.matookit.objects.DcResultExplore;

public class FcExpAuto extends TimerTask
{
	private FcAction action;
	
	private DcArea area;
	private String idFloor;
	
	private boolean flgFirstExplore = true;

	private JTable tblLog;

	private TrayIcon tray;

	private JButton button;

	private JComboBox<String> cbxExpFloor;
	
	public FcExpAuto(FcAction action, String nameArea, JTable tblLog, TrayIcon tray, JButton button, JComboBox<String> cbxExpFloor)
	{
		this.action = action;
		this.tblLog = tblLog;
		this.tray = tray;
		this.button = button;
		this.cbxExpFloor = cbxExpFloor;
		
		this.idFloor = ((String)cbxExpFloor.getSelectedItem()).split(" ")[1];
		
		cbxExpFloor.setEnabled(false);
		
		for(DcArea a:action.arthur.areas)
			if(a.name.equals(nameArea))
			{
				this.area = a;
				break;
			}
	}
	
	public void run()
	{
		
		try
		{
			FcLog.getInstance().log("Timer-ExpAuto", true);
			FcLog.getInstance().log("Area-"+area.name, true);
			FcLog.getInstance().log("Floor-"+idFloor, true);
			
			if(flgFirstExplore)
			{
				action.Floor(area, idFloor);
				flgFirstExplore = false;
			}
			
			DcResultExplore result = action.Explore(area, idFloor);
			
			String[] st = new String[6];
			String sl = "Floor-"+idFloor+" Progress-"+result.prog+"% ";
			st[0] = idFloor;
			st[1] = result.prog+"%";
			st[2] = result.exp;
			st[3] = result.gold;
			
			LogPrgExplore(result);
			
			boolean flgWait = false;
			switch(result.type)
			{
			case "5":
				sl += "Floor-Complete";
				st[4] = "探索完毕";
				break;
			case "6":
				sl += "AP-Lack-Wait";
				st[4] = "没AP";
				flgWait = !flgWait;
				break;
			case "12":
			case "13":
				sl += (result.type.equals("12")?"AP-":"BC-")+"Recv-"+result.recover;
				st[4] = "回"+(result.type.equals("12")?"AP":"BC");
				st[5] = result.recover;
				break;
			case "15":
				sl += "Card-Get";
				st[4] = "合成卡";
				break;
			case "3":
				sl += "Card-New";
				st[4] = "新卡";
				break;
			case "19":
				sl += "Collection-Get-"+result.amtItem;
				st[4] = "收集";
				st[5] = result.amtItem;
				break;
			case "0":
				sl += "Nothing";
				st[4] = "无事件";
				break;
			case "2":
				sl += "Meet-Player-"+result.encounter.name;
				st[4] = "遇玩家";
				st[5] = result.encounter.name;
				break;
			case "1":
				sl += "Meet-Fairy-"+result.fairy.name+"-Lv."+result.fairy.level;
				st[4] = "遇妖精";
				st[5] = result.fairy.name;
				action.FairyFight(result.fairy);
				break;
			}
			
//			FcUtil.p("ReaminExp-"+arthur.info.exp+"-Exp-"+result.exp+"-Gold-"+result.gold);
//			v.add(new String("ReaminExp-"+arthur.info.exp+"-Exp-"+result.exp+"-Gold-"+result.gold));
			
			if(result.hasNextFloor)
			{
				tray.displayMessage("区域探索完成", area.name+" 区域"+idFloor, TrayIcon.MessageType.NONE);
				area.floors.get(Integer.valueOf(idFloor)).prog = "100";
				idFloor = String.valueOf(Integer.parseInt(idFloor) + 1);
				action.Floor(area, idFloor);
				
				cbxExpFloor.removeAllItems();;
				cbxExpFloor.addItem("");
				
				for(DcFloor f:FcUtil.reverse(area.floors))
					cbxExpFloor.addItem("区域 "+f.idFloor+" ("+f.prog+"%) -"+f.cost);
			}
			
			if(result.prog.equals("100") && !result.hasNextFloor)
			{
				tray.displayMessage("秘境探索完成", area.name+" 自动退出", TrayIcon.MessageType.NONE);
				button.setText("开始");
				cbxExpFloor.setEnabled(true);
				this.cancel();
			}
				
			
			FcLog.getInstance().log(sl, true);
			((DefaultTableModel)tblLog.getModel()).addRow(st);
			
			if(flgWait)
				Thread.sleep((Integer.parseInt(area.floors.get(Integer.parseInt(idFloor) - 1).cost)-action.arthur.points.nowAP)*180000);
		}
		catch(Exception e1) { e1.printStackTrace(); }
	}
	
	private void LogPrgExplore(DcResultExplore result) throws Exception
	{
		File log = new File("./wrk/dat/prg/prgArea"+area.idArea+area.name+".txt");
		
		log.createNewFile();
		
		StringBuilder sb = new StringBuilder();
		sb.append(idFloor).append("(").append(area.floors.get(Integer.parseInt(idFloor)-1).cost).append("):").append(result.prog).append("\r\n");
		
		FcUtil.Output(log, sb.toString().getBytes(), true);
	}
}
