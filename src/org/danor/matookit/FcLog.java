package org.danor.matookit;

import java.io.File;

public class FcLog
{
	private final String timeStart;
	
	private final File fileLog;
	
	private FcLog() throws Exception
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
			FcUtil.p(content);
		
		FcUtil.Output(fileLog, (Thread.currentThread().getId()+" "+FcUtil.stpShift(System.currentTimeMillis())+" "+content+"\r\n").getBytes("utf-8"), true);//ToEH NtTE
	}
	
	public synchronized void log(String content) throws Exception
	{
		FcUtil.p(content);
		
		FcUtil.Output(fileLog, (Thread.currentThread().getId()+" "+FcUtil.stpShift(System.currentTimeMillis())+" "+content+"\r\n").getBytes("utf-8"), true);//ToEH NtTE
	}

	private static class FcLogContainer
	{
		private static FcLog instance;
		static
		{
			try { instance = new FcLog(); }
			catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public static FcLog getInstance() { return FcLogContainer.instance; }
}
