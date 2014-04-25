package danor.matookit.utils;

import java.io.File;

public class ULog
{
	private final String timeStart;
	
	private final File fileLog;
	
	private ULog() throws Exception
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
			UUtil.p(content);
		
		UUtil.Output(fileLog, (Thread.currentThread().getId()+" "+UUtil.stpShift(System.currentTimeMillis())+" "+content+"\r\n").getBytes("utf-8"), true);//ToEH NtTE
	}
	
	public synchronized void log(String content) throws Exception
	{
		UUtil.p(content);
		
		UUtil.Output(fileLog, (Thread.currentThread().getId()+" "+UUtil.stpShift(System.currentTimeMillis())+" "+content+"\r\n").getBytes("utf-8"), true);//ToEH NtTE
	}

	private static class FcLogContainer
	{
		private static ULog instance;
		static
		{
			try { instance = new ULog(); }
			catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public static ULog getInstance() { return FcLogContainer.instance; }
}
