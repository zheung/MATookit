package danor.matookit.middles;

import danor.matookit.natures.NPoint;
import danor.matookit.utils.*;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;

public class MPoint
{
	private NPoint point;
	private JLabel valAP;
	private JLabel valBC;
	private JButton prgAP;
	private JButton prgBC;
	
	private Timer timerAP;
	private Timer timerBC;
	
	private boolean alertAP = false;
	private boolean alertBC = false;
	
	private boolean isReset = false;
	
	private MPoint(NPoint point, JLabel valAP, JLabel valBC, JButton prgAP, JButton prgBC)
	{
		this.point = point;
		this.valAP = valAP;
		this.valBC = valBC;
		this.prgAP = prgAP;
		this.prgBC = prgBC;
	}

	public static void reset()
	{
		McPointContainer.instance.timerAP.cancel();
		McPointContainer.instance.timerBC.cancel();
		
		McPointContainer.instance.isReset = true;
	}
	public static void update()
	{
		if(McPointContainer.instance == null)
			return;
		
		NPoint point = McPointContainer.instance.point;
		Timer timerAP = McPointContainer.instance.timerAP;
		Timer timerBC = McPointContainer.instance.timerBC;
		JLabel valAP = McPointContainer.instance.valAP;
		JLabel valBC = McPointContainer.instance.valBC;
		JButton prgAP = McPointContainer.instance.prgAP;
		JButton prgBC = McPointContainer.instance.prgBC;
		
		if(timerAP != null)
			timerAP.cancel();
		
		valAP.setText(point.nowAP() + "/" + point.maxAP());
		prgAP.setBounds(70, 50, (int)(100*(double)point.nowAP()/point.maxAP()), 20);
		timerAP = new Timer();
		timerAP.schedule(new TimerTask()
		{
			public void run()
			{
				point.nowAP(point.nowAP()+1);
				valAP.setText(point.nowAP() + "/" + point.maxAP());
				prgAP.setBounds(70, 50, (int)(100*(double)point.nowAP()/point.maxAP()), 20);
				
				try
				{
					if(point.maxAP() - point.nowAP() < 7)
					{	
						if(!McPointContainer.instance.alertAP)
						{
							UUtil.Sound();
							McPointContainer.instance.alertAP = true;
						}
					}
					else
						McPointContainer.instance.alertAP = false;
				}
				catch(Exception e) { e.printStackTrace(); }
			}
		}, 180000 - point.revAP() * 1000, 180000);
		
		if(timerBC != null)
			timerBC.cancel();
		
		valBC.setText(point.nowBC() + "/" + point.maxBC());
		prgBC.setBounds(70, 80, (int)(100*(double)point.nowBC()/point.maxBC()), 20);
		timerBC = new Timer();
		timerBC.schedule(new TimerTask()
		{
			public void run()
			{
				point.nowBC(point.nowBC()+1);
				valBC.setText(point.nowBC() + "/" + point.maxBC());
				prgBC.setBounds(70, 80, (int)(100*(double)point.nowBC()/point.maxBC()), 20);
				
				try
				{
					if(point.maxBC() - point.nowBC() < 14)
					{	
						if(!McPointContainer.instance.alertBC)
						{
							UUtil.Sound();
							McPointContainer.instance.alertBC = true;
						}
					}
					else
						McPointContainer.instance.alertBC = false;
				}
				catch(Exception e) { e.printStackTrace(); }
			}
		}, 60000 - point.revBC() * 1000, 60000);
			
	}

	private static class McPointContainer { private static MPoint instance; }
	public synchronized static MPoint getInstance(NPoint point, JLabel valAP, JLabel valBC, JButton prgAP, JButton prgBC)
	{
		if(McPointContainer.instance == null)
			return (McPointContainer.instance = new MPoint(point, valAP, valBC, prgAP, prgBC));
		else if(McPointContainer.instance.isReset)
			return (McPointContainer.instance = new MPoint(point, valAP, valBC, prgAP, prgBC));
		else
			return McPointContainer.instance;
	}
	public synchronized static MPoint getInstance() { return McPointContainer.instance; }
}

