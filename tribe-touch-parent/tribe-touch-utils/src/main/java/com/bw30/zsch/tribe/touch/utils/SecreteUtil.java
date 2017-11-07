package com.bw30.zsch.tribe.touch.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.bw30.aes.Base64;
import org.bw30.aes.DES;
import org.bw30.aes.EncryptUtil;
import org.bw30.aes.RSAEncrypt;

import com.alibaba.fastjson.JSONObject;

/**
 * 加密工具
 * 
 * @author ShengHao
 *
 *         2016年11月21日 - 上午10:02:55
 */
public class SecreteUtil {

	private final static String CHARSET = "UTF-8";

	/**
	 * 生成DES算法的密钥
	 */
	public static String createDesSecretKey() throws Exception {
		String secretKey = new String(Base64.encode(DES.generatorDESKey().getBytes(CHARSET)), CHARSET);
		return secretKey;
	}

	/**
	 * 使用DES算法对参数做加密操作
	 */
	public static String secretByDesKey(String data, String secretKey) throws Exception {
		String secretContent = EncryptUtil.encryptBySymmetry(data, secretKey);
		return secretContent;
	}

	/**
	 * 使用DES算法对参数做解密操作
	 */
	public static String decretByDesKey(String data, String secretKey) throws Exception {
		String content = EncryptUtil.decryptBySymmetry(data, secretKey);
		return content;
	}

	/**
	 * 使用RSA算法做公钥加密
	 */
	public static String secretByRsaPublicKey(String data, String publicKey) throws Exception {
		RSAEncrypt rsaEncrypt = new RSAEncrypt();
		rsaEncrypt.loadPublicKey(publicKey);
		String secretContent = new String(
				Base64.encode(RSAEncrypt.encrypt(rsaEncrypt.getPublicKey(), data.getBytes(CHARSET))), CHARSET);
		return secretContent;
	}

	public static String decretByRsaPrivateKey(String data, String privateKey) throws Exception {
		// PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new
		// PKCS8EncodedKeySpec(Hex.decode(privateKey));
		// KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		// PrivateKey pKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		// Cipher cipher = Cipher.getInstance("RSA");
		// cipher.init(Cipher.DECRYPT_MODE, pKey);
		// byte[] output = cipher.doFinal(data.getBytes());
		// return new String(Hex.encode(output));
		String secretContent = EncryptUtil.decryptByPrivateKey(data, privateKey);
		return secretContent;

	}

	public static String urlEncode(String content) {
		String str = "";
		try {
			str = URLEncoder.encode(content, CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 使用RSA算法做私钥加密
	 */
	public static void main2(String[] args) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("a", "1");
		jsonObject.put("b", "2");
		jsonObject.put("c", "3");

		// openssl生成的RSA秘钥
		String opensslPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALc/SLaMqRxaEUS4EiYFGOEtGViBNJq+N4KcfJ8jDGfPKxzmMeh43YZ+3AUonRaXsEUfRTcTaC7/s4AfwcxVzf12y8GCbTZqvfvM8dgzYUaAnt40MRssd0DCCMVIlfZru+ECBUhMP/GaVJp8UbgWSSzGxKTJ3OdxyT0DvsxiO/pRAgMBAAECgYBv2V7GngCy337e1QOu1TmkZ+SqkuLK+QPk4yNl9vyheGA2WYNpBC0i5NfjBjEsExZyem3C+kODPGwR7lBaKp156Cv5dboLhDHDqzbm/j//hsUZHSO1kniDN/PKAwE2PiCO+v6QUxxHUPk+wRXd0R1KXvqvZ52T9Noj5Hr9kP+ogQJBAPJ0Ibe2HHVE1yVf56bHbs3HohTQ+5EMwVsOKeTyOnjZa0zpDiXiKt8j0R9dXBjVkhNwlfj7NhXzz2rsIx92cqkCQQDBfE88DoafuNv5OfFV3VJ9oTwvXpH33YB8uzzSjQ77gZmWQQEKjKhZrnPSp0nirlgG43wr9+cGEIfiBEv6qjtpAkEAwGznLdU9dN00HBen1ThHes7MltTeNFr5QvlUyr5tJej5PkthzTNZb0wP92Kza4plq0QZ40gNvxKINoY/wgLSMQJBAI5fSm6GwckF3demK09jbC/FVOQGUqJw+vrNTBQid8PZuYo2iu2YUsiq9c8sCdIjN+LZwaUY5YYw48wMHVx1pwECQAIeqT6Hr5EJbfhEe6QuLtfZxr2bmpG8pIP3SM/EeJ+R1p0kb4xGDzqqPjbIVbh6uUF0mlEdrVk4wFs/Ai4QmE0=";
		String opensslPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3P0i2jKkcWhFEuBImBRjhLRlYgTSavjeCnHyfIwxnzysc5jHoeN2GftwFKJ0Wl7BFH0U3E2gu/7OAH8HMVc39dsvBgm02ar37zPHYM2FGgJ7eNDEbLHdAwgjFSJX2a7vhAgVITD/xmlSafFG4FkksxsSkydzncck9A77MYjv6UQIDAQAB";

		String signature = PayUtil.buildMysign(jsonObject, opensslPrivateKey);
		System.out.println(signature);
		System.out.println(PayUtil.checkMysign(jsonObject, opensslPublicKey, signature));

	}

}
