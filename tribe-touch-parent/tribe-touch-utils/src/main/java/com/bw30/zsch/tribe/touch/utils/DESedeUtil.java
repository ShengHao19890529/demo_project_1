package com.bw30.zsch.tribe.touch.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESedeUtil {

	// 向量
	private final static String iv = "01234567";

	/**
	 * 生成desedekey
	 */
	public static String createDesedeKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
		keyGenerator.init(168);
		SecretKey secretKey = keyGenerator.generateKey();
		byte[] keyByteArr = secretKey.getEncoded();
		String key = new String(Base64.encode(keyByteArr));
		return key;
	}

	public static String encryptThreeDESECB(String data, String key, String encoding) throws Exception {
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(encoding));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, securekey, ips);
		byte[] b = cipher.doFinal(data.getBytes(encoding));
		return Base64.encode(b);
	}

	// 3DESECB解密,key必须是长度大于等于 3*8 = 24 位
	public static String decryptThreeDESECB(String data, String key, String encoding) throws Exception {
		// --通过base64,将字符串转成byte数组
		byte[] bytesrc = Base64.decode(data);
		// --解密的key
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(encoding));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);
		// --Chipher对象解密
		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, securekey, ips);
		byte[] retByte = cipher.doFinal(bytesrc);

		return new String(retByte, encoding);
	}

}
