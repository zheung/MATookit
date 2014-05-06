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

	public synchronized static void reset()
	{
		if(McPointContainer.instance != null)
		{
			if(McPointContainer.instance.timerAP != null)
				McPointContainer.instance.timerAP.cancel();
			if(McPointContainer.instance.timerBC != null)
				McPointContainer.instance.timerBC.cancel();

			McPointContainer.instance.isReset = true;
		}
	}
	public synchronized static void update()
	{
		if(McPointContainer.instance == null)
			return;
		
		if(McPointContainer.instance.timerAP != null)
			McPointContainer.instance.timerAP.cancel();
		
		McPointContainer.instance.valAP.setText(McPointContainer.instance.point.nowAP() + "/" + McPointContainer.instance.point.maxAP());
		McPointContainer.instance.prgAP.setBounds(70, 50, (int)(100*(double)McPointContainer.instance.point.nowAP()/McPointContainer.instance.point.maxAP()), 20);
		McPointContainer.instance.timerAP = new Timer();
		McPointContainer.instance.timerAP.schedule(new TimerTask()
		{
			public void run()
			{
				McPointContainer.instance.point.nowAP(McPointContainer.instance.point.nowAP()+1);
				McPointContainer.instance.valAP.setText(McPointContainer.instance.point.nowAP() + "/" + McPointContainer.instance.point.maxAP());
				McPointContainer.instance.prgAP.setBounds(70, 50, (int)(100*(double)McPointContainer.instance.point.nowAP()/McPointContainer.instance.point.maxAP()), 20);
				
				try
				{
					if(McPointContainer.instance.point.maxAP() - McPointContainer.instance.point.nowAP() < 7)
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
		}, 180000 - McPointContainer.instance.point.revAP() * 1000, 180000);
		
		if(McPointContainer.instance.timerBC != null)
			McPointContainer.instance.timerBC.cancel();
		
		McPointContainer.instance.valBC.setText(McPointContainer.instance.point.nowBC() + "/" + McPointContainer.instance.point.maxBC());
		McPointContainer.instance.prgBC.setBounds(70, 80, (int)(100*(double)McPointContainer.instance.point.nowBC()/McPointContainer.instance.point.maxBC()), 20);
		McPointContainer.instance.timerBC = new Timer();
		McPointContainer.instance.timerBC.schedule(new TimerTask()
		{
			public void run()
			{
				McPointContainer.instance.point.nowBC(McPointContainer.instance.point.nowBC()+1);
				McPointContainer.instance.valBC.setText(McPointContainer.instance.point.nowBC() + "/" + McPointContainer.instance.point.maxBC());
				McPointContainer.instance.prgBC.setBounds(70, 80, (int)(100*(double)McPointContainer.instance.point.nowBC()/McPointContainer.instance.point.maxBC()), 20);
				
				try
				{
					if(McPointContainer.instance.point.maxBC() - McPointContainer.instance.point.nowBC() < 14)
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
		}, 60000 - McPointContainer.instance.point.revBC() * 1000, 60000);
			
	}

	private static class McPointContainer { private static MPoint instance; }
	public synchronized static MPoint getInstance(NPoint point, JLabel valAP, JLabel valBC, JButton prgAP, JButton prgBC)
	{
		if(McPointContainer.instance == null || McPointContainer.instance.isReset)
			return (McPointContainer.instance = new MPoint(point, valAP, valBC, prgAP, prgBC));
		else
			return McPointContainer.instance;
	}
	public synchronized static MPoint getInstance() { return McPointContainer.instance; }
}

