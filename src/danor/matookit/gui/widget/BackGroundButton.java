package danor.matookit.gui.widget;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BackGroundButton extends JButton
{
	private static final long serialVersionUID = 1L;

	private final JLabel maskLabel = new JLabel();

	private final boolean isMask;
	
	public BackGroundButton(Container c, String arg0, boolean isMask)
	{
		super(arg0);
		
		maskLabel.addMouseListener(new MouseListener()
		{
			public void mouseReleased(MouseEvent e){}
			public void mousePressed(MouseEvent e){}
			public void mouseExited(MouseEvent e){}
			public void mouseEntered(MouseEvent e){}
			public void mouseClicked(MouseEvent e){}
		});
		
		c.add(isMask?maskLabel:this);
		c.add(isMask?this:maskLabel);
		
		this.isMask = isMask;
		
		if(!isMask)
		{
			maskLabel.setBackground(Color.WHITE);
			maskLabel.setOpaque(true);
		}
	}
	public BackGroundButton(Container c, boolean isMask)
	{
		this(c, (String)null, isMask);
	}
	
	public void setBounds(int x, int y, int width, int height)
	{
		super.setBounds(x, y, width, height);
		
		if(isMask)
			maskLabel.setBounds(x, y, width, height);
		else
			maskLabel.setBounds(x+1, y+1, width-2, height-2);
	}
	
	public void initDragFrame(JFrame frame)
	{
		DragFrameListener listener = new DragFrameListener(frame);  
		maskLabel.addMouseListener(listener);  
		maskLabel.addMouseMotionListener(listener); 
	}
}
