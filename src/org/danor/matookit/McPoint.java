package org.danor.matookit;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;

public class McPoint
{
	private DcPoint point;
	private JLabel valAP;
	private JLabel valBC;
	private JButton prgAP;
	private JButton prgBC;
	
	protected Timer timerAP;
	protected Timer timerBC;
	
	protected boolean alertAP = false;
	protected boolean alertBC = false;
	
	public McPoint(DcPoint point, JLabel valAP, JLabel valBC, JButton prgAP, JButton prgBC)
	{
		this.point = point;
		this.valAP = valAP;
		this.valBC = valBC;
		this.prgAP = prgAP;
		this.prgBC = prgBC;
	}

	public void reset()
	{
		if(timerAP != null)
			timerAP.cancel();
		
		valAP.setText(point.nowAP + "/" + point.maxAP);
		prgAP.setBounds(70, 50, (int)(100*(double)point.nowAP/point.maxAP), 20);
		timerAP = new Timer();
		timerAP.schedule(new TimerTask()
		{
			public void run()
			{
				point.nowAP++;
				valAP.setText(point.nowAP + "/" + point.maxAP);
				prgAP.setBounds(70, 50, (int)(100*(double)point.nowAP/point.maxAP), 20);
				
				try
				{
					if(point.maxAP - point.nowAP < 7)
					{	
						if(!alertAP)
						{
							FcUtil.Sound();
							alertAP = true;
						}
					}
					else
						alertAP = false;
				}
				catch(Exception e) { e.printStackTrace(); }
			}
		}, 180000 - point.revAP * 1000, 180000);
		
		if(timerBC != null)
			timerBC.cancel();
		
		valBC.setText(point.nowBC + "/" + point.maxBC);
		prgBC.setBounds(70, 80, (int)(100*(double)point.nowBC/point.maxBC), 20);
		timerBC = new Timer();
		timerBC.schedule(new TimerTask()
		{
			public void run()
			{
				point.nowBC++;
				valBC.setText(point.nowBC + "/" + point.maxBC);
				prgBC.setBounds(70, 80, (int)(100*(double)point.nowBC/point.maxBC), 20);
				
				try
				{
					if(point.maxBC - point.nowBC < 14)
					{	
						if(!alertBC)
						{
							FcUtil.Sound();
							alertBC = true;
						}
					}
					else
						alertBC = false;
				}
				catch(Exception e) { e.printStackTrace(); }
			}
		}, 60000 - point.revBC * 1000, 60000);
			
	}
}

