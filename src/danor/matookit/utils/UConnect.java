package danor.matookit.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class UConnect extends Thread
{
	private final UOption option;
	private final UKey db;
	
	private int codResponse;
	public String cookie;
	
	public boolean isSuccess;
	public File result;
	
	/**
	 *@optionBoolean rqtCookie, typMethod
	 *@optionString cookie, url, param, path
	 */
	public UConnect(UOption option, UKey db, ULog log) throws Exception
	{
		this.option = option;
		this.db = db;

		this.start();
		this.join();
	}
	
	public void run() 
	{
		try
		{
			while(this.result == null && codResponse != 404)
				result = option.getBoolean("typMethod")?Post():Get();
		}
		catch(SocketTimeoutException e)
		{
			isSuccess = true;
			e.printStackTrace();
		}
		catch(Exception e)
		{
			isSuccess = false;
			e.printStackTrace();
		}
		
		isSuccess = true;
	}
	
	private File Post() throws Exception
	{
	//创建请求
		HttpURLConnection connect = (HttpURLConnection) new URL(option.getString("url")).openConnection();
	//传递HTTP参数
		connect.setRequestMethod("POST");
		connect.setDoOutput(true);
		connect.setDoInput(true);
		connect.setConnectTimeout(1000 * 24);
		
		for(int i=0;i<6;i++) 
		{
			String[] kv = db.Data("Property", i);
			connect.setRequestProperty(kv[1],kv[2].equals(";")?"":kv[2]);
		}
	//设置cookie
		if(option.getString("cookie") != null)
		{
			connect.setRequestProperty("Cookie",option.getString("cookie"));
			connect.setRequestProperty("Cookie2","$Version=1");
		}
	//传递参数
		if(option.getString("param") != null)
		{
			OutputStreamWriter osw = new OutputStreamWriter(connect.getOutputStream(), "utf-8");
			osw.write(option.getString("param")); osw.flush(); osw.close();
		}
	//完成请求
		if(connect != null) connect.disconnect();
	//判断是否需要返回cookie
		if(option.getBoolean("rqtCookie"))
			this.cookie = connect.getHeaderField("Set-Cookie").split(" ")[0].replace(";", "");
	//保存响应
		File pakFile = null;
		if(option.getString("path") != null)
		{
		//获取响应
			InputStream is = connect.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int len = 0;
			
			while ((len = is.read(b, 0, b.length)) != -1)                    
			    baos.write(b, 0, len);
			is.close();
			
			ULog.log("Connect-Post-Success");
		//输出响应
			pakFile = new File(option.getString("path"));
			pakFile.createNewFile();

			UUtil.Output(pakFile, baos.toByteArray(), false);
		}
		
		return pakFile;
	}

	private File Get() throws Exception
	{
	//创建请求
		HttpURLConnection connect = (HttpURLConnection) new URL(option.getString("url")).openConnection();
	//传递属性
		connect.setRequestMethod("GET");
		connect.setConnectTimeout(1000 * 24);
		
		int[] aryInt = {0,1,3,4}; 
		for(int i:aryInt) 
		{
			String[] kv = db.Data("Property", i);
			connect.setRequestProperty(kv[1],kv[2].equals(";")?"":kv[2]);
		}
	//判断状态
		codResponse = connect.getResponseCode();
		if(codResponse == 200)
		{
		//获取响应
			InputStream is = connect.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = is.read(b, 0, b.length)) != -1)                    
			    baos.write(b, 0, len);
			is.close();
			
			ULog.log("Connect-Get-Success");
		//保存响应
				
			File pakFile = new File(option.getString("path"));
			pakFile.createNewFile();
			
//			if(option.getBoolean("rqtDecrypt"))
//				FcConvert.decryptAES(baos.toByteArray(), pakFile, db.Data("Cipher", 1)[2].getBytes("utf-8"));
//			else
				UUtil.Output(pakFile, baos.toByteArray(), false);
			
			return pakFile;	
		}
		else
		{
			ULog.log("Connect-Get-Error-"+codResponse+"-"+(option.getString("url")));
			
			return null;
		}
	}
}
