package com.bw30.zsch.tribe.touch.utils;

public class Key {
	/**
	 * 公钥
	 */
	private String pubKey;
	/**
	 * 私钥
	 */
	private String priKey;

	public Key(String pubKey, String priKey) {
		this.pubKey = pubKey;
		this.priKey = priKey;
	}

	public String getPubKey() {
		return pubKey;
	}

	public String getPriKey() {
		return priKey;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("pubKey:" + pubKey);
		builder.append('\n');
		builder.append("priKey:" + priKey);
		return builder.toString();
	}
}
