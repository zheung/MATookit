package danor.matookit.utils;

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

import java.util.Base64;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class UConvert
{
	/**
	 * AES解密
	 * @param aryByte 密文的字节数组.如果等于null,从File中读取明文,解密后写入cvtFile
	 * @param cvtFile 保存明文或密文(artByte等于null)的文件
	 * @param keyAES 密钥的字节数组
	 * @return 明文的字节数组
	 */
	public static byte[] decryptAES(byte[] aryByte, File cvtFile, byte[] keyAES) throws Exception
	{
		if(aryByte == null && cvtFile != null)
			aryByte = UUtil.Input(cvtFile);
		
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyAES, "AES"));
		aryByte = cipher.doFinal(aryByte);
		
		if(cvtFile != null)
			UUtil.Output(cvtFile, aryByte, false);
		
		return aryByte;
	}
	/**
	 * AES加密
	 * @param aryByte 明文的字节数组.如果等于null,从File中读取明文,加密后写入cvtFile
	 * @param cvtFile 保存明文(artByte等于null)或密文的文件
	 * @param keyAES 密钥的字节数组
	 * @return 密文的字节数组
	 */
	public static byte[] encryptAES(byte[] aryByte, File cvtFile, byte[] keyAES) throws Exception
	{
		if(aryByte == null && cvtFile != null)
			aryByte = UUtil.Input(cvtFile);
		
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyAES, "AES"));
		aryByte = cipher.doFinal(aryByte);
		
		if(cvtFile != null)
			UUtil.Output(cvtFile, aryByte, false);
		
		return aryByte;
	}
	public static byte[] generateKeyAES() throws Exception
	{
		KeyGenerator keygen = KeyGenerator.getInstance("AES");  
		keygen.init(128, SecureRandom.getInstance("SHA1PRNG"));
		
		return keygen.generateKey().getEncoded();
	}
	
	public static byte[] decryptRSA(byte[] aryByte, File cvtFile, byte[] keyRSA64) throws Exception
	{
		if(aryByte == null && cvtFile != null)
			aryByte = UUtil.Input(cvtFile);
		
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(UConvert.decodeBase64(keyRSA64, null))));
		aryByte = cipher.doFinal(aryByte);
		
		if(cvtFile != null)
			UUtil.Output(cvtFile, aryByte, false);
		
		return aryByte;
	}
	public static byte[] encryptRSA(byte[] aryByte, File cvtFile, byte[] keyRSA64) throws Exception
	{
		if(aryByte == null && cvtFile != null)
			aryByte = UUtil.Input(cvtFile);
		
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(UConvert.decodeBase64(keyRSA64, null))));
		aryByte = cipher.doFinal(aryByte);
		
		if(cvtFile != null)
			UUtil.Output(cvtFile, aryByte, false);
		
		return aryByte;
	}
	
	public static byte[] decodeBase64(byte[] aryByte, File cvtFile) throws Exception
	{
		if(aryByte == null && cvtFile != null)
			aryByte = UUtil.Input(cvtFile);

		byte[] bytes = Base64.getDecoder().decode(aryByte);

		if(cvtFile != null)
			UUtil.Output(cvtFile, bytes, false);
		
		return bytes;
	}
	public static byte[] encodeBase64(byte[] aryByte, File cvtFile) throws Exception
	{
		if(aryByte == null && cvtFile != null)
			aryByte = UUtil.Input(cvtFile);
		
		byte[] bytes = Base64.getEncoder().encode(aryByte);

		if(cvtFile != null)
			UUtil.Output(cvtFile, bytes, false);
		
		return bytes;
	}
	
	public static byte[] encodeUrl(byte[] aryByte, File cvtFile) throws Exception
	{
		if(aryByte == null && cvtFile != null)
			aryByte = UUtil.Input(cvtFile);
		
		aryByte = URLEncoder.encode(new String(aryByte, "utf-8"),"utf-8").getBytes();
		
		if(cvtFile != null)
			UUtil.Output(cvtFile, aryByte, false);
		
		return aryByte;
	}
	public static byte[] decodeUrl(byte[] aryByte, File cvtFile) throws Exception
	{
		if(aryByte == null && cvtFile != null)
			aryByte = UUtil.Input(cvtFile);
		
		aryByte = URLDecoder.decode(new String(aryByte, "utf-8"),"utf-8").getBytes();
		
		if(cvtFile != null)
			UUtil.Output(cvtFile, aryByte, false);
		
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
	public static byte[] decryptAESCBC(byte[] aryByte, File cvtFile, byte[] keyAES, byte[] keyCBC) throws Exception
	{
		if(aryByte == null && cvtFile != null)
			aryByte = UUtil.Input(cvtFile);
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyAES, "AES"),new IvParameterSpec(keyCBC));
		aryByte = cipher.doFinal(aryByte);
		
		if(cvtFile != null)
			UUtil.Output(cvtFile, aryByte, false);
		
		return aryByte;
	}
	
	public static void xmlFormat(File cvtFile) throws Exception
	{
		String content = new String(UUtil.Input(cvtFile), "utf-8").replaceAll("&#10;", "|").replaceAll("&", "^");
		UUtil.Output(cvtFile, content.getBytes("utf-8"), false);
		
		Document d = new SAXReader().read(cvtFile);
		XMLWriter output = new XMLWriter(new FileOutputStream(cvtFile), UXml.ofm);
		output.write(d);
		output.close();
	}
}