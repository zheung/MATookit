package danor.matookit.gui.widget;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import danor.matookit.utils.UUtil;

public class FramePanel
{
	private final JFrame frame;

	protected JPanel panel;
	protected BackGroundButton bgbBorder;
	protected JSeparator spt;
	
	protected JButton btnClose;
	protected JButton btnMinimize;
	private JButton btnSetting;
	private JButton btnMenu;
	protected JButton btnExit;

	public FramePanel(JFrame frame)
	{
		this.frame = frame;
	}
	
	public void initFrame(boolean hvTray)
	{
		panel = new JPanel();
		panel.setBounds(10, 10, frame.getWidth()-26, frame.getHeight()-48);
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		bgbBorder = new BackGroundButton(frame.getContentPane(), true);
		bgbBorder.setBounds(0, 0, frame.getWidth()-6, frame.getHeight()-28);
		bgbBorder.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		bgbBorder.initDragFrame(frame);
		
		spt = new JSeparator();
		spt.setBounds(frame.getWidth()-67, 0, 14, frame.getHeight()-48);
		spt.setBackground(Color.GRAY);
		spt.setOrientation(SwingConstants.VERTICAL);
		panel.add(spt);
		
		btnClose = new JButton();
		btnMinimize = new JButton();
		btnSetting = new JButton();
		btnMenu = new JButton();
		btnExit = new JButton();
		btnClose.setBounds(frame.getWidth()-62, 0, 30, 30);
		btnMinimize.setBounds(frame.getWidth()-62, 40, 30, 30);
		btnSetting.setBounds(frame.getWidth()-62, frame.getHeight()-158, 30, 30);
		btnMenu.setBounds(frame.getWidth()-62, frame.getHeight()-118, 30, 30);
		btnExit.setBounds(frame.getWidth()-62, frame.getHeight()-78, 30, 30);
		btnClose.setIcon(UUtil.getImageIcon("./wrk/ico/Close.png", 30, 30));
		btnMinimize.setIcon(UUtil.getImageIcon("./wrk/ico/Minimize.png", 30, 30));
		btnSetting.setIcon(UUtil.getImageIcon("./wrk/ico/Settings.png", 30, 30));
		btnMenu.setIcon(UUtil.getImageIcon("./wrk/ico/Menu.png", 30, 30));
		btnExit.setIcon(UUtil.getImageIcon("./wrk/ico/Exit.png", 30, 30));
		btnClose.setFocusable(false);
		btnMinimize.setFocusable(false);
		btnSetting.setFocusable(false);
		btnMenu.setFocusable(false);
		btnExit.setFocusable(false);
		panel.add(btnClose);
		panel.add(btnMinimize);
		panel.add(btnSetting);
		panel.add(btnMenu);
		panel.add(btnExit);
		
		if(hvTray)
			btnClose.addActionListener((e)-> { frame.setVisible(false); frame.dispose(); });
		else
			btnClose.addActionListener((e)-> { System.exit(0); });
			
		btnMinimize.addActionListener((e)-> { frame.setState(JFrame.ICONIFIED); });
		btnExit.addActionListener((e)-> { System.exit(0); });
		
		frame.setUndecorated(true);
		com.sun.awt.AWTUtilities.setWindowOpaque(frame, false);
		frame.getRootPane().setWindowDecorationStyle(javax.swing.JRootPane.NONE);
	}

	public JPanel getPanel() { return panel; }

	public void setMinimize(boolean isVisible) { btnMinimize.setVisible(isVisible); }
	public void setMenu(boolean isVisible) { btnMenu.setVisible(isVisible); }
	public void setSetting(boolean isVisible) { btnSetting.setVisible(isVisible); }
	public void setExit(boolean isVisible) { btnExit.setVisible(isVisible); }

	public void setMenu(ActionListener l) { btnMenu.addActionListener(l); }
	public void setSetting(ActionListener l) { btnSetting.addActionListener(l); }
	public void setExit(ActionListener l) { btnExit.addActionListener(l); }
}
