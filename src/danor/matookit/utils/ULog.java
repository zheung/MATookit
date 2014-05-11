package danor.matookit.utils;

import java.io.File;

import danor.matookit.functions.FServer;

public class ULog
{
	private final String timeStart;
	
	private final File fileLog;
	
	private ULog() throws Exception
	{
		timeStart = Long.toString(System.currentTimeMillis());
		
		if(!FServer.dirLogAll.exists()) FServer.dirLogAll.mkdir();//ToEH
		
		fileLog = new File(FServer.dirLogAll, timeStart+".log");
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
