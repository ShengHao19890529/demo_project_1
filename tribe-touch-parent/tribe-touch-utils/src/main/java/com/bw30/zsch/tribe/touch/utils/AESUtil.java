package com.bw30.zsch.tribe.touch.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 *
 */
public class AESUtil {
	private final static Logger logger = Logger.getLogger(AESUtil.class);

	public static String Encrypt(String sSrc, String sKey) {
		if (sKey == null) {
			System.out.print("为空null");
			return null;
		}
		if (sKey.length() != 16) {
			System.out.print("长度不是16位");
			return null;
		}
		byte[] raw;
		byte[] encrypted = null;
		try {
			raw = sKey.getBytes("utf-8");

			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.toString(), e);
		} catch (IllegalBlockSizeException e) {
			logger.error(e.toString(), e);
		} catch (BadPaddingException e) {
			logger.error(e.toString(), e);
		} catch (InvalidKeyException e) {
			logger.error(e.toString(), e);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.toString(), e);
		} catch (NoSuchPaddingException e) {
			logger.error(e.toString(), e);
		}
		return new Base64().encodeToString(encrypted);
	}

	public static String Decrypt(String sSrc, String sKey) {
		try {
			if (sKey == null) {
				System.out.print("为空null");
				return null;
			}
			if (sKey.length() != 16) {
				System.out.print("长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = new Base64().decode(sSrc);
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original, "utf-8");
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		/*
		 * 此处使用AES-128-ECB加密模式，key需要为16位。
		 */
		String cKey = "hnair1234567890!";
		// 需要加密的字串
		String cSrc = "liuxing";
		System.out.println(cSrc);
		// 加密
		String enString = AESUtil.Encrypt(cSrc, cKey);
		System.out.println("加密后的字串是：" + enString);

		// 解密
		String DeString = AESUtil.Decrypt(enString, cKey);
		System.out.println("解密后的字串是：" + DeString);
	}
}
