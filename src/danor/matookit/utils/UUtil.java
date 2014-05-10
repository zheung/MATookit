package danor.matookit.utils;

import java.applet.Applet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UUtil
{
	
	public static byte[] Input(File ifile) throws Exception
	{
		InputStream is = new FileInputStream(ifile);
		byte[] fbytes = new byte[is.available()];
		while(is.read(fbytes) != -1);
		is.close();
		return fbytes;
	}
	public static void Output(File ofile, byte[] fbytes, boolean isAppend) throws Exception
	{
		ofile.getParentFile().mkdirs();
		
		OutputStream os = new FileOutputStream(ofile, isAppend);
		for(byte b:fbytes)
		{
			os.write(b);
			os.flush();
		}
		os.close();
	}
	
	public static String TimeShift(String time)
	{
		int t = Integer.parseInt(time);
		int m = t / 60;
		int s = t - (m * 60);
		int h = m / 60;
		m = m - (h * 60);
		
		return Align(h+"","0",2,0) + "h" + Align(m+"","0",2,0) + "m" + Align(s+"","0",2,0) + "s";
	}
	
	public static void p(Object obj) { System.out.println(Thread.currentThread().getId()+" "+stpShift(System.currentTimeMillis())+" "+obj); }
	public static void pp(Object obj) { System.out.println(obj); }

	public static void q(Object obj,boolean isHead){ System.out.print(isHead?Thread.currentThread().getId()+" "+obj:obj); }
	public static void qq(Object obj) { System.out.print(obj); }

	public static String g() throws Exception { return new BufferedReader(new InputStreamReader(System.in)).readLine(); }

	public static String stpShift(long stamp)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return sdf.format(stamp);
	}
	
	public static String Align(String original, String padding, int length, int direction)
	{
		if(direction == 0)//0:在前面填充;1:在后面填充
			for(int i=0; i<length-original.length(); i++)
				original = padding + original;
		else
			for(int i=0; i<length-original.length(); i++)
				original += padding;
		
		return original;
	}

//	public static void Sound() throws Exception
//	{
//		File audioFile = new File("./res/Ring.mp3");
//		Player audioPlayer = Manager.createRealizedPlayer(audioFile.toURI().toURL());
//		
//		audioPlayer.start();
//		Thread.currentThread();
//		Thread.sleep((long)(int)(audioPlayer.getDuration().getSeconds() * 1000) + 500);
//		audioPlayer.stop();
//		audioPlayer.close();
//	}
	
	public static void Sound() throws Exception
	{
		Applet.newAudioClip(new File("./wrk/res/gui/rin.wav").toURI().toURL()).play();
	}
	
	public static <T> List<T> reverse(List<T> list)
	{
		List<T> lstReverse = new ArrayList<T>(list);
		Collections.reverse(lstReverse);
		return lstReverse;
	}
}