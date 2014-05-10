package danor.matookit.utils;

import java.io.File;

public class ULog
{
	private final String timeStart;
	
	private final File fileLog;
	
	private ULog() throws Exception
	{
		timeStart = Long.toString(System.currentTimeMillis());
		
		File dir = new File("./wrk.cn/log");
		if(!dir.exists()) dir.mkdir();//ToEH
		
		fileLog = new File("./wrk.cn/log/"+timeStart+".log");
		fileLog.createNewFile();//ToEH NtTE
	}
	
	public synchronized static void log(String content, boolean typPrint) throws Exception
	{
		getInstance();
		
		if(typPrint)
			UUtil.p(content);
		
		UUtil.Output(ULogContainer.instance.fileLog, (Thread.currentThread().getId()+" "+UUtil.stpShift(System.currentTimeMillis())+" "+content+"\r\n").getBytes("utf-8"), true);//ToEH NtTE
	}
	
	public synchronized static void log(String content) throws Exception
	{
		getInstance();
		
		UUtil.p(content);
		
		UUtil.Output(ULogContainer.instance.fileLog, (Thread.currentThread().getId()+" "+UUtil.stpShift(System.currentTimeMillis())+" "+content+"\r\n").getBytes("utf-8"), true);//ToEH NtTE
	}

	private static class ULogContainer
	{
		private static ULog instance;
		static
		{
			try { instance = new ULog(); log("Log-Init");}
			catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	private static ULog getInstance() { return ULogContainer.instance; }
}
