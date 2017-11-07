package com.bw30.zsch.tribe.touch.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class SignKeyFactory {
	public static Key getKeyPairInstatnce() throws NoSuchAlgorithmException {
		KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");
		keygen.initialize(512);
		KeyPair keys = keygen.generateKeyPair(); // 生成密钥组
		PublicKey pubKey = keys.getPublic();
		PrivateKey priKey = keys.getPrivate();
		byte[] pubKeyBuffer = pubKey.getEncoded();
		byte[] priKeyBuffer = priKey.getEncoded();

		if (pubKeyBuffer != null && priKeyBuffer != null) {
			String pubKeyStr = new String(Base64.encode(pubKeyBuffer));
			String priKeyStr = new String(Base64.encode(priKeyBuffer));
			Key signKey = new Key(pubKeyStr, priKeyStr);
			return signKey;
		}
		return null;
	}
}
