package org.danor.matookit;

import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class FcParam
{
	protected final Map<String, byte[]> map = new TreeMap<String, byte[]>();
	protected final boolean isLogin;
	protected final FcDataBase db;
	
	public FcParam(boolean isLogin, FcDataBase db)
	{
		this.isLogin = isLogin;
		this.db = db;
	}
	
	protected FcParam put(String pKey, byte[] pValue)
	{
		map.put(pKey, pValue);
		return this;
	}
	protected String get() throws Exception
	{
		byte[] keyAES = FcConvert.generateKeyAES();
		String strKeyRSA = db.Data("Cipher",7)[2]+db.Data("Cipher",7)[3];
		byte[] keyRSA = (strKeyRSA).getBytes();
		Cipher cipherRSA = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipherRSA.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(FcConvert.decodeBase64(keyRSA, null))));
		
		StringBuilder param = new StringBuilder().append("K=").append(new String(FcConvert.encodeUrl(FcConvert.encodeBase64(cipherRSA.doFinal(FcConvert.encodeBase64(keyAES, null)), null), null), "utf-8")).append("%0A&");
		
		for (Entry<String, byte[]> p:this.map.entrySet()) 
		{
			String value;
			
			Cipher cipherA = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipherA.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyAES, "AES"));
			
			byte[] valueA64 = FcConvert.encodeBase64(cipherA.doFinal(p.getValue()), null);
			
			value = new String(FcConvert.encodeUrl(isLogin?FcConvert.encodeBase64(cipherRSA.doFinal(valueA64), null):valueA64, null), "utf-8");

			param.append(p.getKey()).append("=").append(value).append("%0A&");
		}
		
		return param.substring(0, param.length() - 1);
	}
	protected String get(boolean isDecrypt) throws Exception
	{
		if(isDecrypt)
			return get();
		else
		{
			StringBuilder param = new StringBuilder().append("K=").append("&");

			for (Entry<String, byte[]> p:this.map.entrySet()) 
				param.append(p.getKey()).append("=").append(new String(p.getValue())).append("&");

			return param.substring(0, param.length() - 1);
		}
	}
}