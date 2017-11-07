package com.bw30.zsch.tribe.touch.utils;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;

import java.nio.charset.Charset;
import java.security.Security;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class DES {
	private String Algorithm = "DES";
	private KeyGenerator keygen;
	private SecretKey deskey;
	private Cipher c;
	private byte[] cipherByte;

	/**
	 * 初始化 DES 实例
	 */
	public DES() {
		init();
	}

	@SuppressWarnings("restriction")
	public void init() {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		try {
			keygen = KeyGenerator.getInstance(Algorithm);
			deskey = keygen.generateKey();
			c = Cipher.getInstance(Algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对 String 进行加密
	 *
	 * @param str
	 *            要加密的数据
	 * @return 返回加密后的 byte 数组
	 */
	public byte[] createEncryptor(String str) {
		try {
			c.init(Cipher.ENCRYPT_MODE, deskey);
			cipherByte = c.doFinal(str.getBytes(Charset.defaultCharset()));
		} catch (java.security.InvalidKeyException e) {
			e.printStackTrace();
		} catch (javax.crypto.BadPaddingException e) {
			e.printStackTrace();
		} catch (javax.crypto.IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		return cipherByte;
	}

	/**
	 * 对 Byte 数组进行解密
	 *
	 * @param buff
	 *            要解密的数据
	 * @return 返回加密后的 String
	 */
	public String createDecryptor(byte[] buff) {
		try {
			c.init(Cipher.DECRYPT_MODE, deskey);
			cipherByte = c.doFinal(buff);
		} catch (java.security.InvalidKeyException e) {
			e.printStackTrace();
		} catch (javax.crypto.BadPaddingException e) {
			e.printStackTrace();
		} catch (javax.crypto.IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		return (new String(cipherByte));
	}

	/**
	 * 使用key对str加密并base64编码后返回
	 * 
	 * @param str
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encode(String str, String key) throws Exception {
		SecureRandom sr = new SecureRandom();
		byte[] rawKey = PayCenterBase64.decode(key.getBytes("UTF-8"));

		DESKeySpec dks = new DESKeySpec(rawKey);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(dks);

		javax.crypto.Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

		byte data[] = str.getBytes("UTF-8");
		byte encryptedData[] = cipher.doFinal(data);
		return new String(PayCenterBase64.encode(encryptedData), "UTF-8");
	}

	/**
	 * 首先进行base64解码再使用key对str解密
	 *
	 * @param str
	 *            加密串
	 * @param key
	 *            key
	 * @return decode
	 * @throws Exception
	 *             Exception
	 */
	public static String decode(String str, String key) throws Exception {
		SecureRandom sr = new SecureRandom();
		byte[] rawKey = PayCenterBase64.decode(key.getBytes("UTF-8"));
		DESKeySpec dks = new DESKeySpec(rawKey);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
		byte encryptedData[] = PayCenterBase64.decode(str.getBytes("UTF-8"));
		byte decryptedData[] = cipher.doFinal(encryptedData);
		return new String(decryptedData, "UTF-8");
	}

	/**
	 * 生成 DESKey
	 *
	 * @return DESKey 一字符串形式保存
	 * @throws java.security.NoSuchAlgorithmException
	 *             该算法不存在
	 */

	public static String generatorDESKey() throws NoSuchAlgorithmException {
		KeyGenerator keygen = KeyGenerator.getInstance("DES");
		SecretKey DESKey = keygen.generateKey();
		return new String(DESKey.getEncoded());

	}

}
