package org.danor.matookit;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class FcPack
{
	private final FcDataBase db;
	private final FcLog log;
	
	private final String url;
	private final String pakPath;
	private final String subPath;
	
	protected int cntPacksLength;
	protected int cntFilesLength = 0;
	protected int cntPack;
	protected List<File> lstFile = new ArrayList<File>();
	
	public FcPack(String url, String pakPath, String subPath, FcDataBase db, FcLog log) throws Exception
	{
		this.url = url;
		this.db = db;
		this.log = log;
		this.pakPath = pakPath;
		this.subPath = subPath;
	}
	
	private void decompressPack(File pakFile) throws Exception
	{
		byte[] bytes = FcUtil.Input(pakFile);
		
		List<String> lstFileName = new ArrayList<>();
		List<Integer> lstFileLength = new ArrayList<>();
		int cntFile = 0;
		
		FcUtil.p("Decompress-Count-" + (cntFile = btoi(bytes[0x11])));
		
		int i = 0x16;
		while(cntFile > 0)
		{
			int nl = bytes[i-1];
			
			byte[] fbs = new byte[nl];
			
			for(int j=0; j<nl; j++)
				fbs[j]=bytes[i+j];
			
			lstFileName.add(new String(fbs));
			
//			if(indent > 0) FcUtil.q(indent + 1, lstFileName.get(lstFileName.size() - 1));
			
			lstFileLength.add(btoi(bytes[i+nl+1], bytes[i+nl+2], bytes[i+nl+3]));

//			if(indent > 0) FcUtil.p(" " + lstFileLength.get(lstFileLength.size() - 1));

			cntFilesLength += lstFileLength.get(lstFileLength.size() - 1);
			i += nl + 8;
			cntFile--;
		}
		
		i-=3;
		for(int j = 0; j < lstFileLength.size(); j++)
		{
			int k = lstFileLength.get(j);
			
			byte[] fba = new byte[k];
			for(int l = 0; l < k; l++)
				fba[l] = bytes[i+l];
			
			i += k + 1;
			
			File f = new File(pakFile.getParent() + "/" + subPath + lstFileName.get(j));

			
			if(lstFileName.get(j).indexOf("rja") == -1)
			{
				File rnf = new File(f.getPath()+".png");
				FcConvert.decryptAES(fba, rnf, db.Data("Cipher", 1)[2].getBytes());
			
				FcUtil.p(lstFileName.get(j)+"-Decrypt");
			}
			else
			{
				FcUtil.Output(f, fba, false);
				
				FcUtil.p(lstFileName.get(j)+"-Save");
			}
			
			lstFile.add(f);
		}
		
		pakFile.delete();
	}
	
	protected List<File> downloadPack() throws Exception
	{
		String urlPack = url.replace("(zkd)", "0");

		FcUtil.p("Download-Pack-0");
		
		String[] ps = urlPack.split("/");
		
		FcOption option = new FcOption().put("rqtCookie", false).put("typMethod", false)
				.put("cookie", (String)null).put("url", urlPack)
				.put("param", (String)null).put("path", pakPath+"\\"+ps[ps.length - 1].replace("?cyt=1", ""));
		FcConnect connect = new FcConnect(option, db, log);
		File pakFile = connect.result;
		
		byte[] bytes = FcUtil.Input(pakFile);
		
		cntPacksLength = btoi(bytes[0x3], bytes[0x4], bytes[0x5]);
		
		cntPack = btoi(bytes[0x9]);

		if(cntPack == 1)
		{
			if(btoi(bytes[0x11]) != 0)
				decompressPack(pakFile);
		}
		else
		{
			pakFile.delete();
			
			int i = 1;
			
			while(i < cntPack)
			{
				urlPack = url.replace("(zkd)", String.valueOf(i));
				
				FcUtil.p("Download-Pack-" + i++);
				
				ps = urlPack.split("/");
				
				option = new FcOption().put("rqtCookie", false).put("typMethod", false)
						.put("cookie", (String)null).put("url", urlPack)
						.put("param", (String)null).put("path", pakPath+"\\"+ps[ps.length - 1].replace("?cyt=1", ""));
				connect = new FcConnect(option, db, log);
				pakFile = connect.result;

				decompressPack(pakFile);
			}
		}
		
		return lstFile;
	}
	
	private static int btoi(byte... aryByte)
	{
		int value = 0;
		for(int i = 0; i < aryByte.length; i++)
		{
			int shift = (aryByte.length - 1 - i) * 8;
			value += (aryByte[i] & 0x000000FF) << shift;
		}
		return value;
	}

	public static void mrgMainbg(File picTop,File picBtm, File picDst) throws Exception
	{
		BufferedImage imgTop = ImageIO.read(picTop);
		BufferedImage imgBtm = ImageIO.read(picBtm);
		
		int wid = imgTop.getWidth();
		int heiTop = imgTop.getHeight()-16;
		int heiBtm = imgBtm.getHeight();
		
	//读取RGB
		int[] imgTopArray = imgTop.getRGB(0, 0, wid, heiTop, null, 0, wid);
		int[] imgBtmArray = imgBtm.getRGB(0, 0, wid, heiBtm, null, 0, wid);

	//生成新图片
		BufferedImage ImageNew = new BufferedImage(wid, heiTop + heiBtm, 2);
		ImageNew.setRGB(0, 0, wid, heiTop, imgTopArray, 0, wid);
		ImageNew.setRGB(0, heiTop, wid, heiBtm, imgBtmArray, 0, wid);

	//写图片
		ImageIO.write(ImageNew, "png", picDst);
	}
}
