package danor.matookit.utils;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;

public class URePrintStream extends PrintStream
{  
	private Vector<String> vector; 
	private JList<String> list;
	private JScrollBar scroll;
	
	public URePrintStream(OutputStream sout, JList<String> slist, JScrollBar Sscroll)
	{
		super(sout);
		this.list = slist;
		vector=new Vector<String>(); 
		vector.add("");
		scroll = Sscroll;
	}

	public void write(byte[] buf, int off, int len)
	{
		final String m = new String(buf, off, len);
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				if(m.equals("\r\n"))
						vector.add((""));
				else
				{
					String ls = vector.lastElement() + m;
					vector.remove(vector.size()-1);
					vector.add(ls);
				}
				list.setListData(vector);
				
				scroll.setValue(scroll.getMaximum()); //设置一个具体位置，value为具体的位置
			}
		});
	}

	public void clear()
	{
		vector.removeAllElements();
		vector.add((""));
		list.setListData(vector);
	}
}
