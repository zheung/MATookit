package danor.matookit.functions;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import danor.matookit.utils.*;

public class FPack
{
	private final String url;
	private final String pakPath;
	private final String subPath;
	
	private int cntPack;
	private List<File> lstFile = new ArrayList<File>();
	
	public FPack(String url, String pakPath, String subPath) throws Exception
	{
		this.url = url;
		this.pakPath = pakPath;
		this.subPath = subPath;
	}
	
	private void decompressPack(File pakFile) throws Exception
	{
		byte[] bytes = UUtil.Input(pakFile);
		
		List<String> lstFileName = new ArrayList<>();
		List<Integer> lstFileLength = new ArrayList<>();
		int cntFile = 0;
		
		UUtil.p("Decompress-Count-" + (cntFile = btoi(bytes[0x11])));
		
		int i = 0x16;
		while(cntFile > 0)
		{
			int nl = bytes[i-1];
			
			byte[] fbs = new byte[nl];
			
			for(int j=0; j<nl; j++)
				fbs[j]=bytes[i+j];
			
			lstFileName.add(new String(fbs));
			
//			UUtil.p("文件名-"lstFileName.get(lstFileName.size() - 1));
			
			lstFileLength.add(btoi(bytes[i+nl+1], bytes[i+nl+2], bytes[i+nl+3]));

//			UUtil.p("长度-" + lstFileLength.get(lstFileLength.size() - 1));

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
				UConvert.decryptAES(fba, rnf, UKey.Data("CipherAES", "1")[0].getBytes());
			
				UUtil.p(lstFileName.get(j)+"-Decrypt");
			}
			else
			{
				UUtil.Output(f, fba, false);
				
				UUtil.p(lstFileName.get(j)+"-Save");
			}
			
			lstFile.add(f);
		}
		
		pakFile.delete();
	}
	
	public List<File> downloadPack() throws Exception
	{
		String urlPack = url.replace("(zkd)", "0");

		UUtil.p("Download-Pack-0");
		
		String[] ps = urlPack.split("/");
		
		UOption option = new UOption().put("rqtCookie", false).put("typMethod", false)
				.put("cookie", (String)null).put("url", urlPack)
				.put("param", (String)null).put("path", pakPath+"\\"+ps[ps.length - 1].replace("?cyt=1", ""));
		UConnect connect = new UConnect(option);
		File pakFile = connect.result;
		
		byte[] bytes = UUtil.Input(pakFile);
		
//		UUtil.p("Pack长度-" + btoi(bytes[0x3], bytes[0x4], bytes[0x5]));
		
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
				
				UUtil.p("Download-Pack-" + i++);
				
				ps = urlPack.split("/");
				
				option = new UOption().put("rqtCookie", false).put("typMethod", false)
						.put("cookie", (String)null).put("url", urlPack)
						.put("param", (String)null).put("path", pakPath+"\\"+ps[ps.length - 1].replace("?cyt=1", ""));
				connect = new UConnect(option);
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
