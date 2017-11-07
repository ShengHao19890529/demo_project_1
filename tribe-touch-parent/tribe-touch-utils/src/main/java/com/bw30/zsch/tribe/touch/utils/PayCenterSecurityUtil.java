package com.bw30.zsch.tribe.touch.utils;

public class PayCenterSecurityUtil {

	/**
	 * @Title: generatorSignKeyPair @Description: (获取数字签名密钥对) @param @return
	 *         设定文件 @return Key 返回类型 @throws
	 */
	public static Key generatorSignKeyPair() {
		Key signKey = null;
		try {
			signKey = SignKeyFactory.getKeyPairInstatnce();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signKey;
	}

	/**
	 * @Title: generatorEncryptKeyPair @Description: (获取非对称加密密钥对) @return
	 *         设定文件 @return Key 返回类型 @throws
	 */
	public static Key generatorEncryptKeyPair() {
		Key encryptKey = null;
		try {
			encryptKey = EncryptKeyFactory.getKeyPairInstatnce();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptKey;
	}

	/**
	 * @Title: generatorSecretKey @Description: (获取DES密钥) @return 设定文件 @return
	 *         String 返回类型 @throws
	 */
	public static String generatorSecretKey() {
		String secretKey = null;
		try {
			secretKey = EncryptKeyFactory.getSecretKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return secretKey;
	}

	/**
	 * @Title: sign @Description: (通过私鈅进行进行签名) @param src源文 @param
	 *         priKey私鈅 @return 返回签名数据 @return String 返回类型 @throws
	 */
	public static String sign(String src, String priKey) {
		String signed = null;
		try {
			signed = SignUtil.sign(src, priKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signed;
	}

	/**
	 * @Title: verify @Description: (根据公钥对原文、签名信息进行校验) @param src原文 @param
	 *         signed签名数据 @param pubKey公钥 @return 设定文件 @return boolean
	 *         返回类型 @throws
	 */

	public static boolean verify(String src, String signed, String pubKey) {
		boolean isOk = false;
		try {
			isOk = SignUtil.verify(src, signed, pubKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isOk;
	}

	/**
	 * @Title: encryptByPublicKey @Description: (通过公钥对原文进行加密) @param
	 *         src原文 @param pubKey公钥 @return 加密数据 @return String 返回类型 @throws
	 */
	public static String encryptByPublicKey(String src, String pubKey) {
		String encStr = null;
		try {
			encStr = EncryptUtil.encryptByPublicKey(src, pubKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encStr;
	}

	/**
	 * @Title: decryptByPrivateKey @Description: (通过私鈅对密文进行解密) @param encrypted
	 *         密文 @param priKey私鈅 @return 原文 @return String 返回类型 @throws
	 */
	public static String decryptByPrivateKey(String encrypted, String priKey) {
		String srcStr = null;
		try {
			srcStr = EncryptUtil.decryptByPrivateKey(encrypted, priKey);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return srcStr;
	}

	/**
	 * @Title: encryptBySymmetry @Description: (用对称密钥对原文进行加密) @param
	 *         src原文 @param deskey密钥 @return 密文 @return String 返回类型 @throws
	 */
	public static String encryptBySymmetry(String src, String deskey) {
		String encStr = null;
		try {
			// encStr = EncryptUtil.encryptBySymmetry(src, deskey);
			encStr = DES.encode(src, deskey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encStr;
	}

	/**
	 * @Title: decryptBySymmetry @Description: (用对称密钥对密文进行解密) @param
	 *         encrypted密文 @param deskey对称密钥 @return 原文 @return String
	 *         返回类型 @throws
	 */
	public static String decryptBySymmetry(String encrypted, String deskey) {
		String srcStr = null;
		try {
			// srcStr = EncryptUtil.decryptBySymmetry(encrypted, deskey);
			// TODO 直接写到外面去 只包装一层就好了
			srcStr = DES.decode(encrypted, deskey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return srcStr;
	}
}
