package com.bw30.zsch.tribe.touch.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bw30.aes.Md5Encrypt;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Damon
 * @Sep 16, 2014
 * @version 1.0
 * @desc PayMent Controller 工具类
 */
public class PayUtil {

	/**
	 * 校验签名
	 * 
	 * @param obj
	 *            待校验对象
	 * @param key
	 *            验签公钥
	 * @param sign
	 *            签名
	 * @return
	 */
	public static boolean checkMysign(final JSONObject obj, final String key, final String sign) {
		String s = createLinkString(obj, true);
		String mysign = Md5Encrypt.md5Enc(s);// MD5编码
		return RSASignature.doCheck(mysign, sign, key);
	}

	public static boolean checkMysign2(final JSONObject obj, final String key, final String sign) {
		JSONObject resultJsonObj = new JSONObject();
		sortForFieldName(resultJsonObj, obj, null);
		return checkMysign(resultJsonObj, key, sign);
	}

	public static String buildMysign2(final JSONObject obj, final String key) {
		JSONObject resultJsonObj = new JSONObject();
		sortForFieldName(resultJsonObj, obj, null);
		return buildMysign(resultJsonObj, key);
	}

	public static void sortForFieldName(JSONObject resultJsonObj, JSONObject obj, String prefixKey) {
		if (obj == null) {
			return;
		}

		List<String> keyList = new ArrayList<>(obj.keySet());
		for (String key : keyList) {

			if ("sign".equals(key)) {
				continue;
			}

			Object o = obj.get(key);
			String currKey = StringUtils.isEmpty(prefixKey) ? key : prefixKey + "-" + key;
			if (o instanceof String) {
				resultJsonObj.put(currKey, (String) o);
			} else if (o instanceof JSONObject) {
				sortForFieldName(resultJsonObj, (JSONObject) o, currKey);
			} else {
				resultJsonObj.put(currKey, o);
			}
		}
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            参与字符拼接的参数Map
	 * @param ifSort
	 *            是否需要排序
	 * @return 拼接后字符串
	 */
	public static String createLinkString(final JSONObject obj, final boolean ifSort) {
		// 取出键集后排序
		List<String> keys = null;
		keys = new ArrayList<String>(obj.keySet());
		if (ifSort) {
			Collections.sort(keys);
		}
		String prestr = "";
		// 依次取出
		for (String key : keys) {
			// 签名字段及空值不参与签名
			if (key.equals("sign"))
				continue;
			String value = obj.getString(key);
			if (StringUtils.isBlank(value))
				continue;
			prestr = prestr + key + "=" + value + "&";
		}
		prestr = prestr.substring(0, prestr.length() - 1);
		return prestr;
	}

	/**
	 * 生成签名
	 * 
	 * @param sArray
	 *            要签名的数组
	 * @param aliMD5Private
	 *            支付宝MD5密钥
	 * @return 签名结果字符串
	 */
	public static String buildMysign(final JSONObject obj, final String key) {
		String mysign = Md5Encrypt.md5Enc(createLinkString(obj, true));// MD5编码
		return RSASignature.signIt(mysign, key);
	}

}
