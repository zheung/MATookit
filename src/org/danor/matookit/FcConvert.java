package org.danor.matookit;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class FcConvert
{
	private static final byte[] encodingTable, decodingTable;
	static
	{
		encodingTable = new byte[64];
		for (int i = 0; i < 26; i++)
			encodingTable[i] = (byte)(65 + i);
		for (int i = 26; i < 52; i++)
			encodingTable[i] = (byte)(97 + i - 26);
		for (int i = 52; i < 62; i++)
			encodingTable[i] = (byte)(48 + i - 52);
		encodingTable[62] = 43;
		encodingTable[63] = 47;
		
		decodingTable = new byte[128];
		for (int i = 0; i < 128; i++)
			decodingTable[i] = (byte) -1;
		for (int i = 'A'; i <= 'Z'; i++)
			decodingTable[i] = (byte) (i - 'A');
		for (int i = 'a'; i <= 'z'; i++)
			decodingTable[i] = (byte) (i - 'a' + 26);
		for (int i = '0'; i <= '9'; i++)
			decodingTable[i] = (byte) (i - '0' + 52);
		decodingTable['+'] = 62;
		decodingTable['/'] = 63;
	}

	/**
	 * AES解密
	 * @param aryByte 密文的字节数组.如果等于null,从File中读取明文,解密后写入cvtFile
	 * @param cvtFile 保存明文或密文(artByte等于null)的文件
	 * @param keyAES 密钥的字节数组
	 * @return 明文的字节数组
	 */
	protected static byte[] decryptAES(byte[] aryByte, File cvtFile, byte[] keyAES) throws Exception
	{
		if(aryByte == null && cvtFile != null)
			aryByte = FcUtil.Input(cvtFile);
		
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyAES, "AES"));
		aryByte = cipher.doFinal(aryByte);
		
		if(cvtFile != null)
			FcUtil.Output(cvtFile, aryByte, false);
		
		return aryByte;
	}
	/**
	 * AES加密
	 * @param aryByte 明文的字节数组.如果等于null,从File中读取明文,加密后写入cvtFile
	 * @param cvtFile 保存明文(artByte等于null)或密文的文件
	 * @param keyAES 密钥的字节数组
	 * @return 密文的字节数组
	 */
	protected static byte[] encryptAES(byte[] aryByte, File cvtFile, byte[] keyAES) throws Exception
	{
		if(aryByte == null && cvtFile != null)
			aryByte = FcUtil.Input(cvtFile);
		
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyAES, "AES"));
		aryByte = cipher.doFinal(aryByte);
		
		if(cvtFile != null)
			FcUtil.Output(cvtFile, aryByte, false);
		
		return aryByte;
	}
	protected static byte[] generateKeyAES() throws Exception
	{
		KeyGenerator keygen = KeyGenerator.getInstance("AES");  
		keygen.init(128, SecureRandom.getInstance("SHA1PRNG"));
		
		return keygen.generateKey().getEncoded();
	}
	
	protected static byte[] decryptRSA(byte[] aryByte, File cvtFile, byte[] keyRSA64) throws Exception
	{
		if(aryByte == null && cvtFile != null)
			aryByte = FcUtil.Input(cvtFile);
		
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(FcConvert.decodeBase64(keyRSA64, null))));
		aryByte = cipher.doFinal(aryByte);
		
		if(cvtFile != null)
			FcUtil.Output(cvtFile, aryByte, false);
		
		return aryByte;
	}
	protected static byte[] encryptRSA(byte[] aryByte, File cvtFile, byte[] keyRSA64) throws Exception
	{
		if(aryByte == null && cvtFile != null)
			aryByte = FcUtil.Input(cvtFile);
		
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(FcConvert.decodeBase64(keyRSA64, null))));
		aryByte = cipher.doFinal(aryByte);
		
		if(cvtFile != null)
			FcUtil.Output(cvtFile, aryByte, false);
		
		return aryByte;
	}
	
	protected static byte[] decodeBase64(byte[] aryByte, File cvtFile) throws Exception
	{
		if(aryByte == null && cvtFile != null)
			aryByte = FcUtil.Input(cvtFile);

		byte[] bytes;
		byte b1;
		byte b2;
		byte b3;
		byte b4;	
		byte[] temp = new byte[aryByte.length];
		int bytesCopied = 0;
		
		for (int i = 0; i < aryByte.length; i++)
			if (aryByte[i] == 61 || !((aryByte[i] < 0) || (aryByte[i] >= 128) || (decodingTable[aryByte[i]] == -1)))
				temp[bytesCopied++] = aryByte[i];
		
		byte[] newData = new byte[bytesCopied];
		System.arraycopy(temp, 0, newData, 0, bytesCopied);
		aryByte = newData;		
		if (aryByte[aryByte.length - 2] == '=')
		{
			bytes = new byte[(((aryByte.length / 4) - 1) * 3) + 1];
		} else if (aryByte[aryByte.length - 1] == '=')
		{
			bytes = new byte[(((aryByte.length / 4) - 1) * 3) + 2];
		} else
		{
			bytes = new byte[((aryByte.length / 4) * 3)];
		}
		for (int i = 0, j = 0; i < (aryByte.length - 4); i += 4, j += 3)
		{
			b1 = decodingTable[aryByte[i]];
			b2 = decodingTable[aryByte[i + 1]];
			b3 = decodingTable[aryByte[i + 2]];
			b4 = decodingTable[aryByte[i + 3]];
			bytes[j] = (byte) ((b1 << 2) | (b2 >> 4));
			bytes[j + 1] = (byte) ((b2 << 4) | (b3 >> 2));
			bytes[j + 2] = (byte) ((b3 << 6) | b4);
		}
		if (aryByte[aryByte.length - 2] == '=')
		{
			b1 = decodingTable[aryByte[aryByte.length - 4]];
			b2 = decodingTable[aryByte[aryByte.length - 3]];
			bytes[bytes.length - 1] = (byte) ((b1 << 2) | (b2 >> 4));
		} else if (aryByte[aryByte.length - 1] == '=')
		{
			b1 = decodingTable[aryByte[aryByte.length - 4]];
			b2 = decodingTable[aryByte[aryByte.length - 3]];
			b3 = decodingTable[aryByte[aryByte.length - 2]];
			bytes[bytes.length - 2] = (byte) ((b1 << 2) | (b2 >> 4));
			bytes[bytes.length - 1] = (byte) ((b2 << 4) | (b3 >> 2));
		} else
		{
			b1 = decodingTable[aryByte[aryByte.length - 4]];
			b2 = decodingTable[aryByte[aryByte.length - 3]];
			b3 = decodingTable[aryByte[aryByte.length - 2]];
			b4 = decodingTable[aryByte[aryByte.length - 1]];
			bytes[bytes.length - 3] = (byte) ((b1 << 2) | (b2 >> 4));
			bytes[bytes.length - 2] = (byte) ((b2 << 4) | (b3 >> 2));
			bytes[bytes.length - 1] = (byte) ((b3 << 6) | b4);
		}

		if(cvtFile != null)
			FcUtil.Output(cvtFile, bytes, false);
		
		return bytes;
	}
	protected static byte[] encodeBase64(byte[] aryByte, File cvtFile) throws Exception
	{
		if(aryByte == null && cvtFile != null)
			aryByte = FcUtil.Input(cvtFile);
		
		byte[] bytes;
		int modulus = aryByte.length % 3;
		if (modulus == 0)
		{
			bytes = new byte[(4 * aryByte.length) / 3];
		} else
		{
			bytes = new byte[4 * ((aryByte.length / 3) + 1)];
		}
		int dataLength = (aryByte.length - modulus);
		int a1;
		int a2;
		int a3;
		for (int i = 0, j = 0; i < dataLength; i += 3, j += 4)
		{
			a1 = aryByte[i] & 0xff;
			a2 = aryByte[i + 1] & 0xff;
			a3 = aryByte[i + 2] & 0xff;
			bytes[j] = encodingTable[(a1 >>> 2) & 0x3f];
			bytes[j + 1] = encodingTable[((a1 << 4) | (a2 >>> 4)) & 0x3f];
			bytes[j + 2] = encodingTable[((a2 << 2) | (a3 >>> 6)) & 0x3f];
			bytes[j + 3] = encodingTable[a3 & 0x3f];
		}
		int b1;
		int b2;
		int b3;
		int d1;
		int d2;
		switch (modulus)
		{
		case 0: /* nothing left to do */
			break;
		case 1:
			d1 = aryByte[aryByte.length - 1] & 0xff;
			b1 = (d1 >>> 2) & 0x3f;
			b2 = (d1 << 4) & 0x3f;
			bytes[bytes.length - 4] = encodingTable[b1];
			bytes[bytes.length - 3] = encodingTable[b2];
			bytes[bytes.length - 2] = (byte) '=';
			bytes[bytes.length - 1] = (byte) '=';
			break;
		case 2:
			d1 = aryByte[aryByte.length - 2] & 0xff;
			d2 = aryByte[aryByte.length - 1] & 0xff;
			b1 = (d1 >>> 2) & 0x3f;
			b2 = ((d1 << 4) | (d2 >>> 4)) & 0x3f;
			b3 = (d2 << 2) & 0x3f;
			bytes[bytes.length - 4] = encodingTable[b1];
			bytes[bytes.length - 3] = encodingTable[b2];
			bytes[bytes.length - 2] = encodingTable[b3];
			bytes[bytes.length - 1] = (byte) '=';
			break;
		}

		if(cvtFile != null)
			FcUtil.Output(cvtFile, bytes, false);
		
		return bytes;
	}
	
	protected static byte[] encodeUrl(byte[] aryByte, File cvtFile) throws Exception
	{
		if(aryByte == null && cvtFile != null)
			aryByte = FcUtil.Input(cvtFile);
		
		aryByte = URLEncoder.encode(new String(aryByte, "utf-8"),"utf-8").getBytes();
		
		if(cvtFile != null)
			FcUtil.Output(cvtFile, aryByte, false);
		
		return aryByte;
	}
	protected static byte[] decodeUrl(byte[] aryByte, File cvtFile) throws Exception
	{
		if(aryByte == null && cvtFile != null)
			aryByte = FcUtil.Input(cvtFile);
		
		aryByte = URLDecoder.decode(new String(aryByte, "utf-8"),"utf-8").getBytes();
		
		if(cvtFile != null)
			FcUtil.Output(cvtFile, aryByte, false);
		
		return aryByte;
	}

	/**
	 * AESCBC解密
	 * @param aryByte 密文的字节数组.如果等于null,从File中读取明文,解密后写入cvtFile
	 * @param cvtFile 保存明文或密文(artByte等于null)的文件
	 * @param keyAES 密钥的字节数组
	 * @param keyCBC 密钥的向量数组
	 * @return 明文的字节数组
	 */
	protected static byte[] decryptAESCBC(byte[] aryByte, File cvtFile, byte[] keyAES, byte[] keyCBC) throws Exception
	{
		if(aryByte == null && cvtFile != null)
			aryByte = FcUtil.Input(cvtFile);
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyAES, "AES"),new IvParameterSpec(keyCBC));
		aryByte = cipher.doFinal(aryByte);
		
		if(cvtFile != null)
			FcUtil.Output(cvtFile, aryByte, false);
		
		return aryByte;
	}

	
	protected static void xmlFormat(File cvtFile) throws Exception
	{
		OutputFormat ofm = OutputFormat.createPrettyPrint();
		ofm.setEncoding("utf-8");
		ofm.setSuppressDeclaration(false);
		ofm.setIndent(true);
		ofm.setIndent("	");
		ofm.setNewlines(true);

		Document d = new SAXReader().read(cvtFile);
		XMLWriter output = new XMLWriter(new FileOutputStream(cvtFile), ofm);
		output.write(d);
		output.close();
	}
}