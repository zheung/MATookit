package danor.matookit.utils;

import java.io.File;

public class UcLog
{
	private final String timeStart;
	
	private final File fileLog;
	
	private UcLog() throws Exception
	{
		timeStart = Long.toString(System.currentTimeMillis());
		
		File dir = new File("./wrk/log");
		if(!dir.exists()) dir.mkdir();//ToEH
		
		fileLog = new File("./wrk/log/"+timeStart+".log");
		fileLog.createNewFile();//ToEH NtTE
		
		log("Log-Start", true);
	}
	
	public synchronized void log(String content, boolean typPrint) throws Exception
	{
		if(typPrint)
			UcUtil.p(content);
		
		UcUtil.Output(fileLog, (Thread.currentThread().getId()+" "+UcUtil.stpShift(System.currentTimeMillis())+" "+content+"\r\n").getBytes("utf-8"), true);//ToEH NtTE
	}
	
	public synchronized void log(String content) throws Exception
	{
		UcUtil.p(content);
		
		UcUtil.Output(fileLog, (Thread.currentThread().getId()+" "+UcUtil.stpShift(System.currentTimeMillis())+" "+content+"\r\n").getBytes("utf-8"), true);//ToEH NtTE
	}

	private static class FcLogContainer
	{
		private static UcLog instance;
		static
		{
			try { instance = new UcLog(); }
			catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public static UcLog getInstance() { return FcLogContainer.instance; }
}
