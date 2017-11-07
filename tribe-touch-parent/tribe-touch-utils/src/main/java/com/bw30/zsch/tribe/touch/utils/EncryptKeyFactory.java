package com.bw30.zsch.tribe.touch.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class EncryptKeyFactory {
	public static final String KEY_ALGORITHM = "RSA";
	public static final String DES_ALGORITHM = "DES";

	public static Key getKeyPairInstatnce() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		final int KEY_SIZE = 512;// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低（取值范围：512～2048）
		keyPairGen.initialize(KEY_SIZE);
		KeyPair keys = keyPairGen.generateKeyPair();
		PublicKey pubKey = keys.getPublic();
		PrivateKey priKey = keys.getPrivate();
		byte[] pubKeyBuffer = pubKey.getEncoded();
		byte[] priKeyBuffer = priKey.getEncoded();
		if (pubKeyBuffer != null && priKeyBuffer != null) {
			String pubKeyStr = new String(Base64.encode(pubKeyBuffer));
			String priKeyStr = new String(Base64.encode(priKeyBuffer));
			Key encryptKey = new Key(pubKeyStr, priKeyStr);
			return encryptKey;
		}
		return null;
	}

	public static String getSecretKey() throws NoSuchAlgorithmException {
		// Security.addProvider(new com.sun.crypto.provider.SunJCE());
		KeyGenerator keygen = KeyGenerator.getInstance(DES_ALGORITHM);
		SecretKey deskey = keygen.generateKey();
		byte[] keyBuffer = deskey.getEncoded();
		return new String(Base64.encode(keyBuffer));
	}
}
