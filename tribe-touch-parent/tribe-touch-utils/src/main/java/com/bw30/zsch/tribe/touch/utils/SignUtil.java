package com.bw30.zsch.tribe.touch.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class SignUtil {
	public static final String KEY_ALGORITHM = "DSA";

	/**
	 * 
	 * @Title: sign @Description: TODO(根据私鈅将原文签名) @param @param content
	 *         源信息 @param @param priKey 私鈅 @param @return 签名后的信息 @param @throws
	 *         IOException @param @throws ClassNotFoundException @param @throws
	 *         NoSuchAlgorithmException @param @throws
	 *         InvalidKeyException @param @throws SignatureException
	 *         设定文件 @return String 返回类型 @throws InvalidKeySpecException @throws
	 */
	public static String sign(String src, String priKey) throws IOException, ClassNotFoundException,
			NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException {
		if (src == null || priKey == null) {
			return null;
		}
		// 读取私鈅
		PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(Base64.decode(priKey));
		java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey myprikey = keyFactory.generatePrivate(priKeySpec);
		// in.close();
		// 初始一个Signature对象,并用私钥对信息签名
		Signature signet = Signature.getInstance(KEY_ALGORITHM);
		signet.initSign(myprikey);
		signet.update(src.getBytes(Charset.defaultCharset()));
		// 签名
		byte[] signed = signet.sign();
		// 将签名以Base64方式保存
		byte[] signedBase64 = PayCenterBase64.encode(signed);
		return new String(signedBase64);
	}

	public static boolean verify(String src, String signed, String pubKey) throws IOException, ClassNotFoundException,
			NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidKeySpecException {
		if (src == null || signed == null || pubKey == null) {
			return false;
		}
		// 读取公鈅
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.decode(pubKey));
		java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey mypubkey = keyFactory.generatePublic(pubKeySpec);
		// 读入签名信息
		byte[] signedBuffer = Base64.decode(signed);
		// 初始一个Signature对象,并用公钥和签名进行验证
		Signature signetcheck = Signature.getInstance(KEY_ALGORITHM);
		signetcheck.initVerify(mypubkey);
		signetcheck.update(src.getBytes(Charset.defaultCharset()));
		return signetcheck.verify(signedBuffer);
	}
}
