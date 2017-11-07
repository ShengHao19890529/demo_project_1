package com.bw30.zsch.tribe.touch.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class EncryptUtil {

	public static final String KEY_ALGORITHM = "RSA";
	public static final String DES_ALGORITHM = "DES";

	/**
	 * @Title: encryptByPublicKey @Description: TODO(应用公钥加密) @param src明文
	 *         (最大53字节) @param pubKey公钥 @return 设定文件 @return String 返回类型 @throws
	 *         NoSuchAlgorithmException @throws InvalidKeySpecException @throws
	 *         NoSuchPaddingException @throws InvalidKeyException @throws
	 *         BadPaddingException @throws IllegalBlockSizeException @throws
	 */
	public static String encryptByPublicKey(String src, String pubKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		if (src == null || pubKey == null) {
			return null;
		}
		// 取得私钥
		X509EncodedKeySpec pkcs8KeySpec = new X509EncodedKeySpec(Base64.decode(pubKey));
		java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(pkcs8KeySpec);
		// 读入加密信息
		byte[] srcBuffer = src.getBytes(Charset.defaultCharset());
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return new String(Base64.encode(cipher.doFinal(srcBuffer)));
	}

	/**
	 * @Title: decryptByPrivateKey @Description: TODO(应用私鈅解密) @param
	 *         encrypted密文 @param priKey私钥 @return @throws
	 *         NoSuchAlgorithmException @throws InvalidKeySpecException @throws
	 *         NoSuchPaddingException @throws InvalidKeyException @throws
	 *         IllegalBlockSizeException @throws BadPaddingException设定文件 @return
	 *         String 返回类型 @throws
	 */
	public static String decryptByPrivateKey(String encrypted, String priKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		if (encrypted == null || priKey == null) {
			return null;
		}
		// 读取公鈅
		java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance(KEY_ALGORITHM);
		PKCS8EncodedKeySpec pubKeySpec = new PKCS8EncodedKeySpec(Base64.decode(priKey));
		PrivateKey privateKey = keyFactory.generatePrivate(pubKeySpec);
		// 读入加密信息
		byte[] encryptedBuffer = Base64.decode(encrypted);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return new String(cipher.doFinal(encryptedBuffer));
	}

	public static String encryptBySymmetry(String src, String deskey)
			throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		if (src == null || deskey == null) {
			return null;
		}
		// 读取密钥
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
		DESKeySpec dks = new DESKeySpec(PayCenterBase64.decode(deskey.getBytes("UTF-8")));
		SecretKey key = keyFactory.generateSecret(dks);
		// 读入原文
		byte[] encryptBuffer;
		try {
			encryptBuffer = src.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			encryptBuffer = src.getBytes(Charset.defaultCharset());
		}
		Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return new String(Base64.encode(cipher.doFinal(encryptBuffer)));
	}

	public static String decryptBySymmetry(String encrypted, String deskey)
			throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		if (encrypted == null || deskey == null) {
			return null;
		}
		// 读取密钥
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
		DESKeySpec dks = new DESKeySpec(Base64.decode(deskey));
		SecretKey key = keyFactory.generateSecret(dks);
		// 读入密文
		byte[] encryptBuffer = Base64.decode(encrypted);
		Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		return new String(cipher.doFinal(encryptBuffer), "GBK");
	}
}
