package danor.matookit.gui.widget;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

public class DragFrameListener extends MouseInputAdapter
{
	private Point point;
	private final JFrame frame;
	
	public DragFrameListener(JFrame frame) { this.frame = frame; }

	public void mouseDragged(MouseEvent e)
	{
		Point newPoint = e.getLocationOnScreen();
		frame.setLocation(frame.getX() + (newPoint.x - point.x), frame.getY() + (newPoint.y - point.y));
		point = newPoint;
	}

	public void mousePressed(MouseEvent e) { point = e.getLocationOnScreen(); }
}